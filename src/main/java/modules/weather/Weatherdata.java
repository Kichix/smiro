package modules.weather;

public class Weatherdata {

    private int temperature;
    private String icon;
    private int wind;

    public Weatherdata(float temp, int wind, String icon) {
        this.temperature = Math.round(temp);
        this.wind = wind;
        this.icon = icon;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }
}
