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
    private Viewport viewport;
    private Stage stage;
    private Table table;

    private Label day;
    private Label time;
    private Label money;

    private int dayNum;
    private String bank;
    private String timeString;

    private Image dayIcon;
    private Image timeIcon;
    private ImageButton settings;

    public Hud() {
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        table = new Table();
        table.top();
        table.setFillParent(true);
        table.debug();

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        day = new Label("Day " + Integer.toString(dayNum), labelStyle);
        time = new Label(timeString, labelStyle);
        money = new Label(bank, labelStyle);


        dayIcon = new Image(new Texture(Con.DAY_ICON));
        timeIcon = new Image(new TextureRegionDrawable(new Texture(Con.TIME_ICON)));

        Drawable settingsDrawable = new TextureRegionDrawable(new Texture(Con.SETTINGS_ICON));
        settings = new ImageButton(settingsDrawable);

        table.add(dayIcon);
        table.add(day);
        table.add(timeIcon).expandX();
        table.add(time);
        table.add(settings).right();
        table.row();
        table.add(money).right();

        stage.addActor(table);
    }
    public Stage getStage() {
        return stage;
    }

}
