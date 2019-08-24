package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRestModel {
    @SerializedName("id") private int id; // Weather condition id.
    @SerializedName("main") private String main; // Group of weather parameters (Rain, Snow, Extreme etc.).
    @SerializedName("description") private String description ; // Weather condition within the group.
    @SerializedName("icon") private String icon ; // Weather icon id.
}
