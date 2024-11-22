package com.test.demo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.td.core.TDError;
import com.td.out.TDBanner;
import com.td.out.TDBannerConfig;
import com.td.out.TDBannerEventListener;
import com.td.out.TDBannerLoadListener;

public class BannerActivity extends AppCompatActivity implements TDBannerLoadListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
    }

    private FrameLayout container = null;

    private void initView() {
        container = findViewById(R.id.container_banner);
        findViewById(R.id.btn_back).setOnClickListener(view -> finish());
        findViewById(R.id.btn_320_50).setOnClickListener(view -> TDBanner.load(DemoActivity.BANNER_320_50_UNIT_ID, new TDBannerConfig(TDBannerConfig.BannerSize.W_320_H_50), BannerActivity.this));
        findViewById(R.id.btn_300_250).setOnClickListener(view -> TDBanner.load(DemoActivity.BANNER_300_250_UNIT_ID, new TDBannerConfig(TDBannerConfig.BannerSize.W_300_H_250), BannerActivity.this));
        findViewById(R.id.btn_320_90).setOnClickListener(view -> TDBanner.load(DemoActivity.BANNER_320_90_UNIT_ID, new TDBannerConfig(TDBannerConfig.BannerSize.W_320_H_90), BannerActivity.this));
        findViewById(R.id.btn_728_90).setOnClickListener(view -> TDBanner.load(DemoActivity.BANNER_728_90_UNIT_ID, new TDBannerConfig(TDBannerConfig.BannerSize.W_728_H_90), BannerActivity.this));
        findViewById(R.id.btn_800_600).setOnClickListener(view -> TDBanner.load(DemoActivity.BANNER_800_600_UNIT_ID, new TDBannerConfig(TDBannerConfig.BannerSize.W_800_H_600), BannerActivity.this));
    }

    @Override
    public void onAdLoaded(@NonNull TDBanner tdBanner) {
        container.removeAllViews();
        Logger.dt(BannerActivity.this, "on banner load success");
        tdBanner.setEventListener(new TDBannerEventListener() {
            @Override
            public void onAdClicked() {
                Logger.dt(BannerActivity.this, "on banner clicked");
            }

            @Override
            public void onAdDismissed() {
                Logger.dt(BannerActivity.this, "on banner dismissed");
            }

            @Override
            public void onAdShowed() {
                Logger.dt(BannerActivity.this, "on banner show");
            }
        });
        container.addView(tdBanner.getAdView());
    }

    @Override
    public void onError(@NonNull TDError tdError) {
        Logger.dt(BannerActivity.this, "on banner load fail: " + tdError.getMsg());
    }

}