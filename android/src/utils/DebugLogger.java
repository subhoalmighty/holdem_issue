package utils;

import android.util.Log;

import java.util.Formatter;

public class DebugLogger {

    public static final boolean ENABLE_DEBUG = true;

    private DebugLogger() {
    }

    public static final String TAG = "HoldemPoker";

    /**
     * A little trick to reuse a formatter in the same thread
     */
    private static class ReusableFormatter {

        private Formatter formatter;
        private StringBuilder builder;

        public ReusableFormatter() {
            builder = new StringBuilder();
            formatter = new Formatter(builder);
        }

        public String format(String msg, Object... args) {
            formatter.format(msg, args);
            String s = builder.toString();
            builder.setLength(0);
            return s;
        }
    }

    private static final
    ThreadLocal<ReusableFormatter>
            thread_local_formatter =
            new ThreadLocal<ReusableFormatter>() {
                protected ReusableFormatter initialValue() {
                    return new ReusableFormatter();
                }
            };

    public static String format(String msg, Object... args) {
        ReusableFormatter formatter = thread_local_formatter.get();
        return formatter.format(msg, args);
    }

    public static void d(String msg) {
        if(ENABLE_DEBUG)
            Log.d(TAG, msg);
    }

    public static void d(String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.d(TAG, format(msg, args));
    }

    public static void d(Throwable err, String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.d(TAG, format(msg, args), err);
    }

    public static void i(String msg) {
        if(ENABLE_DEBUG)
            Log.i(TAG, msg);
    }

    public static void i(String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.i(TAG, format(msg, args));
    }

    public static void i(Throwable err, String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.i(TAG, format(msg, args), err);
    }

    public static void w(String msg) {
        if(ENABLE_DEBUG)
            Log.w(TAG, msg);
    }

    public static void w(String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.w(TAG, format(msg, args));
    }

    public static void w(Throwable err, String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.w(TAG, format(msg, args), err);
    }

    public static void e(String msg) {
        if(ENABLE_DEBUG)
            Log.e(TAG, msg);
    }

    public static void e(String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.e(TAG, format(msg, args));
    }

    public static void e(Throwable err, String msg, Object... args) {
        if(ENABLE_DEBUG)
            Log.e(TAG, format(msg, args), err);
    }
}
