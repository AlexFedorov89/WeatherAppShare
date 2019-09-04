package com.geekbrains.fedorov.alex.weathernew;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.geekbrains.fedorov.alex.weathernew.rest.NetworkService;
import com.geekbrains.fedorov.alex.weathernew.fragments.AboutAppFragment;
import com.geekbrains.fedorov.alex.weathernew.fragments.MainScreenFragment;
import com.geekbrains.fedorov.alex.weathernew.fragments.SettingsFragment;
import com.geekbrains.fedorov.alex.weathernew.rest.entities.WeatherRequestRestModel;
import com.geekbrains.fedorov.alex.weathernew.services.Locations;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import android.view.Menu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "com.geekbrains.fedorov.alex.WeatherApp";

    public final static String BROADCAST_ACTION = "com.geekbrains.fedorov.alex.get_weather_action";

    private UpdateDataViewModel dataViewModel;

    private ServiceFinishedReceiver receiver = new ServiceFinishedReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataViewModel();
        initToolbarAndDrawer();
        replaceFragmentInContainer(new MainScreenFragment());
        getCityFromSharedPref();
    }

    private void initDataViewModel() {
        dataViewModel = ViewModelProviders.of(this).get(UpdateDataViewModel.class);

        dataViewModel.getNewCity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateData(s);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(receiver, new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();

        saveCityInSharedPref();

        unregisterReceiver(receiver);
    }

    private void saveCityInSharedPref() {
        final SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = defaultPrefs.edit();

        editor.putString(Constants.CURRENT_CITY_SHARED_PRF, dataViewModel.getCurrentCity());
        editor.apply();
    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void replaceFragmentInContainer(Fragment fr) {
        replaceFragmentInContainer(fr, false);
    }

    private void replaceFragmentInContainer(Fragment fr, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.container, fr)
                .commit();
    }

    private void getCityFromSharedPref() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String cityFromSharedPrf = sharedPreferences.getString(Constants.CURRENT_CITY_SHARED_PRF, null);

        if (cityFromSharedPrf != null) {
            dataViewModel.setCurrentCity(cityFromSharedPrf);
            dataViewModel.setCity(cityFromSharedPrf);
        } else {
            dataViewModel.getCurrentLocation().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    //tempView.setText(String.format("%s%s", s, "°"));
                    dataViewModel.setCity(s);
                    // after first application - remove obs.
                    dataViewModel.getCurrentLocation().removeObserver(this);
                }
            });

            // Request permission.
            requestLocationPermissions();
            Locations.getAndSetCurrentLocation(getApplicationContext(), dataViewModel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length <= 0 || (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.MSG_GIVE_PERMISSIONS), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void requestLocationPermissions() {
        final String[] permissions = new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, Constants.REQUEST_CODE_PERMISSIONS);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.preferences) {
            replaceFragmentInContainer(new SettingsFragment(), true);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case (R.id.nav_home):
                cleanPopBackStack();
                replaceFragmentInContainer(new MainScreenFragment());

                break;
            case (R.id.about_app):
                replaceFragmentInContainer(new AboutAppFragment(), true);

                break;
            case (R.id.updateData):
                updateData(dataViewModel.getCurrentCity());

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void updateData(String city) {
        dataViewModel.setCurrentCity(city);

        NetworkService.getInstance()
                .getAPI()
                .loadWeather(city + "," + dataViewModel.getCurrentCountry(), "2f52e9c6a1593820f50a6e8250774404", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call, @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherRequestRestModel post = response.body();

                            int currentTemp = (int) post.main.temp;
                            int currentHumidity = (int) post.main.humidity;

                            // Updates data on screen.
                            dataViewModel.setTemp(currentTemp);
                            dataViewModel.setHumidity(currentHumidity);

                            dataViewModel.handler.addWeather(currentTemp, currentHumidity);

                            Snackbar.make(findViewById(android.R.id.content), getString(R.string.DATA_UPDATED), Snackbar.LENGTH_SHORT).show();
                        }
                        else if (response.raw().code() == 404) {

                            Snackbar.make(findViewById(android.R.id.content), getString(R.string.CITY_NOT_FOUND), Snackbar.LENGTH_SHORT).show();

                            // set zero temp and humidity.
                            dataViewModel.setTemp(0);
                            dataViewModel.setHumidity(0);
                        }
                        else {
                            Log.d(TAG, String.format("Unknown error code: %d, body: %s", response.raw().code(), response.raw().body()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherRequestRestModel> call, @NonNull Throwable t) {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.ERROR_REQUEST), Snackbar.LENGTH_SHORT).show();

                        Log.d(TAG, "Retrofit onFailure: " + t.getMessage());

                        t.printStackTrace();
                    }
                });
    }

    private void cleanPopBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private class ServiceFinishedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.DATA_UPDATED), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
