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
        int cityId = SQLTableCities.readCity(sqLiteDatabase, city);

        if (cityId == -1) {
            SQLTableCities.createEntryCity(sqLiteDatabase, city);

            cityId = SQLTableCities.readCity(sqLiteDatabase, city);
        }

        this.currentCityId = cityId;
    }

    @Override
    public boolean addWeather(int temperature, int humidity) {
        SQLTableWeather.addWeather(sqLiteDatabase, currentCityId, temperature, humidity);

        return true;
    }



    @Override
    public void getWeather() {
        SQLTableWeather.getWeather(sqLiteDatabase, currentCityId, dataViewModel);

    }


}
