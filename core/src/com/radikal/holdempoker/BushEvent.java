package com.radikal.holdempoker;

public abstract class BushEvent {

    public final static int EXIT = 0;
    public final static int PAY = 1;
    public final static int CREATE_SYNC = 2;
    public final static int UPDATE_SCORE = 3;
    public final static int START_STOP_SYNC = 4;
    public final static int ANALYTICS_SEND_SCREEN = 5;
    public final static int ANALYTICS_SEND_EVENT = 6;
    public final static int COPY_TO_CLIPBOARD = 7;
    public final static int FB_SHARE = 8;
    public final static int CALL_HELPLINE = 9;
    public final static int SET_NOTIFICATION_PREFERENCE = 10;
    public final static int LOAD_BOTTOM_AD = 11;
    public final static int HIDE_BOTTOM_AD = 12;
    public final static int SHOW_INTERSTITIAL_AD = 13;

    public abstract void notify(Object obj, int msg);
}
