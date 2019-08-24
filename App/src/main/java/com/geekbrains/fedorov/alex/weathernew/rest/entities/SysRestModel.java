package com.geekbrains.fedorov.alex.weathernew.rest.entities;

import com.google.gson.annotations.SerializedName;

/**
 * <b>type</b> - <i>integer</i> - Internal parameter.
 * <br><b>id</b> - <i>float</i> - Internal parameter.
 * <b>message</b> - <i>float</i> - Internal parameter.
 * <b>country</b> - <i>String</i> - Country code (GB, JP etc.).
 * <b>sunrise</b> - <i>long</i> - Sunrise time, unix, UTC.
 * <b>sunset</b> - <i>long</i> - Sunset time, unix, UTC.<p>
 */

public class SysRestModel {
    @SerializedName("type") public int type;
    @SerializedName("id") public int id;
    @SerializedName("message") public float message;
    @SerializedName("country") public String country;
    @SerializedName("sunrise") public long sunrise;
    @SerializedName("sunset") public long sunset;
}
