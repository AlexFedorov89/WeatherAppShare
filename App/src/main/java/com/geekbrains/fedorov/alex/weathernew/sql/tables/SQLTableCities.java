package com.geekbrains.fedorov.alex.weathernew.sql.tables;

import android.database.sqlite.SQLiteDatabase;

public class SQLTableCities {
    public static String CITIES_TABLE_NAME = "cities";
    public static String CITIES_FIELD_ID = "id";
    public static String CITIES_FIELD_NAME = "name";

    public static void createTable(SQLiteDatabase sqLiteDatabase){
        // Create table cities.
        sqLiteDatabase.execSQL("CREATE TABLE " + CITIES_TABLE_NAME + " ("
                + CITIES_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + CITIES_FIELD_NAME + " TEXT"
                + ");");
    }
}
