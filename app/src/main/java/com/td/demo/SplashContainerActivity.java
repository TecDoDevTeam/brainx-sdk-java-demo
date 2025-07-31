package com.td.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.td.core.TDError;
import com.td.out.TDSplash;
import com.td.out.TDSplashAd;
import com.td.out.TDSplashAdListener;
import com.td.out.TDSplashConfig;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivitySplashContainerBinding;

public class SplashContainerActivity extends ViewBindingActivity<ActivitySplashContainerBinding> implements TDSplashAdListener {

    private final TDSplashAd splashAd = new TDSplashAd(DemoActivity.SPLASH_UNIT_ID, new TDSplashConfig());

    @Override
    protected void initView(ActivitySplashContainerBinding binding) {
        splashAd.setListener(this);
        loadSplash();
    }

    private void loadSplash() {
        splashAd.load();
    }

    @Override
    public void onAdLoaded(@NonNull TDSplash ad) {
        Logger.dt(this, "on splash load success");
        splashAd.show(binding.containerSplash);
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on splash load error: " + error.getMsg());
        finish();
    }

    @Override
    public void onAdShowed() {
        Logger.dt(this, "splash on showed");
    }

    @Override
    public void onAdDismissed() {
        Logger.dt(this, "splash on dismissed");
        finish();
    }

    @Override
    public void onAdClicked() {
        Logger.dt(this, "splash on clicked");
    }

    @Override
    public void onAdShowedFail(@NonNull TDError error) {
        Logger.et(this, "on splash showed fail");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashAd.destroy();
    }
} 