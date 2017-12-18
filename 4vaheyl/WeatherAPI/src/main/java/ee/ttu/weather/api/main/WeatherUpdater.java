package ee.ttu.weather.api.main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by User on 6.10.2017.
 */

public class WeatherUpdater {
    
    public List<WeatherApiResponse> getCurrentWeatherAndThreeDaysWeatherForCities(List<String> cities) throws IOException {
        CurrentWeatherRepository currentWeather = new CurrentWeatherRepository();
        ForecastWeatherRepository forecastWeather = new ForecastWeatherRepository();
        
        List<WeatherApiResponse> weather = new ArrayList<>();
        for (String city: cities) {
            List<String> countryInfo = Arrays.asList(city.split("\\s*,\\s*"));
            WeatherApiRequest weatherRequest = new WeatherApiRequest(countryInfo.get(0), countryInfo.get(1));
            weather.add(currentWeather.getCurrentTemperature(weatherRequest));
            weather.addAll(forecastWeather.ThreeDaysWeather(weatherRequest));
        }

        return weather;
    }

}





