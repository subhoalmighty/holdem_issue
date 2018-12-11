package com.radikal.holdempoker.sprite.gameTools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.FontManager;
import com.radikal.holdempoker.HoldemPokerGame;
import com.radikal.holdempoker.MusicManager;
import com.radikal.holdempoker.graphics.Line;
import com.radikal.holdempoker.sprite.ClickableStageExtent;

/**
 * Created by SubrataMondal on 04/12/17.
 */

public class GameSettingsSprite extends ClickableStageExtent {

    private TextureAtlas lobbyAtlas;
    private Button musicBtn, soundBtn;
    private Sprite bgSprite;

    public GameSettingsSprite(HoldemPokerGame game) { super(game); }

    @Override
    public void construct() {
        Image bgImage = new Image(game.assetManager.get("img/bg/bg_settings.png", Texture.class));
        bgImage.setBounds(getX(), getY(), getWidth(), getHeight());
        stage.addActor(bgImage);

        lobbyAtlas = this.game.assetManager.get("img/lobby/lobby_atlas.pack", TextureAtlas.class);

        Image logoImage = new Image(new TextureRegionDrawable(lobbyAtlas.findRegion("settings_popup_icon")));
        logoImage.setBounds(getX() + 0.8f * Constants.btnWidth,
                getY() + getHeight() - Constants.headerBtnHeight - 0.6f * Constants.btnWidth,
                Constants.headerBtnHeight, Constants.headerBtnHeight);
        stage.addActor(logoImage);

        BitmapFont settingsFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 18);
        Label.LabelStyle settingsStyle = new Label.LabelStyle(settingsFont, Constants.colorGreenOpaque);

        Label settingsLabel = new Label("SETTINGS", settingsStyle);
        settingsLabel.setPosition(logoImage.getX() + logoImage.getWidth() + 0.2f * Constants.btnWidth,
                getY() + getHeight() - Constants.headerBtnHeight - 0.6f * Constants.btnWidth);
        stage.addActor(settingsLabel);

        Line h1 = new Line(getX() + Constants.dashBoardCategoryImageGap,
                settingsLabel.getY() - 0.1f * Constants.btnHeight,
                getWidth() - Constants.dashBoardCategoryImageGap, 2,
                Line.LineDirection.horizontal,Color.GRAY);
        stage.addActor(h1);

        /*BitmapFont textFont = game.fontManager.getFont(FontManager.TYPE_OSWALD_MEDIUM, 16);
        Label.LabelStyle textStyle = new Label.LabelStyle(textFont, Color.GRAY);

        Label musicLabel = new Label("SOUND", textStyle);
        musicLabel.setPosition(settingsLabel.getX(),
                settingsLabel.getY() - Constants.toggleBtnHeight - 0.0f * Constants.btnHeight);
        stage.addActor(musicLabel);

        musicBtn = new Button(new TextureRegionDrawable(lobbyAtlas.findRegion("settings_toggle_off")),
                new TextureRegionDrawable(lobbyAtlas.findRegion("settings_toggle_off")),
                new TextureRegionDrawable(lobbyAtlas.findRegion("settings_toggle_on")));
        musicBtn.setBounds(getX() + getWidth() - Constants.toggleBtnWidth - 0.4f * Constants.btnWidth,
                musicLabel.getY(),
                Constants.toggleBtnWidth,
                Constants.toggleBtnHeight);
        musicBtn.addListener(listener);
        stage.addActor(musicBtn);
        musicBtn.setChecked(game.musicOn);

        Label soundLabel = new Label("NOTIFICATION", textStyle);
        soundLabel.setPosition(musicLabel.getX(),
                musicLabel.getY() - Constants.toggleBtnHeight - 0.4f * Constants.btnHeight);
        stage.addActor(soundLabel);

        soundBtn = new Button(new TextureRegionDrawable(lobbyAtlas.findRegion("settings_toggle_on")),
                new TextureRegionDrawable(lobbyAtlas.findRegion("settings_toggle_on")),
                new TextureRegionDrawable(lobbyAtlas.findRegion("settings_toggle_on")));
        soundBtn.setBounds(getX() +  getWidth() - Constants.toggleBtnWidth - 0.4f * Constants.btnWidth,
                soundLabel.getY(),
                Constants.toggleBtnWidth,
                Constants.toggleBtnHeight);
        soundBtn.addListener(listener);
        stage.addActor(soundBtn);
        soundBtn.setChecked(game.soundOn);*/
    }

    private EventListener listener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.musicManager.playSound(MusicManager.BUTTON);

            if (event.getListenerActor() == musicBtn) {
                if (game.musicOn) {
                    musicBtn.setChecked(false);
                    game.musicManager.stopMusic();
                } else {
                    musicBtn.setChecked(true);
                    game.musicOn = true;
                    game.musicManager.playMusic();
                }
            } else if (event.getListenerActor() == soundBtn) {
                if (game.soundOn) {
                    soundBtn.setChecked(false);
                    game.musicManager.stopSound();
                } else {
                    soundBtn.setChecked(true);
                    game.soundOn = true;
                }
            }
        }
    };
}