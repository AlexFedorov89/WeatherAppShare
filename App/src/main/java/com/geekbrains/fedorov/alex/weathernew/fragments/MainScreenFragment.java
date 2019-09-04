package com.geekbrains.fedorov.alex.weathernew.fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import com.geekbrains.fedorov.alex.weathernew.R;
import com.geekbrains.fedorov.alex.weathernew.dbHandlers.HandlerImplSQL;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;

import java.util.Objects;

public class MainScreenFragment extends Fragment {
    private TextView tempView;
    private TextView humidityView;
    private TextView cityView;
    private UpdateDataViewModel dataViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen, container, false);

        dataViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UpdateDataViewModel.class);
        initViews(view);
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

        return view;
    }

    private void initDB() {
        dataViewModel.handler = new HandlerImplSQL(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initViews(View view) {
        tempView        = view.findViewById(R.id.temperature);
        humidityView    = view.findViewById(R.id.humidity);
        cityView        = view.findViewById(R.id.city);
    }

    private void setTemp(float temperature) {
        if (this.tempView != null) {
            this.tempView.setText(String.valueOf(temperature));
        }
    }

    private void setHumidity(float humidity) {
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