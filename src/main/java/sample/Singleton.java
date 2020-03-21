package sample;

public final class Singleton {
    private static Singleton instance;
    public Controller controller;
    public Weather weather;

    private Singleton(Controller controller) {
        this.controller = controller;
    }

    private Singleton(Weather weather) {
        this.weather = weather;
    }

    public static Singleton getWeatherInstance(Weather weather) {
        if (instance == null) {
            instance = new Singleton(weather);
        }
        return instance;
    }

    public static Singleton getControllerInstance(Controller controller) {
        if (instance == null) {
            instance = new Singleton(controller);
        }
        return instance;
    }
}