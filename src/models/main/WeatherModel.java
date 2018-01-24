package models.main;


import classes.main.Weather;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherModel {

    private static String APICall = "http://api.openweathermap.org/data/2.5/weather?";
    private static String APIParams = "q=London,uk&id=524901&&units=metric&mode=json";
    private static String APIKey = "&APPID=9b18da9706ee34498f2d2d183409c9b7";

    public static void getWeather() {
        Gson gson = new Gson();
        URL url;
        try {
            url = new URL(APICall + APIParams + APIKey);

            InputStreamReader reader = new InputStreamReader(url.openStream());
            WeatherJSON weatherJSON = gson.fromJson(reader, WeatherJSON.class);
            reader.close();
            weatherJSON.setWeather();
            Weather.init(
                    Integer.parseInt(weatherJSON.getWeather().get("id").toString().replaceAll("\"", "")),
                    weatherJSON.getWeather().get("main").toString().replaceAll("\"", " ~ "),
                    weatherJSON.getWeather().get("description").toString().replaceAll("\"", " ~ "),
                    weatherJSON.getMain().get("temp") + " °C."
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
