package com.acquire.sdk.app.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckAccountIDTask extends AsyncTask<String, Void, Void> {

    private Activity mActivity;
    private SucessCallBack mCallBack;
    private String URL = "https://app.acquire.io/api/auth/account?id=";
    public CheckAccountIDTask(Activity activity, SucessCallBack sucessCallBack) {
        mActivity = activity;
        mCallBack = sucessCallBack;
    }

    @Override
    protected Void doInBackground(String... strings) {
        checkAccountID(strings[0]);
        return null;
    }

    private void checkAccountID(final String accId) {

        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(URL + accId);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            StringBuilder response = new StringBuilder();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                response.append(current);
            }
            JSONObject jsonObject = new JSONObject(response.toString());
            final boolean success = jsonObject.optBoolean("success");
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCallBack.sucess(success);
                    if (success) {
                        Toast.makeText(mActivity, "Please wait until app re-launches.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mActivity, "Account Id does not exist.\nPlease check your account Id.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
