package com.example.evaluacion_rogerac.service.api;

import com.example.evaluacion_rogerac.service.entity.GenericResponse;
import com.example.evaluacion_rogerac.service.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {

    String base = "api/cliente";
    @POST(base)
    Call<GenericResponse<Cliente>> guardarCliente(@Body Cliente c);
}