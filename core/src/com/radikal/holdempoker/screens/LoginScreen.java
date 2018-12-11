package com.radikal.holdempoker.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
import com.radikal.holdempoker.graphics.DropDownRectangle;
import com.radikal.holdempoker.graphics.Line;
import com.radikal.holdempoker.models.GameRoom;
import com.radikal.holdempoker.models.Json_Response;
import com.radikal.holdempoker.models.Token;
import com.radikal.holdempoker.models.User;
import com.radikal.holdempoker.models.UserGroup;

import retrofit2.Call;
import retrofit2.Response;

public class LoginScreen extends ScreenAdapterBase {

    private User mUser;
    private Token mToken;
    private Group scrollableRoot;
    private ScrollPane rootScrollPane;
    private TextureAtlas loginAtlas;
    private ImageTextButton loginBtn;
    private TextButton forgotPasswordBtn, registerUserBtn;
    private TextField loginTextField, passwordTextField;
    private DropDownRectangle errorRectangle;
    private Label errorLabel;
    private GameRoom[] mGameRooms;

    private Call<Json_Response> mLoginCall, mGetRoomCall;

    public LoginScreen(HoldemPokerGame game) {
        this(game, null);
    }

    public LoginScreen(HoldemPokerGame game, User user) {
        super(game);
        mUser = user;
    }

    @Override
    public void onScreenResize(float width, float height) {

    }


    @Override
    public String getScreenName() {
        return LoginScreen.class.getSimpleName();
    }

    @Override
    public void show() {
        scrollableRoot = new Group();
        scrollableRoot.setBounds(0, 0, Constants.screenWidth, Constants.screenHeight);

        game.event.notify("Login Screen", BushEvent.ANALYTICS_SEND_SCREEN);

        loginAtlas = this.game.assetManager.get("img/login/login_atlas.pack", TextureAtlas.class);

        Image bgImage = new Image(game.assetManager.get("img/bg/bg_login.png", Texture.class));
        bgImage.setBounds(0, 0, Constants.screenWidth, Constants.screenHeight);
        scrollableRoot.addActor(bgImage);

        BitmapFont textFieldFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 18, Constants.colorEditTextBorder);

        Pixmap cursorColorMap = getPixmapRectangle(1, Constants.loginTextHeight, Constants.colorEditTextBorder);
        Pixmap selectionColorMap = getPixmapRectangle(1, Constants.loginTextHeight, Constants.colorBtnYellowWithAlpha);
        Pixmap clearColorMap = getPixmapRectangle(Constants.loginTextWidth, Constants.loginTextHeight, Color.CLEAR);

        TextField.TextFieldStyle textStyle = new TextField.TextFieldStyle(
                textFieldFont,
                Constants.colorEditTextBorder,
                new Image(new Texture(cursorColorMap)).getDrawable(),
                new Image(new Texture(selectionColorMap)).getDrawable(),
                new Image(new Texture(clearColorMap)).getDrawable());
        textStyle.messageFont = textFieldFont;
        textStyle.messageFontColor = Constants.colorEditTextBorder;

        Image passwordBgImage = new Image(loginAtlas.findRegion("input_bg"));
        passwordBgImage.setBounds(Constants.loginTextX, Constants.loginTextY,
                Constants.loginTextWidth, Constants.loginTextHeight);
        scrollableRoot.addActor(passwordBgImage);

        passwordTextField = new TextField("", textStyle);
        passwordTextField.setBounds(passwordBgImage.getX() + 0.4f * Constants.btnWidth,
                passwordBgImage.getY() + 0.4f * Constants.btnWidth,
                Constants.loginTextWidth - 0.8f * Constants.btnWidth,
                Constants.loginTextHeight - 0.8f * Constants.btnWidth);
        passwordTextField.setMessageText("Password");
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
        //passwordTextField.setOnscreenKeyboard(keyboard);
        scrollableRoot.addActor(passwordTextField);

        Image loginBgImage = new Image(loginAtlas.findRegion("input_bg"));
        loginBgImage.setBounds(passwordBgImage.getX(),
                passwordBgImage.getY() + Constants.loginTextGap,
                Constants.loginTextWidth, Constants.loginTextHeight);
        scrollableRoot.addActor(loginBgImage);

        loginTextField = new TextField("", textStyle);
        loginTextField.setBounds(
                loginBgImage.getX() + 0.4f * Constants.btnWidth,
                loginBgImage.getY() + 0.4f * Constants.btnWidth,
                Constants.loginTextWidth - 0.8f * Constants.btnWidth,
                Constants.loginTextHeight - 0.8f * Constants.btnWidth);
        loginTextField.setMessageText("Email");
        //loginTextField.setOnscreenKeyboard(keyboard);
        scrollableRoot.addActor(loginTextField);

        Image taglineImage = new Image(loginAtlas.findRegion("tagline"));
        taglineImage.setBounds(
                Constants.taglineX, loginBgImage.getY() + Constants.taglineTextGap,
                Constants.taglineWidth, Constants.taglineHeight);
        scrollableRoot.addActor(taglineImage);

        ImageTextButton.ImageTextButtonStyle loginImageTextBtnStyle = new ImageTextButton.ImageTextButtonStyle(
                new Image(loginAtlas.findRegion("green_btn")).getDrawable(),
                new Image(loginAtlas.findRegion("green_btn")).getDrawable(),
                new Image(loginAtlas.findRegion("green_btn")).getDrawable(),
                game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16, Constants.colorTextYellow));

        loginBtn = new ImageTextButton("LOGIN", loginImageTextBtnStyle);
        loginBtn.setBounds(passwordBgImage.getX(), passwordBgImage.getY() - Constants.loginTextGap,
                Constants.loginTextWidth, Constants.loginTextHeight);
        loginBtn.addListener(clickListener);
        scrollableRoot.addActor(loginBtn);

        TextButton.TextButtonStyle btnForgotPassStyle = new TextButton.TextButtonStyle();
        btnForgotPassStyle.font = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 18, Constants.colorBlueOpaque);

        Group forgotGroup = new Group();

        String forgotPassStr = "Forgot Password";
        GlyphLayout forgotPassGlyphLayout = new GlyphLayout();
        forgotPassGlyphLayout.setText(btnForgotPassStyle.font, forgotPassStr);

        forgotPasswordBtn = new TextButton(forgotPassStr, btnForgotPassStyle);
        forgotPasswordBtn.setBounds(0, 0, forgotPassGlyphLayout.width, forgotPassGlyphLayout.height);
        forgotPasswordBtn.addListener(clickListener);
        forgotGroup.addActor(forgotPasswordBtn);

        Line forgotUnderline = new Line(0, -0.15f * Constants.btnHeight, forgotPassGlyphLayout.width,
                0.05f * Constants.btnWidth, Line.LineDirection.horizontal,
                Color.WHITE);
        forgotGroup.addActor(forgotUnderline);

        Line divider = new Line(forgotPassGlyphLayout.width + 0.2f * Constants.btnWidth, 0,
                0.2f * Constants.btnWidth, forgotPassGlyphLayout.height, Line.LineDirection.vertical,
                Constants.colorDividerBrown);
        forgotGroup.addActor(divider);

        String createAccPassStr = "Create a New Account";
        GlyphLayout createAccPassGlyphLayout = new GlyphLayout();
        createAccPassGlyphLayout.setText(btnForgotPassStyle.font, createAccPassStr);

        registerUserBtn = new TextButton(createAccPassStr, btnForgotPassStyle);
        registerUserBtn.setBounds(forgotPassGlyphLayout.width + 0.4f * Constants.btnWidth, 0,
                createAccPassGlyphLayout.width, createAccPassGlyphLayout.height);
        registerUserBtn.addListener(clickListener);
        forgotGroup.addActor(registerUserBtn);

        Line registerUnderline = new Line(forgotPassGlyphLayout.width + 0.4f * Constants.btnWidth,
                -0.15f * Constants.btnHeight, createAccPassGlyphLayout.width,
                0.05f * Constants.btnWidth, Line.LineDirection.horizontal,
                Color.WHITE);
        forgotGroup.addActor(registerUnderline);

        forgotGroup.setPosition((Constants.screenWidth - (forgotPassGlyphLayout.width + 0.6f * Constants.btnWidth +
                        createAccPassGlyphLayout.width)) / 2,
                loginBtn.getY() - Constants.taglineTextGap);
        scrollableRoot.addActor(forgotGroup);
        rootScrollPane = new ScrollPane(scrollableRoot);
        rootScrollPane.setBounds(0, 0,Constants.screenWidth, Constants.screenHeight);
        rootScrollPane.layout();
        rootScrollPane.setTouchable(Touchable.enabled);
        stage.addActor(rootScrollPane);

        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act();

        super.render(delta);
    }

    EventListener clickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.musicManager.playSound(MusicManager.BUTTON);
            //keyboard.show(false);
            Gdx.input.setOnscreenKeyboardVisible(false);
            if (event.getListenerActor() == loginBtn) {
                if(checkInputErrors()) {
                    //showLoader();

                    ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
                    mLoginCall = apiService.login(  loginTextField.getText().trim(),
                                                    passwordTextField.getText().trim()  );
                    mLoginCall.enqueue(LoginScreen.this);
                }
            } else if (event.getListenerActor() == forgotPasswordBtn) {

            } else if (event.getListenerActor() == registerUserBtn) {
                dispose();
                game.setScreen(new RegisterScreen(game));
            }
        }
    };

    private boolean checkInputErrors() {
        if(loginTextField.getText().equals("")) {
            return showError(loginTextField);
        } else if(passwordTextField.getText().equals("")) {
            return showError(passwordTextField);
        }
        return true;
    }

    private boolean showError(TextField textField) {
        if(errorLabel != null && errorLabel.hasParent()) {
            errorLabel.remove();
        }
        if(errorRectangle != null && errorRectangle.hasParent()) {
            errorRectangle.remove();
        }
        errorRectangle =
                new DropDownRectangle(textField.getX() + textField.getWidth() + 0.6f * Constants.btnWidth,
                        textField.getY() - Constants.btnHeight / 2,
                        Constants.btnWidth, Constants.btnHeight,
                        Constants.widthRatio * 5,
                        DropDownRectangle.PointerPosition.left,Constants.widthRatio * 5,
                        Constants.colorPopupBlackBg, Constants.colorEditTextBorder);
        scrollableRoot.addActor(errorRectangle);

        BitmapFont bodyFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 22);
        Label.LabelStyle bodyStyle = new Label.LabelStyle(bodyFont, Constants.colorEditTextBorder);

        errorLabel = new Label("?", bodyStyle);
        errorLabel.setAlignment(Align.center);
        errorLabel.setWrap(true);
        errorLabel.pack();
        errorLabel.setBounds(errorRectangle.getX(), errorRectangle.getY(),
                errorRectangle.getWidth(), errorRectangle.getHeight());
        scrollableRoot.addActor(errorLabel);
        return false;
    }

    private void callGameRoomsApi() {
        ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
        mGetRoomCall = apiService.getRooms();
        mGetRoomCall.enqueue(LoginScreen.this);
    }

    @Override
    public void onResponse(Call<Json_Response> call, Response<Json_Response> response) {
        super.onResponse(call, response);
        if(call.equals(mLoginCall)) {
            if ( response.body().getCode() == 200 ) {
                UserGroup mUserGroup = response.body().getUserGroup();
                mUser = mUserGroup.user;
                mToken = mUserGroup.token;
                if (mToken != null) {
                    Utils.ACCESS_TOKEN = mToken.accessToken;
                    Utils.REFRESH_TOKEN = mToken.refreshToken;
                    Utils.USER_DISPLAY_NAME = loginTextField.getText().trim();

                    Utils.USER = mUser;
                    game.saveCredentials(true,
                                            loginTextField.getText().trim(),
                                            passwordTextField.getText().trim());

                    callGameRoomsApi();

                    /*Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            dispose();
                            game.setScreen(new LobbyScreen(game, true));
                        }
                    });*/

                    /*showLoader();*/
                }
            } else if ( response.body().getCode() == 590 ){
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        final OkMessageDialog okMessageDialog = new OkMessageDialog(game);
                        okMessageDialog.setHeader("Alert!").setBody("User name or password is incorrect!").
                                setOkBtn("Ok", new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        game.musicManager.playSound(MusicManager.BUTTON);
                                        okMessageDialog.hide();
                                    }
                                });
                        okMessageDialog.show();
                    }
                });
            } else if ( response.body().getCode() == 595 ) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        final OkMessageDialog okMessageDialog = new OkMessageDialog(game);
                        okMessageDialog.setHeader("Alert!").setBody("Error in Login. Try Again!"). // issue token null
                                setOkBtn("Ok", new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                game.musicManager.playSound(MusicManager.BUTTON);
                                okMessageDialog.hide();
                            }
                        });
                        okMessageDialog.show();
                    }
                });
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
