package com.radikal.holdempoker.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by SubrataMondal on 03/12/17.
 */

public class RoundedCornerRectangle extends Actor {

    public enum PointerPosition {
        none,
        top,
        left,
        bottom,
        right,
        topArbitrary,
        leftArbitrary,
        bottomArbitrary,
        rightArbitrary,
    }

    protected PointerPosition pointerPosition = PointerPosition.none;

    protected ShapeRenderer mShapeRenderer;
    protected float x, y, width, height, radius;
    protected float borderWidth;
    protected Color fillColor = Color.WHITE, borderColor = Color.RED;

    public RoundedCornerRectangle(float x, float y, float width, float height, float radius) {
        this(x, y, width, height, radius, 4);
    }

    public RoundedCornerRectangle(float x, float y, float width, float height, float radius, float borderWidth) {
        this(x, y, width, height, radius, borderWidth, Color.WHITE, Color.BLACK);
    }

    public RoundedCornerRectangle(float x, float y, float width, float height, float radius, float borderWidth, Color fillColor, Color borderColor) {
        mShapeRenderer = new ShapeRenderer();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.borderWidth = borderWidth;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
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

        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mShapeRenderer.setColor(this.fillColor);

        mShapeRenderer.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius);
        mShapeRenderer.rect(x, y + radius, radius, height - 2 * radius);
        mShapeRenderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
        mShapeRenderer.rect(x + radius, y + height - radius, width - 2 * radius, radius);
        mShapeRenderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        mShapeRenderer.rect(x + width - radius, y + radius, radius, height - 2 * radius);
        mShapeRenderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
        mShapeRenderer.rect(x + radius, y, width - 2 * radius, radius);
        mShapeRenderer.arc(x + radius, y + radius, radius, 180f, 90f);

        mShapeRenderer.end();

        if(this.borderWidth > 0) {
            mShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            mShapeRenderer.setColor(this.borderColor);
            Gdx.gl20.glLineWidth(this.borderWidth);

            if (pointerPosition != PointerPosition.left && pointerPosition != PointerPosition.leftArbitrary)
                mShapeRenderer.line(x, y + radius, x, y + height - radius);
            arc(x + radius, y + height - radius, radius, 90f, 90f);
            if (pointerPosition != PointerPosition.top && pointerPosition != PointerPosition.topArbitrary)
                mShapeRenderer.line(x + radius, y + height, x + width - radius, y + height);
            arc(x + width - radius, y + height - radius, radius, 0f, 90f);
            if (pointerPosition != PointerPosition.right && pointerPosition != PointerPosition.rightArbitrary)
                mShapeRenderer.line(x + width, y + height - radius, x + width, y + radius);
            arc(x + width - radius, y + radius, radius, 270f, 90f);
            if (pointerPosition != PointerPosition.bottom && pointerPosition != PointerPosition.bottomArbitrary)
                mShapeRenderer.line(x + width - radius, y, x + radius, y);
            arc(x + radius, y + radius, radius, 180f, 90f);

            mShapeRenderer.end();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }

    protected void arc (float x, float y, float radius, float start, float degrees) {
        arc(x, y, radius, start, degrees, Math.max(1, (int)(6 * (float)Math.cbrt(radius) * (degrees / 360.0f))));
    }

    protected void arc (float x, float y, float radius, float start, float degrees, int segments) {
        if (segments <= 0) throw new IllegalArgumentException("segments must be > 0.");
        float theta = (2 * MathUtils.PI * (degrees / 360.0f)) / segments;
        float cos = MathUtils.cos(theta);
        float sin = MathUtils.sin(theta);
        float cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians);
        float cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians);
        float cxEnd, cyEnd;

        for (int i = 0; i < segments; i++) {
            cxEnd = cos * cx - sin * cy;
            cyEnd = sin * cx + cos * cy;
            mShapeRenderer.line(x + cx, y + cy, x + cxEnd, y + cyEnd);
            cx = cxEnd;
            cy = cyEnd;
        }
    }
}
