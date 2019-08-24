package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import com.google.gson.annotations.SerializedName;

/**
 * <b>coord</b> - <i>CoordRestModel</i> - Coordinates.
 * <br><b>weather</b> - <i>WeatherRestModel[]</i> - (more info Weather condition codes).
 * <br><b>base</b> - <i>String</i> - Internal parameter.
 * <br><b>main</b> - <i>MainRestModel</i> - Common data of temperature.
 * <br><b>visibility</b> - <i>integer</i> - Visibility, meter.
 * <br><b>wind</b> - <i>WindRestModel</i> - Data about wind.
 * <br><b>clouds</b> - <i>CloudsRestModel</i> - Data about clouds.
 * <br><b>dt</b> - <i>long</i> - Time of data calculation, unix, UTC.
 * <br><b>sys</b> - <i>SysRestModel</i> - Additional info.
 * <br><b>id</b> - <i>long</i> - City ID.
 * <br><b>timezone</b> - <i>integer</i> - Shift in seconds from UTC.
 * <br><b>name</b> - <i>String</i> - City name.
 * <br><b>cod</b> - <i>integer</i> - Internal parameter.
 * <p>
 */

public class WeatherRequestRestModel {
    @SerializedName("coord") public CoordRestModel coordinates;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("base") public String base;
    @SerializedName("main") public MainRestModel main;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind") public WindRestModel wind;
    @SerializedName("clouds") public  CloudsRestModel clouds;
    @SerializedName("dt") public long dt;
    @SerializedName("sys") public  SysRestModel sys;
    @SerializedName("id") public  long id;
    @SerializedName("timezone") public int timezone;
    @SerializedName("name") public String name;
    @SerializedName("cod") public int cod;
}
