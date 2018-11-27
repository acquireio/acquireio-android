package com.acquire.sdk.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.acquire.sdk.app.util.CheckAccountIDTask;
import com.acquire.sdk.app.util.SucessCallBack;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (AppComponent.AccID != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);
        } else {
            showInputDialog();
        }
    }

    private void showInputDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(SplashActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_splash, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(SplashActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput.setCancelable(true)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        if (userInputDialogEditText.getText().toString().trim().isEmpty()) {
                            Toast.makeText(SplashActivity.this, "Account id can't be blank", Toast.LENGTH_LONG).show();
                            showInputDialog();
                            return;
                        }

                        new CheckAccountIDTask(SplashActivity.this, new SucessCallBack() {
                            @Override
                            public void sucess(boolean b) {
                                if (b) {
                                    launchApplication(userInputDialogEditText.getText().toString().trim());
                                } else {
                                    showInputDialog();
                                }
                            }
                        }).execute(userInputDialogEditText.getText().toString().trim());

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        dialogBox.cancel();
                        finish();
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.setCanceledOnTouchOutside(false);
        alertDialogAndroid.show();
    }


    private void launchApplication(String accID) {
        SharedPreferences prefs = SplashActivity.this.getSharedPreferences("Acquire_sdk", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("acc_id", accID);
        editor.apply();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mStartActivity = new Intent(SplashActivity.this, SplashActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(SplashActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) SplashActivity.this.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50, mPendingIntent);
                System.exit(0);
            }
        }, 500);
    }

    public void openAcquire(View view) {
        String url = "https://app.acquire.io/signup";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
