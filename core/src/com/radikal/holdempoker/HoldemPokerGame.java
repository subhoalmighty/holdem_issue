package com.radikal.holdempoker;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.radikal.holdempoker.models.SavedUserModel;
import com.radikal.holdempoker.screens.ScreenAdapterBase;
import com.radikal.holdempoker.screens.SplashScreen;

public class HoldemPokerGame extends Game {
	public BushEvent event;

	public AssetManager assetManager;
	public MusicManager musicManager;
	public FontManager fontManager;
	private String fireBasePushToken = "";
	private ScreenAdapterBase currentScreen;

	public Preferences setPreference, recordPreference, userPreference;
	public boolean soundOn, musicOn;
	private boolean notificationOn;

	public HoldemPokerGame() {
		this(null, "");
	}

	public HoldemPokerGame(BushEvent bushEvent, String versionName) {
		this(bushEvent, versionName, true);
	}

	public HoldemPokerGame(BushEvent bushEvent, String versionName, boolean isLoginVisible) {
		this.event = bushEvent;
		assetManager = new AssetManager();
		musicManager = new MusicManager(this);
		fontManager = new FontManager(this);
		Utils.CURRENT_VERSION_NAME = versionName;

	}

	public void setNotificationOn(boolean isOn) {
		event.notify(isOn, BushEvent.SET_NOTIFICATION_PREFERENCE);
		notificationOn = isOn;
	}

	public boolean getNotificationOn() {
		return notificationOn;
	}

	public void setFireBasePushToken(String token) {
		fireBasePushToken = token;
	}

	public void refreshFireBasePushToken(String token) {
		fireBasePushToken = token;
		if(currentScreen != null && (
				currentScreen.getScreenName().equals("LobbyScreen") ||
						currentScreen.getScreenName().equals("GameScreen") ||
						currentScreen.getScreenName().equals("GiftCouponScreen"))) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					//currentScreen.setFcmToken(fireBasePushToken);
				}
			});
		}
	}

	public String getFireBasePushToken() {
		return fireBasePushToken;
	}

	public void endSession(final int sessionId, final String displayName) {
		if(currentScreen != null &&
				currentScreen.getScreenName().equals("GameScreen")) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					//((GameScreen) currentScreen).endSession(displayName);
				}
			});
		}
	}

	public void networkStateChange(final int connectionType) {
		if(currentScreen != null &&
				currentScreen.getScreenName().equals("GameScreen")) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					//((GameScreen) currentScreen).networkStateChange(connectionType);
				}
			});
		}
	}

	public void setNumberOfPlayers(final int sessionId, final int numPlayers) {
		if(currentScreen != null &&
				currentScreen.getScreenName().equals("GameScreen")) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					//((GameScreen) currentScreen).setNumberOfPlayers(numPlayers);
				}
			});
		}
	}

	public void forceLogout() {
		if(currentScreen != null &&
				currentScreen.getScreenName().equals("LobbyScreen") ||
				currentScreen.getScreenName().equals("GameScreen") ||
				currentScreen.getScreenName().equals("GiftCouponScreen")) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					//currentScreen.forceLogout();
				}
			});
		}
	}


	@Override
	public void create () {
		init();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose () {
		save();
		fontManager.dispose();
		musicManager.dispose();
		assetManager.dispose();

	}

	public void onScreenResize(float width, float height) {
		if(currentScreen != null)
			currentScreen.onScreenResize(width, height);
	}

	public ScreenAdapterBase getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(ScreenAdapterBase screen) {
		currentScreen = screen;
	}


	public void init() {
		recordPreference = Gdx.app.getPreferences("record");
		setPreference = Gdx.app.getPreferences("set");
		userPreference = Gdx.app.getPreferences("user");

		soundOn = setPreference.getBoolean("soundOn", true);
		musicOn = setPreference.getBoolean("musicOn", true);
		notificationOn = setPreference.getBoolean("notificationOn", true);
	}

	public void save() {
		setPreference.putBoolean("musicOn", musicOn).flush();
		setPreference.putBoolean("soundOn", soundOn).flush();
		setPreference.putBoolean("notificationOn", notificationOn).flush();
	}

	public void saveCredentials( Boolean isLoggedIn, String userName, String password ) {
		setPreference.putBoolean("isLoggedIn", isLoggedIn).flush();
		setPreference.putString("userName", userName).flush();
		setPreference.putString("password", password).flush();
	}

	public SavedUserModel getCredentials() {
		SavedUserModel savedUserModel = new SavedUserModel(	setPreference.getBoolean("isLoggedIn", false),
															setPreference.getString("userName", null),
															setPreference.getString("password", null) );
		return savedUserModel;
	}
}
