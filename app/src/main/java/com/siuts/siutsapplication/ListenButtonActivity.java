package com.siuts.siutsapplication;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.siuts.siutsapplication.domain.Constants;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.saeid.fabloading.LoadingView;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class ListenButtonActivity extends AppCompatActivity {
    public static final String TAG = ListenButtonActivity.class.getName();

    @BindView(R.id.listenButton) LoadingView listenButton;
    private MediaRecorder mRecorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_button);
        ButterKnife.bind(this);

        listenButton.addAnimation(0x64B5F6, R.drawable.emberiza_citrinella, LoadingView.FROM_LEFT);
        listenButton.addAnimation(0x64B5ff, R.drawable.apus_apus, LoadingView.FROM_BOTTOM);
        listenButton.addAnimation(0x64B500, R.drawable.chloris_chloris, LoadingView.FROM_TOP);
        listenButton.addAnimation(0x64B5F6, R.drawable.fab_shadow, LoadingView.FROM_RIGHT);

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
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioChannels(Constants.AUDIO_NUM_CHANNELS);
        mRecorder.setAudioSamplingRate(Constants.AUDIO_SAMPLING_RATE);
        mRecorder.setAudioEncodingBitRate(Constants.AUDIO_BITRATE);
        mRecorder.setOutputFile(getTemporaryAudioFile().getAbsolutePath());
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
            result = false;
        }
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        Log.d(TAG, String.format("Recording path %s with status %d", getTemporaryAudioFile().getAbsolutePath()));
        return result;
    }
}
