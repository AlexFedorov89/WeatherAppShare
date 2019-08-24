package com.geekbrains.fedorov.alex.weathernew.dbHandlers;

public interface Handler {
    boolean addWeather(int temperature, int humidity);
    void getWeather();
    void changeCity(String city);

}
