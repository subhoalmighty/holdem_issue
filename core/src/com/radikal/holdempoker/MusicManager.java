package com.radikal.holdempoker;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicManager {

    private HoldemPokerGame game;
    private Music bg;
    private Sound[] sound;
    private float bgVolume = 0.3f;
    private float soundVolume = 0.3f;

    public static final int BUTTON = 0;
    public static final int POP = 1;
    public static final int ADDCOIN = 2;
    public static final int GAMEOVER = 3;
    public static final int SUCCEED = 4;

    public static final int SELECT = 5;
    public static final int FRESH = 6;
    public static final int HAMMER = 7;
    public static final int BOMB = 8;
    public static final int LEVELUP = 9;

    public static final int GOOD = 10;
    public static final int GREAT = 11;
    public static final int COOL = 12;

    public MusicManager(HoldemPokerGame game) { this.game = game; }

    public void load() {
        this.game.assetManager.load("audio/bg.mp3", Music.class);
        this.game.assetManager.load("audio/button.mp3", Sound.class);
        this.game.assetManager.load("audio/pop.ogg", Sound.class);
        this.game.assetManager.load("audio/select.wav", Sound.class);
        this.game.assetManager.load("audio/addcoin.ogg", Sound.class);
        this.game.assetManager.load("audio/bomb.ogg", Sound.class);
        this.game.assetManager.load("audio/hammer.mp3", Sound.class);
        this.game.assetManager.load("audio/fresh.ogg", Sound.class);
        this.game.assetManager.load("audio/succeed.ogg", Sound.class);
        this.game.assetManager.load("audio/gameover.mp3", Sound.class);
        this.game.assetManager.load("audio/levelup.ogg", Sound.class);
        this.game.assetManager.load("audio/cool.ogg", Sound.class);
        this.game.assetManager.load("audio/good.ogg", Sound.class);
        this.game.assetManager.load("audio/great.ogg", Sound.class);
    }

    public void getMusic() {
        bg = this.game.assetManager.get("audio/bg.mp3", Music.class);
        bg.setLooping(true);
        bg.setVolume(bgVolume);
    }

    public void setBgVolume(float volume) {
        bg.setVolume(bgVolume);
    }

    public void getSound() {
        sound = new Sound[13];
        sound[BUTTON] = this.game.assetManager.get("audio/button.mp3", Sound.class);
        sound[POP] = this.game.assetManager.get("audio/pop.ogg", Sound.class);
        sound[SELECT] = this.game.assetManager.get("audio/select.wav", Sound.class);
        sound[ADDCOIN] = this.game.assetManager.get("audio/addcoin.ogg", Sound.class);
        sound[BOMB] = this.game.assetManager.get("audio/bomb.ogg", Sound.class);
        sound[HAMMER] = this.game.assetManager.get("audio/hammer.mp3", Sound.class);
        sound[FRESH] = this.game.assetManager.get("audio/fresh.ogg", Sound.class);
        sound[SUCCEED] = this.game.assetManager.get("audio/succeed.ogg", Sound.class);
        sound[GAMEOVER] = this.game.assetManager.get("audio/gameover.mp3", Sound.class);
        sound[LEVELUP] = this.game.assetManager.get("audio/levelup.ogg", Sound.class);
        sound[COOL] = this.game.assetManager.get("audio/cool.ogg", Sound.class);
        sound[GOOD] = this.game.assetManager.get("audio/good.ogg", Sound.class);
        sound[GREAT] = this.game.assetManager.get("audio/great.ogg", Sound.class);
    }

    public void playSound(int type) {
        if (this.game.soundOn)
            sound[type].setVolume(sound[type].play(), soundVolume);
    }

    public void stopSound() {
        this.game.soundOn = false;
    }

    public void playMusic() {
        if (this.game.musicOn)
            bg.play();
    }

    public void stopMusic() {
        this.game.musicOn = false;
        bg.stop();
    }

    public void dispose() {
        if (bg != null)
            bg.dispose();

        if (sound != null) {
            for (Sound s : sound) {
                s.dispose();
            }
        }
    }


}
