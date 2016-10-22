package com.siuts.siutsapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.siuts.siutsapplication.domain.Constants;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.saeid.fabloading.LoadingView;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class ListenButtonActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;
    public static final String TAG = "MYTAG";

    @BindView(R.id.listenButton) LoadingView listenButton;
    @BindView(R.id.testStartButton) Button startButton;
    @BindView(R.id.testStopButton) Button stopButton;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_button);
        ButterKnife.bind(this);
        checkPermissions();

        startButton.setOnClickListener((View v) -> {
            startRecording();
        });

        stopButton.setOnClickListener((View v) -> {
            stopRecording();
        });

        listenButton.addAnimation(0x64B5F6, R.drawable.emberiza_citrinella, LoadingView.FROM_LEFT);
        listenButton.addAnimation(0x64B5ff, R.drawable.apus_apus, LoadingView.FROM_BOTTOM);
        listenButton.addAnimation(0x64B500, R.drawable.chloris_chloris, LoadingView.FROM_TOP);
        listenButton.addAnimation(0x64B5F6, R.drawable.fab_shadow, LoadingView.FROM_RIGHT);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void checkPermissions() {
        // http://stackoverflow.com/questions/3782786/android-mediarecorder-setaudiosource-failed
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onStop() {
        mRecorder.reset();
        mRecorder.release();
    }

    public File getStorageFolder() {
        try {
            File folder = new File(getExternalFilesDir(null), Constants.AUDIO_FOLDER_NAME);
            FileUtils.forceMkdir(folder);
            return folder;
        } catch (IOException e) {
            // TODO: maybe there is a user-friendly way to handle this exception
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public File getTemporaryAudioFile() {
        return new File(getStorageFolder(), Constants.TEMPORARY_AUDIO_FILE_NAME);
    }

    private void startRecording() {
        String audioFilePath = getTemporaryAudioFile().getAbsolutePath();
        Log.d(TAG, String.format("Starting recording to file %s", audioFilePath));
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioChannels(Constants.AUDIO_NUM_CHANNELS);
        mRecorder.setAudioSamplingRate(Constants.AUDIO_SAMPLING_RATE);
        mRecorder.setAudioEncodingBitRate(Constants.AUDIO_BITRATE);
        mRecorder.setOutputFile(audioFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    /**
     * Stop the recording
     * @return true if the recording was successful.
     */
    private boolean stopRecording() {
        boolean result = true;
        try {
            mRecorder.stop();
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
            result = false;
        }
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        Log.d(TAG, String.format("Recording status " + result));

        // play test
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(getTemporaryAudioFile().getAbsolutePath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return result;
    }


}
