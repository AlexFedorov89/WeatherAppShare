package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import com.google.gson.annotations.SerializedName;

/**
 * <b>lon</b> - <i>float</i> - City geo location, longitude.
 * <br><b>lat</b> - <i>float</i> - City geo location, latitude.<p>
 */

public class CoordRestModel {
    @SerializedName("lon") private float lon;
    @SerializedName("lat") private float lat;
}
