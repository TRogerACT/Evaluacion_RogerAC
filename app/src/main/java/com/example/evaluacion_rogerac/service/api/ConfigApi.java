package com.example.evaluacion_rogerac.service.api;

import com.example.evaluacion_rogerac.service.entity.service.Usuario;
import com.example.evaluacion_rogerac.service.util.DateSerializer;
import com.example.evaluacion_rogerac.service.util.TimeSerializer;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    public static final String baseUrlE = "http://10.0.2.2:9080";
    private static Retrofit retrofit;
    private static String token = "";

    private static ClienteApi clienteApi;
    private static UsuarioApi usuarioApi;
    private static FileApi fileApi;
    private static  CuentasApi cuentasApi;
    static {
        initClient();
    }

    private static void initClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlE)//cambiar aquí con la ip del ordenador al usar el movil
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    public static OkHttpClient getClient() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.level(HttpLoggingInterceptor.Level.BODY);

        StethoInterceptor stetho = new StethoInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(loggin)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);
        return builder.build();
    }

    public static void setToken(String value) {
        token = value;
        initClient();
    }

    public static FileApi getFile() {
        if (fileApi == null) {
            fileApi = retrofit.create(FileApi.class);
        }
        return fileApi;
    }
    public static ClienteApi getClienteApi() {
        if (clienteApi == null) {
            clienteApi = retrofit.create(ClienteApi.class);
        }
        return clienteApi;
    }

    public static UsuarioApi getUsuarioApi() {
        if (usuarioApi == null) {
            usuarioApi = retrofit.create(UsuarioApi.class);
        }
        return usuarioApi;
    }

    public static CuentasApi getCuentasApi() {
        if(cuentasApi == null){
            cuentasApi = retrofit.create(CuentasApi.class);
        }
        return  cuentasApi;
    }

}
