package com.example.evaluacion_rogerac.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaluacion_rogerac.service.api.ConfigApi;
import com.example.evaluacion_rogerac.service.api.FileApi;
import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileRepository {
    private final FileApi api;
    private static FileRepository repository;

    public FileRepository() {
        this.api = ConfigApi.getFile();
    }

    public static FileRepository getInstance(){
        if(repository == null){
            repository = new FileRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<File>> savePhoto(MultipartBody.Part part, RequestBody requestBody){
        final MutableLiveData<GenericResponse<File>> mld = new MutableLiveData<>();
        this.api.save(part, requestBody).enqueue(new Callback<GenericResponse<File>>() {
            @Override
            public void onResponse(Call<GenericResponse<File>> call, Response<GenericResponse<File>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<File>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}