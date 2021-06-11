package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;
import com.mygdx.game.Screens.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//Temp class to test out how I'll build sprites
public class TempSprite extends Sprite {
    private World world;
    private Main game;

    private Body body;

    private TextureRegion region;
    private int coordX, coordY;
    private float totalDeltaTime;

    public ScreenDisplay getScreen() {
        return screen;
    }

    private ScreenDisplay screen;
    private ArrayList<Tile> currPathing;



    public TempSprite(World world, Main game, float[] pos, ScreenDisplay screenDisplay){
        super();
        this.world = world;
        this.game = game;
        this.screen = screenDisplay;
        totalDeltaTime = 0;
        coordX = 0;
        coordY = 0;
        setPosition(pos[0], pos[1]);
        setBounds(getX(), getY(),16,16);
        currPathing = new ArrayList<>();
        region = new TextureRegion(new Texture("Stairs.png"));
        setRegion(region);
        defineBody();
        locate(body.getPosition().x,body.getPosition().y);
    }

    /**
     * Creates the Body and Fixture of the sprite
     */
    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8,8);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

    }

    public void update(float delta) {
            move(delta);
            this.setPosition(body.getPosition().x - getWidth() / 2f,body.getPosition().y - getHeight() / 2f);
            if(currPathing.isEmpty()){
                moveAround();
            }
    }

    /**
     * Chooses a location on the map to move to if reached target destination
     */
    public void moveAround() {
            Random random = new Random();
            int x = random.nextInt(35);
            int y = random.nextInt(24);
            if(game.getLayout() != null) {
                if (!game.getLayout()[y][x].isObstacle()) {
                    calcSteps(x, y);
                }
            }
    }

    /**
     * Move according to the currPathing Arraylist
     * @param delta : delta time
     */
    public void move(float delta) {
        if(currPathing.size() > 0) {
            Tile nextStep = currPathing.get(0);
            //Using linear velocity
            Vector2 fv = new Vector2((nextStep.getX()-coordX)*20, (coordY-nextStep.getY())*20);
            body.setLinearVelocity(fv);
            setPosition(body.getPosition().x,body.getPosition().y);
            totalDeltaTime += delta;
            if(totalDeltaTime >= 0.8f){
                locate(body.getPosition().x, body.getPosition().y);
                totalDeltaTime = 0;
                currPathing.remove(0);
            }
        }
        else{
            body.setLinearVelocity(0,0);
        }
    }
    /**
     * Algorithm to calculate the optimal path from current location to endX,endY using A* algorithm
     *
     * @param endX : Ending X coordinate on the mapped out 2d Array
     * @param endY : Ending Y coordinate on the mapped out 2d Array
     * @return ArrayList of the Tiles that should be passed sequentially
     */
    public ArrayList<Tile> calcSteps(int endX, int endY){
        ArrayList<Tile> openSet = new ArrayList<>();
        ArrayList<Tile> closedSet = new ArrayList<>();
        Tile pos = game.getLayout()[coordY][coordX];
        Tile end = game.getLayout()[endY][endX];
        currPathing.clear();

        //define start (current position)
        locate(body.getPosition().x,body.getPosition().y);
        openSet.add(pos);
    //A* algorithm
        while (openSet.size() > 0){
            //check value with lowest F(n)
            int windex = 0;
            for(int i = 0; i < openSet.size(); i++){
                if(openSet.get(0).getF() < openSet.get(windex).getF()){
                    windex = i;
                }
            }
            //Store best move in variable
            Tile best = openSet.get(windex);
            //check if current is the end goal
            if(best.equals(end)){
                ArrayList<Tile> pathing = new ArrayList<>();
                Tile trace = best;
                while(trace!= pos) {
                    //bug fix, change the path tile colours
                    //trace.getTile().setTextureRegion(new TextureRegion(new Texture("Stairs.png")));
                    pathing.add(trace);
                    trace = trace.getParent();
                }
                Collections.reverse(pathing);
                currPathing = pathing;
                return pathing;
            }
            //Remove from possible searches
            openSet.remove(windex);
            //Add to confirmed searches
            closedSet.add(best);
            //Check the neighboring tiles
            ArrayList<Tile> neigbors = getNeighbors(best);
            for(Tile neigbor : neigbors) {
                    //if the neighbor isn't already checked to be the best
                    if (!closedSet.contains(neigbor) && !neigbor.isObstacle()) {
                        //update G value
                        float tempG = best.getG() + 1;
                        //If you have already flagged the neighbor before
                        if (openSet.contains(neigbor)) {
                            //If this path to this neighbor is shorter than the previously found one
                            if (tempG < neigbor.getG()) {
                                //update G
                                neigbor.setG(tempG);
                            }
                        } else {
                            //Else add G score and add Neighbor to be checked later
                            neigbor.setG(tempG);
                            openSet.add(neigbor);
                        }
                        //Heuristic is just finding number of moves till the end
                        neigbor.setH(heuristic(neigbor, end));
                        //Set the Full score of number of actions + actual distance
                        neigbor.setF(neigbor.getG() + neigbor.getH());
                        //Set the parent Tile
                        neigbor.setParent(best);
                    }
                }
            }
        //If everything is searched but no way to get to destination then there's an error
        return null;
    }
    //Create an ArrayList of Tiles that are adjacent to origin

    /**
     * Create an arrayList of neighboring tiles if there is
     * @param origin : center point of all the neighbors
     * @return Arraylist of the neighboring tiles
     */
    public ArrayList<Tile> getNeighbors(Tile origin) {
        ArrayList<Tile> TBR = new ArrayList<>();
        if (origin.getY() != 0){
            Tile n1 = game.getLayout()[origin.getY() - 1][origin.getX()];
            TBR.add(n1);
        }
        if (origin.getY() < 23) {
            Tile n2 = game.getLayout()[origin.getY() + 1][origin.getX()];
            TBR.add(n2);
        }
        if (origin.getX() != 0) {
            Tile n3 = game.getLayout()[origin.getY()][origin.getX() - 1];
            TBR.add(n3);
        }
        if (origin.getX() < 34) {
            Tile n4 = game.getLayout()[origin.getY()][origin.getX() + 1];
            TBR.add(n4);
        }
        return TBR;
    }

    /**
     * Manhattan distance between two points in the array
     * @param point1 : Starting point
     * @param point2 : Ending point
     * @return Lowest amount of steps to reach point 2 from point 1
     */
    public float heuristic(Tile point1, Tile point2){
        float dist =(float) Math.abs(point1.getY() - point2.getY()) + Math.abs(point1.getX() - point2.getX());
        return dist;
    }

    /**
     * locates the sprite in the array pixel coordinates to array coordinates
     * @param x : x position pixels
     * @param y : y position pixels
     */
    public void locate(float x, float y){
        setCoordX(Math.round(x)/16);
        setCoordY(Math.round(Con.HEIGHT-y)/16);
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }
    public void setCoordY(int coordY){
        this.coordY = coordY;
    }

    public String toString(){
        return "("+coordX+","+coordY+")";
    }
}
