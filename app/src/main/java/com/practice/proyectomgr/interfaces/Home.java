package com.practice.proyectomgr.interfaces;
import com.practice.proyectomgr.models.Homes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Home {
    @GET("/api/v1/home/{id}")
    public Call<Homes> findHome(@Path("id") String id);
}