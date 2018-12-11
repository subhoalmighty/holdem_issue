package com.radikal.holdempoker;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.Random;

import utils.DebugLogger;

public class AndroidLauncher extends AndroidApplication {
	public static final String TAG = "AndroidLauncher";
	public static final int REQUEST_CODE = 1;
	public static final int FB_REQUEST_CODE = 2;
	private Context mContext;
	private HoldemPokerGame game;
	private String fireBasePushToken = "";
	public Random random;
	private IntentFilter mIntentFilter;
	//private Tracker mTracker;
	//private CallbackManager fbCallbackManager;
	//private ShareDialog fbShareDialog;
	private int CALL_PHONE_REQUEST = 1871;
	private String mPhoneNo = "";
	//private AdView mAdView;
	//private InterstitialAd mInterstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		String version = "";
		try {
			PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		FrameLayout gameHolderFrameLayout = findViewById(R.id.game_holder_frame_layout);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		game = new HoldemPokerGame(event, version);
		gameHolderFrameLayout.addView(initializeForView(game, config));
		setListenerToRootView();
		//game.setFireBasePushToken(FirebaseInstanceId.getInstance().getToken());

		random = new Random();
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("PUSH_TOKEN_REFRESHED");
		mIntentFilter.addAction("PUSH_RECEIVED");
		mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

		//MainApplication application = (MainApplication) getApplication();
		//mTracker = application.getDefaultTracker();

	}

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DebugLogger.i(TAG + " onWindowFocusChanged: " + hasFocus);
        /*if (hasFocus) {
            hideSystemUI();
        } else {
            showSystemUI();
        }*/
    }

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    private void showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mTracker.setScreenName("Launcher Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        registerReceiver(mBroadcastReceiver, mIntentFilter);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        //unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListenerToRootView();
    }

    public void setListenerToRootView() {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
    }

    public void removeListenerToRootView() {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect visibleDisplayFrame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleDisplayFrame);
            game.onScreenResize(visibleDisplayFrame.width(), visibleDisplayFrame.height());
        }
    };

	BushEvent event = new BushEvent() {
		@Override
		public void notify(final Object object, int tag) {
			Message msg = handler.obtainMessage();
			msg.what = tag;
			handler.sendMessage(msg);
			switch (tag) {
				case BushEvent.PAY:
					/*PayTmTransaction txn = (PayTmTransaction) object;
					Intent intent = new Intent(getBaseContext(), PaytmActivity.class);
					intent.putExtra("EXTRA_PAYTM_AMOUNT", txn.amount);
					intent.putExtra("EXTRA_PAYTM_ORDER_ID", txn.orderId);
					intent.putExtra("EXTRA_PAYTM_CUST_ID", txn.customerId);
					intent.putExtra("EXTRA_PAYTM_CHECHSUM", txn.checksum);
					startActivityForResult(intent, REQUEST_CODE);*/

					//startPayTmTransaction(txn.amount, txn.orderId, txn.checksum);
					break;
				case BushEvent.CREATE_SYNC:

					break;
				case BushEvent.UPDATE_SCORE:

					break;
				case BushEvent.START_STOP_SYNC:

					break;
				case BushEvent.ANALYTICS_SEND_SCREEN:
					/*if (mTracker != null) {
						mTracker.setScreenName((String) object);
						mTracker.send(new HitBuilders.ScreenViewBuilder().build());
					}*/
					break;
				case BushEvent.ANALYTICS_SEND_EVENT:
					/*if (mTracker != null) {
						Map<String, String> actionStrings = (HashMap<String, String>) object;
						mTracker.send(new HitBuilders.EventBuilder()
								.setCategory(actionStrings.get("category"))
								.setAction(actionStrings.get("action"))
								.build());
					}*/
					break;
				case BushEvent.COPY_TO_CLIPBOARD:
					/*handler.post(new Runnable() {
						@Override
						public void run() {
							Map<String, String> clipBoardStrings = (HashMap<String, String>) object;
							ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
							ClipData clip = ClipData.newPlainText(clipBoardStrings.get("label"), clipBoardStrings.get("text"));
							clipboard.setPrimaryClip(clip);
							Toast.makeText(getApplicationContext(), "Coupon copied to clipboard.", Toast.LENGTH_LONG).show();
						}
					});*/
					break;
				case BushEvent.FB_SHARE:
					/*handler.post(new Runnable() {
						@Override
						public void run() {
							fbShareDialog.registerCallback(fbCallbackManager, new FacebookCallback<Sharer.Result>() {
								@Override
								public void onSuccess(Sharer.Result result) {
									//DebugLogger.e(TAG + " " + result.getPostId());
									//if(result.getPostId() != null) {
									Toast.makeText(getBaseContext(), "Posted on Facebook. You receive 5000 points.", Toast.LENGTH_LONG).show();
									game.fbShareSuccess();
									//} else {
									//Toast.makeText(getBaseContext(), "Facebook posting canceled.", Toast.LENGTH_LONG).show();
									//game.fbShareCancel();
									//}
								}

								@Override
								public void onCancel() {
									DebugLogger.e(TAG);
									Toast.makeText(getBaseContext(), "Facebook posting canceled.", Toast.LENGTH_LONG).show();
									game.fbShareCancel();
								}

								@Override
								public void onError(FacebookException error) {
									DebugLogger.e(TAG + error);
									Toast.makeText(getBaseContext(), "Facebook posting error.", Toast.LENGTH_LONG).show();
									game.fbShareError();
								}
							}, FB_REQUEST_CODE);
							if (ShareDialog.canShow(ShareLinkContent.class)) {
								ShareLinkContent linkContent = new ShareLinkContent.Builder()
										.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.ziGames.bubbleBlast"))
										.setQuote("I’m playing Fortune game and earned 5000 bonus!\n" +
												"You can also play and win Paytm cash every hour.")
										.build();
								fbShareDialog.show(linkContent, ShareDialog.Mode.FEED);
							}
						}
					});*/
					break;
				case BushEvent.CALL_HELPLINE:
					/*String helplineNo = (String) object;
					showCallIntent(helplineNo);*/
					break;
				case BushEvent.SET_NOTIFICATION_PREFERENCE:
					boolean isOn = (boolean) object;
					SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
					editor.putBoolean("notifications_new_message", isOn);
					editor.apply();
					break;
				case BushEvent.LOAD_BOTTOM_AD:
					/*handler.post(new Runnable() {
						@Override
						public void run() {
							if (mAdView != null) {
								//mAdView.setVisibility(View.VISIBLE);
                                *//*AdRequest adRequest = new AdRequest.Builder()
                                        .addTestDevice("5F88501C187A901F338A91E93B78907E").build();
                                mAdView.loadAd(adRequest);*//*
							}
						}
					});*/
					break;
				case BushEvent.HIDE_BOTTOM_AD:
					/*handler.post(new Runnable() {
						@Override
						public void run() {
							if (mAdView != null) {
								//mAdView.setVisibility(View.GONE);
							}
						}
					});*/
					break;
				case BushEvent.SHOW_INTERSTITIAL_AD:
					/*handler.post(new Runnable() {
						@Override
						public void run() {
							if (mInterstitialAd.isLoaded()) {
								mInterstitialAd.show();
							}
						}
					});*/
					break;
				case BushEvent.EXIT:
					Gdx.app.exit();
					((Activity) mContext).finish();
					break;
			}
		}
	};
}
