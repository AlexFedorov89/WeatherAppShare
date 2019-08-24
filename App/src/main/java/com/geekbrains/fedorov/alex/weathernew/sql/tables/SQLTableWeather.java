package com.geekbrains.fedorov.alex.weathernew.sql.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;

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

    public static void addWeather(SQLiteDatabase sqLiteDatabase, int cityId, int temperature, int humidity) {
        ContentValues cv = new ContentValues();
        cv.put(WEATHER_FIELD_DATE, System.currentTimeMillis());
        cv.put(WEATHER_FIELD_CITY_ID, cityId);
        cv.put(WEATHER_FIELD_TEMP, temperature);
        cv.put(WEATHER_FIELD_HUMIDITY, humidity);

        sqLiteDatabase.insert(WEATHER_TABLE_NAME, null, cv);
    }

    public static void getWeather(SQLiteDatabase sqLiteDatabase, int currentCityId, UpdateDataViewModel dataViewModel) {
        Cursor c = sqLiteDatabase.rawQuery("SELECT " + WEATHER_FIELD_TEMP + ", "
                + WEATHER_FIELD_HUMIDITY + " FROM " + WEATHER_TABLE_NAME + " WHERE "
                + WEATHER_FIELD_CITY_ID + " = ? ORDER BY "
                + WEATHER_FIELD_DATE
                + " DESC LIMIT 1", new String[] {String.valueOf(currentCityId)});

        if(c != null && c.moveToFirst()){
            // define the number of columns named in the sample.
            int temperatureColIndex = c.getColumnIndex(WEATHER_FIELD_TEMP);
            int humidityColIndex = c.getColumnIndex(WEATHER_FIELD_HUMIDITY);

            dataViewModel.setTemp(c.getInt(temperatureColIndex));
            dataViewModel.setHumidity(c.getInt(humidityColIndex));

            c.close();

        }
    }
}
