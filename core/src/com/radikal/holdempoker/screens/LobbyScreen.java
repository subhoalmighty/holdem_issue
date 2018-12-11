package com.radikal.holdempoker.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.radikal.holdempoker.BushEvent;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.MusicManager;
import com.radikal.holdempoker.models.AllRooms;
import com.radikal.holdempoker.models.Room;
import com.radikal.holdempoker.sprite.gameTools.GameSettingsSprite;


public class LobbyScreen extends ScreenAdapterBase {

    private boolean isLogin = false;
    private Group rootLayout;
    private GameSettingsSprite gameSettingsSprite;
    private TextureAtlas dashboardAtlas;
    private Sprite bgSprite;
    private ImageButton profileBtn, subscriptionBtn, leaderBoardBtn, settingsBtn;

    private AllRooms gameRooms = new AllRooms(new Room[]{
            new Room(1, "Texas Holdem Poker Game", "dummy/px_01.jpg"),
            new Room(2, "Tournament 1", "dummy/px_02.jpg"),
            new Room(3, "Tournament 2", "dummy/px_03.jpg"),
            new Room(4, "Tournament 3", "dummy/px_04.jpg")
    });

    public LobbyScreen(HoldemPokerGame game) {
        this(game, true);
    }

    public LobbyScreen(HoldemPokerGame game, boolean isLogin) {
        super(game);
        this.isLogin = isLogin;
    }

    @Override
    public void onScreenResize(float width, float height) {

    }

    @Override
    public String getScreenName() {
        return LobbyScreen.class.getSimpleName();
    }

    public void show() {
        rootLayout = new Group();
        rootLayout.setBounds(0, 0, Constants.screenWidth, Constants.screenHeight);

        dashboardAtlas = game.assetManager.get("img/lobby/lobby_atlas.pack", TextureAtlas.class);
        bgSprite = new Sprite(game.assetManager.get("img/bg/bg_dashboard.png", Texture.class));

        game.event.notify("Lobby Screen", BushEvent.ANALYTICS_SEND_SCREEN);

        Drawable settingsImageDrawable = new Image(dashboardAtlas.findRegion("settings_btn")).getDrawable();
        ImageButton.ImageButtonStyle settingsImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                settingsImageDrawable, settingsImageDrawable, settingsImageDrawable,
                settingsImageDrawable, settingsImageDrawable, settingsImageDrawable);

        settingsBtn = new ImageButton(settingsImageTextBtnStyle);
        settingsBtn.setBounds(Constants.screenWidth - 2.0f * Constants.btnWidth - Constants.lobbyTopBtnGap,
                Constants.screenHeight - 2.0f * Constants.btnHeight - Constants.lobbyTopBtnGap,
                2.0f * Constants.btnWidth, 2.0f * Constants.btnHeight);
        settingsBtn.addListener(btnClickListener);
        rootLayout.addActor(settingsBtn);

        Drawable leaderboardImageDrawable = new Image(dashboardAtlas.findRegion("leaderboard_btn")).getDrawable();
        ImageButton.ImageButtonStyle leaderboardImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                leaderboardImageDrawable, leaderboardImageDrawable, leaderboardImageDrawable,
                leaderboardImageDrawable, leaderboardImageDrawable, leaderboardImageDrawable);

        leaderBoardBtn = new ImageButton(leaderboardImageTextBtnStyle);
        leaderBoardBtn.setBounds(settingsBtn.getX() - 2.0f * Constants.btnWidth,
                settingsBtn.getY(),
                2.0f * Constants.btnWidth, 2.0f * Constants.btnHeight);
        leaderBoardBtn.addListener(btnClickListener);
        rootLayout.addActor(leaderBoardBtn);

        Drawable subscriptionImageDrawable = new Image(dashboardAtlas.findRegion("subscription_btn")).getDrawable();
        ImageButton.ImageButtonStyle subscriptionImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                subscriptionImageDrawable, subscriptionImageDrawable, subscriptionImageDrawable,
                subscriptionImageDrawable, subscriptionImageDrawable, subscriptionImageDrawable);

        subscriptionBtn = new ImageButton(subscriptionImageTextBtnStyle);
        subscriptionBtn.setBounds(leaderBoardBtn.getX() - 2.0f * Constants.btnWidth,
                leaderBoardBtn.getY(),
                2.0f * Constants.btnWidth, 2.0f * Constants.btnHeight);
        subscriptionBtn.addListener(btnClickListener);
        rootLayout.addActor(subscriptionBtn);

        Drawable profileImageDrawable = new Image(dashboardAtlas.findRegion("profile_btn")).getDrawable();
        ImageButton.ImageButtonStyle profileImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                profileImageDrawable, profileImageDrawable, profileImageDrawable,
                profileImageDrawable, profileImageDrawable, profileImageDrawable);

        profileBtn = new ImageButton(profileImageTextBtnStyle);
        profileBtn.setBounds(subscriptionBtn.getX() - 2.0f * Constants.btnWidth,
                subscriptionBtn.getY(),
                2.0f * Constants.btnWidth, 2.0f * Constants.btnHeight);
        profileBtn.addListener(btnClickListener);
        rootLayout.addActor(profileBtn);

        BitmapFont textFieldFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16, Constants.colorEditTextBorder);

        Group paneHolder = new Group();
        paneHolder.setBounds(0, 0,
                Constants.btnWidth + Constants.dashBoardCategoryRibbonWidth * gameRooms.rooms.length, Constants.dashBoardCategoryHeight);
        for (int i = 0; i < gameRooms.rooms.length; i++) {
            paneHolder.addActor(getListAdapter(gameRooms.rooms[i], i, textFieldFont));
        }

        ScrollPane scrollPane = new ScrollPane(paneHolder);
        scrollPane.setBounds(0, Constants.btnWidth,
                Constants.screenWidth,
                Constants.dashBoardCategoryHeight);
        scrollPane.layout();
        scrollPane.setTouchable(Touchable.childrenOnly);
        rootLayout.addActor(scrollPane);

        gameSettingsSprite = new GameSettingsSprite(game);
        gameSettingsSprite.setBounds(Constants.screenWidth - Constants.settingsPanelWidth,
                (Constants.screenHeight - Constants.settingsPanelHeight) / 2,
                Constants.settingsPanelWidth, Constants.settingsPanelHeight);
        gameSettingsSprite.construct();

        stage.addActor(rootLayout);
    }

    private Group getListAdapter(Room room, int position, BitmapFont adapterFont) {
        Group listAdapter = new Group();
        listAdapter.setBounds( Constants.btnWidth + position * Constants.dashBoardCategoryRibbonWidth, 0,
                Constants.dashBoardCategoryRibbonWidth, Constants.dashBoardCategoryHeight);

        Image adapterBgImage = new Image(new Sprite(game.assetManager.get("img/bg/bg_lobby_panel.png", Texture.class)));
        adapterBgImage.setBounds(0, 0, Constants.dashBoardCategoryWidth, Constants.dashBoardCategoryHeight);
        adapterBgImage.setScaling(Scaling.fit);
        listAdapter.addActor(adapterBgImage);

        Texture texture1 = new Texture(Gdx.files.internal(room.display_img));
        Image categoryImage = new Image(texture1);
        categoryImage.setBounds(Constants.dashBoardCategoryImageGap,
                Constants.dashBoardCategoryHeight / 2 - Constants.dashBoardCategoryImageGap,
                Constants.dashBoardCategoryWidth - 2.0f * Constants.dashBoardCategoryImageGap,
                Constants.dashBoardCategoryHeight / 2);
        listAdapter.addActor(categoryImage);

        Drawable adapterRibbonImageDrawable = new Image(dashboardAtlas.findRegion("ribbon_text_area")).getDrawable();
        ImageTextButton.ImageTextButtonStyle adapterImageTextBtnStyle = new ImageTextButton.ImageTextButtonStyle(
                adapterRibbonImageDrawable, adapterRibbonImageDrawable, adapterRibbonImageDrawable,
                adapterFont);

        ImageTextButton adapterRibbonImage = new ImageTextButton(room.title.toUpperCase(), adapterImageTextBtnStyle);
        adapterRibbonImage.setBounds((Constants.dashBoardCategoryWidth - Constants.dashBoardCategoryRibbonWidth) / 2,
                Constants.dashBoardCategoryHeight / 2 - Constants.dashBoardCategoryRibbonHeight,
                Constants.dashBoardCategoryRibbonWidth, Constants.dashBoardCategoryRibbonHeight);
        adapterRibbonImage.getLabelCell().width(Constants.dashBoardCategoryWidth);
        adapterRibbonImage.getLabel().setWrap(true);
        adapterRibbonImage.invalidate();
        listAdapter.addActor(adapterRibbonImage);

        Drawable adapterInfoPlayBtnDrawable = new Image(dashboardAtlas.findRegion("info_play_btn")).getDrawable();
        ImageTextButton.ImageTextButtonStyle adapterInfoPlayButtonStyle = new ImageTextButton.ImageTextButtonStyle(
                adapterInfoPlayBtnDrawable, adapterInfoPlayBtnDrawable, adapterInfoPlayBtnDrawable,
                adapterFont);

        ImageTextButton adapterInfoBtn = new ImageTextButton("INFO", adapterInfoPlayButtonStyle);
        adapterInfoBtn.setBounds(Constants.dashBoardCategoryWidth / 2 - Constants.dashBoardCategoryImageGap - 1.6f * Constants.btnWidth,
                (Constants.dashBoardCategoryHeight / 2 - Constants.dashBoardCategoryRibbonHeight - Constants.btnHeight) / 2 +
                        Constants.dashBoardCategoryImageGap,
                2.0f * Constants.btnWidth, Constants.btnHeight);
        adapterInfoBtn.setName("infoBtn_" + position);
        adapterInfoBtn.addListener(btnClickListener);
        listAdapter.addActor(adapterInfoBtn);

        ImageTextButton adapterPlayBtn = new ImageTextButton("PLAY", adapterInfoPlayButtonStyle);
        adapterPlayBtn.setBounds(Constants.dashBoardCategoryWidth / 2 + Constants.dashBoardCategoryImageGap - 0.4f * Constants.btnWidth,
                (Constants.dashBoardCategoryHeight / 2 - Constants.dashBoardCategoryRibbonHeight - Constants.btnHeight) / 2 +
                        Constants.dashBoardCategoryImageGap,
                2.0f * Constants.btnWidth, Constants.btnHeight);
        adapterPlayBtn.setName("playBtn_" + position);
        adapterPlayBtn.addListener(btnClickListener);
        listAdapter.addActor(adapterPlayBtn);

        return listAdapter;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if(bgSprite != null)
            batch.draw(bgSprite, 0, 0, Constants.screenWidth, Constants.screenHeight);
        batch.end();

        stage.draw();
        stage.act();

        if(gameSettingsSprite != null)
            gameSettingsSprite.draw(batch);

        super.render(delta);
    }

    EventListener btnClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.musicManager.playSound(MusicManager.BUTTON);

            if(event.getListenerActor().getName() != null) {
                if (event.getListenerActor().getName().contains("infoBtn_")) {
                    //dispose();
                    //game.setScreen(new GameScreen(game));
                    /*showLoader();
                    ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
                    mGetSessionDetailCall = apiService.getSessionDetail();
                    mGetSessionDetailCall.enqueue(LobbyScreen.this);*/
                } else if (event.getListenerActor().getName().contains("playBtn_")) {
                    /*if(!Utils.IS_STARTUP_NOTIFICATION_SHOWN && mPopupTimerTask != null)
                        mPopupTimerTask.cancel();*/
                    dispose();
                    game.setScreen(new GameScreen(game));
                }
            } else if (event.getListenerActor() == settingsBtn) {
                if(!gameSettingsSprite.isVisible()) {
                    gameSettingsSprite.show();
                } else {
                    gameSettingsSprite.hide();
                }
            }
        }
    };
}