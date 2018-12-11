package com.radikal.holdempoker.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.radikal.holdempoker.BushEvent;
import com.radikal.holdempoker.Constants;
import com.radikal.holdempoker.HoldemPokerGame;

public class GameScreen extends ScreenAdapterBase {

    public static final class GameState {
        public final static int RUNNING = 0;
        public final static int PAUSED = 1;
        public final static int WINNER = 2;
        public final static int FAILED = 3;
    }

    private static final int DEFAULT_PRACTICE_SESSION_ID = -1;
    private static final long THREAD_DELAY = 5000;

    private int gameState;
    private int sessionId;
    private int mTotalParticipants = 0;
    private TextureAtlas gameAtlas;
    private Group rootLayout;
    private Sprite bgSprite;


    public GameScreen(HoldemPokerGame game) {
        super(game);
    }

    @Override
    public void onScreenResize(float width, float height) {

    }

    @Override
    public String getScreenName() {
        return GameScreen.class.getSimpleName();
    }

    @Override
    public void show() {
        rootLayout = new Group();
        rootLayout.setBounds(0, 0, Constants.screenWidth, Constants.screenHeight);

        gameAtlas = game.assetManager.get("img/lobby/lobby_atlas.pack", TextureAtlas.class);
        bgSprite = new Sprite(game.assetManager.get("img/bg/bg_game_board.png", Texture.class));

        game.event.notify("Game Screen", BushEvent.ANALYTICS_SEND_SCREEN);

        stage.addActor(rootLayout);
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

        super.render(delta);
    }
}
