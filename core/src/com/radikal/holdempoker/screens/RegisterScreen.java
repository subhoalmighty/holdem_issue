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
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.radikal.holdempoker.BushEvent;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.MusicManager;
import com.radikal.holdempoker.graphics.DropDownRectangle;
import com.radikal.holdempoker.graphics.Line;
import com.radikal.holdempoker.models.User;

public class RegisterScreen extends ScreenAdapterBase {

    private User mUser;
    private Group scrollableRoot;
    private ScrollPane rootScrollPane;
    private TextureAtlas loginAtlas;
    private ImageTextButton loginBtn;
    private TextButton goBackBtn, goToLogin;
    private TextField loginTextField, passwordTextField, confirmPasswordTextField;
    private DropDownRectangle errorRectangle;
    private Label errorLabel;

    public RegisterScreen(HoldemPokerGame game) {
        this(game, null);
    }

    public RegisterScreen(HoldemPokerGame game, User user) {
        super(game);
        mUser = user;
    }

    @Override
    public void onScreenResize(float width, float height) {

    }


    @Override
    public String getScreenName() {
        return RegisterScreen.class.getSimpleName();
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

        Image confirmPasswordBgImage = new Image(loginAtlas.findRegion("input_bg"));
        confirmPasswordBgImage.setBounds(passwordBgImage.getX(),
                passwordBgImage.getY() - Constants.loginTextGap,
                Constants.loginTextWidth, Constants.loginTextHeight);
        scrollableRoot.addActor(confirmPasswordBgImage);

        confirmPasswordTextField = new TextField("", textStyle);
        confirmPasswordTextField.setBounds(
                confirmPasswordBgImage.getX() + 0.4f * Constants.btnWidth,
                confirmPasswordBgImage.getY() + 0.4f * Constants.btnWidth,
                Constants.loginTextWidth - 0.8f * Constants.btnWidth,
                Constants.loginTextHeight - 0.8f * Constants.btnWidth);
        confirmPasswordTextField.setMessageText("Confirm Password");
        //loginTextField.setOnscreenKeyboard(keyboard);
        scrollableRoot.addActor(confirmPasswordTextField);

        ImageTextButton.ImageTextButtonStyle loginImageTextBtnStyle = new ImageTextButton.ImageTextButtonStyle(
                new Image(loginAtlas.findRegion("green_btn")).getDrawable(),
                new Image(loginAtlas.findRegion("green_btn")).getDrawable(),
                new Image(loginAtlas.findRegion("green_btn")).getDrawable(),
                game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16, Constants.colorTextYellow));

        loginBtn = new ImageTextButton("CREATE ACCOUNT", loginImageTextBtnStyle);
        loginBtn.setBounds(confirmPasswordBgImage.getX(), confirmPasswordBgImage.getY() - Constants.loginTextGap,
                Constants.loginTextWidth, Constants.loginTextHeight);
        loginBtn.addListener(clickListener);
        scrollableRoot.addActor(loginBtn);

        Group forgotGroup = new Group();

        TextButton.TextButtonStyle goBackBtnStyle = new TextButton.TextButtonStyle();
        goBackBtnStyle.font = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 14, Constants.colorEditTextBorder);

        String goBackStr = "<";
        GlyphLayout goBackGlyphLayout = new GlyphLayout();
        goBackGlyphLayout.setText(goBackBtnStyle.font, goBackStr);

        goBackBtn = new TextButton(goBackStr, goBackBtnStyle);
        goBackBtn.setBounds(0, 0, goBackGlyphLayout.width, goBackGlyphLayout.height);
        goBackBtn.addListener(clickListener);
        forgotGroup.addActor(goBackBtn);

        TextButton.TextButtonStyle btnExistingUserStyle = new TextButton.TextButtonStyle();
        btnExistingUserStyle.font = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16, Constants.colorBlueOpaque);

        String existingUserStr = "Existing User Login";
        GlyphLayout existingUserGlyphLayout = new GlyphLayout();
        existingUserGlyphLayout.setText(btnExistingUserStyle.font, existingUserStr);

        goToLogin = new TextButton(existingUserStr, btnExistingUserStyle);
        goToLogin.setBounds(goBackGlyphLayout.width + 0.4f * Constants.btnWidth, 0,
                existingUserGlyphLayout.width, existingUserGlyphLayout.height);
        goToLogin.addListener(clickListener);
        forgotGroup.addActor(goToLogin);

        forgotGroup.setPosition((Constants.screenWidth - (goBackGlyphLayout.width + 0.6f * Constants.btnWidth +
                        existingUserGlyphLayout.width)) / 2,
                loginBtn.getY() - Constants.forgotPassTextGap);
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
                    /*ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
                    mLoginCall = apiService.login(loginTextField.getText().trim(),
                            passwordTextField.getText().trim());
                    mLoginCall.enqueue(LoginScreen.this);*/
                }
            } else if (event.getListenerActor() == goBackBtn) {
                dispose();
                game.setScreen(new LoginScreen(game));
            } else if (event.getListenerActor() == goToLogin) {
                dispose();
                game.setScreen(new LoginScreen(game));
            } /*else if (event.getListenerActor() == infoBtn) {
                getKnowUsText();
            }*/
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
                new DropDownRectangle(textField.getX() + textField.getWidth() + 0.8f * Constants.btnWidth,
                        textField.getY(),
                        Constants.btnWidth, Constants.btnHeight,
                        Constants.widthRatio * 5,
                        DropDownRectangle.PointerPosition.left,Constants.widthRatio * 2,
                        Constants.colorPopupBlackBg, Constants.colorBlueOpaque);
        scrollableRoot.addActor(errorRectangle);

        BitmapFont bodyFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 22);
        Label.LabelStyle bodyStyle = new Label.LabelStyle(bodyFont, Constants.colorBlueOpaque);

        errorLabel = new Label("?", bodyStyle);
        errorLabel.setAlignment(Align.center);
        errorLabel.setWrap(true);
        errorLabel.pack();
        errorLabel.setBounds(errorRectangle.getX(), errorRectangle.getY(),
                errorRectangle.getWidth(), errorRectangle.getHeight());
        scrollableRoot.addActor(errorLabel);
        return false;
    }
}
