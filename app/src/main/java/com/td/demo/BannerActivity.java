package com.td.demo;

import androidx.annotation.NonNull;
import com.td.core.TDError;
import com.td.out.TDBanner;
import com.td.out.TDBannerAdListener;
import com.td.out.TDBannerAdView;
import com.td.out.TDBannerConfig;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivityBannerBinding;

public class BannerActivity extends ViewBindingActivity<ActivityBannerBinding> implements TDBannerAdListener {
    private TDBannerAdView bannerAd;

    @Override
    protected void initView(ActivityBannerBinding binding) {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btn32050.setOnClickListener(view -> loadAd(DemoActivity.BANNER_320_50_UNIT_ID));
        binding.btn300250.setOnClickListener(view -> loadAd(DemoActivity.BANNER_300_250_UNIT_ID));
        binding.btn32090.setOnClickListener(view -> loadAd(DemoActivity.BANNER_320_90_UNIT_ID));
        binding.btn72890.setOnClickListener(view -> loadAd(DemoActivity.BANNER_728_90_UNIT_ID));
        binding.btn800600.setOnClickListener(view -> loadAd(DemoActivity.BANNER_800_600_UNIT_ID));
    }

    private void loadAd(String unitId) {
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        binding.containerBanner.removeAllViews();
        TDBannerAdView adView = new TDBannerAdView(this, unitId, new TDBannerConfig());
        bannerAd = adView;
        adView.setListener(this);
        adView.load();
        binding.containerBanner.addView(adView);
    }

    @Override
    public void onAdLoaded(@NonNull TDBanner ad) {
        Logger.dt(this, "on banner load success");
    }

    @Override
    public void onAdShowed() {
        Logger.dt(this, "on banner show");
    }

    @Override
    public void onAdDismissed() {
        Logger.dt(this, "on banner dismissed");
    }

    @Override
    public void onAdClicked() {
        Logger.dt(this, "on banner clicked");
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on banner load fail: " + error.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerAd != null) {
            bannerAd.destroy();
        }
    }
} 