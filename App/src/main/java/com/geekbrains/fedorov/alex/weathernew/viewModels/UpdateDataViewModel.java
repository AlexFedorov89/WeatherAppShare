package com.geekbrains.fedorov.alex.weathernew.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geekbrains.fedorov.alex.weathernew.dbHandlers.Handler;

public class UpdateDataViewModel extends ViewModel {
    private String currentCity;
    public Handler handler;

    private final MutableLiveData<String> temp = new MutableLiveData<>();
    private final MutableLiveData<String> humidity = new MutableLiveData<>();
    private final MutableLiveData<String> city = new MutableLiveData<>();

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public LiveData<String> getNewCity() {
        return city;
    }

    public void setTemp(int temp) {
        this.temp.setValue(String.valueOf(temp));
    }

    public void setHumidity(int humidity) {
        this.humidity.setValue(String.valueOf(humidity));
    }

    public LiveData<String> getTemp() {
        return temp;
    }

    public LiveData<String> getHumidity() {
        return humidity;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
}