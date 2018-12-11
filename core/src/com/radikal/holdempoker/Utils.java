package com.radikal.holdempoker;

import com.radikal.holdempoker.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean IS_PRODUCTION = true;
    public static int SESSION_TYPE = SessionType.GLOBAL;
    public static String CURRENT_VERSION_NAME = "";
    public static String ACCESS_TOKEN = "";
    public static String REFRESH_TOKEN = "";
    public static String USER_DISPLAY_NAME = "";
    public static User USER;
    //public static GameConfig GAME_CONFIG;
    public static Boolean IS_STARTUP_NOTIFICATION_SHOWN = false;

    //public static String FCM_SENDERS_ID = "53795516460";
    //public static final String PHONE_NUMBER_COUNTRY_PREFIX = "+91 ";
    //public static final String RUPEE_PREFIX = "₹ ";

    /*public static final String MID = IS_PRODUCTION ? "ZGAMSE75333266474640" : "ZIGAME67047711807972";
    public static final String INDUSTRY_TYPE_ID = IS_PRODUCTION ? "Retail109" : "Retail";
    public static final String EMAIL = "imran.khan847@gmail.com";
    public static final String PHONE = "9918713101";
    public static final String CHANNEL_ID = "WAP";
    public static final String WEBSITE = IS_PRODUCTION ? "ZGAMSEWAP" : "APP_STAGING";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";//http://54.204.60.179/api/paytm_callback";
    public static final String CALLBACK_URL_BASE = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";//http://54.204.60.179/api/paytm_callback";*/

    /*public static String getCallbackUrl(String orderId) {
        return CALLBACK_URL_BASE + orderId;
    }*/

    public static class UserType {
        public static int EXPERT = 1;
        public static int PRACTICE = 2;
    }

    public static class ConnectionType {
        public static int NONE = 0;
        public static int MOBILE = 1;
        public static int WIFI = 2;
        public static int OTHER = 3;
    }

    public static class SessionType {
        public static int GLOBAL = 1;
        public static int DUMMY = 2;
    }

    public static class WinnerType {
        public static final int TOP = 1;
        public static final int DAILY = 2;
        public static final int WEEKLY = 3;
        public static final int MONTHLY = 4;
    }

    public static class CouponType {
        public static final int SESSION = 1;
        public static final int WINNER = 2;
    }

    public static final String APP_DATE_FORMAT_FULL = "EEE, MMM d, ''yy";
    public static final String APP_DATE_FORMAT_HALF = "MMM d, ''yy";

    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";//"2017-12-20 23:02:13.000000"
    public static final String SERVER_TIME_FORMAT = "HH:mm:ss";
    public static final String SERVER_DATE_TIME_FORMAT = SERVER_DATE_FORMAT
            + " " + SERVER_TIME_FORMAT;

    public static boolean isPhoneValid(String number) {
        return validateTenDigitPhone(number);
    }

    public static boolean isPasswordValid(String password) {
        return passwordValidation(password);
    }

    public static boolean isDisplayNameValid(String displayName) {
        return displayNameValidation(displayName);
    }

    public static String HoursMintsSecondFormat(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    public static String HourMintFormat(long millis) {
        String hms = String.format("%02d hrs %02d mints", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
        return hms;
    }

    public static String MintsSecondFormat(long millis) {
        String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    public static String getDateFromServer(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    Utils.SERVER_DATE_TIME_FORMAT, Locale.getDefault());
            Date date = sdf.parse(dateStr);
            return (new SimpleDateFormat(format, Locale.getDefault()).format(date));

        } catch (ParseException e) {

        }
        return dateStr;
    }

    public static Date getFormattedDateFromServer(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    Utils.SERVER_DATE_TIME_FORMAT, Locale.getDefault());
            return sdf.parse(dateStr);
        } catch (ParseException e) {

        }
        return new Date();
    }

    public static boolean validateTenDigitPhone(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$");
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public static boolean passwordValidation(String passStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                //Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$");
                Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!$%@#£€*?&_-]{4,}$");
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(passStr);
        return matcher.matches();
    }

    public static boolean displayNameValidation(String str) {
        final Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher matcher = pattern.matcher(str);
        return !matcher.find();
    }

}
