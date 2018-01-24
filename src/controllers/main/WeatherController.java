package controllers.main;

import classes.main.Weather;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import models.main.WeatherModel;

public class WeatherController {

    private static Label weatherLabel;
    private static Button refreshButton;

    public static void construct (Label label, Button button) {
        weatherLabel = label;
        refreshButton = button;

        refreshWeatherData();
        loadWeatherToScreen();
    }


    public static void outline () {
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshWeatherData();
                loadWeatherToScreen();
            }
        });
    }

    public static void refreshWeatherData () {
        WeatherModel.getWeather();
        System.out.println("Weather refreshed.");
    }

    private static void loadWeatherToScreen () {
        weatherLabel.setText(Weather.getDescription() + Weather.getTemp());
        System.out.println("Weather displayed.");
    }





}
