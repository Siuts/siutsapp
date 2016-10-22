package com.siuts.siutsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListenButtonActivity extends AppCompatActivity {

    @BindView(R.id.listenButton) Button listenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_button);
        ButterKnife.bind(this);

        listenButton.setOnClickListener((View v) -> {
            System.out.println("hello");
        });
    }
}
