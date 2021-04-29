package com.practice.proyectomgr.interfaces;

import com.practice.proyectomgr.models.Producto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductoAPI {
    @GET("/api/v1/home/{id}")
    public Call<Producto> Home(@Path("id") String id);

}
