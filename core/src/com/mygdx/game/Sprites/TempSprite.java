package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Con;
import com.mygdx.game.Main;
import com.mygdx.game.Screens.Tile;

import java.util.ArrayList;

public class TempSprite extends SpriteBase {
    private World world;
    private Main game;
    private Body body;

    private TextureRegion region;
    private int coordX, coordY;

    private ArrayList<Tile> currPathing;



    public TempSprite(World world, Main game, float[] pos){
        this.world = world;
        this.game = game;
        setPosition(pos[0], pos[1]);
        setBounds(getX(), getY(),16,16);
        currPathing = new ArrayList<>();
        region = new TextureRegion(new Texture("Stairs.png"));
        setRegion(region);
        defineBody();
        locate(getX(),getY());
        //Temporary
        calcSteps(28,8);
        System.out.println("("+coordX+","+ coordY+")");
    }

    public void defineBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(9,9);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float delta) {
            setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y - getHeight()/2);
    }

    //Run calcSteps at Key times for each sprite
    @Override
    public ArrayList<Tile> calcSteps(int endX, int endY){
        System.out.println("calc start");
        ArrayList<Tile> openSet = new ArrayList<>();
        ArrayList<Tile> closedSet = new ArrayList<>();
        Tile pos = game.getSpriteManager().getLayout()[coordY][coordX];
        Tile end = game.getSpriteManager().getLayout()[endY][endX];
        currPathing.clear();

        //define start (current position)
        locate(this.getX(),this.getY());
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
                    trace.getTile().setTextureRegion(new TextureRegion(new Texture("Stairs.png")));

                    pathing.add(trace);
                    trace = trace.getParent();

                }
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
    public ArrayList<Tile> getNeighbors(Tile origin) {
        ArrayList<Tile> TBR = new ArrayList<>();
        if (origin.getY() != 0){
            Tile n1 = game.getSpriteManager().getLayout()[origin.getY() - 1][origin.getX()];
            TBR.add(n1);
        }
        if (origin.getY() < 23) {
            Tile n2 = game.getSpriteManager().getLayout()[origin.getY() + 1][origin.getX()];
            TBR.add(n2);
        }
        if (origin.getX() != 0) {
            Tile n3 = game.getSpriteManager().getLayout()[origin.getY()][origin.getX() - 1];
            TBR.add(n3);
        }
        if (origin.getX() < 34) {
            Tile n4 = game.getSpriteManager().getLayout()[origin.getY()][origin.getX() + 1];
            TBR.add(n4);
        }
        return TBR;
    }

    public float heuristic(Tile point1, Tile point2){
        float dist =(float) Math.abs(point1.getY() - point2.getY()) + Math.abs(point1.getX() - point2.getX());

        return dist;
    }

    public void locate(float x, float y){
        coordX = Math.round(x)/16;
        coordY = Math.round(Con.HEIGHT-y)/16;
    }

}
