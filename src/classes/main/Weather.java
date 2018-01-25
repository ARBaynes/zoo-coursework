package classes.main;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Weather implements Runnable {
    private static String APICall = "http://api.openweathermap.org/data/2.5/weather?";
    private static String APIParams = "q=London,uk&id=524901&&units=metric&mode=json";
    private static String APIKey = "&APPID=9b18da9706ee34498f2d2d183409c9b7";


    private static String description;
    private static String temp;


    public Weather () {}

    public Weather (String description, String temp) {
        setDescription(description);
        setTemp(temp);
    }

    private void setTemp(String temp) {
        Weather.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    private void setDescription(String description) {
        Weather.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void run() {
        getWeather();
    }

    private void getWeather() {
        Gson gson = new Gson();
        URL url;
        try {
            url = new URL(APICall + APIParams + APIKey);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            WeatherJSON weatherJSON = gson.fromJson(reader, WeatherJSON.class);
            reader.close();
            weatherJSON.setWeather();
            this.setDescription(weatherJSON.getWeather().get("description").toString().replaceAll("\"", " ~ "));
            this.setTemp(weatherJSON.getMain().get("temp") + " °C.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class WeatherJSON {
        private JsonArray weather;
        private JsonObject main;
        private JsonObject weatherObject;
        public void setWeather () {
            this.weatherObject = this.weather.get(0).getAsJsonObject();
        }
        public void setMain(JsonObject main) {
            this.main = main;
        }
        public JsonObject getMain() {
            return main;
        }
        public JsonObject getWeather() {
            return weatherObject;
        }
    }

}
