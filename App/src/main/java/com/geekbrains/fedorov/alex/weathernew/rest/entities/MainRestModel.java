package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import com.google.gson.annotations.SerializedName;

/**
 * <b>temp</b> - <i>float</i> - Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
 * <br><b>pressure</b> - <i>float</i> - Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa.
 * <br><b>humidity</b> - <i>float</i> - Humidity, %.
 * <br><b>temp_min</b> - <i>float</i> - Minimum temperature at the moment. This is deviation from current temp that
 * is possible for large cities and megalopolises geographically expanded (use these parameter optionally).
 * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
 * <br><b>temp_max</b> - <i>float</i> - Maximum temperature at the moment.
 * This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally).
 * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.<p>
 */

public class MainRestModel {
    @SerializedName("temp") public float temp;
    @SerializedName("pressure") public float pressure;
    @SerializedName("humidity") public float humidity;
    @SerializedName("temp_min") public float tempMin;
    @SerializedName("temp_max") public float tempMax;
}
