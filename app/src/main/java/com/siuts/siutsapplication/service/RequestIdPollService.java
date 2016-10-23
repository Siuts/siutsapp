package com.siuts.siutsapplication.service;


import com.siuts.siutsapplication.domain.ClassifiedBird;
import com.siuts.siutsapplication.domain.Endpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestIdPollService {

    // Synchronous declaration
    @GET(Endpoints.POLL + "/{requestId}")
    Call<List<ClassifiedBird>> classifiedBirds(@Path("requestId") String requestId);
}
