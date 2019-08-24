package com.geekbrains.fedorov.alex.weathernew.sql.tables;

import android.database.sqlite.SQLiteDatabase;

public class SQLTableWeather {
    public static String WEATHER_TABLE_NAME = "weather";
    public static String WEATHER_FIELD_ID = "id";
    public static String WEATHER_FIELD_DATE = "date";
    public static String WEATHER_FIELD_CITY_ID = "cityId";
    public static String WEATHER_FIELD_TEMP = "temperature";
    public static String WEATHER_FIELD_HUMIDITY = "humidity";

    public static void createTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE "
                + WEATHER_TABLE_NAME + " ("
                + WEATHER_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WEATHER_FIELD_DATE + " INTEGER,"
                + WEATHER_FIELD_CITY_ID + " INTEGER,"
                + WEATHER_FIELD_TEMP + " INTEGER,"
                + WEATHER_FIELD_HUMIDITY + " INTEGER"
                + ");");
    }
}
