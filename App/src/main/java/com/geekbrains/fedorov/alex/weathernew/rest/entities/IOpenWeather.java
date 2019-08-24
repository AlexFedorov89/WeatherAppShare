package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequestRestModel> loadWeather(@Query("q") String city,
                                              @Query("APPID") String keyApi,
                                              @Query("units") String units);
}
