package com.example.evaluacion_rogerac.service.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.Cliente;
import com.example.evaluacion_rogerac.service.repository.ClienteRepository;

public class ClienteViewModel extends AndroidViewModel {
    private final ClienteRepository repository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c){
        return  repository.guardarCliente(c);
    }
}
