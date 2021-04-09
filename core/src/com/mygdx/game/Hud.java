package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
    private Main game;

    private Viewport viewport;
    private Stage stage;
    private Table table;

    private Label day;
    private Label time;
    private Label money;

    private int dayNum;
    private String bank;
    private String hourString;
    private String minuteString;
    private int hours;
    private int minutes;
    private float totalDeltaTime;

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

        totalDeltaTime = 0;

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        day = new Label("Day " + Integer.toString(dayNum), labelStyle);
        time = new Label( "00:00",  labelStyle);
        money = new Label(bank, labelStyle);


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
        table.add(money).right();

        stage.addActor(table);
    }
    public void update(float dt){
        //Day end? -> Day count +1, else stay same
        //Day end? -> set back to 07:00, else increase by 00:01
        totalDeltaTime += dt;
        if(totalDeltaTime >= 1){
            minutes++;
            if(minutes > 59){
                hours++;
                minutes = 0;
            }
            if(hours < 10){
                hourString = "0" + hours;
            }
            else{
                hourString = Integer.toString(hours);
            }
            if(minutes < 10){
                minuteString = "0" + minutes;
            }
            else{
                minuteString = Integer.toString(minutes);
            }
            time.setText(hourString + ":" + minuteString);
            totalDeltaTime = 0;
        }
//        if(game.isDayEnd()){
//            dayNum += 1;
//            hours = 7;
//            minutes = 0;
//            game.setDayEnd(false);
//        }
//        else{
//
//        }
    }
    public Stage getStage() {
        return stage;
    }

}
