package com.geekbrains.fedorov.alex.weathernew.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geekbrains.fedorov.alex.weathernew.Constants;
import com.geekbrains.fedorov.alex.weathernew.services.Locations;
import com.geekbrains.fedorov.alex.weathernew.R;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    private EditText newCityView;
    private UpdateDataViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings, container, false);

        initViews(view);
        setViewModelProvider(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.preferences);

        if (item != null)
            item.setVisible(false);
    }

    private void initViews(View view) {
        newCityView = view.findViewById(R.id.newCity);
        view.findViewById(R.id.commitCityChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run method in ViewModel.
                String newCity = newCityView.getText().toString();
                if (!isCityWithCountryCode(newCity)) {
                    newCity += "," + Constants.COUNTRY_RUSSIA;
                }
                //model.setCurrentCity(newCity);
                model.setCity(newCity);

                // Hides soft keyboard.
                final InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);

                // Show msg.
                Snackbar.make(view, getActivity().getString(R.string.cityChanged), Snackbar.LENGTH_LONG)
                        .show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        ImageButton btnCurrentLocation = view.findViewById(R.id.btnCurrentLocation);
        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locations.getAndSetCurrentLocation(getContext(), model);
            }
        });
    }

    private boolean isCityWithCountryCode(String city) {
        return city.indexOf(',') != -1;
    }

    private void setViewModelProvider(View view) {
        model = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UpdateDataViewModel.class);
        model.getCurrentLocation().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                newCityView.setText(s);
            }
        });
    }
}