package com.geekbrains.fedorov.alex.weathernew.services;

import android.app.IntentService;
import android.content.Intent;

public class WeatherUpdaterService extends IntentService {

    private static String SERVICE_NAME = "background_get_weather_service_from_http";

    public WeatherUpdaterService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
