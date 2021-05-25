package com.mygdx.game.Utils;

import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Main;
import com.mygdx.game.ScreenDisplay;
import com.mygdx.game.Screens.Fade;
import com.mygdx.game.Screens.GroundFloor;
import com.mygdx.game.Screens.StreetView;
import com.mygdx.game.Screens.TitleScreen;

public class InputListener implements InputProcessor {

    private Main game;
    private ScreenDisplay prePause;

    public InputListener(Main game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int i) {
        if(i == Keys.M && !(game.getScreen() instanceof Fade)){
            if(game.getDisplaying()!=ScreenDisplay.DAYEND) {
                if (!game.getDisplaying().equals(ScreenDisplay.PAUSE)){
                    System.out.println(game.getDisplaying());
                    prePause = game.getDisplaying();
                    game.changeScreen(ScreenDisplay.PAUSE);
                } else{
                    game.changeScreen(prePause);
                }
                //Set displaying to pause, draw menu in screen class
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int point, int button){
        if (button == Buttons.LEFT && !game.getDisplaying().equals(ScreenDisplay.PAUSE)) {
            if(game.getScreen() instanceof StreetView){
                ((StreetView) game.getScreen()).clickFixture(x,y);
            }else if(game.getScreen() instanceof GroundFloor){
                ((GroundFloor) game.getScreen()).clickFixture(x,y);
            }else if(game.getScreen() instanceof TitleScreen){
                ((TitleScreen) game.getScreen()).clickFixture(x,y);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
