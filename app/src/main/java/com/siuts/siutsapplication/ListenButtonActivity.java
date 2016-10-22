package com.siuts.siutsapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.saeid.fabloading.LoadingView;


public class ListenButtonActivity extends AppCompatActivity {

    @BindView(R.id.listenButton) LoadingView listenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_button);
        ButterKnife.bind(this);

        listenButton.addAnimation(0x64B5F6, R.drawable.emberiza_citrinella, LoadingView.FROM_LEFT);

    }
}
