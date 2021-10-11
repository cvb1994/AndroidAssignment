package com.example.assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiManager {
    public static String BASE_URL = "https://dataservice.accuweather.com";

    @GET("/forecasts/v1/hourly/12hour/353412?apikey=CxILqfbYMdKI30fs02iXyl2JZJdF2MeU&language=vi-vn&metric=true")
    Call<List<Item>> getList();
}
