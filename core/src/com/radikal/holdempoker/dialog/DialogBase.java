package com.radikal.holdempoker.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.radikal.holdempoker.HoldemPokerGame;

public class DialogBase implements InputProcessor {

    protected HoldemPokerGame game;
    protected Stage stage;
    protected InputMultiplexer inputMultiplexer;
    protected boolean isShowing;

    public final static int CONTINUE_BTN = 0;
    public final static int QUIT_BTN = 1;
    public final static int CONTINUE_GAME_BTN = 2;
    public final static int RESTART_GAME_BTN = 3;
    public final static int BACK_TO_MENU_BTN = 4;
    public final static int CLOSE_INFO_BTN = 5;
    public final static int ACCEPT_TNC_BTN = 6;
    public final static int SHOW_LEADERBOARD = 7;
    public final static int CLOSE_LEADERBOARD = 8;
    public final static int NEXT_SESSION = 9;
    public final static int PLAY_AS_EXPERT = 10;
    public final static int PLAY_PRACTICE = 11;
    public final static int FORCE_LOGOUT = 12;
    public final static int SHARE_ON_FB = 13;
    public final static int HIDE_STARTUP_NOTIFICATION = 14;



    public interface DialogClickListener {
        void onDialogClicked(int btnTag);
    }

    protected DialogClickListener mDialogClickListener = null;

    public void setDialogClickListener(DialogClickListener dialogClickListener)  {
        mDialogClickListener = dialogClickListener;
    }

    public DialogBase( HoldemPokerGame game ) {

        this.game = game;
        stage = new Stage();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);

    }

    public boolean isShowingDialog() {
        return isShowing;
    }

    public void show() {
        isShowing = true;
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCatchBackKey(true);
    }

    public void hide() {
        if(isShowing) {
            isShowing = false;
            stage.getRoot().clearChildren();
            this.game.getCurrentScreen().setInputProcessor();
        }
    }

    public void dispose() {
        stage.dispose();
    }

    public static Pixmap getPixmapRectangle(float width, float height, Color color) {
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if( keycode == Input.Keys.BACK ) {
            if ( isShowing ) {
                this.hide();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
