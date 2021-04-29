package com.practice.proyectomgr.interfaces;

import com.practice.proyectomgr.models.HomeSpaces;
import com.practice.proyectomgr.models.HomeSpacesValues;
import com.practice.proyectomgr.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeSpace {

        @GET("/api/v1/space/{home_id}")
        public Call<List<HomeSpacesValues>> findSpace(@Path("home_id") String home_id);

        @POST("/api/v1/space")
        public Call<HomeSpacesValues> saveSpace(
                @Query("spaceName") String username,
                @Query("spaceFunction") String spaceFunction,
                @Query("home_id") String home_id
        );


}
