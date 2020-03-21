package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import sample.factory.Dialog;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.factory.ErrorDialog;
import sample.factory.WarningDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Weather implements WeatherModel {

    public static  Dialog dialog;


    String url = "http://api.openweathermap.org/data/2.5/forecast?";
    String cityId = "q=";
    final String APIID = "APPID=fd1ab7b2f231e500c32eab7e264fa362";

    ArrayList<Observer> observers = new ArrayList<>();

    public Weather() {
    }

    public void addToObserve(Observer observer) {
        System.out.println("added");
        observers.add(observer);
    }

    @Override
    public void getWeather(String cityId) {
        this.cityId = "q=" + cityId;
        String requestUrl = url + this.cityId + "&" + APIID;
        System.out.println(requestUrl);

        JSONParser jsonParser = new JSONParser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    JSONObject object = jsonParser.getJSONFromUrl(requestUrl);//Long term operation goes outside

                    @Override
                    public void run() {
                        processRequest(object);
                    }
                });
            }
        }).start();
    }

    private void processRequest(JSONObject object) {
        String cod = String.valueOf(object.get("cod"));//Request status
        if (cod.equals("200")) {
            JSONArray dataArray = object.getJSONArray("list");
            for (int n = 0; n < dataArray.length(); n++) {

                JSONObject element = dataArray.getJSONObject(n);

                String sDate1 = (String) element.getString("dt_txt");//I am have to get date in text because I can't convert given dt


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date date5 = null;
                try {
                    date5 = formatter.parse(sDate1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String year = String.valueOf(localDate.getYear());
                String month = String.valueOf(localDate.getMonthValue());
                String day = String.valueOf(localDate.getDayOfMonth());

                LocalDate localDate2 = date5.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String year1 = String.valueOf(localDate2.getYear());
                String month1 = String.valueOf(localDate2.getMonthValue());
                String day1 = String.valueOf(localDate2.getDayOfMonth());

//                System.out.println(year1);
//                System.out.println(month1);
//                System.out.println(day1);
//                System.out.println(year);
//                System.out.println(month);
//                System.out.println(day);


                if (day.equals(day1) && month.equals(month1)) {
                    JSONArray weatherList = element.getJSONArray("weather");//Array of weather condition for each 2 hour
                    JSONObject weatherElement = weatherList.getJSONObject(0);
                    System.out.println("Same!!!!");
                    //Objects in the array
                    JSONObject object3 = (JSONObject) element.get("main");
                    long temp = Math.round(Double.parseDouble(object3.get("temp").toString()) - 273.15);
                    long tempMin = Math.round(Double.parseDouble(object3.get("temp_min").toString()) - 273.15);
                    long tempMax = Math.round(Double.parseDouble(object3.get("temp_max").toString()) - 273.15);
                    String humidity = object3.get("humidity").toString();
//                  System.out.println(observers.size());

                    //Get city name
                    JSONObject city = object.getJSONObject("city");

//                  System.out.println(observers.size()); //is controller subscribed

                    int feels = (int) (0.5 * (temp + 61.0 + ((temp - 68.0) * 1.2) + (Integer.parseInt(humidity) * 0.094)));//"Feels like formula"

                    System.out.println(feels);

                    //update all the data on the view
                    for (int i = 0; i < observers.size(); i++) {
                        observers.get(i).updateTemperatures(Long.toString(temp), Long.toString(tempMin), Long.toString(tempMax), Integer.toString(feels));
                        observers.get(i).updateWeatherStatus(String.valueOf(weatherElement.get("description")));
                        observers.get(i).updateHumidity(humidity);
                        observers.get(i).updateCurrentDate(day, month);
                        observers.get(i).updateCity(String.valueOf(city.get("name")));
                    }


                    System.out.println(String.valueOf(weatherElement.get("icon")));
                    switch (String.valueOf(weatherElement.get("icon"))) {
                        case "01d":
                            observers.get(0).updateImage("/images/sun.png");
                            break;
                        case "02d":
                            observers.get(0).updateImage("/images/cloud.png");
                            break;
                        case "03d":
                            observers.get(0).updateImage("/images/020-cloud.png");
                            break;
                        case "04d":
                            observers.get(0).updateImage("/images/021-cloud.png");
                            break;
                        case "09d":
                            observers.get(0).updateImage("/images/025-rain.png");
                            break;
                        case "10d":
                            observers.get(0).updateImage("/images/rainD.png");
                            break;
                        case "11d":
                            observers.get(0).updateImage("/images/026-storm.png");
                            break;
                        case "13d":
                            observers.get(0).updateImage("/images/snow.png");
                            break;
                        case "50d":
                            observers.get(0).updateImage("/images/009-fog.png");
                            break;


                        case "01n":
                            observers.get(0).updateImage("/images/sun.png");
                            break;
                        case "02n":
                            observers.get(0).updateImage("/images/cloud.png");
                            break;
                        case "03n":
                            observers.get(0).updateImage("/images/020-cloud.png");
                            break;
                        case "04n":
                            System.out.println("Here");
                            observers.get(0).updateImage("/images/021-cloud.png");
                            break;
                        case "09n":
                            observers.get(0).updateImage("/images/025-rain.png");
                            break;
                        case "10n":
                            observers.get(0).updateImage("/images/rainD.png");
                            break;
                        case "11n":
                            observers.get(0).updateImage("/images/026-storm.png");
                            break;
                        case "13n":
                            observers.get(0).updateImage("/images/snow.png");
                            break;
                        case "50n":
                            observers.get(0).updateImage("/images/009-fog.png");
                            break;
                        default:
                            observers.get(0).updateImage("/images/009-fog.png");
                    }

                    break;
                }
            }
        } else if (cod.equals("404")) {
            dialog = new WarningDialog();
            dialog.renderWindow();
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Error");
//            alert.setHeaderText("No such city in the database");
//            alert.setContentText("Incorrect city name ");
//
//            alert.showAndWait();
        } else {
            dialog = new ErrorDialog();
            dialog.renderWindow();
        }
    }
}

//
//{
//     "cod": "200",
//     "message": 0,
//     "cnt": 40,
//     "list": [
//        {
//        "dt": 1576432800,
//        "main": {
//        "temp": 263.89,
//        "feels_like": 259.72,
//        "temp_min": 263.89,
//        "temp_max": 263.89,
//        "pressure": 1036,
//        "sea_level": 1036,
//        "grnd_level": 921,
//        "humidity": 87,
//        "temp_kf": 0
//        },
//        "weather": [
//        {
//        "id": 800,
//        "main": "Clear",
//        "description": "clear sky",
//        "icon": "01n"
//        }
//        ],
//        "clouds": {
//           "all": 0
//        },
//        "wind": {
//        "speed": 1.48,
//        "deg": 145
//        },
//        "sys": {
//          "pod": "n"
//        },
//          "dt_txt": "2019-12-15 18:00:00"
//        },
// ],
//  "city": {
//    "id": 1537162,
//    "name": "Almaty Oblysy",
//    "coord": {
//      "lat": 44.5,
//      "lon": 78
//    },
//    "country": "KZ",
//    "timezone": 21600,
//    "sunrise": 1576376232,
//    "sunset": 1576408119
//  }
//}