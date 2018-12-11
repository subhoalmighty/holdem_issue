package com.radikal.holdempoker.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.radikal.holdempoker.Constants;

public class ImageTextBtnSprite extends Group {

    private Button button;
    private Label label;

    public ImageTextBtnSprite(Drawable normal) {
        this(normal, normal);
    }

    public ImageTextBtnSprite(Drawable normal, Drawable down) {
        button = new Button(normal, down);
        button.setBounds(0,
                Constants.dashboardLabelHeight + Constants.dashboardLabelGap ,
                Constants.dashboardBtnWidth,
                Constants.dashboardBtnHeight);
        button.align(Align.center);
        addActor(button);
    }

    public void setText(String text, Label.LabelStyle style) {
        label = new Label(text, style);
        label.setColor(Color.GRAY);
        label.setBounds((Constants.dashboardBtnWidth - Constants.dashboardLabelWidth) / 2, 0,
                Constants.dashboardLabelWidth,
                Constants.dashboardLabelHeight);
        label.setAlignment(Align.center, Align.center);
        addActor(label);
    }

}
