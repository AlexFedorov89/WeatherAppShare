package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import com.google.gson.annotations.SerializedName;

/**
 * <b>speed</b> - <i>float</i> - Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
 * <br><b>deg</b> - <i>float</i> - Wind direction, degrees (meteorological).<p>
 */

public class WindRestModel {
    @SerializedName("speed") public float speed;
    @SerializedName("deg") public float deg;
}
