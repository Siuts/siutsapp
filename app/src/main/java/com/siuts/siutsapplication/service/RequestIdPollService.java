package com.siuts.siutsapplication.service;


import com.siuts.siutsapplication.domain.ClassifiedBird;
import com.siuts.siutsapplication.domain.Endpoints;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestIdPollService {

    // Synchronous declaration
    @GET(Endpoints.POLL + "/{requestId}")
    Call<ArrayList<ClassifiedBird>> getClassifiedBirds(@Path("requestId") String requestId);
}
