package ee.ttu.weather.api.test;

/**
 * Created by User on 22.09.2017.
 */

import ee.ttu.weather.api.main.WeatherApi;
import ee.ttu.weather.api.main.WeatherApiRepository;
import ee.ttu.weather.api.main.WeatherApiRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WeatherApiTest {
    private WeatherApiRepository repository;
    private WeatherApiRequest request;

    @Before
    public void setUp() throws Exception {
        repository = new WeatherApiRepository();
        request = new WeatherApiRequest();

    }

    @Test
    public void testGetCurrentWeather() {
        WeatherApi response = WeatherApiRepository.getCurrentTemperature(request);
        assertNotNull(response);
    }


    @Test
    public void testIfCurrentTemperatureIsRealistic() {
        WeatherApi response = WeatherApiRepository.getCurrentTemperature(request);
        assertTrue(response.getTemperature() < 60 && response.getTemperature() > -60);
        }


    @Test
    public void testGetThreeDaysForecast() {
        List<WeatherApi> response = repository.ThreeDaysWeather(request);

        assertNotNull(response);
        assertTrue(response.size() == 3);

        for (WeatherApi code : response) {
            assertNotNull(code.getCountryCode());
        }
        for (WeatherApi min : response) {
            assertNotNull(min.getMinTemperature());
        }

        for (WeatherApi max : response) {
            assertNotNull(max.getMaxTemperature());
        }
    }


    @Test
    public void testIfMaxTemperatureIsHigherThanMinTemperature() {
        List<WeatherApi> response = repository.ThreeDaysWeather(request);

        for (WeatherApi day : response) {
            assertTrue(day.getMaxTemperature() > day.getMinTemperature());
        }
    }


    @Test
    public void testIfCoordinatesAreInRightFormatWithCurrentTemperature(){
        WeatherApi response = WeatherApiRepository.getCurrentTemperature(request);
        // have to be xxx:yyy \d{3}.\d{3}
        assertTrue(response.getCoordinatesLat().toString().matches("\\d{2}.\\d{2}")); //\d{3}.\d{3} - punane
        assertTrue(response.getCoordinatesLon().toString().matches("\\d{2}.\\d{2}"));
        }

    @Test
    public void testIfCoordinatesAreInRightFormatWithThreeDaysWeather(){
        List<WeatherApi> response = repository.ThreeDaysWeather(request);

        for (WeatherApi day : response) {
            assertTrue(day.getCoordinatesLat().toString().matches("\\d{2}.\\d{3}"));
            assertTrue(day.getCoordinatesLon().toString().matches("\\d{2}.\\d{4}"));
        }
    }

}