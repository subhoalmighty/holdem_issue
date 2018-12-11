package com.radikal.holdempoker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Constants {

    public final static float screenWidth = Gdx.graphics.getWidth();
    public final static float screenHeight = Gdx.graphics.getHeight();
    public final static float referenceWidth = 2560f;
    public final static float referenceHeight = 1440f;

    public final static float widthRatio = screenWidth / referenceWidth;
    public final static float heightRatio = screenHeight / referenceHeight;

    public final static float btnWidth = 138 * widthRatio;
    public final static float btnHeight = btnWidth;

    public final static float headerBtnHeight = 108 * widthRatio;

    public final static float dropDownPointerWidth = 20 * widthRatio;
    public final static float dropDownPointerHeight = 30 * heightRatio;

    //Login
    public final static float taglineWidth = 1286 * widthRatio;
    public final static float taglineX = (screenWidth - taglineWidth) / 2;
    public final static float taglineHeight = 152 * widthRatio;
    public final static float taglineTextGap = 1.05f * taglineHeight;

    //Lobby
    public final static float lobbyTopBtnGap = 0.5f * btnWidth;
    public final static float dashBoardCategoryWidth = 616 * widthRatio;
    public final static float dashBoardCategoryHeight = 918 * heightRatio;

    public final static float dashBoardCategoryRibbonWidth = 685 * widthRatio;
    public final static float dashBoardCategoryRibbonHeight = 265 * heightRatio;

    public final static float dashBoardCategoryImageGap = 45 * heightRatio;

    public final static float settingsPanelWidth = 792 * widthRatio;
    public final static float settingsPanelHeight = 1117 * heightRatio;

    //Settings
    public final static float toggleBtnWidth = 111 * widthRatio;
    public final static float toggleBtnHeight = 69 * widthRatio;

    public final static float settingsBtnWidth = 90 * widthRatio;

    //Other
    public final static float loginTextWidth = 5 * btnWidth;
    public final static float loginTextHeight = btnWidth;
    public final static float loginTextX = (screenWidth - loginTextWidth) / 2;
    public final static float loginTextY = (screenHeight - loginTextHeight) / 2 - 0.2f * btnHeight;

    public final static float forgotPassTextHeight = 0.8f * btnWidth;
    public final static float loginTextGap = 1.0f * loginTextHeight;
    public final static float forgotPassTextGap = 0.6f * loginTextGap;

    //colors
    public final static Color colorDropDownBlack = new Color(0x00000033);
    public final static Color colorDividerBrown = new Color(0x904520ff);
    public final static Color colorPopupBlackBg = new Color(0x000000a6);//65%
    public final static Color colorPopupDarkBlackBg = new Color(0x000000d9);//85%
    public final static Color colorEditTextBorder = new Color(0xc86837ff);
    public final static Color colorBtnYellowWithAlpha = new Color(0xfbf31c33);
    public final static Color colorTextYellow = new Color(0xfff600ff);
    public final static Color colorBlueOpaque = new Color(0x00faffff);
    public final static Color colorBlueWithAlpha = new Color(0xfbf3133);
    public final static Color colorGreenOpaque = new Color(0x1c7a1fff);
    public final static Color colorGreenWithAlpha = new Color(0x47fe00a6);
    public final static Color colorBubbleText = new Color(0x80848aff);
    public final static Color colorBubbleTextLight = new Color(0xa3aea7ff);
    public final static Color colorDashBoardText = new Color(0xa3a3a3ff);
    public final static Color colorTextRed = Color.RED;

    // drawables constants
    public final static float dashboardBtnWidth = 271 * widthRatio;
    public final static float dashboardBtnHeight = dashboardBtnWidth;

    public final static float dashboardLabelWidth = 100 * widthRatio;
    public final static float dashboardLabelHeight = 30 * heightRatio;
    public final static float dashboardLabelGap = 1.2f * dashboardLabelHeight;

    public final static float giftBtnX = screenWidth / 2 - 1.1f * dashboardBtnWidth;
    public final static float giftBtnY = (screenHeight - dashboardBtnHeight) / 2 - 100 * widthRatio;

    public final static float playBtnX = giftBtnX - 1.2f * dashboardBtnWidth;
    public final static float playBtnY = giftBtnY;

    public final static float loaderWidth = 192 * widthRatio;
    public final static float loaderHeight = loaderWidth;

    // card properties constants

    public final static int CLUBS = 1;
    public final static int SPADES = 2;
    public final static int HEARTS = 3;
    public final static int DIAMONDS = 4;

    public final static int ACE = 1;
    public final static int TWO = 2;
    public final static int THREE = 3;
    public final static int FOUR = 4;
    public final static int FIVE = 5;
    public final static int SIX = 6;
    public final static int SEVEN = 7;
    public final static int EIGHT = 8;
    public final static int NINE = 9;
    public final static int TEN = 10;
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;


}
