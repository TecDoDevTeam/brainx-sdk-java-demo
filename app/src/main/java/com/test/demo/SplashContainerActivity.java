package com.test.demo;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.td.core.TDError;
import com.td.out.TDSplash;
import com.td.out.TDSplashConfig;
import com.td.out.TDSplashEventListener;
import com.td.out.TDSplashLoadListener;

public class SplashContainerActivity extends AppCompatActivity implements TDSplashLoadListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_container);
        initView();
        loadSplash();
    }

    private FrameLayout container = null;

    private void initView() {
        container = findViewById(R.id.container_splash);
    }

    private void loadSplash() {
        TDSplash.load(DemoActivity.SPLASH_UNIT_ID, new TDSplashConfig(), SplashContainerActivity.this);
    }

    @Override
    public void onAdLoaded(TDSplash tdSplash) {
        Logger.dt(this, "on splash load success");
        tdSplash.setEventListener(new TDSplashEventListener() {
            @Override
            public void onAdClicked() {
                Logger.dt(SplashContainerActivity.this, "splash on clicked");
            }

            @Override
            public void onAdDismissed() {
                Logger.dt(SplashContainerActivity.this, "splash on dismissed");
                finish();
            }

            @Override
            public void onAdShowed() {
                Logger.dt(SplashContainerActivity.this, "splash on showed");
            }
        });
        container.addView(tdSplash.getAdView());
    }

    @Override
    public void onError(TDError tdError) {
        Logger.dt(this, "on splash load error: " + tdError.getMsg());
        finish();
    }

}