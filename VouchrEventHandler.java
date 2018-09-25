package com.zellepay.zdk.ux;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.surpriise.vouchrcommon.engine.interfaces.AnalyticsManager;

import java.util.Map;

public class VouchrEventHandler implements AnalyticsManager {
    private final String TAG = getClass().getSimpleName();
    private boolean bNext = false;

    public VouchrEventHandler() { }

    public void startup(Context context) {
        Log.d(TAG, "startup()");
    }
    public void shutdown() {
        Log.d(TAG, "shutdown()");
    }
    public void trackTimedEvent(String event, Map<String, String> eventParams) {}
    public void stopTimedEvent(String event, Map<String, String> eventParams) {}
    public void track(String event, Map<String, String> eventParams) {
        if (event == "Create_Surpriise_Cancel") { // Need to find out Create_Surpriise_Complete
            bNext = true;
        }
        Log.d(TAG, "track("+event+", "+bNext+")");
    }
    public void trackScreen(Activity activity, String screenName) {
        Log.d(TAG, "trackScreen("+screenName+")");
    }
    public void onLogin() {
        Log.d(TAG, "onLogin()");
    }
    public void onStop(Context context) {
        Log.d(TAG, "onStop()");
    }
    public void onDestroy(Context context) {
        Log.d(TAG, "onDestroy()");
        if (bNext) {
        }
    }
}
