package com.test.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.td.common.utils.LogUtil;
import com.td.core.TDConfig;
import com.td.core.TDError;
import com.td.core.TDSDK;

public class DemoActivity extends AppCompatActivity {

    public static int APP_ID = 1000001;
    public static String BANNER_320_50_UNIT_ID = "1000000001";
    public static String BANNER_300_250_UNIT_ID = "1000000002";
    public static String BANNER_320_90_UNIT_ID = "1000000003";
    public static String BANNER_728_90_UNIT_ID = "1000000004";
    public static String BANNER_800_600_UNIT_ID = "1000000005";
    public static String SPLASH_UNIT_ID = "1000000006";
    public static String INTER_UNIT_ID = "1000000008";
    public static String REWARD_VIDEO_UNIT_ID = "1000000019";
    public static String NATIVE_UNIT_ID = "1000000194";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
    }

    private CheckBox gdprCheckbox = null;
    private CheckBox ccpaCheckbox = null;
    private CheckBox coppaCheckbox = null;
    
    private void initView() {
        gdprCheckbox = findViewById(R.id.checkbox_gdpr);
        ccpaCheckbox = findViewById(R.id.checkbox_ccpa);
        coppaCheckbox = findViewById(R.id.checkbox_coppa);
        findViewById(R.id.btn_init).setOnClickListener (View -> TDSDK.init (
                DemoActivity.this,
                new TDConfig.Builder().setAppId(APP_ID).setCOPPA(coppaCheckbox.isChecked()).setGDPR(gdprCheckbox.isChecked()).setCCPA(ccpaCheckbox.isChecked()).build(),
                new TDSDK.InitCallback() {
                    @Override
                    public void onSDKInitSuccess() {
                        Logger.dt(DemoActivity.this,  "on sdk init success");
                    }

                    @Override
                    public void onSDKInitFail(@NonNull TDError tdError) {
                        Logger.dt(DemoActivity.this, "on sdk init fail " + tdError.getMsg());
                    }
            }));
        findViewById(R.id.btn_banner).setOnClickListener(View -> {
            Intent intent = new Intent(DemoActivity.this, BannerActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_splash).setOnClickListener(View -> {
            Intent intent = new Intent(DemoActivity.this, SplashActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_interstitial).setOnClickListener(View -> {
            Intent intent = new Intent(DemoActivity.this, InterActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btn_rewardvideo).setOnClickListener(View -> {
            Intent intent = new Intent(DemoActivity.this, RewardActivity.class);
            startActivity(intent);
        });
    }

}