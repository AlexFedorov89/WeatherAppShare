package com.geekbrains.fedorov.alex.weathernew.fragments;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.geekbrains.fedorov.alex.weathernew.R;
import com.geekbrains.fedorov.alex.weathernew.dbHandlers.HandlerImplSQL;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;

import java.util.Objects;

import static android.content.Context.SENSOR_SERVICE;

public class MainScreenFragment extends Fragment {

    private TextView tempView;
    private TextView humidityView;
    private TextView cityView;

    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;

    private String CURRENT_CITY = "CURRENT_CITY";

    UpdateDataViewModel dataViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_screen, container, false);

        dataViewModel = ViewModelProviders.of(getActivity()).get(UpdateDataViewModel.class);
        initViews(view);
        getCityFromSharedPref();
        initDB();


        dataViewModel.getNewCity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dataViewModel.setCurrentCity(s);

                cityView.setText(s);

                dataViewModel.handler.changeCity(s);
            }
        });


        dataViewModel.getTemp().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tempView.setText(String.format("%s%s", s, "°"));
            }
        });

        dataViewModel.getHumidity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                humidityView.setText(String.format("%s%s", s, "%"));
            }
        });


        setCity();

        return view;
    }

    private void initDB() {
        dataViewModel.handler = new HandlerImplSQL(getActivity(), dataViewModel.getCurrentCity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        sensorManager.registerListener(listenerTemp, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(listenerHumidity, sensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();

        saveCityInSharedPref();

//        sensorManager.unregisterListener(listenerTemp, sensorTemperature);
//        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
    }

//    private void observeCityChange() {
//
//        model.getNewCity().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                currentCity = s;
//                cityView.setText(s);
//            }
//        });
//    }

    private void initViews(View view) {
        tempView = view.findViewById(R.id.temperature);
        humidityView = view.findViewById(R.id.humidity);
        cityView = view.findViewById(R.id.city);
    }

    private void getCityFromSharedPref() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        dataViewModel.setCurrentCity(sharedPreferences.getString(CURRENT_CITY, getString(R.string.defaultCity)));
    }

    private void saveCityInSharedPref() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = defaultPrefs.edit();

        editor.putString(CURRENT_CITY, dataViewModel.getCurrentCity());
        editor.apply();
    }

    private void setCity() {
        if (cityView != null) {
            cityView.setText(dataViewModel.getCurrentCity());
        }
    }

    public void setTemp(float temperature) {

        if (this.tempView != null) {
            this.tempView.setText(String.valueOf(temperature));
        }
    }

    public void setHumidity(float humidity) {
        if (this.humidityView != null) {
            this.humidityView.setText(String.valueOf(humidity));
        }
    }

    private SensorEventListener listenerTemp = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            setTemp(sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener listenerHumidity = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            setHumidity(sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
