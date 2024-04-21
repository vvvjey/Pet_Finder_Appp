package com.example.pet_finder_app;


import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.lang.Integer;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

public class FilterAdopt extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] breed = { "India", "USA", "China", "Japan", "Other"};
    String[] color = { "Red", "Green", "Blue", "Pink", "Other"};
    EditText min, max;
    RangeSlider priceBar;
    TextView distance;
    Slider seekbar;
    Toolbar arrowBack;
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_adopt);

        TextView catCheck = findViewById(R.id.catCheck);
        catCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView dogCheck = findViewById(R.id.dogCheck);
        dogCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView turtleCheck = findViewById(R.id.turtleCheck);
        turtleCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView hamsCheck = findViewById(R.id.hamsCheck);
        hamsCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });


        TextView femaleCheck = findViewById(R.id.femaleCheck);
        TextView maleCheck = findViewById(R.id.maleCheck);
        arrowBack = findViewById(R.id.toolbarArrowBack);
        maleCheck.setSelected(true);
        maleCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdoptingPetActivity.class));
            }
        });
        femaleCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView smallCheck = findViewById(R.id.smallCheck);
        smallCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView mediumCheck = findViewById(R.id.mediumCheck);
        mediumCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView largeCheck = findViewById(R.id.largeCheck);
        largeCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView babyCheck = findViewById(R.id.babyCheck);
        babyCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView youngCheck = findViewById(R.id.youngCheck);
        youngCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView adultCheck = findViewById(R.id.adultCheck);
        adultCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        TextView seniorCheck = findViewById(R.id.seniorCheck);
        seniorCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });

        Spinner spinbreed = (Spinner) findViewById(R.id.spinnerBreed);
        spinbreed.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter br = new ArrayAdapter(this,android.R.layout.simple_spinner_item, breed);
        br.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinbreed.setAdapter(br);

        Spinner spincolor = (Spinner) findViewById(R.id.spinnerColor);
        spincolor.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter cl = new ArrayAdapter(this,android.R.layout.simple_spinner_item, color);
        cl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spincolor.setAdapter(cl);

        distance = (TextView) findViewById(R.id.tvDistance);
        seekbar = (Slider) findViewById(R.id.seekBar);

        seekbar.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                distance.setText("Distance: " + String.valueOf(v) + "km");
            }
        });


        min = (EditText) findViewById(R.id.editMin);
        max = (EditText) findViewById(R.id.editMax);
        priceBar = (RangeSlider) findViewById(R.id.priceBar);

        priceBar.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider rangeSlider, float v, boolean b) {
                min.setText(String.valueOf(priceBar.getValues().get(0)));
                max.setText(String.valueOf(priceBar.getValues().get(1)));
            }
        });

    }
}
