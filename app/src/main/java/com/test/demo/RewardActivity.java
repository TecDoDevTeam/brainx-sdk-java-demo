package com.test.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.td.core.TDError;
import com.td.out.TDRewardItem;
import com.td.out.TDRewardVideo;
import com.td.out.TDRewardVideoConfig;
import com.td.out.TDRewardVideoEventListener;
import com.td.out.TDRewardVideoLoadListener;

public class RewardActivity extends AppCompatActivity implements TDRewardVideoLoadListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(view -> finish());
        findViewById(R.id.btn_load).setOnClickListener(view -> TDRewardVideo.load(DemoActivity.REWARD_VIDEO_UNIT_ID, new TDRewardVideoConfig("USER_ID"), RewardActivity.this));
        findViewById(R.id.btn_show).setOnClickListener(view -> {
            if (rewardVideo != null) {
                rewardVideo.show();
            } else {
                Logger.dt(this, "reward video has not loaded yet, please load first");
            }
        });
    }

    private TDRewardVideo rewardVideo = null;

    @Override
    public void onAdLoaded(@NonNull TDRewardVideo ad) {
        Logger.dt(this, "on reward video load success");
        rewardVideo = ad;
        rewardVideo.setEventListener(new TDRewardVideoEventListener() {
            @Override
            public void onAdShowedFail(@NonNull TDError error) {
                Logger.dt(RewardActivity.this, "on reward video on ad showed fail " + error);
            }

            @Override
            public void onRewardedSuccess(@NonNull TDRewardItem rewardItem) {
                Logger.dt(RewardActivity.this, "on reward video on rewarded success " + rewardItem);
            }

            @Override
            public void onRewardedFail() {
                Logger.dt(RewardActivity.this, "on reward video on rewarded fail");
            }

            @Override
            public void onAdShowed() {
                Logger.dt(RewardActivity.this, "on reward video on ad showed");
            }

            @Override
            public void onAdDismissed() {
                Logger.dt(RewardActivity.this, "on reward video on ad dismissed");
            }

            @Override
            public void onAdClicked() {
                Logger.dt(RewardActivity.this, "on reward video on ad clicked");
            }
        });
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on reward video load error: " + error.getMsg());
    }

}