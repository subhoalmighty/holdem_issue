package com.radikal.holdempoker.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.radikal.holdempoker.HoldemPokerGame;

/**
 * Created by SubrataMondal on 04/12/17.
 */

public abstract class ClickableStageExtent {

    protected Stage stage;
    protected HoldemPokerGame game;
    protected Rectangle bound;
    private boolean isVisible = false;

    public ClickableStageExtent(HoldemPokerGame game) {
        this.game = game;
        stage = new Stage(game.getCurrentScreen().getViewport());
    }

    public void setBounds(float x, float y, float width, float height) {
        bound = new Rectangle(x, y, width, height);
    }

    public float getX() {
        return bound != null ? bound.getX() : 0;
    }

    public float getY() {
        return bound != null ? bound.getY() : 0;
    }

    public float getWidth() {
        return bound != null ? bound.getWidth() : 0;
    }

    public float getHeight() {
        return bound != null ? bound.getHeight() : 0;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public abstract void construct();

    public void show() {
        if(isVisible)
            return;
        isVisible = true;
        game.getCurrentScreen().getInputMultiplexer().addProcessor(stage);
    }

    public void hide() {
        if(!isVisible)
            return;
        isVisible = false;
        game.getCurrentScreen().getInputMultiplexer().removeProcessor(stage);
    }

    public void draw(Batch batch) {
        if(!isVisible)
            return;
        stage.draw();
        stage.act();
    }

    public void dispose() {
        stage.dispose();
    }
}