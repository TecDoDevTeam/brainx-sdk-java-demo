package com.td.demo;

import androidx.annotation.NonNull;
import com.td.core.TDError;
import com.td.out.TDRewardItem;
import com.td.out.TDRewardVideo;
import com.td.out.TDRewardVideoAd;
import com.td.out.TDRewardVideoAdListener;
import com.td.out.TDRewardVideoConfig;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivityRewardBinding;

public class RewardActivity extends ViewBindingActivity<ActivityRewardBinding> implements TDRewardVideoAdListener {
    private final TDRewardVideoAd rewardAd = new TDRewardVideoAd(DemoActivity.REWARD_VIDEO_UNIT_ID, new TDRewardVideoConfig("user:tecdo"));

    @Override
    protected void initView(ActivityRewardBinding binding) {
        rewardAd.setListener(this);
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnLoad.setOnClickListener(view -> rewardAd.load());
        binding.btnShow.setOnClickListener(view -> {
            if (rewardAd.isReady()) {
                rewardAd.show();
            } else {
                Logger.dt(this, "reward video has not loaded yet, please load first");
            }
        });
    }

    @Override
    public void onAdLoaded(@NonNull TDRewardVideo ad) {
        Logger.dt(this, "on reward video load success");
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on reward video load error: " + error.getMsg());
    }

    @Override
    public void onAdShowedFail(@NonNull TDError error) {
        Logger.dt(this, "on reward video on ad showed fail " + error);
    }

    @Override
    public void onRewardedSuccess(@NonNull TDRewardItem rewardItem) {
        Logger.dt(this, "on reward video on rewarded success " + rewardItem);
    }

    @Override
    public void onRewardedFail() {
        Logger.dt(this, "on reward video on rewarded fail");
    }

    @Override
    public void onAdShowed() {
        Logger.dt(this, "on reward video on ad showed");
    }

    @Override
    public void onAdDismissed() {
        Logger.dt(this, "on reward video on ad dismissed");
    }

    @Override
    public void onAdClicked() {
        Logger.dt(this, "on reward video on ad clicked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rewardAd.destroy();
    }
} 