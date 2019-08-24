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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.geekbrains.fedorov.alex.weathernew.R;
import com.geekbrains.fedorov.alex.weathernew.viewModels.UpdateDataViewModel;
import com.google.android.material.snackbar.Snackbar;

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
    }

    private void setViewModelProvider(View view) {
        model = ViewModelProviders.of(getActivity()).get(UpdateDataViewModel.class);
        view.findViewById(R.id.commitCityChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run method in ViewModel.
                model.setCity(newCityView.getText().toString());

                // Hides soft keyboard.
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                // Show msg.
                Snackbar.make(view, getActivity().getString(R.string.cityChanged), Snackbar.LENGTH_LONG)
                        .show();

            }
        });
    }
}
