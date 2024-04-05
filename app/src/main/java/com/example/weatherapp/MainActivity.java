package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView temperatureT;
    TextView cityName;
    String city;
    TextInputEditText editText;
    ImageView imageMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureT = findViewById(R.id.TemperatureT);
        cityName = findViewById(R.id.city_name);
    }

    public void getWeather(View view) {
        editText = findViewById(R.id.EnterCity);
        city = editText.getText().toString();
        if (city.equals("")){
            Toast.makeText(this, "Не введен город", Toast.LENGTH_SHORT).show();
            return;
        }

        String apiKey = "ac804f49205e53e77d14a9e283ddbc4d";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        new GetURLData().execute(url);
    }

    private class GetURLData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // Проверка кода ответа
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    cityName.setText(city.substring(0, 1).toUpperCase() + city.substring(1));


                    // Город существует, получаем данные
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line).append("\n");
                    }
                    return buffer.toString();
                } else {
                    // Город не существует, возвращаем null
                    return null;
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    // Изменение температуры
                    double kelvinTemp = Double.parseDouble(jsonObject.getJSONObject("main").getString("temp"));
                    double celsiusTemp = kelvinTemp - 273.15;
                    String formattedTemp = String.format("%.2f", celsiusTemp);
                    temperatureT.setText(formattedTemp + " C");
                    // Получение иконки погоды
                    String iconCode = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                    imageMain = findViewById(R.id.TemperImage);
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
                            imageMain.setImageResource(R.drawable.d50d);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Город не существует, выводим сообщение об ошибке
                Toast.makeText(MainActivity.this, "Город не найден", Toast.LENGTH_SHORT).show();
            }
        }
    }

}