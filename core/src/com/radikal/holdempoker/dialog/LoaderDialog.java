package com.radikal.holdempoker.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.graphics.RoundedCornerRectangle;

public class LoaderDialog extends DialogBase {

	private Sprite mLoaderSprite = null;
    private float elapsedTime;
    private Label messageLabel;

	public LoaderDialog(HoldemPokerGame game) {
	    super(game);
	}

	@Override
	public void show() {
	    super.show();

        RoundedCornerRectangle bgTotalSprite = new RoundedCornerRectangle(
                0, 0,
                Constants.screenWidth, Constants.screenHeight,
                0, 0, Constants.colorDropDownBlack, Color.CLEAR);
        stage.addActor(bgTotalSprite);

		Texture texture = game.assetManager.get("img/game/loader.png", Texture.class);
		mLoaderSprite =  new Sprite(texture);

        BitmapFont highScoreFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 28);
        Label.LabelStyle highScoreStyle = new Label.LabelStyle(highScoreFont, Constants.colorDividerBrown);

        String scoreString = "";

        messageLabel = new Label(scoreString, highScoreStyle);
        messageLabel.setPosition((Constants.screenWidth - Constants.loaderWidth) / 2,
                Constants.screenHeight / 2 - Constants.loaderHeight - Constants.btnHeight);
        stage.addActor(messageLabel);
	}

	public void setText(String message) {
	    if(isShowingDialog())
	        messageLabel.setText(message);
    }

    public void draw(Batch batch) {
	    if(mLoaderSprite == null || !isShowing)
	        return;

        elapsedTime = (float) ((int) (System.currentTimeMillis() % 2000) / 2000.0f);

        batch.begin();
        batch.draw(mLoaderSprite,
                (Constants.screenWidth - Constants.loaderWidth) / 2,
                (Constants.screenHeight - Constants.loaderHeight) / 2,
                Constants.loaderWidth / 2, Constants.loaderHeight / 2,
                Constants.loaderWidth, Constants.loaderHeight, 1.0f, 1.0f, 360 * (1 - elapsedTime));
        batch.end();
    }
}
