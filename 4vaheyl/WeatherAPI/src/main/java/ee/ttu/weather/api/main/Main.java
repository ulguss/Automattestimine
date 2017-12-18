package ee.ttu.weather.api.main;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 5.11.2017.
 */
public class Main   {
    public static void main(String[] args) {
        try {

            WeatherUpdater repository = new WeatherUpdater();
            DataInputAsker DataInputAsker = new DataInputAsker();
            List<String> cities = DataInputAsker.getCityFromConsoleOrFromFile();

            List<WeatherApiResponse> weather = repository.getCurrentWeatherAndThreeDaysWeatherForCities(cities);
            DataInputWriter dataInputWriter = new DataInputWriter();
            dataInputWriter.WriteCurrentWeatherAndThreeDaysWeatherToFileByCity(weather);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
