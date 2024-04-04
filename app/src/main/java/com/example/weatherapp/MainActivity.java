package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    //Домашной экран
    private RelativeLayout HomeRL;

    //Прогрессбар с загрузкой
    private ProgressBar LoadingPB;

    //Название города, температура, температура днем и ночью
    private TextView CityName, Temperature, TempDnN;

    //Город который вводится в поле поиска
    private TextInputEditText CityEdit;

    //Изображения с значком погоды и поиска
    private ImageView iconIV, searchIV;

    //Кнопка для перехода на недельную погоду
    private Button NextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Связь между xml и переменными
        HomeRL = findViewById(R.id.Home_id);
        LoadingPB = findViewById(R.id.loading);

        CityName = findViewById(R.id.city_name);
        Temperature = findViewById(R.id.TemperatureT);
        TempDnN = findViewById(R.id.TempDnNigt);

        CityEdit = findViewById(R.id.EnterCity);

        iconIV = findViewById(R.id.TemperImage);
        searchIV = findViewById(R.id.search_pic);

        NextButton = findViewById(R.id.NextButton);




    }
}