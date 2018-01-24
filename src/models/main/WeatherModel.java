package models.main;


public class WeatherModel {
    private static Object currentWeather;
    private static String APIKey = "9b18da9706ee34498f2d2d183409c9b7";
    private static String APICall = "api.openweathermap.org/data/2.5/weather";
    private static String APIParams = "?q=London,uk&id=524901&APPID=";

    public static void setCurrentWeather () {

    }

    public static String getCurrentWeather () {
        return currentWeather.toString();
    }

    public static void refreshCurrentWeather () {

    }

}
