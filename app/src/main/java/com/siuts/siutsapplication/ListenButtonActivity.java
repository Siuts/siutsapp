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

import com.siuts.siutsapplication.domain.Constants;
import com.siuts.siutsapplication.service.FileUploadService;
import com.siuts.siutsapplication.service.ServiceGenerator;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.saeid.fabloading.LoadingView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

//        listenButton.addAnimation(0x64B5F6, R.drawable.canary_with_microphone, LoadingView.FROM_LEFT);
//        listenButton.addAnimation(0x64B5ff, R.drawable.canary_confused_1, LoadingView.FROM_TOP);
//        listenButton.addAnimation(0x64B500, R.drawable.canary_music_symbol_1, LoadingView.FROM_BOTTOM);

        listenButton.setOnClickListener((View v) -> {
            listenButton.startAnimation();
        });

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

    // https://futurestud.io/tutorials/retrofit-2-how-to-upload-files-to-server
    public void uploadAudio(String audioFilePath) {
        // TODO: clean up stuff and adjust it to our use case
        // create upload service client
        FileUploadService service = ServiceGenerator.createService(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(audioFilePath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
}
