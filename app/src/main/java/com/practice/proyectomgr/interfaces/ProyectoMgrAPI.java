package com.practice.proyectomgr.interfaces;

import com.practice.proyectomgr.models.Producto;
import com.practice.proyectomgr.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProyectoMgrAPI {
  //  @GET("/api/v1/users?{emailuser}&{emailpass}")
  /*  public Call<User> find(
            @Query("emailuser") String emailuser,
            @Query("emailpass") String emailpass

    );*/
    @GET("/api/v1/users")
   public Call<User> find(
            @Query("email") String emailuser,
            @Query("password") String emailpass);
    @POST("/api/v1/user")
    public Call<User> save(
            @Query("username") String username,
            @Query("email") String email,
            @Query("password") String password,
            @Query("home") String home
    );
}
