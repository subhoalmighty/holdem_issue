package com.radikal.holdempoker.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.radikal.holdempoker.BushEvent;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.MusicManager;
import com.radikal.holdempoker.Utils;
import com.radikal.holdempoker.api.ApiClient;
import com.radikal.holdempoker.api.ApiInterface;
import com.radikal.holdempoker.dialog.OkMessageDialog;
import com.radikal.holdempoker.models.GameRoom;
import com.radikal.holdempoker.models.Json_Response;
import com.radikal.holdempoker.models.SavedUserModel;
import com.radikal.holdempoker.models.Token;
import com.radikal.holdempoker.models.User;
import com.radikal.holdempoker.models.UserGroup;

import jdk.nashorn.internal.runtime.logging.DebugLogger;
import retrofit2.Call;
import retrofit2.Response;

public class SplashScreen extends ScreenAdapterBase {

    private Sprite mSprite = null;
    private Label loaderLbl;
    private int elapsedTime = 0;
    private static int TIME_STAY = 200;
    private float percent;
    private boolean isLoaded = false;
    private SavedUserModel savedUserModel;
    private Call<Json_Response> mLoginCall, mGetRoomCall;
    private User mUser;
    private Token mToken;
    private GameRoom[] mGameRooms;

    public SplashScreen(HoldemPokerGame game) {
        super(game);
    }

    @Override
    public void onScreenResize(float width, float height) {

    }

    @Override
    public String getScreenName() {
        return SplashScreen.class.getSimpleName();
    }

    @Override
    public void show() {
        isLoaded = false;

        savedUserModel = game.getCredentials();

        game.event.notify("Splash Screen", BushEvent.ANALYTICS_SEND_SCREEN);

        game.assetManager.load("img/bg/bg_splash.png", Texture.class);
        game.assetManager.finishLoading();

        Texture texture = game.assetManager.get("img/bg/bg_splash.png", Texture.class);
        mSprite = new Sprite(texture);

        //Font
        game.fontManager.loadFonts();
        game.assetManager.finishLoading();

        //Loader
        BitmapFont numFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16);
        Label.LabelStyle numStyle = new Label.LabelStyle(numFont, Color.GRAY);

        loaderLbl = new Label("Loading : 0%", numStyle);
        loaderLbl.setBounds((Constants.screenWidth - 200 * Constants.widthRatio) / 2, 20 * Constants.widthRatio,
                200 * Constants.widthRatio, 60 * Constants.widthRatio);
        loaderLbl.setAlignment(Align.center);

        //Sound
        game.musicManager.load();
        game.assetManager.finishLoading();
        game.musicManager.getMusic();
        game.musicManager.getSound();
        game.musicManager.playMusic();

        game.assetManager.load("img/game/loader.png", Texture.class);
        //BackGrounds
        game.assetManager.load("img/bg/bg_login.png", Texture.class);
        game.assetManager.load("img/bg/bg_dashboard.png", Texture.class);
        game.assetManager.load("img/bg/bg_lobby_panel.png", Texture.class);
        game.assetManager.load("img/bg/bg_game_board.png", Texture.class);
        game.assetManager.load("img/bg/bg_popup.png", Texture.class);
        game.assetManager.load("img/bg/bg_settings.png", Texture.class);
        //game.assetManager.load("img/bg/info_screen_bg.png", Texture.class);

        //Atlas
        game.assetManager.load("img/login/login_atlas.pack", TextureAtlas.class);
        game.assetManager.load("img/lobby/lobby_atlas.pack", TextureAtlas.class);

        game.assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if(mSprite != null)
            batch.draw(mSprite, 0, 0, Constants.screenWidth, Constants.screenHeight);
        batch.end();

        stage.draw();
        stage.act();

        if(!isLoaded && ++elapsedTime >= TIME_STAY && game.assetManager.update()) {
            isLoaded = true;
            callLoginApi();
        }

        percent = Interpolation.linear.apply(percent, game.assetManager.getProgress(), 0.1f);
        loaderLbl.setText("Loading : " + (int) Math.ceil(percent * 100) + "%");
    }

    private void callLoginApi() {
        if( savedUserModel.getLoggedIn() ) {
            ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
            mLoginCall = apiService.login(  savedUserModel.getUsername(),
                                            savedUserModel.getPassword() );
            mLoginCall.enqueue(SplashScreen.this);
        } else {
            dispose();
            game.setScreen(new HomeScreen(game));
        }
    }

    private void callGameRoomsApi() {
        ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
        mGetRoomCall = apiService.getRooms();
        mGetRoomCall.enqueue(SplashScreen.this);
    }

    @Override
    public void dispose() {
        game.assetManager.unload("img/bg/bg_splash.png");
        super.dispose();
    }

    @Override
    public void onResponse(Call<Json_Response> call, Response<Json_Response> response) {
        super.onResponse(call, response);
        if( call.equals(mLoginCall) ) {
            if ( response.body().getCode() == 200 ) {
                UserGroup mUserGroup = response.body().getUserGroup();
                mUser = mUserGroup.user;
                mToken = mUserGroup.token;
                if (mToken != null) {
                    Utils.ACCESS_TOKEN = mToken.accessToken;
                    Utils.REFRESH_TOKEN = mToken.refreshToken;
                    Utils.USER_DISPLAY_NAME = savedUserModel.getUsername();

                    Utils.USER = mUser;
                    game.saveCredentials(   savedUserModel.getLoggedIn(),
                                            savedUserModel.getUsername(),
                                            savedUserModel.getPassword() );
                    callGameRoomsApi();

                    /*showLoader();*/
                }
            } else {
                Gdx.app.log("Error","Error in Login from splash");
            }
        } else if( call.equals(mGetRoomCall) ) {
            if ( response.body().getCode() == 200 ) {
                mGameRooms = response.body().getGameRooms();
                Gdx.app.log("Size of Room",String.valueOf(mGameRooms.length));
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        dispose();
                        game.setScreen(new LobbyScreen(game, true));
                    }
                });
            }
        }
    }
}