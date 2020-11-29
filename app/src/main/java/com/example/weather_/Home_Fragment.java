package com.example.weather_;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.weather_.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Home_Fragment extends Fragment {

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=moscow&appid=f370763538b29033cdb879554029b1b5";
    private EditText cit;
    private EditText temp;
    private EditText pressure;
    private EditText humidity;
    private EditText windSpeed;
    private String city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frgm =inflater.inflate(R.layout.fragment_home_, container, false);
        TextView text = frgm.findViewById(R.id.text);
        ImageView image = frgm.findViewById(R.id.image);
        TableLayout dop_fun = frgm.findViewById(R.id.dop_fun);

        TextView textView3 = frgm.findViewById(R.id.textView4);
        TextView textView5 = frgm.findViewById(R.id.textView6);

        windSpeed = frgm.findViewById(R.id.textView3);
        humidity = frgm.findViewById(R.id.textView5);
        temp = frgm.findViewById(R.id.textView);
        pressure = frgm.findViewById(R.id.textView7);
        cit = frgm.findViewById(R.id.tx_mos);

        return frgm;
    }

    @SuppressLint("DefaultLocale")
    public void changeCity(String city) {
        try {
            final String url;
            url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                    city,
                    BuildConfig.WEATHER_API_KEY);

            final URL uri = new URL(url);
            final Handler handler = new Handler();// Запоминаем основной поток
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                        String result = getLines(in);
                        // преобразование данных запроса в модель
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        // Возвращаемся к основному потоку
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayWeather(weatherRequest);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
    public void displayWeather(WeatherRequest weatherRequest) {
        cit.setText(weatherRequest.getName());
        temp.setText(String.format("%s", (int)weatherRequest.getMain().getTemp() + " °C"));
        pressure.setText(String.format("%s", weatherRequest.getMain().getPressure() + " mm.rt.st."));
        humidity.setText(String.format("%s", weatherRequest.getMain().getHumidity() + " %"));
        windSpeed.setText(String.format("%s", weatherRequest.getWind().getSpeed() + " m/c"));

    }
};

