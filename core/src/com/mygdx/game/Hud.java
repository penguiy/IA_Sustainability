package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private ImageButton settings;

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

        Drawable settingsDrawable = new TextureRegionDrawable(new Texture(Con.SETTINGS_ICON));
        settings = new ImageButton(settingsDrawable);

        table.add(dayIcon);
        table.add(day).padLeft(8).padRight(18);
        table.add(timeIcon).center().padRight(8);
        table.add(time);
        table.add(new Label("    ", labelStyle)).expandX();
        table.add(settings).right();
        table.row();
        table.add();
        table.add();
        table.add();
        table.add();
        table.add();

        table.add(money).padRight(4);

        stage.addActor(table);
    }
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
            else if (totalDeltaTime >= Con.STANDARD_TIME/100) {
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
