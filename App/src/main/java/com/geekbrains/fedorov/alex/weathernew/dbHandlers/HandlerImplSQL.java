package com.geekbrains.fedorov.alex.weathernew.dbHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geekbrains.fedorov.alex.weathernew.sql.DBHelper;
import com.geekbrains.fedorov.alex.weathernew.sql.tables.SQLTableCities;
import com.geekbrains.fedorov.alex.weathernew.sql.tables.SQLTableWeather;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;

public class HandlerImplSQL implements Handler {
    private SQLiteDatabase sqLiteDatabase;
    private UpdateDataViewModel dataViewModel;
    private int currentCityId;

    public HandlerImplSQL(Context context, String city) {
        sqLiteDatabase = new DBHelper(context).getWritableDatabase();
        dataViewModel = new UpdateDataViewModel();

        changeCity(city);
    }

    @Override
    public void changeCity(String city) {
        // TODO Разнести все по таблицам.
        int cityId = readCity(city);

        if (cityId == -1) {
            sqLiteDatabase.execSQL("INSERT INTO " + SQLTableCities.CITIES_TABLE_NAME + " (" + SQLTableCities.CITIES_FIELD_NAME + ") VALUES (\"" + city + "\");");

            cityId = readCity(city);
        }

        this.currentCityId = cityId;
    }

    private int readCity(String city){
        // TODO Разнести все по таблицам.
        int resultCityId;
        Cursor c = sqLiteDatabase.rawQuery("SELECT " + SQLTableCities.CITIES_FIELD_ID + " FROM " + SQLTableCities.CITIES_TABLE_NAME + " WHERE " + SQLTableCities.CITIES_FIELD_NAME + " = ? LIMIT 1", new String[] {city});

        if(c != null && c.moveToFirst()){
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(SQLTableCities.CITIES_FIELD_ID);
            resultCityId = c.getInt(idColIndex);

            c.close();

            return resultCityId;
        }

        return -1;
    }

    @Override
    public boolean addWeather(int temperature, int humidity) {

        // TODO Разнести все по таблицам.
        ContentValues cv = new ContentValues();
        cv.put(SQLTableWeather.WEATHER_FIELD_DATE, System.currentTimeMillis());
        cv.put(SQLTableWeather.WEATHER_FIELD_CITY_ID, currentCityId);
        cv.put(SQLTableWeather.WEATHER_FIELD_TEMP, temperature);
        cv.put(SQLTableWeather.WEATHER_FIELD_HUMIDITY, humidity);

        sqLiteDatabase.insert(SQLTableWeather.WEATHER_TABLE_NAME, null, cv);

        return true;
    }

    @Override
    public void getWeather() {
//        // TODO Разнести все по таблицам.
//        Cursor c = sqLiteDatabase.rawQuery("SELECT " + SQLTableWeather.WEATHER_FIELD_TEMP + ", " + SQLTableWeather.WEATHER_FIELD_HUMIDITY + " FROM " + SQLTableWeather.WEATHER_TABLE_NAME + " WHERE " + SQLTableWeather.WEATHER_FIELD_CITY_ID + " = ? ORDER BY " + SQLTableWeather.WEATHER_FIELD_DATE + " DESC LIMIT 1", new String[] {String.valueOf(currentCityId)});
//
//        if(c != null && c.moveToFirst()){
//            // определяем номера столбцов по имени в выборке
//            int temperatureColIndex = c.getColumnIndex(SQLTableWeather.WEATHER_FIELD_TEMP);
//            int humidityColIndex = c.getColumnIndex(SQLTableWeather.WEATHER_FIELD_HUMIDITY);
//
//            dataViewModel.setTemp(c.getInt(temperatureColIndex));
//            dataViewModel.setHumidity(c.getInt(humidityColIndex));
//
//            c.close();
//
//        }

    }
}
