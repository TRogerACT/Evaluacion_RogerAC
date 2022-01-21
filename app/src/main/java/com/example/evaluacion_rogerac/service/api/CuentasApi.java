package com.example.evaluacion_rogerac.service.api;

import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.Cuentas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CuentasApi {
    String base = "api/Cuentas";

    @GET(base)
    Call<GenericResponse<List<Cuentas>>> listarCuentas();

}
