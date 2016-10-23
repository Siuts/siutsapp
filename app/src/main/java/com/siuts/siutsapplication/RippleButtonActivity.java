package com.siuts.siutsapplication;

import android.app.Activity;
import android.os.Bundle;

import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.saeid.fabloading.LoadingView;

public class RippleButtonActivity extends Activity {

    @BindView(R.id.slowRipple) RippleBackground slowRipple;
    @BindView(R.id.fastRipple) RippleBackground fastRipple;
    @BindView(R.id.recordButton) LoadingView recordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_button);
        ButterKnife.bind(this);
        setupRecordButtonAnimation();

        slowRipple.startRippleAnimation();
    }

    private void setupRecordButtonAnimation() {
        recordButton.addAnimation(0x64B5F6, R.drawable.canary_with_mic, LoadingView.FROM_LEFT);
        recordButton.addAnimation(0x64B5ff, R.drawable.canary_confused, LoadingView.FROM_TOP);
        recordButton.addAnimation(0x64B5F6, R.drawable.canary_with_mic, LoadingView.FROM_RIGHT);
        recordButton.addAnimation(0x64B500, R.drawable.canary_music_symbol, LoadingView.FROM_BOTTOM);
    }

    @OnClick(R.id.recordButton)
    public void pressRecordButton() {
        slowRipple.stopRippleAnimation();
        fastRipple.startRippleAnimation();
        recordButton.startAnimation();
    }

}
