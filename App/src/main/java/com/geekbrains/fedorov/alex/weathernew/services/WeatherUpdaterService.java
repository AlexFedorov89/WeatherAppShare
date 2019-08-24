package com.geekbrains.fedorov.alex.weathernew.services;

import android.app.IntentService;
import android.content.Intent;

import com.geekbrains.fedorov.alex.weathernew.MainActivity;

public class WeatherUpdaterService extends IntentService {

    private static String SERVICE_NAME = "background_get_weather_service_from_http";

    public WeatherUpdaterService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // get weather from web-server

        Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION);
        sendBroadcast(broadcastIntent);
        stopSelf();
    }
}
