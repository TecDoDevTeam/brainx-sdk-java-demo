package com.td.demo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.td.core.TDError;
import com.td.demo.base.ViewBindingActivity;
import com.td.demo.databinding.ActivityNativeBinding;
import com.td.demo.databinding.NativeTemplateBinding;
import com.td.nativead.TDMediaView;
import com.td.nativead.TDNativeView;
import com.td.out.TDNative;
import com.td.out.TDNativeAd;
import com.td.out.TDNativeAdListener;
import com.td.out.TDNativeConfig;
import com.td.out.VideoController;

import java.util.ArrayList;
import java.util.List;

public class NativeActivity extends ViewBindingActivity<ActivityNativeBinding> implements TDNativeAdListener {

    private TDNativeAd nativeAd;

    @Override
    protected void initView(ActivityNativeBinding binding) {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnLoadTemplateRendering.setOnClickListener(view -> loadNativeAd(TDNativeConfig.NativeType.TEMPLATE_RENDERING));
        binding.btnLoadSelfRendering.setOnClickListener(view -> loadNativeAd(TDNativeConfig.NativeType.SELF_RENDERING));
    }

    private void loadNativeAd(TDNativeConfig.NativeType type) {
        binding.containerNative.removeAllViews();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        TDNativeAd ad = new TDNativeAd(DemoActivity.NATIVE_UNIT_ID, new TDNativeConfig(type));
        nativeAd = ad;
        ad.setListener(this);
        ad.load();
    }

    @Override
    public void onRenderSuccess(@NonNull TDNativeView view) {
        Logger.dt(this, "on native template render success");
        binding.containerNative.addView(view);
    }

    @Override
    public void onRenderFail(@NonNull TDError error) {
        Logger.dt(this, "on native template render fail " + error);
    }

    @Override
    public void onAdShowed() {
        Logger.dt(this, "on native show");
    }

    @Override
    public void onAdDismissed() {
        Logger.dt(this, "on native dismissed");
    }

    @Override
    public void onAdClicked() {
        Logger.dt(this, "on native clicked");
    }

    @Override
    public void onAdLoaded(@NonNull TDNative ad) {
        if (nativeAd.getRenderType() == TDNativeConfig.NativeType.TEMPLATE_RENDERING) {
            nativeAd.renderForTemplate(this);
        } else if (nativeAd.getRenderType() == TDNativeConfig.NativeType.SELF_RENDERING) {
            selfRenderNativeAd(nativeAd, ad);
        }
        Logger.dt(this, "on native load success");
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(this, "on native load fail: " + error.getMsg());
    }

    private void selfRenderNativeAd(TDNativeAd tdNativeAd, TDNative ad) {
        NativeTemplateBinding selfBinding = NativeTemplateBinding.inflate(getLayoutInflater(), binding.containerNative, false);
        ViewGroup container = selfBinding.getRoot();
        List<View> creativeViews = new ArrayList<>();

        // Icon
        if (ad.getIcon().isEmpty()) {
            selfBinding.adIcon.setVisibility(View.GONE);
        } else {
            selfBinding.adIcon.setVisibility(View.VISIBLE);
            Glide.with(selfBinding.adIcon).load(ad.getIcon()).into(selfBinding.adIcon);
        }
        creativeViews.add(selfBinding.adIcon);

        // Title
        selfBinding.adTitle.setText(ad.getTitle());
        creativeViews.add(selfBinding.adTitle);

        // Desc
        if (ad.getDescription().isEmpty()) {
            selfBinding.adDesc.setVisibility(View.GONE);
        } else {
            selfBinding.adDesc.setVisibility(View.VISIBLE);
            selfBinding.adDesc.setText(ad.getDescription());
            creativeViews.add(selfBinding.adDesc);
        }

        // Media
        TDMediaView mediaView = ad.getMediaView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        selfBinding.mediaContainer.addView(mediaView, 0, lp);
        creativeViews.add(mediaView);
        if (mediaView.getMediaContent().hasVideoContent()) {
            VideoController vc = mediaView.getMediaContent().getVideoController();
            // you can use the videoController to control video
        }

        // CTA
        selfBinding.btnCtaNativeTemplate.setText(ad.getCTAText());
        creativeViews.add(selfBinding.btnCtaNativeTemplate);

        // Logo
        View adLogoView = ad.getAdLogoView(this);
        selfBinding.adLogoContainer.addView(adLogoView);
        creativeViews.add(adLogoView);

        // Dislike
        View dislikeView = selfBinding.btnCloseNativeTemplate;

        boolean result = tdNativeAd.bindViewsForInteraction(container, creativeViews, dislikeView);
        Logger.dt(this, "on native self render bind " + (result ? "success" : "fail"));
        if (result) {
            binding.containerNative.addView(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }
} 