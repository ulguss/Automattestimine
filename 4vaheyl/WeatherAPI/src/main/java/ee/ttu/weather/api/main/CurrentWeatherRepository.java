package ee.ttu.weather.api.main;

import org.json.JSONObject;

/**
 * Created by User on 16.12.2017.
 */
public class CurrentWeatherRepository {

    public static WeatherApiResponse getCurrentTemperature(WeatherApiRequest request) {
        UrlConnectionMaker url = new UrlConnectionMaker();

        try {
            JSONObject report = url.getResponseFromWheatherApiByUrl(request, "weather");

            if (report == null) {
                return null;
            }
            CurrentWeatherReport currentWeather = new CurrentWeatherReport(report);
            return currentWeather;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
