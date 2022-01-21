package com.example.evaluacion_rogerac.service.api;

import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileApi {
    String base = "api/file";
    @Multipart
    @POST(base)
    Call<GenericResponse<File>> save(@Part MultipartBody.Part file, @Part("nombre") RequestBody requestBody);
}
