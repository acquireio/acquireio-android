package com.acquire.sdk.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.acquire.sdk.app.util.CheckAccountIDTask;
import com.acquire.sdk.app.util.SelectedTab;
import com.acquire.sdk.app.util.SucessCallBack;
import com.acquireio.AcquireApp;
import com.acquireio.callbacks.OnSessionEvents;
import com.acquireio.enums.CallType;

import java.util.List;

import static com.acquire.sdk.app.AppComponent.AccID;

public class HelpActivity extends BaseActivity implements OnSessionEvents {

    private TextView txtSdkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        txtSdkStatus = findViewById(R.id.txtSdkStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedTab(SelectedTab.HELP);
        ((TextView) findViewById(R.id.txtAccId)).setText(AccID);
    }

    public void changeAccId(View view) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(HelpActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(HelpActivity.this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput.setCancelable(true).setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogBox, int id) {
                new CheckAccountIDTask(HelpActivity.this, new SucessCallBack() {
                    @Override
                    public void sucess(boolean b) {
                        if (b) {
                            SharedPreferences prefs = HelpActivity.this.getSharedPreferences("Acquire_sdk", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.putString("acc_id", userInputDialogEditText.getText().toString());
                            editor.apply();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mStartActivity = new Intent(HelpActivity.this.getApplicationContext(), HelpActivity.class);
                                    int mPendingIntentId = 123456;
                                    PendingIntent mPendingIntent = PendingIntent
                                            .getActivity(HelpActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                                    AlarmManager mgr = (AlarmManager) HelpActivity.this.getSystemService(Context.ALARM_SERVICE);
                                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 50, mPendingIntent);
                                    System.exit(0);
                                }
                            }, 500);

                        } else {
                            changeAccId(null);
                        }
                    }
                }).execute(userInputDialogEditText.getText().toString().trim());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogBox, int id) {
                dialogBox.cancel();
            }
        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    /**
     * Method to place a direct audio call by calling sdk function
     */
    public void placeAudioCall(View view) {
        if (AcquireApp.INSTANCE != null) AcquireApp.INSTANCE.startDirectSupportChat(CallType.AUDIO);
    }

    /**
     * Method to place a direct video call by calling sdk function
     */
    public void placeVideoCall(View view) {
        if (AcquireApp.INSTANCE != null) AcquireApp.INSTANCE.startDirectSupportChat(CallType.VIDEO);
    }

    /**
     * Method to logout by calling sdk function
     */
    public void doLogout(View view) {
        MutableLiveData<Boolean> success = new MutableLiveData<>();
        if (txtSdkStatus.getText().toString().equals("Disconnected")) {
            Toast.makeText(this, "SDK not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        success.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) txtSdkStatus.setText("Disconnected");
            }
        });
        AcquireApp.INSTANCE.logOut(success);
    }

    /**
     * Method to restart sdk using sdk init function
     */
    public void doRestart(View view) {
        AcquireApp.INSTANCE.init(this.getApplication(), AccID);
        AcquireApp.INSTANCE.setSessionListener(this);
    }

    @Override
    public void onSessionConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtSdkStatus.setText("Connected");
            }
        });

    }

    @Override
    public void onSessionDisconnected(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtSdkStatus.setText("Disconnected");
            }
        });

    }

    @Override
    public void onAgentOnline() {

    }

    @Override
    public void onAgentOffline() {

    }

    @Override
    public void onAgentAvailable() {

    }

    @Override
    public void onCallConnected() {

    }

    @Override
    public void onTriggerEvent(String s) {

    }

    @Override
    public void onChatClosed() {

    }

    @Override
    public void onChatWidgetClose() {

    }

    @Override
    public void onTagChange(List<String> list) {

    }

    @Override
    public void noAgentAvailable() {

    }

    @Override
    public void onWaitDialogAppear() {

    }

    @Override
    public void onWaitDialogDisappear() {

    }

    @Override
    public void onCallDisconnected() {

    }
}
