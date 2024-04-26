package com.example.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {
    String city;
    private String API_URL;
    private TextView[] dateViews;
    private TextView[] tempDayViews;
    private TextView[] tempNightViews;
    private ImageView[] weatherIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        // Retrieve the city data from the Intent
        city = intent.getStringExtra("city");
        API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=ac804f49205e53e77d14a9e283ddbc4d&cnt=40";
        initializeViews();
        fetchWeatherData();
    }

    private void initializeViews() {
        dateViews = new TextView[]{
                findViewById(R.id.textView2),
                findViewById(R.id.textView5),
                findViewById(R.id.textView8),
                findViewById(R.id.textView11),
                findViewById(R.id.textView14)
        };
        tempDayViews = new TextView[]{
                findViewById(R.id.textView3),
                findViewById(R.id.textView6),
                findViewById(R.id.textView9),
                findViewById(R.id.textView12),
                findViewById(R.id.textView15)
        };
        tempNightViews = new TextView[]{
                findViewById(R.id.textView4),
                findViewById(R.id.textView7),
                findViewById(R.id.textView10),
                findViewById(R.id.textView13),
                findViewById(R.id.textView16)
        };
        weatherIcons = new ImageView[]{
                findViewById(R.id.imageView),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5)
        };
    }

    private void fetchWeatherData() {
        new Thread(() -> {
            try {
                String json = Utils.fetchData(API_URL);  // Assume Utils.fetchData() makes an HTTP GET request
                JSONObject jsonObject = new JSONObject(json);
                JSONArray list = jsonObject.getJSONArray("list");
                runOnUiThread(() -> updateUI(list));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateUI(JSONArray list) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
            // Обновляем данные каждые 24 часа (8 позиций в массиве на 3 часа)
            for (int i = 0; i < 5; i++) {
                // 15:00 для дневной температуры
                JSONObject dayItem = list.getJSONObject(i * 8 + 5);  // i * 8 + 5 = 5, 13, 21, 29, 37 (дневные часы)
                // 03:00 следующего дня для ночной температуры
                JSONObject nightItem = list.getJSONObject(i * 8 + 2);  // i * 8 + 2 = 2, 10, 18, 26, 34 (ночные часы)
                long dt = dayItem.getLong("dt") * 1000;
                JSONObject mainDay = dayItem.getJSONObject("main");
                JSONObject mainNight = nightItem.getJSONObject("main");
                double tempDay = mainDay.getDouble("temp") - 273.15; // Кельвины в Цельсии
                double tempNight = mainNight.getDouble("temp") - 273.15;

                dateViews[i].setText(sdf.format(new Date(dt)));
                tempDayViews[i].setText(String.format(Locale.getDefault(), "%.1f°C", tempDay));
                tempNightViews[i].setText(String.format(Locale.getDefault(), "%.1f°C", tempNight));
                // Устанавливаем иконку погоды

                String iconCode = dayItem.getJSONArray("weather").getJSONObject(0).getString("icon");
                iconCode = iconCode.replace('n', 'd'); // Замена "n" на "d" в коде иконки
                Log.d("WeatherApp", "Icon Code for " + sdf.format(new Date(dt)) + ": " + iconCode); // Логирование кода иконки

                ImageView imageMain = weatherIcons[i]; // Обновление ссылки на ImageView
                switch (iconCode) {
                    case "01d":
                        imageMain.setImageResource(R.drawable.d01d);
                        break;
                    case "02d":
                        imageMain.setImageResource(R.drawable.d02d);
                        break;
                    case "03d":
                        imageMain.setImageResource(R.drawable.d03d);
                        break;
                    case "04d":
                        imageMain.setImageResource(R.drawable.d04d);
                        break;
                    case "09d":
                        imageMain.setImageResource(R.drawable.d09d);
                        break;
                    case "10d":
                        imageMain.setImageResource(R.drawable.d10d);
                        break;
                    case "11d":
                        imageMain.setImageResource(R.drawable.d11d);
                        break;
                    case "13d":
                        imageMain.setImageResource(R.drawable.d13d);
                        break;
                    case "50d":
                        imageMain.setImageResource(R.drawable.d50d);
                        break;
                    default:
                        imageMain.setImageResource(R.drawable.d50d); // Фолбэк, если код иконки не распознан
                }

//                String iconCode = dayItem.getJSONArray("weather").getJSONObject(0).getString("icon");
//                int resId = getResources().getIdentifier("d" + iconCode, "drawable", getPackageName());
//                weatherIcons[i].setImageResource(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
