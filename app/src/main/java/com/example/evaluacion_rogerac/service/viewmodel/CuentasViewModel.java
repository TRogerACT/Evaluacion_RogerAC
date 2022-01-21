package com.example.evaluacion_rogerac.service.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.Cuentas;
import com.example.evaluacion_rogerac.service.repository.CuentasRepository;

import java.util.List;

public class CuentasViewModel extends AndroidViewModel {
    private final CuentasRepository repository;

    public CuentasViewModel(@NonNull Application application) {
        super(application);
        repository = CuentasRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Cuentas>>> listarCuentas(){
        return this.repository.listarCuentas();
    }
}
