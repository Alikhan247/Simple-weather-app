package sample;

public interface Observer {
    public void updateTemperatures(String temp,String tempMin,String tempMax, String feels);
    public void updateHumidity(String temp);
    public void updateCity(String temp);
    public void updateWeatherStatus(String temp);
    public void updateCurrentDate(String day, String month);
    public void updateImage(String img);
}
