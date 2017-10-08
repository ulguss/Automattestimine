package ee.ttu.weather.api.main;

import org.json.*;

/**
 * Created by User on 6.10.2017.
 */
public class CurrentTemperatureReport {
    public String city;
    public String countryCode;
    public double currentTemperature;

    public CurrentTemperatureReport(JSONObject json){
        JSONArray list = json.getJSONArray("list");
        JSONObject data = list.getJSONObject(0);

        this.city = data.getString("name");
        this.countryCode = data.getJSONObject("sys").getString("country");
        this.currentTemperature = data.getJSONObject("main").getDouble("temp");
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return countryCode;
    }

    public Double getCurrentTemperature() {
        return currentTemperature;
    }



    @Override
    public String toString() {
        return "CurrentWeatherReport{" +
                "city='" + city + '\'' +
                ", code='" + countryCode + '\'' +
                ", currentTemperature=" + currentTemperature +
                '}';
    }
}