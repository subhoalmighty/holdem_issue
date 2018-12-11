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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.radikal.holdempoker.BushEvent;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.MusicManager;
import com.radikal.holdempoker.graphics.DropDownRectangle;
import com.radikal.holdempoker.graphics.Line;
import com.radikal.holdempoker.models.User;

public class HomeScreen extends ScreenAdapterBase {

    private User mUser;
    private Group rootLayout;
    private TextureAtlas loginAtlas;
    private ImageButton guestBtn, fbLoginBtn, loginBtn;

    public HomeScreen(HoldemPokerGame game) {
        this(game, null);
    }

    public HomeScreen(HoldemPokerGame game, User user) {
        super(game);
        mUser = user;
    }

    @Override
    public void onScreenResize(float width, float height) {

    }

    @Override
    public String getScreenName() {
        return HomeScreen.class.getSimpleName();
    }

    @Override
    public void show() {
        rootLayout = new Group();
        rootLayout.setBounds(0, 0, Constants.screenWidth, Constants.screenHeight);

        game.event.notify("Home Screen", BushEvent.ANALYTICS_SEND_SCREEN);

        loginAtlas = this.game.assetManager.get("img/login/login_atlas.pack", TextureAtlas.class);

        Image bgImage = new Image(game.assetManager.get("img/bg/bg_login.png", Texture.class));
        bgImage.setBounds(0, 0, Constants.screenWidth, Constants.screenHeight);
        rootLayout.addActor(bgImage);

        Drawable guestImageDrawable = new Image(loginAtlas.findRegion("play_as_guest")).getDrawable();

        ImageButton.ImageButtonStyle guestImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                guestImageDrawable, guestImageDrawable, guestImageDrawable, guestImageDrawable, guestImageDrawable, guestImageDrawable);

        guestBtn = new ImageButton(guestImageTextBtnStyle);
        guestBtn.setBounds(Constants.loginTextX, Constants.loginTextY,
                Constants.loginTextWidth, Constants.loginTextHeight);
        guestBtn.addListener(clickListener);
        rootLayout.addActor(guestBtn);

        Drawable fbLoginImageDrawable = new Image(loginAtlas.findRegion("fb_login")).getDrawable();

        ImageButton.ImageButtonStyle fbLoginImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                fbLoginImageDrawable, fbLoginImageDrawable, fbLoginImageDrawable, fbLoginImageDrawable, fbLoginImageDrawable, fbLoginImageDrawable);

        fbLoginBtn = new ImageButton(fbLoginImageTextBtnStyle);
        fbLoginBtn.setBounds(guestBtn.getX(),
                guestBtn.getY() + 1.2f * Constants.loginTextHeight,
                Constants.loginTextWidth, Constants.loginTextHeight);
        fbLoginBtn.addListener(clickListener);
        rootLayout.addActor(fbLoginBtn);

        Image taglineImage = new Image(loginAtlas.findRegion("tagline"));
        taglineImage.setBounds(
                Constants.taglineX, fbLoginBtn.getY() + Constants.taglineHeight,
                Constants.taglineWidth, Constants.taglineHeight);
        rootLayout.addActor(taglineImage);

        Drawable loginImageDrawable = new Image(loginAtlas.findRegion("login")).getDrawable();

        ImageButton.ImageButtonStyle loginImageTextBtnStyle = new ImageButton.ImageButtonStyle(
                loginImageDrawable, loginImageDrawable, loginImageDrawable, loginImageDrawable, loginImageDrawable, loginImageDrawable);

        loginBtn = new ImageButton(loginImageTextBtnStyle);
        loginBtn.setBounds(guestBtn.getX(),
                guestBtn.getY() - 1.2f * Constants.loginTextHeight,
                Constants.loginTextWidth, Constants.loginTextHeight);
        loginBtn.addListener(clickListener);
        rootLayout.addActor(loginBtn);

        stage.addActor(rootLayout);

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
            if (event.getListenerActor() == loginBtn) {
                //showLoader();
                /*ApiInterface apiService = ApiClient.GetClient().create(ApiInterface.class);
                mLoginCall = apiService.login(loginTextField.getText().trim(),
                        passwordTextField.getText().trim());
                mLoginCall.enqueue(LoginScreen.this);*/
                dispose();
                game.setScreen(new LoginScreen(game));
            } else if (event.getListenerActor() == fbLoginBtn) {

            } else if (event.getListenerActor() == guestBtn) {
                //dispose();
                //game.setScreen(new RegisterScreen(game));
            }
        }
    };
}
