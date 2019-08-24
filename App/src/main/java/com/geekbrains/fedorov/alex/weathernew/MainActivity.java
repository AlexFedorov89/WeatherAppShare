package com.geekbrains.fedorov.alex.weathernew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;

import com.geekbrains.fedorov.alex.weathernew.rest.NetworkService;
import com.geekbrains.fedorov.alex.weathernew.fragments.AboutAppFragment;
import com.geekbrains.fedorov.alex.weathernew.fragments.MainScreenFragment;
import com.geekbrains.fedorov.alex.weathernew.fragments.SettingsFragment;
import com.geekbrains.fedorov.alex.weathernew.rest.entities.WeatherRequestRestModel;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
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

import android.view.Menu;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "com.geekbrains.fedorov.alex.WeatherApp";

    public final static String BROADCAST_ACTION = "com.geekbrains.fedorov.alex.get_weather_action";

    private UpdateDataViewModel dataViewModel;

    private ServiceFinishedReceiver receiver = new ServiceFinishedReceiver();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataViewModel();
        initToolbarAndDrawer();
        replaceFragmentInContainer(new MainScreenFragment());
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

        // On start updates data.
        updateData();

        registerReceiver(receiver, new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(receiver);
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

                updateData();

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void updateData(){
        updateData(dataViewModel.getCurrentCity());
    }

    private void updateData(String city) {
        NetworkService.getInstance()
                .getAPI()
                .loadWeather( city + ",ru", "2f52e9c6a1593820f50a6e8250774404", "metric")
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

                            Toast.makeText(MainActivity.this, "Data updated!", Toast.LENGTH_SHORT).show();
                        }

                        // Is it good to use that?
                        if (response.raw().code() == 404) {
                        //if (response.raw().code().equalsIgnoreCase("not found")) {
                            Toast.makeText(MainActivity.this, "City not found!", Toast.LENGTH_SHORT).show();

                            // set zero temp and humidity.
                            dataViewModel.setTemp(0);
                            dataViewModel.setHumidity(0);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherRequestRestModel> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Error occurred while getting request!", Toast.LENGTH_SHORT).show();

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
        public void onReceive(Context context, Intent intent) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.UpdateDataInfoString), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
