package com.geekbrains.fedorov.alex.weathernew.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.geekbrains.fedorov.alex.weathernew.R;

public class CustomView extends LinearLayout {


    public CustomView(Context context) {
        super(context);
        initializeViews(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.about_app, this);
    }

}
