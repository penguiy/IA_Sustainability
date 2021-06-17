package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Hud extends Actor {


    private Main game;

    private Viewport viewport;
    private Stage stage;
    private Table table;

    private Label day;
    private Label time;
    private Label money;

    private String hourString;
    private String minuteString;
    private int hours;
    private int minutes;
    private float totalDeltaTime;

    private boolean dayEnd;

    private Image dayIcon;
    private Image timeIcon;

    public ImageButton getDetails() {
        return details;
    }

    private ImageButton details;

    private Group progressWater;
    private Group progressLight;
    private Group progressAC;
    private Group progressTrash;
    private Group progressFood;

    private Label progressWaterLabel;
    private Label progressLightLabel;
    private Label progressACLabel;
    private Label progressTrashLabel;
    private Label progressFoodLabel;

    public Hud(Main myGame) {
        game = myGame;
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        table = new Table();
        table.top();
        table.setFillParent(true);
        table.debug();

        hourString = "07";
        minuteString = "55";
        hours = 7;
        minutes = 55;
        totalDeltaTime = 0;

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        day = new Label("Day " + game.getPlayer().getDayNum(), labelStyle);
        time = new Label(hourString + ":" + minuteString, labelStyle);
        money = new Label("", labelStyle);


        dayIcon = new Image(new Texture(Con.DAY_ICON));
        timeIcon = new Image(new TextureRegionDrawable(new Texture(Con.TIME_ICON)));


        progressWater = new Group();
        progressLight = new Group();
        progressAC = new Group();
        progressTrash = new Group();
        progressFood = new Group();

        progressWaterLabel = new Label(game.getPlayer().getOdds().get("WATER").toString() + "%", labelStyle);
        progressLightLabel = new Label(game.getPlayer().getOdds().get("LIGHT").toString() + "%", labelStyle);
        progressACLabel = new Label(game.getPlayer().getOdds().get("AC").toString() + "%", labelStyle);
        progressTrashLabel = new Label(game.getPlayer().getOdds().get("TRASH").toString() + "%", labelStyle);
        progressFoodLabel = new Label(game.getPlayer().getOdds().get("FOOD").toString() + "%", labelStyle);

        Drawable detailsDrawable = new TextureRegionDrawable(new Texture(Con.MENU_ICON));
        details = new ImageButton(detailsDrawable);
        details.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //TODO make all the groups visible
                if(!(game.getDisplaying().equals(ScreenDisplay.PAUSE) ||game.getDisplaying().equals(ScreenDisplay.DAYEND))){
                    System.out.println(progressWaterLabel.getText());
                }
                return false;
            }
        });

        table.add(dayIcon);
        table.add(day).padLeft(8).padRight(18);
        table.add(timeIcon).center().padRight(8);
        table.add(time);
        table.add(new Label("    ", labelStyle)).expandX();
        table.add(new Label("    ", labelStyle)).size(100,2).right();
        table.row();
        table.add();
        table.add();
        table.add();
        table.add();
        table.add();
        table.add(money).padRight(4);
        table.row();
        table.add(details).size(24).padLeft(4);
        stage.addActor(table);
    }

    /**
     * Update labels depending on time
     * @param dt delta time
     */
    public void update(float dt){
        //Day end? -> Day count +1, else stay same
        //Make so time doesn't count when in menu
        if(!game.getDisplaying().equals(ScreenDisplay.DAYEND)) {
            totalDeltaTime += dt;
            if (hours >= Con.FINAL_HOUR && minutes >= Con.FINAL_MIN) {
                //Day end menu
                hours = 7;
                minutes = 55;
                game.changeScreen(ScreenDisplay.DAYEND);
                game.getPlayer().nextDay();
                totalDeltaTime = 0;
                day.setText("Day " + game.getPlayer().getDayNum());

            }
            else if (totalDeltaTime >= Con.STANDARD_TIME/4) {
                minutes++;
                if (minutes > 59) {
                    hours++;
                    minutes = 0;
                }
                if (hours < 10) {
                    hourString = "0" + hours;
                } else {
                    hourString = Integer.toString(hours);
                }
                if (minutes < 10) {
                    minuteString = "0" + minutes;
                } else {
                    minuteString = Integer.toString(minutes);
                }
                time.setText(hourString + ":" + minuteString);
                totalDeltaTime = 0;
            }
            money.setText(game.getPlayer().getPoints());
            progressWaterLabel.setText(game.getPlayer().getOdds().get("WATER").toString() + "%");
            progressLightLabel.setText(game.getPlayer().getOdds().get("LIGHT").toString() + "%");
            progressACLabel.setText(game.getPlayer().getOdds().get("AC").toString() + "%");
            progressTrashLabel.setText(game.getPlayer().getOdds().get("TRASH").toString() + "%");
            progressFoodLabel.setText(game.getPlayer().getOdds().get("FOOD").toString() + "%");

        }
    }


    public Stage getStage() {
        return stage;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
    public boolean isDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(boolean dayEnd) {
        this.dayEnd = dayEnd;
    }

}
