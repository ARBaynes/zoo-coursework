package models.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class WeatherJSON {

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
