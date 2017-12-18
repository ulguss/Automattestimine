package ee.ttu.weather.api.main;

import org.json.JSONObject;

/**
 * Created by User on 17.12.2017.
 */
public class CurrentWeatherReport extends WeatherApiResponse {

    public CurrentWeatherReport(JSONObject json) {
        this.setCityName(json.getString("name"));
        this.setCountryCode(json.getJSONObject("sys").getString("country"));

        this.setCoordinatesLat(json.getJSONObject("coord").getDouble("lat"));
        this.setCoordinatesLon(json.getJSONObject("coord").getDouble("lon"));

        this.setTemperature(json.getJSONObject("main").getDouble("temp"));
        this.setMinTemperature(json.getJSONObject("main").getDouble("temp_min"));
        this.setMaxTemperature(json.getJSONObject("main").getDouble("temp_max"));
    }
}
