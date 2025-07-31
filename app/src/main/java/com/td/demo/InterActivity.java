package com.td.demo;

import androidx.annotation.NonNull;
import com.td.core.TDError;
import com.td.out.TDInterstitial;
import com.td.out.TDInterstitialAd;
import com.td.out.TDInterstitialAdListener;
import com.td.out.TDInterstitialConfig;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivityInterBinding;

public class InterActivity extends ViewBindingActivity<ActivityInterBinding> implements TDInterstitialAdListener {

    private final TDInterstitialAd interstitialAd = new TDInterstitialAd(DemoActivity.INTER_UNIT_ID, new TDInterstitialConfig());

    @Override
    protected void initView(ActivityInterBinding binding) {
        interstitialAd.setListener(this);
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnLoad.setOnClickListener(view -> interstitialAd.load());
        binding.btnShow.setOnClickListener(view -> {
            if (interstitialAd.isReady()) {
                interstitialAd.show();
            } else {
                Logger.dt(this, "inter has not loaded yet, please load first");
            }
        });
    }

    @Override
    public void onAdLoaded(@NonNull TDInterstitial ad) {
        Logger.dt(this, "on inter load success");
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on inter load error: " + error.getMsg());
    }

    @Override
    public void onAdShowedFail(@NonNull TDError error) {
        Logger.dt(this, "on inter on ad showed fail " + error);
    }

    @Override
    public void onAdShowed() {
        Logger.dt(this, "on inter on ad showed");
    }

    @Override
    public void onAdDismissed() {
        Logger.dt(this, "on inter on ad dismissed");
    }

    @Override
    public void onAdClicked() {
        Logger.dt(this, "on inter on ad clicked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        interstitialAd.destroy();
    }
} 