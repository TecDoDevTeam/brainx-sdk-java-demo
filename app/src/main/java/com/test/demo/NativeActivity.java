package com.test.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.td.core.TDError;
import com.td.nativead.TDNativeView;
import com.td.out.TDNative;
import com.td.out.TDNativeConfig;
import com.td.out.TDNativeEventListener;
import com.td.out.TDNativeLoadListener;

import java.util.ArrayList;
import java.util.List;

class NativeActivity extends AppCompatActivity implements TDNativeLoadListener {

    private boolean needSelfRendering = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        initView();
    }

    private void initView() {
        container = findViewById(R.id.container_native);
        findViewById(R.id.btn_back).setOnClickListener(view -> finish());
        findViewById(R.id.btn_load_template_rendering).setOnClickListener(view -> {
            needSelfRendering = false;
            TDNative.load(DemoActivity.NATIVE_UNIT_ID, new TDNativeConfig(TDNativeConfig.NativeType.TEMPLATE_RENDERING), this);
        });
        findViewById(R.id.btn_load_self_rendering).setOnClickListener(view -> {
            needSelfRendering = true;
            TDNative.load(DemoActivity.NATIVE_UNIT_ID, new TDNativeConfig(TDNativeConfig.NativeType.SELF_RENDERING), this);
        });
    }

    @Override
    public void onAdLoaded(TDNative tdNative) {
        container.removeAllViews();
        Logger.dt(this, "on native load success");
        tdNative.setEventListener(new TDNativeEventListener() {
            @Override
            public void onRenderSuccess(@NonNull TDNativeView view) {
                Logger.dt(NativeActivity.this, "on native template render success");
                container.addView(view);
            }

            @Override
            public void onRenderFail() {
                Logger.dt(NativeActivity.this, "on native template render fail");
            }

            @Override
            public void onAdShowed() {
                Logger.dt(NativeActivity.this, "on native show");
            }

            @Override
            public void onAdDismissed() {
                Logger.dt(NativeActivity.this, "on native dismissed");
            }

            @Override
            public void onAdClicked() {
                Logger.dt(NativeActivity.this, "on native clicked");
            }
        });
        if (needSelfRendering) {
            selfRenderNative(tdNative);
        } else {
            tdNative.renderForTemplate(NativeActivity.this);
        }
    }

    @Override
    public void onError(@NonNull TDError error) {
        Logger.dt(NativeActivity.this, "on native load fail: " + error.getMsg());
    }

    private void selfRenderNative(TDNative nativeAd) {
        handler.post(() -> {
            formSelfRenderingView(nativeAd, (container, creativeViews, dislikeView) -> {
                boolean result = nativeAd.bindViewsForInteraction(container, creativeViews, dislikeView);
                Logger.dt(NativeActivity.this, "on native self render bind " + (result ? "success" : "fail"));
                if (result) {
                    NativeActivity.this.container.addView(container, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
            });
        });
    }

    private void formSelfRenderingView(TDNative nativeAd, InflateCallback inflateCallback) {
        ViewGroup container = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.native_template, this.container, false);
        ArrayList<View> creativeViews = new ArrayList<>();

        ImageView iconIV = container.findViewById(R.id.iv_icon_native_template);
        if (nativeAd.getIcon().isEmpty()) {
            iconIV.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(nativeAd.getIcon()).into(iconIV);
            creativeViews.add(iconIV);
        }

        TextView titleTV = container.findViewById(R.id.tv_title_native_template);
        titleTV.setText(nativeAd.getTitle());
        creativeViews.add(titleTV);

        TextView descTV = container.findViewById(R.id.tv_desc_native_template);
        if (nativeAd.getDescription().isEmpty()) {
            descTV.setVisibility(View.GONE);
        } else {
            descTV.setText(nativeAd.getDescription());
            creativeViews.add(descTV);
        }

        ViewGroup mediaViewContainer = container.findViewById(R.id.container_media_native_template);
        View mediaView = nativeAd.getMediaView(NativeActivity.this);
        mediaViewContainer.addView(mediaView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        creativeViews.add(mediaView);

        TextView ctaBtn = container.findViewById(R.id.btn_cta_native_template);
        ctaBtn.setText(nativeAd.getCTAText());
        creativeViews.add(ctaBtn);

        View LogoView = nativeAd.getAdLogoView(NativeActivity.this);
        creativeViews.add(LogoView);

        View dislikeView = container.findViewById(R.id.btn_close_native_template);

        inflateCallback.onInflateSuccess(container, creativeViews, dislikeView);
    }

    private interface InflateCallback {

        void onInflateSuccess(ViewGroup container, List<View> creativeViews, View dislikeView);

    }

}