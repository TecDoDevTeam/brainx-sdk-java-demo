package com.test.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.td.core.TDError;
import com.td.out.TDSplash;
import com.td.out.TDSplashConfig;
import com.td.out.TDSplashEventListener;
import com.td.out.TDSplashLoadListener;

public class SplashActivity extends AppCompatActivity implements TDSplashLoadListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private FrameLayout container = null;

    private void initView() {
        container = findViewById(R.id.container_splash);
        TDSplashConfig tdSplashConfig = new TDSplashConfig();
        tdSplashConfig.setAdTimeOut(5);
        findViewById(R.id.btn_back).setOnClickListener(View -> finish());
        findViewById(R.id.btn_load).setOnClickListener(View -> TDSplash.load(DemoActivity.SPLASH_UNIT_ID, tdSplashConfig, SplashActivity.this));
        findViewById(R.id.btn_load_in_activity).setOnClickListener(View -> {
            Intent intent = new Intent(SplashActivity.this, SplashContainerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onAdLoaded(TDSplash tdSplash) {
        Logger.dt(this, "on splash load success");
        tdSplash.setEventListener(new TDSplashEventListener() {

            @Override
            public void onAdShowed() {
                Logger.dt(SplashActivity.this, "splash on showed");
            }

            @Override
            public void onAdDismissed() {
                Logger.dt(SplashActivity.this, "splash on dismissed");
                finish();
            }

            @Override
            public void onAdClicked() {
                Logger.dt(SplashActivity.this, "splash on clicked");
            }
        });
        container.addView(tdSplash.getAdView());
    }

    @Override
    public void onError(TDError tdError) {
        Logger.dt(this, "on splash load error: " + tdError.getMsg());
    }

}