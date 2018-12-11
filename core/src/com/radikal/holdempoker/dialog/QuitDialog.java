package com.radikal.holdempoker.dialog;

import com.radikal.holdempoker.HoldemPokerGame;

import java.awt.Button;

public class QuitDialog extends DialogBase {

    private Button continueBtn, quitBtn;

    public QuitDialog(HoldemPokerGame game) {
        super(game);
    }

    public void show() {
        super.show();

        /*Pixmap clearColorMap = getPixmapRectangle(Constants.loginTextWidth, Constants.loginTextHeight, Color.CLEAR);
        RoundedCornerRectangle bgTotalSprite = new RoundedCornerRectangle(
                0, 0,
                Constants.screenWidth, Constants.screenHeight,
                0, 0, Constants.colorPopupDarkBlackBg, Color.CLEAR);
        stage.addActor(bgTotalSprite);*/

    }

}
