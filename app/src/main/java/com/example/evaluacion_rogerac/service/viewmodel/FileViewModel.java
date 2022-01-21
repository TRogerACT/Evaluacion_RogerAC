package com.example.evaluacion_rogerac.service.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.File;
import com.example.evaluacion_rogerac.service.repository.FileRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileViewModel extends AndroidViewModel {
    private final FileRepository repository;

    public FileViewModel(@NonNull Application application) {
        super(application);
        this.repository = FileRepository.getInstance();
    }
    public LiveData<GenericResponse<File>> save(MultipartBody.Part part, RequestBody requestBody){
        return this.repository.savePhoto(part, requestBody);
    }
}