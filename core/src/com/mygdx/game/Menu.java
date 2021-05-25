package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Table table;
    public Menu(){
        viewport = new FitViewport(Con.WIDTH, Con.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        table = new Table();
        table.setFillParent(true);

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0,0,0,0.7f);
        bgPixmap.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(bg);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        TextButton shop = new TextButton("SHOP", style);
        TextButton event = new TextButton("EVENT", style);
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
