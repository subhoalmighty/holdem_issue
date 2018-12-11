package com.radikal.holdempoker.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.radikal.holdempoker.Constants;

/**
 * Created by SubrataMondal on 03/12/17.
 */

public class DropDownRectangle extends RoundedCornerRectangle {

    private Vector2 initialPoint = null;

    public DropDownRectangle(float x, float y, float width, float height, float radius,
                             PointerPosition pointerPosition) {
        this(x, y, width, height, radius, pointerPosition, 4);
    }

    public DropDownRectangle(float x, float y, float width, float height, float radius,
                             PointerPosition pointerPosition, float borderWidth) {
        this(x, y, width, height, radius, pointerPosition, borderWidth, Color.WHITE, Color.BLACK);
    }

    public DropDownRectangle(float x, float y, float width, float height, float radius,
                             PointerPosition pointerPosition, float borderWidth, Color fillColor, Color borderColor) {
        super(x, y, width, height, radius, borderWidth, fillColor, borderColor);
        this.pointerPosition = pointerPosition;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawPointer(batch);
    }

    public void setPointerPosition(float x, float y) {
        initialPoint = new Vector2(x, y);
    }

    protected void drawPointer(Batch batch) {
        if((this.pointerPosition == PointerPosition.topArbitrary ||
                this.pointerPosition == PointerPosition.leftArbitrary ||
                this.pointerPosition == PointerPosition.bottomArbitrary ||
                this.pointerPosition == PointerPosition.rightArbitrary) && initialPoint == null) {
                throw new IllegalArgumentException("Pointer position is null!");
        }
        batch.end();

        mShapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        mShapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(this.fillColor);

        if(this.pointerPosition == PointerPosition.top) {
            initialPoint = new Vector2(getX() + getWidth() / 2, getY() + getHeight());
        } else if(this.pointerPosition == PointerPosition.left) {
            initialPoint = new Vector2(getX(), getY() + getHeight() / 2);
        } else if(this.pointerPosition == PointerPosition.bottom) {
            initialPoint = new Vector2(getX() + getWidth() / 2, getY());
        } else if(this.pointerPosition == PointerPosition.right) {
            initialPoint = new Vector2(getX() + getWidth(), getY() + getHeight() / 2);
        }

        if(this.pointerPosition == PointerPosition.top || this.pointerPosition == PointerPosition.topArbitrary ) {
            mShapeRenderer.triangle(initialPoint.x - Constants.dropDownPointerWidth / 2, initialPoint.y,
                    initialPoint.x, initialPoint.y + Constants.dropDownPointerHeight,
                    initialPoint.x + Constants.dropDownPointerWidth / 2, initialPoint.y);
        } else if(this.pointerPosition == PointerPosition.left || this.pointerPosition == PointerPosition.leftArbitrary) {
            mShapeRenderer.triangle(initialPoint.x, initialPoint.y - Constants.dropDownPointerWidth / 2,
                    initialPoint.x - Constants.dropDownPointerHeight, initialPoint.y,
                    initialPoint.x, initialPoint.y + Constants.dropDownPointerWidth / 2);
        } else if(this.pointerPosition == PointerPosition.bottom | this.pointerPosition == PointerPosition.bottomArbitrary) {
            mShapeRenderer.triangle(initialPoint.x + Constants.dropDownPointerWidth / 2, initialPoint.y,
                    initialPoint.x, initialPoint.y - Constants.dropDownPointerHeight,
                    initialPoint.x - Constants.dropDownPointerWidth / 2, initialPoint.y);
        } else if(this.pointerPosition == PointerPosition.right || this.pointerPosition == PointerPosition.rightArbitrary) {
            mShapeRenderer.triangle(initialPoint.x, initialPoint.y + Constants.dropDownPointerWidth / 2,
                    initialPoint.x + Constants.dropDownPointerHeight, initialPoint.y,
                    initialPoint.x, initialPoint.y - Constants.dropDownPointerWidth / 2);
        }

        mShapeRenderer.end();

        mShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        mShapeRenderer.setColor(this.borderColor);
        Gdx.gl20.glLineWidth(this.borderWidth);

        if(this.pointerPosition == PointerPosition.top || this.pointerPosition == PointerPosition.topArbitrary) {
            mShapeRenderer.line(x + radius, y + height,
                    initialPoint.x - Constants.dropDownPointerWidth / 2, initialPoint.y);
            mShapeRenderer.line(initialPoint.x - Constants.dropDownPointerWidth / 2, initialPoint.y,
                    initialPoint.x, initialPoint.y + Constants.dropDownPointerHeight);
            mShapeRenderer.line(initialPoint.x, initialPoint.y + Constants.dropDownPointerHeight,
                    initialPoint.x + Constants.dropDownPointerWidth / 2, initialPoint.y);
            mShapeRenderer.line(initialPoint.x + Constants.dropDownPointerWidth / 2, initialPoint.y,
                    x + width - radius, y + height);
        } else if(this.pointerPosition == PointerPosition.left || this.pointerPosition == PointerPosition.leftArbitrary) {
            mShapeRenderer.line(x, y + radius,
                    initialPoint.x, initialPoint.y - Constants.dropDownPointerWidth / 2);
            mShapeRenderer.line(initialPoint.x, initialPoint.y - Constants.dropDownPointerWidth / 2,
                    initialPoint.x - Constants.dropDownPointerHeight, initialPoint.y);
            mShapeRenderer.line(initialPoint.x - Constants.dropDownPointerHeight, initialPoint.y,
                    initialPoint.x, initialPoint.y + Constants.dropDownPointerWidth / 2);
            mShapeRenderer.line(initialPoint.x, initialPoint.y + Constants.dropDownPointerWidth / 2,
                    x, y + height - radius);
        } else if(this.pointerPosition == PointerPosition.bottom || this.pointerPosition == PointerPosition.bottomArbitrary) {
            mShapeRenderer.line(x + width - radius, y,
                    initialPoint.x + Constants.dropDownPointerWidth / 2, initialPoint.y);
            mShapeRenderer.line(initialPoint.x + Constants.dropDownPointerWidth / 2, initialPoint.y,
                    initialPoint.x, initialPoint.y - Constants.dropDownPointerHeight);
            mShapeRenderer.line(initialPoint.x, initialPoint.y - Constants.dropDownPointerHeight,
                    initialPoint.x - Constants.dropDownPointerWidth / 2, initialPoint.y);
            mShapeRenderer.line(initialPoint.x - Constants.dropDownPointerWidth / 2, initialPoint.y,
                    x + radius, y);
        } else if(this.pointerPosition == PointerPosition.right || this.pointerPosition == PointerPosition.rightArbitrary) {
            mShapeRenderer.line(x + width, y + height - radius,
                    initialPoint.x, initialPoint.y + Constants.dropDownPointerWidth / 2);
            mShapeRenderer.line(initialPoint.x, initialPoint.y + Constants.dropDownPointerWidth / 2,
                    initialPoint.x + Constants.dropDownPointerHeight, initialPoint.y);
            mShapeRenderer.line(initialPoint.x + Constants.dropDownPointerHeight, initialPoint.y,
                    initialPoint.x, initialPoint.y - Constants.dropDownPointerWidth / 2);
            mShapeRenderer.line(initialPoint.x, initialPoint.y - Constants.dropDownPointerWidth / 2,
                    x + width, y + radius);
        }

        mShapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }
}
