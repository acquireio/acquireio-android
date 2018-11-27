package com.acquire.sdk.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.acquire.sdk.app.util.CustomFlag;
import com.acquire.sdk.app.util.SelectedTab;
import com.acquireio.AcquireApp;
import com.acquireio.builder.CoBrowse;
import com.acquireio.builder.SupportFAB;
import com.acquireio.sdk.model.Visitor;
import com.skydoves.colorpickerview.AlphaTileView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import static com.acquireio.builder.SupportFAB.FAB_POSITION.BOTTOM_LEFT;
import static com.acquireio.builder.SupportFAB.FAB_POSITION.BOTTOM_RIGHT;

public class SettingActivity extends BaseActivity {

    private AlphaTileView alphaTileView, maskColorView;
    private AlphaTileView alphaTileView1;

    private TextView txtTitle;
    private TextView txtCobId;
    private ScrollView scrollView2;
    private EditText etTimemax;
    private EditText etTimemin;
    private EditText etQuality;
    private EditText etCOBCode;
    private Switch swStopBtn;
    private Switch swConfirm;
    private Spinner spnFABSize;
    private Spinner spnFABPos;
    private EditText etFABText;
    private EditText etFABele;
    private Switch swAnim;
    private Button btnSaveCob;
    private Button btnSaveFAB;
    private RelativeLayout rlBottomBar;
    private LinearLayout llHome;
    private LinearLayout llHelp;
    private CoBrowse cobrowse;

    private int fabColor = -2, fabTextColor = -1, maskColor = -3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();

    }

    private void initViews() {
        txtTitle = findViewById(R.id.txtTitle);
        txtCobId = findViewById(R.id.txtCobId);
        scrollView2 = findViewById(R.id.scrollView2);
        etTimemax = findViewById(R.id.etTimemax);
        etTimemin = findViewById(R.id.etTimeMin);
        etQuality = findViewById(R.id.etQuality);
        etCOBCode = findViewById(R.id.etCOBCode);
        swStopBtn = findViewById(R.id.swStopBtn);
        swConfirm = findViewById(R.id.swConfirm);
        spnFABSize = findViewById(R.id.spnFABSize);
        spnFABPos = findViewById(R.id.spnFABPos);
        etFABText = findViewById(R.id.etFABText);
        etFABele = findViewById(R.id.etFABele);
        swAnim = findViewById(R.id.swAnim);
        btnSaveCob = findViewById(R.id.btnSaveCob);
        btnSaveFAB = findViewById(R.id.btnSaveFAB);
        rlBottomBar = findViewById(R.id.rlBottomBar);
        llHome = findViewById(R.id.llHome);
        llHelp = findViewById(R.id.llHelp);

        maskColorView = findViewById(R.id.maskColorView);
        alphaTileView = findViewById(R.id.alphaTileView);
        alphaTileView1 = findViewById(R.id.alphaTileView1);
        if (AcquireApp.getInstance() != null) {
            maskColorView.setPaintColor(AcquireApp.getInstance().getMaskingViewColor());
            maskColor = AcquireApp.getInstance().getMaskingViewColor();
        }
        alphaTileView.setPaintColor(Color.BLACK);
        alphaTileView1.setPaintColor(Color.BLACK);
        txtCobId.setText(Visitor.cob_code);
        setDataFromBuilder();
    }

    private void setDataFromBuilder() {

        // For Cobrowse data
        etTimemax.setText(String.valueOf(AcquireApp.coBrowse.getMAX_UPDATE_TIME()));
        etTimemin.setText(String.valueOf(AcquireApp.coBrowse.getMIN_UPDATE_TIME()));
        etQuality.setText(String.valueOf(AcquireApp.coBrowse.getBITMAP_QUALITY()));
        etCOBCode.setText(String.valueOf(AcquireApp.coBrowse.getCoBrowseCODE() == null ? "" : AcquireApp.coBrowse.getCoBrowseCODE()));
        swStopBtn.setChecked(AcquireApp.coBrowse.getHideStopButton());
        swConfirm.setChecked(AcquireApp.coBrowse.isConfirmStop());

        if (AcquireApp.supportFAB == null) {

            return;
        }

        spnFABSize.setSelection(AcquireApp.supportFAB.getFabSize());
        spnFABPos.setSelection(AcquireApp.supportFAB.getFabIconPosition() == BOTTOM_LEFT ? 0 : 1);
        etFABText.setText(AcquireApp.supportFAB.getFabText());
        alphaTileView.setPaintColor(AcquireApp.supportFAB.getFabTextColor());
        fabTextColor = AcquireApp.supportFAB.getFabTextColor();
        etFABele.setText(String.valueOf(AcquireApp.supportFAB.getFabElevation()));
        alphaTileView1.setPaintColor(AcquireApp.supportFAB.getFabColor());
        fabColor = AcquireApp.supportFAB.getFabColor();
        swAnim.setChecked(AcquireApp.supportFAB.isfabAnimation());
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedTab(SelectedTab.SETTINGS);
    }

    public void openColorPicker(View view) {
        int picker = 0;
        if (view.getTag() != null && view.getTag().toString().equalsIgnoreCase("fab")) {
            picker = 1;
        } else if (view.getTag() != null && view.getTag().toString().equalsIgnoreCase("mask")) {
            picker = 2;
        }
        dialog(picker);
    }

    public void dialog(final int picker) {
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("ColorPicker Dialog");
        builder.setFlagView(new CustomFlag(this, R.layout.layout_flag));
        builder.setPositiveButton("select", new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
//                setLayoutColor(envelope);
                switch (picker) {
                    case 0:
                        fabTextColor = envelope.getColor();
                        alphaTileView.setPaintColor(envelope.getColor());
                        break;
                    case 1:
                        fabColor = envelope.getColor();
                        alphaTileView1.setPaintColor(envelope.getColor());
                        break;
                    case 2:
                        maskColor = envelope.getColor();
                        if (AcquireApp.getInstance() != null)
                            AcquireApp.getInstance().setMaskingViewColor(maskColor);
                        maskColorView.setPaintColor(envelope.getColor());
                        break;
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.attachAlphaSlideBar();
        builder.attachBrightnessSlideBar();
        builder.show();
    }

    public void saveCOB(View view) {
        // First do logout current session to make changes to config
        AcquireApp.logOut();

        // Getting values from the App

        long maxUpdateTime = Long.parseLong(etTimemax.getText().toString().trim().isEmpty() ? "1000" : etTimemax.getText().toString().trim());
        long minUpdateTime = Long.parseLong(etTimemin.getText().toString().trim().isEmpty() ? "400" : etTimemin.getText().toString().trim());
        int bitmapQuality = Integer.parseInt(etQuality.getText().toString().trim().isEmpty() ? "10" : etQuality.getText().toString().trim());
        String CobCode = etCOBCode.getText().toString().trim();

        // Then create cobrowse builder object
        CoBrowse.CobrowseBuilder cobrowseBuilder = new CoBrowse.CobrowseBuilder().setMAX_UPDATE_TIME(maxUpdateTime)
                .setMIN_UPDATE_TIME(minUpdateTime).setBITMAP_QUALITY(bitmapQuality)
                .setHideStopButton(swStopBtn.isChecked()).setConfirmStop(swConfirm.isChecked());
        if (!CobCode.isEmpty()) {
            cobrowseBuilder.setCoBrowseCODE(CobCode);
        }

        cobrowse = cobrowseBuilder.build();
        // pass this cobrowse object in initCobrowse method to reflect changes.
        AcquireApp.initCobrowse(this.getApplication(), AppComponent.AccID, cobrowse);
        Toast.makeText(this, "Cobrowse Settings Saved\nPlease wait...", Toast.LENGTH_SHORT).show();
    }

    public void saveFAB(View view) {
        // First do logout current session to make changes to config
        AcquireApp.logOut();
        if (((CheckBox) findViewById(R.id.cbDefautFAB)).isChecked()) {
            AcquireApp.supportFAB = null;
            AcquireApp.setShowDefaultFAB(true);
            AcquireApp.init(this.getApplication(), AppComponent.AccID);
            Toast.makeText(this, "FAB Settings Saved\nPlease wait...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Getting values from the App
        int fabSize = spnFABSize.getSelectedItemPosition();
        int fabPOs = spnFABPos.getSelectedItemPosition();
        String fabTExt = etFABText.getText().toString().trim();
        // Directly set the values to the supportFAB variable
        SupportFAB.SupportFABBuilder supportFABBuilder = new SupportFAB.SupportFABBuilder();
        supportFABBuilder.setFabSize(fabSize)
                .setFabIconPosition(fabPOs == 0 ? SupportFAB.FAB_POSITION.BOTTOM_LEFT : BOTTOM_RIGHT)
                .setFabElevation(etFABele.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(etFABele.getText().toString()))
                .setFABAnimation(swAnim.isChecked()).build();

        if (!fabTExt.isEmpty()) {
            supportFABBuilder.setFabText(fabTExt);
        }
        if (fabColor != -2) {
            supportFABBuilder.setFabColor(fabColor);
        }
        if (fabTextColor != -1) {
            supportFABBuilder.setFabTextColor(fabTextColor);
        }
        AcquireApp.supportFAB = supportFABBuilder.build();

        // Then re start the sdk after turn on FAB
        AcquireApp.setShowDefaultFAB(true);
        AcquireApp.init(this.getApplication(), AppComponent.AccID);
        Toast.makeText(this, "FAB Settings Saved\nPlease wait...", Toast.LENGTH_SHORT).show();

    }

    public void setDefaultFAB(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            AcquireApp.supportFAB = null;
            findViewById(R.id.llFABSettings).setVisibility(View.GONE);
        } else {
            findViewById(R.id.llFABSettings).setVisibility(View.VISIBLE);
        }
    }

    public void showFAB(View view) {
        AcquireApp.logOut();
        AcquireApp.setShowDefaultFAB(true);
        AcquireApp.init(this.getApplication(), AppComponent.AccID);
    }

    public void removeFAB(View view) {
        AcquireApp.logOut();
        AcquireApp.setShowDefaultFAB(false);
        AcquireApp.init(this.getApplication(), AppComponent.AccID);
    }

    public void showAdvance(View view) {
        findViewById(R.id.cardCob).setVisibility(View.VISIBLE);
        findViewById(R.id.cardFAB).setVisibility(View.VISIBLE);
    }
}
