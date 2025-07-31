package com.td.demo;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.td.core.TDError;
import com.td.out.TDSplash;
import com.td.out.TDSplashAd;
import com.td.out.TDSplashAdListener;
import com.td.out.TDSplashConfig;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivitySplashBinding;

public class SplashActivity extends ViewBindingActivity<ActivitySplashBinding> implements TDSplashAdListener {

    private final TDSplashAd splashAd = new TDSplashAd(DemoActivity.SPLASH_UNIT_ID, new TDSplashConfig());

    @Override
    protected void initView(ActivitySplashBinding binding) {
        splashAd.setListener(this);
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnLoad.setOnClickListener(view -> splashAd.load());
        binding.btnLoadInActivity.setOnClickListener(view -> {
            startActivity(new Intent(SplashActivity.this, SplashContainerActivity.class));
        });
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
    public void onAdLoaded(@NonNull TDSplash ad) {
        Logger.dt(this, "on splash load success");
        splashAd.show(binding.containerSplash);
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on splash load error: " + error.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashAd.destroy();
    }
} 