package com.radikal.holdempoker.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SubrataMondal on 03/12/17.
 */

public class Line extends Actor {

    public enum LineDirection {
        horizontal,
        vertical,
    }

    protected LineDirection lineDirection = LineDirection.horizontal;

    protected ShapeRenderer mShapeRenderer;
    protected float x, y, width, height;
    protected Color color = Color.GRAY;

    public Line(float x, float y, float width, float height) {
        this(x, y, width, height, LineDirection.horizontal);
    }

    public Line(float x, float y, float width, float height, LineDirection direction) {
        this(x, y, width, height, direction, Color.GRAY);
    }

    public Line(float x, float y, float width, float height, LineDirection direction, Color color) {
        mShapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lineDirection = direction;
        this.color = color;
        setBounds(this.x, this.y, this.width, this.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawShape(batch);
    }

    protected void drawShape(Batch batch) {
        batch.end();

        mShapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        mShapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        mShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        mShapeRenderer.setColor(this.color);
        Gdx.gl20.glLineWidth(lineDirection == LineDirection.vertical ? this.width : this.height);

        if(lineDirection == LineDirection.horizontal)
            mShapeRenderer.line(x, y, x + width, y);
        else
            mShapeRenderer.line(x, y, x, y + height);

        mShapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }
}
