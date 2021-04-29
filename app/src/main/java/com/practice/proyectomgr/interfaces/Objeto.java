package com.practice.proyectomgr.interfaces;

import com.practice.proyectomgr.models.Objetos;
import com.practice.proyectomgr.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Objeto {


    //  @GET("/api/v1/users?{emailuser}&{emailpass}")
  /*  public Call<User> find(
            @Query("emailuser") String emailuser,
            @Query("emailpass") String emailpass

    );*/
    @GET("/api/v1/item/{id}")
    public Call<Objetos> find(
            @Query("email") String emailuser);
    @POST("/api/v1/item")
    public Call<Objetos> saveItem(
            @Query("name") String name,
            @Query("description") String description,
            @Query("itemFunction") String itemFunction,
            @Query("itemType") String itemType,
            @Query("price") String price,
            @Query("home_space_id") String home_space_id,
            @Query("user_id") String user_id

    );





}
