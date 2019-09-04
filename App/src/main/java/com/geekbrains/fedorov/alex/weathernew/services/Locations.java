package com.geekbrains.fedorov.alex.weathernew.services;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.geekbrains.fedorov.alex.weathernew.Constants;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Locations {
    public static void getAndSetCurrentLocation(final Context context, final UpdateDataViewModel dataViewModel) {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(context));
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {

                    // Create geocoder
                    final Geocoder geo = new Geocoder(context);

                    // Try to get addresses list
                    List<Address> list;

                    try {
                        list = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();

                        dataViewModel.setCurrentLocation(String.format("%s,%s", Constants.DEFAULT_CITY, Constants.COUNTRY_RUSSIA));
                        return;
                    }

                    if (list.isEmpty()) {
                        dataViewModel.setCurrentLocation(String.format("%s,%s", Constants.DEFAULT_CITY, Constants.COUNTRY_RUSSIA));
                        return;
                    }

                    // Get first element from List
                    Address a = Objects.requireNonNull(list).get(0);

                    dataViewModel.setCurrentLocation(String.format("%s,%s", a.getAdminArea(), a.getCountryCode()));
                }
            }
        });

    }
}

