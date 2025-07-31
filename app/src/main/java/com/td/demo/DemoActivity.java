package com.td.demo;

import android.content.Intent;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import com.td.common.utils.LogUtil;
import com.td.core.TDConfig;
import com.td.core.TDError;
import com.td.core.TDSDK;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivityDemoBinding;

public class DemoActivity extends ViewBindingActivity<ActivityDemoBinding> {

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

    private CheckBox gdprCheckbox;
    private CheckBox ccpaCheckbox;
    private CheckBox coppaCheckbox;

    @Override
    protected void initView(ActivityDemoBinding binding) {
        gdprCheckbox = binding.checkboxGdpr;
        ccpaCheckbox = binding.checkboxCcpa;
        coppaCheckbox = binding.checkboxCoppa;

        binding.btnInit.setOnClickListener(view -> TDSDK.init(
                DemoActivity.this,
                new TDConfig.Builder()
                        .setAppId(APP_ID)
                        .setCOPPA(coppaCheckbox.isChecked())
                        .setGDPR(gdprCheckbox.isChecked())
                        .setCCPA(ccpaCheckbox.isChecked())
                        .build(),
                new TDSDK.InitCallback() {
                    @Override
                    public void onSDKInitSuccess() {
                        Logger.dt(DemoActivity.this, "on sdk init success");
                    }

                    @Override
                    public void onSDKInitFail(@NonNull TDError tdError) {
                        Logger.dt(DemoActivity.this, "on sdk init fail " + tdError.getMsg());
                    }
                }));

        binding.btnBanner.setOnClickListener(view -> startActivity(new Intent(DemoActivity.this, BannerActivity.class)));
        binding.btnSplash.setOnClickListener(view -> startActivity(new Intent(DemoActivity.this, SplashActivity.class)));
        binding.btnInterstitial.setOnClickListener(view -> startActivity(new Intent(DemoActivity.this, InterActivity.class)));
        binding.btnRewardvideo.setOnClickListener(view -> startActivity(new Intent(DemoActivity.this, RewardActivity.class)));
        binding.btnNative.setOnClickListener(view -> startActivity(new Intent(DemoActivity.this, NativeActivity.class)));
    }
} 