package com.geekbrains.fedorov.alex.weathernew.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.geekbrains.fedorov.alex.weathernew.sql.tables.SQLTableCities;
import com.geekbrains.fedorov.alex.weathernew.sql.tables.SQLTableWeather;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "WeatherAppDB";
    private static int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        SQLTableCities.createTable(sqLiteDatabase);
        SQLTableWeather.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
