package com.radikal.holdempoker.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.dialog.DialogBase;
import com.radikal.holdempoker.dialog.LoaderDialog;
import com.radikal.holdempoker.dialog.QuitDialog;
import com.radikal.holdempoker.models.Json_Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ScreenAdapterBase implements
        Callback<Json_Response>,
        InputProcessor, Screen,
        DialogBase.DialogClickListener {

    protected HoldemPokerGame game;
    protected SpriteBatch batch;
    public Stage stage;
    protected OrthographicCamera gameCam;
    protected Viewport gameViewPort;
    protected InputMultiplexer inputMultiplexer;
    protected QuitDialog quitDialog;
    /*protected ForceLogoutDialog forceLogoutDialog;*/
    protected LoaderDialog mLoaderDialog;


    public ScreenAdapterBase(HoldemPokerGame game) {
        this.game = game;
        this.game.setCurrentScreen(this);
        this.gameCam = new OrthographicCamera();
        gameViewPort = new StretchViewport(Constants.screenWidth, Constants.screenHeight, this.gameCam);
        stage = new Stage(gameViewPort);
        batch = new SpriteBatch();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCatchBackKey(true);
    }

    public Viewport getViewport() {
        return gameViewPort;
    }

    public InputMultiplexer getInputMultiplexer() { return inputMultiplexer; }

    public abstract void onScreenResize(float width, float height);

    public abstract String getScreenName();

    @Override
    public void show() {

        quitDialog = new QuitDialog(game);
        quitDialog.setDialogClickListener(this);

        /*forceLogoutDialog = new ForceLogoutDialog(game);
        forceLogoutDialog.setDialogClickListener(this);*/

        mLoaderDialog = new LoaderDialog(game);

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameCam.combined);
        /*if(quitDialog != null)
            quitDialog.draw(batch);
        if(forceLogoutDialog != null)
            forceLogoutDialog.draw(batch);
        if(mLoaderDialog != null)
            mLoaderDialog.draw(batch);*/

    }

    /*public void setFcmToken(String token) {
        showLoader();
        ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
        mSendPushTokenCall = apiService.updateFcmToken(token);
        mSendPushTokenCall.enqueue(this);
        if(mLoaderDialog != null)
            mLoaderDialog.setText("Configuring...");
    }
    public void forceLogout() {
        if(forceLogoutDialog != null)
            forceLogoutDialog.show();
    }

    public void showLoader() {
        if(mLoaderDialog != null)
            mLoaderDialog.show();
    }

    public void hideLoader() {
        if(mLoaderDialog != null)
            mLoaderDialog.hide();
    }*/


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        /*if (keycode == Input.Keys.BACK) {
            if(forceLogoutDialog != null && forceLogoutDialog.isShowingDialog())
                return true;
            if(quitDialog != null)
                quitDialog.show();
            return true;
        }*/
        return false;

    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        /*if(quitDialog != null)
            quitDialog.dispose();
        if(forceLogoutDialog != null)
            forceLogoutDialog.dispose();
        if(mLoaderDialog != null)
            mLoaderDialog.dispose();*/
        stage.dispose();
        batch.dispose();

    }

    public void setInputProcessor() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void onDialogClicked(int btnTag) {
        if (btnTag == DialogBase.CONTINUE_BTN) {
            quitDialog.hide();
            this.setInputProcessor();
        } else if (btnTag == DialogBase.QUIT_BTN) {
            if(getScreenName().equals("SplashScreen") ||
                    getScreenName().equals("LoginScreen") ||
                    getScreenName().equals("RegisterScreen")) {
                Map<String, String> clipBoardStrings = new HashMap<String, String>();
                clipBoardStrings.put("category", "Logout");
                clipBoardStrings.put("action", "Quit");
                //game.event.notify(clipBoardStrings, BushEvent.ANALYTICS_SEND_EVENT);
                dispose();
                //game.event.notify("Exit", BushEvent.EXIT);
            } else {
                /*showLoader();
                ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
                mExitCall = apiService.logout();
                mExitCall.enqueue(this);*/
            }
        } else if (btnTag == DialogBase.FORCE_LOGOUT) {
            /*showLoader();
            ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
            mForceLogoutCall = apiService.logout();
            mForceLogoutCall.enqueue(this);*/
        }

    }

    protected String getUniqueOrderId() {
        Random r = new Random(System.currentTimeMillis());
        String orderId = "ORDS" + (1 + r.nextInt(2)) * 10000
                + r.nextInt(10000);
        return orderId;
    }

    public void showLoader() {
        if(mLoaderDialog != null)
            mLoaderDialog.show();
    }

    public void hideLoader() {
        if(mLoaderDialog != null)
            mLoaderDialog.hide();
    }


    @Override
    public void onResponse(Call<Json_Response> call, Response<Json_Response> response) {

        Gdx.app.log("Credential", "Super onResponse Called");
        /*Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                hideLoader();
            }
        });

        Gdx.app.log("onResponse ", response.toString() + ", ");
        if (response != null && response.body() != null && response.body().getResponseCode() == ACCESS_TOKEN_EXPIRED) {
            showLoader();
            ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
            mTokenCall = apiService.getNewToken(Utils.REFRESH_TOKEN);
            mTokenCall.enqueue(ScreenAdapterBase.this);
        }

        if(call.equals(mTokenCall)) {
            if (response != null && response.body() != null && response.body().getResponseCode() == 200) {
                Token mToken = response.body().getToken();
                if (mToken != null) {
                    Utils.ACCESS_TOKEN = mToken.accessToken;
                    Utils.REFRESH_TOKEN = mToken.refreshToken;
                    Gdx.app.log("Response", Utils.ACCESS_TOKEN + "");
                    Gdx.app.log("Response", Utils.REFRESH_TOKEN + "");
                    *//*try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*//*
                }
            }
        } else if(call.equals(mExitCall)) {
            if (response.body().getResponseCode() == 200) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> clipBoardStrings = new HashMap<String, String>();
                        clipBoardStrings.put("category", "Logout");
                        clipBoardStrings.put("action", "Quit");
                        game.event.notify(clipBoardStrings, BushEvent.ANALYTICS_SEND_EVENT);
                        dispose();
                        game.event.notify("Exit", BushEvent.EXIT);
                    }
                });
            }
        } else if(call.equals(mForceLogoutCall)) {
            if (response.body().getResponseCode() == 200) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> clipBoardStrings = new HashMap<String, String>();
                        clipBoardStrings.put("category", "Logout");
                        clipBoardStrings.put("action", "Multiple Login");
                        game.event.notify(clipBoardStrings, BushEvent.ANALYTICS_SEND_EVENT);
                        dispose();
                        game.setScreen(new LoginScreen(game));
                    }
                });
            }
        }*/
    }

    @Override
    public void onFailure(Call<Json_Response> call, Throwable t) {
        /*Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                hideLoader();
            }
        });*/

    }

    public static Pixmap getPixmapRectangle(float width, float height, Color color) {
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }
}
