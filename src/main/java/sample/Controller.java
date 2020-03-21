package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DateFormatSymbols;

public class Controller implements Observer {

    @FXML
    private Label tempLabel;
    @FXML
    private Label cityName;
    @FXML
    private Label weatherStatus;
    @FXML
    private Label humidity;
    @FXML
    private Label maxTemperature;
    @FXML
    private Label minTemperature;
    @FXML
    private Label currentDate;
    @FXML
    private Label feelsLike;
    @FXML
    private ImageView statusImage;
    @FXML
    private ImageView updateButton;

    private String nameFromInput = "Almaty";

    @FXML
    private TextField search;


    @FXML
    private void getSearch() {
        this.nameFromInput = search.getText();
        System.out.println(nameFromInput);
        updateWeather();
    }

    public void exit() {
        Platform.exit();
    }

    public void updateByCityName(String cityName) {
        this.nameFromInput = cityName;
        System.out.println(nameFromInput);
//        updateWeather();
    }

    public void updateWeather() {
        System.out.println(nameFromInput);
        System.out.println("Updating...");
        RotateTransition rt = new RotateTransition(Duration.millis(2000), updateButton);
        rt.setByAngle(360);
//        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        Weather weather = new Weather();
        Singleton singleton = Singleton.getWeatherInstance(weather);

        singleton.weather.addToObserve(this);
        singleton.weather.getWeather(nameFromInput);


    }

    public void setTempLabel(String text) {
        this.tempLabel.setText(text);
    }

    @Override
    public void updateTemperatures(String temp, String tempMin, String tempMax, String feels) {
        this.tempLabel.setText(temp + "°C");
        this.minTemperature.setText("MIN: " + tempMin);
        this.maxTemperature.setText("MAX: " + tempMax);
        this.feelsLike.setText("Feels: " + feels);
        this.feelsLike.setMaxWidth(Double.MAX_VALUE);
        this.feelsLike.setAlignment(Pos.CENTER);
        this.tempLabel.setMaxWidth(Double.MAX_VALUE);
        this.tempLabel.setAlignment(Pos.CENTER);
    }

    @Override
    public void updateHumidity(String hum) {
        this.humidity.setText(hum + "%");
    }

    @Override
    public void updateCity(String city) {
        this.cityName.setMaxWidth(Double.MAX_VALUE);
        this.cityName.setAlignment(Pos.CENTER);
        this.cityName.setText(city);
    }

    @Override
    public void updateWeatherStatus(String status) {
        String[] words = status.split("\\s+");
        String result = "";
//        System.out.println(words[0]);
//        System.out.println(words[1]);
        for (int i = 0; i < words.length - 1; i++) {//Delete -1 after fixing problem with split
            words[i] = words[i].replaceAll("\\s", "");
            result += words[i].substring(0, 1).toUpperCase() + status.substring(1);
        }
        this.weatherStatus.setMaxWidth(Double.MAX_VALUE);
        this.weatherStatus.setAlignment(Pos.CENTER);
        this.weatherStatus.setText(result);
    }

    @Override
    public void updateCurrentDate(String day, String month) {
        month = getMonth(Integer.parseInt(month));
        String date = month + " " + day;
        this.currentDate.setMaxWidth(Double.MAX_VALUE);
        this.currentDate.setAlignment(Pos.CENTER);
        this.currentDate.setText(date);
    }

    @Override
    public void updateImage(String img) {
        Image image = null;
        image = new Image(this.getClass().getResource(img).toString());
        this.statusImage.setImage(image);
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

}
