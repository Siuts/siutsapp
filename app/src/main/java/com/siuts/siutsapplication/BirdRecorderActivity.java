package com.siuts.siutsapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.siuts.siutsapplication.domain.ClassifiedBird;
import com.siuts.siutsapplication.domain.Constants;
import com.siuts.siutsapplication.domain.Endpoints;
import com.siuts.siutsapplication.domain.RecordingState;
import com.siuts.siutsapplication.service.ClientGenerator;
import com.siuts.siutsapplication.service.FileUploadClient;
import com.siuts.siutsapplication.service.RequestIdPollService;
import com.skyfishjy.library.RippleBackground;
import com.vstechlab.easyfonts.EasyFonts;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.saeid.fabloading.LoadingView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BirdRecorderActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;
    public static final String TAG = "MYTAG";

    @BindView(R.id.slowRipple) RippleBackground slowRipple;
    @BindView(R.id.fastRipple) RippleBackground fastRipple;
    @BindView(R.id.recordButton) LoadingView recordButton;
    @BindView(R.id.recordButtonText) TextView recordButtonText;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private RecordingState state = RecordingState.NOT_RECORDING;
    private String previousRequestId = "";
    private Thread pollingThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_recorder);
        ButterKnife.bind(this);
        checkPermissions();
        setupRecordButtonAnimation();
        recordButtonText.setTypeface(EasyFonts.robotoThin(this));

        slowRipple.startRippleAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (state == RecordingState.RECORDING) {
            stopRecording();
        }
        /*if (pollingThread != null) {
            pollingThread.interrupt();
        }*/
        state = RecordingState.NOT_RECORDING;
    }

    @OnClick(R.id.recordButton)
    public void pressRecordButton() {
        if (state == RecordingState.NOT_RECORDING) {
            enterRecordingState();
        }
    }

    private void enterRecordingState() {
        startAnimations();
        state = RecordingState.RECORDING;
        recordButtonText.setText("Listening to a bird...");
        startRecording();
        createStopRecordingTimer();
    }

    private void startAnimations() {
        slowRipple.stopRippleAnimation();
        fastRipple.startRippleAnimation();
        recordButton.startAnimation();
    }

    private void stopAnimations() {
        slowRipple.startRippleAnimation();
        fastRipple.stopRippleAnimation();
        recordButton.pauseAnimation();
    }

    private void createStopRecordingTimer() {
        Handler handler = new Handler();
        Runnable runnable = () -> enterUploadState();
        handler.postDelayed(runnable, Constants.RECORDING_LENGTH);
    }

    private void enterUploadState() {
        if (stopRecording()) {
            Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show();
            state = RecordingState.UPLOADING;
            recordButtonText.setText("Uploading ..");
            uploadAudio(getTemporaryAudioFile().getPath());
        } else {
            state = RecordingState.FAILED;
            stopAnimations();
            Toast.makeText(this, "Recording failed! :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void enterProcessingState() {
        state = RecordingState.PROCESSING;
        recordButtonText.setText("Processing ..");

        // just in case we have to quickly fake data
        //Intent intent = new Intent(this, ClassificationResultsActivity.class);
        //startActivity(intent);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoints.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestIdPollService service = retrofit.create(RequestIdPollService.class);

        Runnable responsePoller = () -> {
            try {
                for (int retryNumber=0 ; retryNumber<Constants.MAX_POLL_RETRIES ; ++retryNumber) {
                    Call<ArrayList<ClassifiedBird>> call = service.getClassifiedBirds(previousRequestId);
                    Response<ArrayList<ClassifiedBird>> response = call.execute();
                    if (response.code() == 200) {
                        Log.e("UPLOAD", "SUCCESS!!!");
                        Intent intent = new Intent(this, ClassificationResultsActivity.class);
                        intent.putExtra(Constants.INTENT_EXTRA_CLASSIFICATION_RESULTS, response.body());
                        startActivity(intent);
                        return;
                    } else {
                        Log.e("UPLOAD", "sleeping");
                        Thread.sleep(Constants.POLL_INTERVAL);
                    }
                }
            } catch (IOException | InterruptedException e) {
                Log.d("UPLOAD", e.getMessage());
            }
        };
        pollingThread = new Thread(responsePoller);
        pollingThread.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void checkPermissions() {
        // http://stackoverflow.com/questions/3782786/android-mediarecorder-setaudiosource-failed
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
            },
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void setupRecordButtonAnimation() {
        recordButton.addAnimation(0x64B5F6, R.drawable.canary_with_mic, LoadingView.FROM_LEFT);
        recordButton.addAnimation(0x64B5ff, R.drawable.canary_confused, LoadingView.FROM_TOP);
        recordButton.addAnimation(0x64B5F6, R.drawable.canary_with_mic, LoadingView.FROM_RIGHT);
        recordButton.addAnimation(0x64B500, R.drawable.canary_music_symbol, LoadingView.FROM_BOTTOM);
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
        try {
            mRecorder.start();
        } catch (Exception ex) {
            Log.e(TAG, "Starting recording audio FAILED");
            Log.e(TAG, ex.getMessage());
        }
    }


    public File getTemporaryAudioFile() {
        return new File(getStorageFolder(), Constants.TEMPORARY_AUDIO_FILE_NAME);
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

    /**
     * Stop the recording
     * @return true if the recording was successful.
     */
    private boolean stopRecording() {
        boolean result = true;
        try {
            mRecorder.stop();
        } catch (RuntimeException e) {
            Log.e(TAG, "STOPPING RECORDING FAILED!");
            e.printStackTrace();
            result = false;
        }
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        Log.d(TAG, String.format("Recording status " + result));

        // play test
        /*try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(getTemporaryAudioFile().getAbsolutePath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }*/
        return result;
    }

    // https://futurestud.io/tutorials/retrofit-2-how-to-upload-files-to-server
    public void uploadAudio(String audioFilePath) {
        // create upload service client
        FileUploadClient service = ClientGenerator.createService(FileUploadClient.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(audioFilePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("audio_data", file.getName(), requestFile);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("UPLOAD", String.format("Response code: %d", response.code()));
                Log.v("UPLOAD", response.message());
                if (response.code() == 200) {
                    try {
                        String responseString = response.body().string();
                        Log.v("UPLOAD", responseString);
                        previousRequestId = responseString;
                        enterProcessingState();
                    } catch (IOException exc) {
                        Log.e("ERROR", exc.getMessage());
                        BirdRecorderActivity.this.state = RecordingState.NOT_RECORDING;
                        stopAnimations();
                    }
                } else {
                    Toast.makeText(BirdRecorderActivity.this, "Bad request!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BirdRecorderActivity.this, "Upload failure!", Toast.LENGTH_LONG).show();
                Log.v("Upload", "failure");
                Log.e("UPLOAD", t.getMessage());

                BirdRecorderActivity.this.state = RecordingState.NOT_RECORDING;
                stopAnimations();
            }
        });
    }

}
