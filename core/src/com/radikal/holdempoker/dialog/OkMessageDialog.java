package com.radikal.holdempoker.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.graphics.RoundedCornerRectangle;

public class OkMessageDialog extends Group {

    protected HoldemPokerGame game;
    protected Stage stage;
    protected boolean isShowing;

    protected TextButton yesBtn, noBtn;
    protected RoundedCornerRectangle bgSprite;
    protected Label headerLabel, bodyLabel;
    protected Pixmap clearColorMap;

	public OkMessageDialog(HoldemPokerGame game) {
        this.game = game;
        stage = game.getCurrentScreen().stage;

		clearColorMap = getPixmapRectangle(Constants.loginTextWidth, Constants.loginTextHeight, Color.CLEAR);

        /*RoundedCornerRectangle bgTotalSprite = new RoundedCornerRectangle(
                0, 0,
                Constants.screenWidth, Constants.screenHeight,
                0, 0, Constants.colorPopupBlackBg, Color.CLEAR);
        addActor(bgTotalSprite);*/

        bgSprite = new RoundedCornerRectangle(
                (Constants.screenWidth - 1.2f * Constants.loginTextWidth) / 2,
                (Constants.screenHeight - 5.0f * Constants.loginTextHeight) / 2,
                1.2f * Constants.loginTextWidth, 5.0f * Constants.loginTextHeight,
                Constants.loginTextHeight / 4, 1, Constants.colorPopupDarkBlackBg, Constants.colorBlueOpaque);
        addActor(bgSprite);

		BitmapFont headerFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 32);
		Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, Constants.colorBlueWithAlpha);

        headerLabel = new Label("ALERT!", headerStyle);
		headerLabel.setPosition(bgSprite.getX(), bgSprite.getY() + bgSprite.getHeight() - 2.0f * Constants.btnWidth);
        headerLabel.setAlignment(Align.center);
        headerLabel.setWidth(bgSprite.getWidth());
		addActor(headerLabel);

        BitmapFont bodyFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16);
        Label.LabelStyle bodyStyle = new Label.LabelStyle(bodyFont, Color.WHITE);
        bodyFont.getData().markupEnabled = true;

        bodyLabel = new Label("Message", bodyStyle);
        bodyLabel.setAlignment(Align.center);
        bodyLabel.setWrap(true);
        bodyLabel.pack();
        bodyLabel.setPosition(bgSprite.getX() + Constants.btnWidth, headerLabel.getY() - 0.8f * Constants.btnWidth);
        bodyLabel.setWidth(bgSprite.getWidth() - 2 * Constants.btnWidth);
        addActor(bodyLabel);
	}

	public OkMessageDialog setHeader(String header) {
        headerLabel.setText(header);
		return this;
	}

	public OkMessageDialog setBody(String body) {
        bodyLabel.setText(body);
		return this;
	}

    public OkMessageDialog setOkBtn(String text, ClickListener listener) {
        BitmapFont continueFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 22, Constants.colorTextYellow);
        TextButton.TextButtonStyle continueStyle = new TextButton.TextButtonStyle(
                new Image(new Texture(clearColorMap)).getDrawable(),
                new Image(new Texture(clearColorMap)).getDrawable(),
                new Image(new Texture(clearColorMap)).getDrawable(), continueFont);

        RoundedCornerRectangle btnYesBg = new RoundedCornerRectangle(
                bgSprite.getX() + (bgSprite.getWidth() - Constants.btnWidth) / 2,
                bgSprite.getY() + 0.5f * Constants.btnHeight,
                Constants.btnWidth, Constants.btnHeight,
                Constants.loginTextHeight / 4, 1, Constants.colorBtnYellowWithAlpha, Constants.colorBlueWithAlpha);
        addActor(btnYesBg);

        yesBtn = new TextButton(text, continueStyle);
        yesBtn.setBounds(btnYesBg.getX(), btnYesBg.getY(),
                Constants.btnWidth, Constants.btnHeight);
        addActor(yesBtn);
        yesBtn.addListener(listener);
        return this;
    }

    public void show() {
        isShowing = true;
        stage.addActor(this);
    }

    public boolean isShowingDialog() {
        return isShowing;
    }

    public void hide() {
        if(isShowing) {
            isShowing = false;
            this.clearChildren();
            if(this.hasParent())
                this.remove();
        }
    }

    public static Pixmap getPixmapRectangle(float width, float height, Color color) {
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }
}