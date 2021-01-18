package com.acquire.sdk.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.acquireio.AcquireApp;

import io.sentry.Sentry;
import io.sentry.android.core.SentryAndroid;

/**
 * Application class for our demo app.
 *
 * @author Nilay Dani
 */
public class AppComponent extends Application {

    public static String AccID;

    @Override
    public void onCreate() {
        super.onCreate();

        // Use the Sentry DSN (client key) from the Project Settings page on Sentry
//        String sentryDsn = "https://9eeb88b8e4d1448db8de85a16ef4387f@sentry.io/1248245";
        SentryAndroid.init(this, options -> {
            options.setDsn("https://9eeb88b8e4d1448db8de85a16ef4387f@o170358.ingest.sentry.io/1248245");
        });

        SharedPreferences prefs = getSharedPreferences("Acquire_sdk", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String restoredText = prefs.getString("acc_id", null);
        if (restoredText != null) {
            AccID = prefs.getString("acc_id", "");
        } else {
            AccID = "d5885"; //TODO Set Default Acc Id here
            editor.putString("acc_id", AccID);
            editor.apply();
        }
        AcquireApp.INSTANCE.init(this, AccID);
    }
}
