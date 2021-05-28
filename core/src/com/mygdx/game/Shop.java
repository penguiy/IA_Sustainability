package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop implements Screen {
    private Viewport viewport;
    private Main game;
    public Stage getStage() {
        return mainStage;
    }

    private Stage mainStage;

    public Stage getStageFirst() {
        return stageFirst;
    }

    public Stage getStageClass() {
        return stageClass;
    }

    public Stage getStageInfra() {
        return stageInfra;
    }

    private Stage stageFirst;
    private Stage stageClass;
    private Stage stageInfra;

    private TextButton exit;

    public boolean isClassTab(){
        return mainStage.equals(stageClass);
    }
    public boolean isInfraTab(){
        return mainStage.equals(stageInfra);
    }

    private Table first;
    private Table classesTable;
    private Table classScrollContainter;
    private Table infrastructure;
    private Table infraScrollContainter;


    private ScrollPane classScroll;
    private ScrollPane infraScroll;

    public Shop(final Main game){
        this.game = game;
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, new OrthographicCamera());
        //main stage which is the one the game class draws
        mainStage = new Stage(viewport);
        //Create different stages(tabs of the shop)
        stageFirst = new Stage(viewport);
        stageInfra = new Stage(viewport);
        stageClass = new Stage(viewport);

    //Setup Landing Page for Shop
        first = new Table();
        first.setFillParent(true);

        //Create black background
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0,0,0,0.8f);
        bgPixmap.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        //Add background
        first.setBackground(bg);

        //Create button style
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();

        //Create an exit button
        exit = new TextButton("Back", style);
        exit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //If on not on first stage, change to first stage
                    if(!mainStage.equals(stageFirst)) {
                        mainStage = stageFirst;
                    }
                return false;
        }
        });
        //Create Shop button
        TextButton shop = new TextButton("SHOP", style);
        shop.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Clause to not overlap the input processors
                if(mainStage.equals(stageFirst)) {
                    //Change mainStage to infrastructure tab
                    mainStage = stageInfra;
                }
                return false;
            }
        });
        //Create Class button
        TextButton event = new TextButton("CLASS", style);
        event.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(mainStage.equals(stageFirst)) {
                    //Change mainStage to infrastructure tab
                    mainStage = stageClass;
                }
                return false;
            }
        });
        //Add both to table
        first.add(shop).expandX();
        first.add(event).expandX();
        first.debug();//Temp
        //Add table to stage
        stageFirst.addActor(first);
        //Set stageFirst as the mainStage onCreate
        mainStage = stageFirst;

    //Setup Class Tab
        classesTable = new Table();
        classesTable.setFillParent(true);

        classesTable.debug();//Temp

        //Set Background
        classesTable.setBackground(bg);
        //Add holder table
        stageClass.addActor(classesTable);

        //Initialise scrolling table
        classScrollContainter = new Table();
        //For every type of flag create a button and add to scrolling table

        labelUpdateClass();

        //TODO DUPLICATE ABOVE FOR INFRASTRUCTURE TAB

        infrastructure = new Table();
        infrastructure.setFillParent(true);
        infrastructure.setBackground(bg);

        infrastructure.add(new Label("Infrastructure",new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
        infrastructure.debug();
        stageInfra.addActor(infrastructure);
    }

    /**
     * If the player has enough points to unlock the chosen upgrade, increase the point multiplier they purchased,
     * increase the purchase count of that multiplier and subtract the necessary payment.
     *
     * Else deny the payment and notify player of insufficient funds
     *
     * @param str Constant parameter to determine the key (Con.LIGHT_WASTE, FOOD_WASTE, AC_WASTE...)
     */
    public void classClick(String str) {
        //If odds are maxed, deny transfer
        if (game.getPlayer().getPoints() >= 0/*game.getPlayer().getClassPrice(str)*/) {
            //Add 0.2 to the current multiplier
            game.getPlayer().setMultiplier(str, Con.MULTI_GROWTH);
            //Count how many times it's been updgraded
            increaseCount(game.getPlayer().getClassCount(), str); //TODO not working
            //Subtract payment
            game.getPlayer().subPoints(0/*game.getPlayer().getClassPrice()*/);
            //Temp print statement
            System.out.println(game.getPlayer().getMulti());

        }else{
            //Invalid Funds : deny payment
            //TODO put up notif
        }
    }
    /**
     * If the player has enough points to unlock the chosen upgrade, increase the odds of a self-resolve they
     * for the section purchased, increase the purchase count of that multiplier and subtract the necessary payment.
     *
     * Else deny the payment and notify player of insufficient funds.
     *
     * @param str Constant parameter to determine the key (Con.LIGHT_WASTE, FOOD_WASTE, AC_WASTE...)
     */
    public void infraClick(String str){
        //If player has enough funds
        if(game.getPlayer().getPoints() >= game.getPlayer().getInfraPrice(str)){
            game.getPlayer().setOdds(str,10);
            //Use this to trigger Screen classes to render infrastructure Layers
            if(!game.getPlayer().getInfraPurchase().contains(str)){
                game.getPlayer().getInfraPurchase().add(str);
            }
            increaseCount(game.getPlayer().getInfraCount(), str);
            game.getPlayer().subPoints(0/*game.getPlayer().getInfraPrice()*/);
        }else{
            //Invalid Funds
        }
    }

    /**
     * Set the mainStage to stageFirst
     */
    public void reset(){
        this.mainStage = stageFirst;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public void increaseCount(HashMap<String, Integer> counter, String section){
        counter.put(section,counter.get(section+1));
    }

    public void labelUpdateClass(){
        classScrollContainter.clear();
        classesTable.clear();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        for(final String str : Con.TRIGGERS){
            Group classDetails = new Group(); //TODO use this to also add in the price of the upgrade to the ScrollPane
            final TextButton button = new TextButton(str, style);
            classDetails.setSize(200, 20);
            classDetails.setTransform(false);
            button.setTouchable(Touchable.enabled);
            button.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(mainStage.equals(stageClass)) {
                        classClick(str);
                        labelUpdateClass();
                    }
                    return false;
                }
            });
            button.setFillParent(true);
            classDetails.addActor(button);
            classDetails.addActor(new Label(Integer.toString(game.getPlayer().getClassPrice(str)),
                    new Label.LabelStyle(new BitmapFont(),
                            Color.WHITE)));
            classScrollContainter.add(classDetails).size(200, 20).pad(10);
        }
        classScrollContainter.row();
        //Add a scroll bar so people don't accidentally purchase things
        classScrollContainter.add(new Label("CLICK AND DRAG HERE TO SCROLL", new Label.LabelStyle(new BitmapFont(),
                Color.WHITE))).colspan(5).expandX();
        classScrollContainter.debug(); //Temp

        //Formatting stuff
        classScrollContainter.pack();
        classScrollContainter.setTransform(false);
        classScrollContainter.setOrigin(classScrollContainter.getWidth() / 2,
                classScrollContainter.getHeight() / 2);

        //Add the scrollTable to the ScrollPane
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        classScroll = new ScrollPane(classScrollContainter, scrollStyle);
        classScroll.layout();

        //Set to only horizontal scrolling
        classScroll.setScrollingDisabled(false,true);

        //Add Header to big table
        classesTable.add(new Label("CLASSES", new Label.LabelStyle(new BitmapFont(), Color.WHITE))).expandX();
        classesTable.add(exit).pad(4);
        classesTable.row();
        //Add ScrollPanel to big table
        classesTable.add(classScroll).colspan(2).fill();
    }
}
