package com.acquire.sdk.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.acquire.sdk.app.util.SelectedTab;
import com.acquireio.AcquireApp;

/**
 * Base class of all Activities of the Demo Application.
 *
 * @author Nilay Dani
 */
public abstract class BaseActivity extends AppCompatActivity {

    private SelectedTab selectedTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    public void onClickEvent(View view) {
        SelectedTab tag = SelectedTab.valueOf(view.getTag().toString());
        if (tag == selectedTab) return;
        selectedTab = tag;
        switch (tag) {
            case HOME:
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
               case HELP:
                startActivity(new Intent(this, HelpActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }

    public void selectedTab(SelectedTab tab) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.llHome);
        ((ImageView) ll.getChildAt(0)).setColorFilter(Color.parseColor("#B3B3B3"), PorterDuff.Mode.SRC_ATOP);
        ((TextView) ll.getChildAt(1)).setTextColor(Color.parseColor("#B3B3B3"));
        RelativeLayout relativeLayout = findViewById(R.id.rlBottomBar);
        selectedTab = tab;
        switch (selectedTab) {
            case HOME:
                LinearLayout linearLayout = (LinearLayout) relativeLayout.getChildAt(0);
                ((ImageView) linearLayout.getChildAt(0)).setColorFilter(Color.parseColor("#585AFD"), PorterDuff.Mode.SRC_ATOP);
                ((TextView) linearLayout.getChildAt(1)).setTextColor(Color.parseColor("#585AFD"));
                break;
            case TEST:
                LinearLayout linearLayout1 = (LinearLayout) relativeLayout.getChildAt(1);
                ((ImageView) linearLayout1.getChildAt(0)).setColorFilter(Color.parseColor("#585AFD"), PorterDuff.Mode.SRC_ATOP);
                ((TextView) linearLayout1.getChildAt(1)).setTextColor(Color.parseColor("#585AFD"));
                break;
            case SETTINGS:
                LinearLayout linearLayout2 = (LinearLayout) relativeLayout.getChildAt(2);
                ((ImageView) linearLayout2.getChildAt(0)).setColorFilter(Color.parseColor("#585AFD"), PorterDuff.Mode.SRC_ATOP);
                ((TextView) linearLayout2.getChildAt(1)).setTextColor(Color.parseColor("#585AFD"));
                break;
            case HELP:
                LinearLayout linearLayout3 = (LinearLayout) relativeLayout.getChildAt(3);
                ((ImageView) linearLayout3.getChildAt(0)).setColorFilter(Color.parseColor("#585AFD"), PorterDuff.Mode.SRC_ATOP);
                ((TextView) linearLayout3.getChildAt(1)).setTextColor(Color.parseColor("#585AFD"));
                break;
        }
    }

    public void startSDK(View view) {
        if (AcquireApp.INSTANCE != null) {
            AcquireApp.INSTANCE.startSupportChat();
        }
    }
}
