package ee.ttu.weather.api.main;

import java.util.Date;

/**
 * Created by User on 25.09.2017.
 */
public class WeatherApiResponse {

    private Date date;
    private String cityName;
    private String countryCode;
    private Double coordinatesLat;
    private Double coordinatesLon;
    private double temperature;
    private double maxTemperature;
    private double minTemperature;


    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public String getCityName() {

        return cityName;
    }

    public void setCityName(String cityName) {

        this.cityName = cityName;
    }

    public String getCountryCode() {

        return countryCode;
    }


    public Double getCoordinatesLat() {

        return coordinatesLat;
    }

    public void setCoordinatesLat(Double coordinatesLat) {

        this.coordinatesLat = coordinatesLat;
    }

    public Double getCoordinatesLon() {

        return coordinatesLon;
    }

    public void setCoordinatesLon(Double coordinatesLon) {

        this.coordinatesLon = coordinatesLon;
    }

    public double getTemperature() {

        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMaxTemperature() {

        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {

        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {

        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {

        this.minTemperature = minTemperature;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "City: " + cityName + ", Country code: " +  countryCode  + "; Latitude: " + coordinatesLat + "; Longitude: " + coordinatesLon + '\n'  + "; Current Temperature: " +  temperature +  '\n'  + ", Minimum temperature: " + minTemperature + '\n'  +
                ", Maximum temperature: " + maxTemperature;
    }


}
