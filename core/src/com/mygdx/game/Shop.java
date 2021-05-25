package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Shop implements Screen {
    private Viewport viewport;

    public Stage getStage() {
        return mainStage;
    }

    private Stage mainStage;
    private Stage stageFirst;
    private Stage stageClass;
    private Stage stageInfra;

    private Table first;
    private Table classes;
    private Table infrastructure;

    public Shop(){
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, new OrthographicCamera());
        mainStage = new Stage(viewport);

        stageFirst = new Stage(viewport);
        stageInfra = new Stage(viewport);
        stageClass = new Stage(viewport);

        first = new Table();
        classes = new Table();
        infrastructure = new Table();
        first.setFillParent(true);
        classes.setFillParent(true);
        infrastructure.setFillParent(true);

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0,0,0,0.7f);
        bgPixmap.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        first.setBackground(bg);
        classes.setBackground(bg);
        infrastructure.setBackground(bg);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        TextButton shop = new TextButton("SHOP", style);
        shop.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainStage = stageInfra;
                return false;
            }
        });
        TextButton event = new TextButton("CLASS", style);
        event.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainStage = stageClass;
                return false;
            }
        });

        first.add(shop,event);
        mainStage.addActor(first);
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
}
