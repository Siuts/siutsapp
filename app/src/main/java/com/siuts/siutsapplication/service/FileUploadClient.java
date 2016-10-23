package com.siuts.siutsapplication.service;

import com.siuts.siutsapplication.domain.Endpoints;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface FileUploadClient {
    @Multipart
    @POST(Endpoints.UPLOAD)
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}
