package com.test.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.td.core.TDError;
import com.td.out.TDInterstitial;
import com.td.out.TDInterstitialConfig;
import com.td.out.TDInterstitialEventListener;
import com.td.out.TDInterstitialLoadListener;

public class InterActivity extends AppCompatActivity implements TDInterstitialLoadListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(view -> finish());
        findViewById(R.id.btn_load).setOnClickListener(view -> TDInterstitial.load(DemoActivity.INTER_UNIT_ID, new TDInterstitialConfig(), InterActivity.this));
        findViewById(R.id.btn_show).setOnClickListener(view -> {
            if (inter != null) {
                inter.show();
            } else {
                Logger.dt(this, "inter has not loaded yet, please load first");
            }
        });
    }

    private TDInterstitial inter = null;

    @Override
    public void onAdLoaded(@NonNull TDInterstitial ad) {
        Logger.dt(this, "on inter load success");
        inter = ad;
        inter.setEventListener(new TDInterstitialEventListener() {
            @Override
            public void onAdShowedFail(@NonNull TDError error) {
                Logger.dt(InterActivity.this, "on inter on ad showed fail " + error);
            }

            @Override
            public void onAdShowed() {
                Logger.dt(InterActivity.this, "on inter on ad showed");
            }

            @Override
            public void onAdDismissed() {
                Logger.dt(InterActivity.this, "on inter on ad dismissed");
            }

            @Override
            public void onAdClicked() {
                Logger.dt(InterActivity.this, "on inter on ad clicked");
            }
        });
    }

    @Override
    public void onError(TDError error) {
        Logger.dt(this, "on inter load error: " + error.getMsg());
    }

}