package com.geekbrains.fedorov.alex.weathernew.sql.tables;

import android.database.Cursor;
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

    public static void createEntryCity(SQLiteDatabase sqLiteDatabase, String city) {
        sqLiteDatabase.execSQL("INSERT INTO " + CITIES_TABLE_NAME + " (" + CITIES_FIELD_NAME + ") VALUES (\"" + city + "\");");
    }

    public static int readCity(SQLiteDatabase sqLiteDatabase, String city){
        int resultCityId;
        Cursor c = sqLiteDatabase.rawQuery("SELECT " + CITIES_FIELD_ID + " FROM " + CITIES_TABLE_NAME + " WHERE " + CITIES_FIELD_NAME + " = ? LIMIT 1", new String[] {city});

        if(c != null && c.moveToFirst()){
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(CITIES_FIELD_ID);
            resultCityId = c.getInt(idColIndex);

            c.close();

            return resultCityId;
        }

        return -1;
    }
}
