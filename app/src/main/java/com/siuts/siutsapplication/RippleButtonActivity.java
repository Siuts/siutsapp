package com.siuts.siutsapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.skyfishjy.library.RippleBackground;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RippleButtonActivity extends AppCompatActivity {

    @BindView(R.id.content) RippleBackground rippleBackground;
    @BindView(R.id.rippleImage) ImageView rippleImage;
    Boolean isCurrentlyRippling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple_button);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.rippleImage)
    public void pressRippleImage() {
        if (isCurrentlyRippling) {
            rippleBackground.stopRippleAnimation();
            isCurrentlyRippling = false;
        } else {
            rippleBackground.startRippleAnimation();
            isCurrentlyRippling = true;
        }
    }

}
