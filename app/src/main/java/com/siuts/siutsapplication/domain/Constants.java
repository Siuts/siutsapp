package com.siuts.siutsapplication.domain;


public class Constants {
    public static final String INTENT_EXTRA_CLASSIFICATION_RESULTS = "INTENT_EXTRA_CLASSIFICATION_RESULTS";

    public static final String AUDIO_FOLDER_NAME = "audiofolder";
    public static final String TEMPORARY_AUDIO_FILE_NAME = "audio.3gpp";
    public static final int AUDIO_SAMPLING_RATE = 44100;
    public static final int AUDIO_NUM_CHANNELS = 1;
    public static final int AUDIO_BITRATE = 192 * 1024;

    public static final int RECORDING_LENGTH = 5 * 1000; // in milliseconds
    public static final int POLL_INTERVAL = 2 * 1000; // in milliseconds
    public static final int MAX_POLL_RETRIES = 500;
}
