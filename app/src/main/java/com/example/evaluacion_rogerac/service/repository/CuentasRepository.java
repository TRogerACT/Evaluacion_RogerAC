package com.example.evaluacion_rogerac.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaluacion_rogerac.service.api.ConfigApi;
import com.example.evaluacion_rogerac.service.api.CuentasApi;
import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.Cuentas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentasRepository {
    private final CuentasApi api;
    private static CuentasRepository repository;

    public CuentasRepository() {
        this.api = ConfigApi.getCuentasApi();
    }

    public static CuentasRepository getInstance() {
        if (repository == null) {
            repository = new CuentasRepository();
        }
        return repository;
    }
    public LiveData<GenericResponse<List<Cuentas>>> listarCuentas(){
        final MutableLiveData<GenericResponse<List<Cuentas>>> mdl = new MutableLiveData<>();
        this.api.listarCuentas().enqueue(new Callback<GenericResponse<List<Cuentas>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Cuentas>>> call, Response<GenericResponse<List<Cuentas>>> response) {
                mdl.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Cuentas>>> call, Throwable t) {
                mdl.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mdl;
    }
}
