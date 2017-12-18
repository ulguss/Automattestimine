package ee.ttu.weather.api.test;

/**
 * Created by User on 22.09.2017.
 */

import ee.ttu.weather.api.main.CurrentWeatherRepository;
import ee.ttu.weather.api.main.ForecastWeatherRepository;
import ee.ttu.weather.api.main.WeatherApiRequest;
import ee.ttu.weather.api.main.WeatherApiResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WeatherApiResponseTest {
    private ForecastWeatherRepository forecastRepo;
    private WeatherApiRequest request;

    @Before
    public void setUp() throws Exception {
        forecastRepo = new ForecastWeatherRepository();
        request = new WeatherApiRequest("Tallinn", "EE");

    }

    @After
    public void tearDown() {
        request = new WeatherApiRequest(null, null);
    }

    @Test
    public void testGetCurrentWeather() {
        try {
            WeatherApiResponse response = CurrentWeatherRepository.getCurrentTemperature(request);
            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No data.");
        }
    }


    @Test
    public void testIfCurrentTemperatureIsRealistic() {
        try {
        WeatherApiResponse response = CurrentWeatherRepository.getCurrentTemperature(request);
        assertTrue(response.getTemperature() < 60 && response.getTemperature() > -60);
        } catch (Exception e) {
            e.printStackTrace();
            fail("No data.");
        }
        }


    @Test
    public void testGetThreeDaysWeather() throws IOException {

        List<WeatherApiResponse> response = forecastRepo.ThreeDaysWeather(request);

        assertNotNull(response);

        for (WeatherApiResponse code : response) {
            assertNotNull(code.getCountryCode());
        }
        for (WeatherApiResponse min : response) {
            assertNotNull(min.getMinTemperature());
        }

        for (WeatherApiResponse max : response) {
            assertNotNull(max.getMaxTemperature());
        }
    }


    @Test
    public void testIfMaxTemperatureIsHigherThanMinTemperature() throws IOException {
        List<WeatherApiResponse> response = forecastRepo.ThreeDaysWeather(request);
        try {
        for (WeatherApiResponse day : response) {
            assertTrue(day.getMaxTemperature() >= day.getMinTemperature());
        }
        } catch (Exception e) {
            e.printStackTrace();
            fail("No data.");
        }
    }


    @Test
    public void testIfCoordinatesAreInRightFormatWithCurrentTemperature(){
        WeatherApiResponse response = CurrentWeatherRepository.getCurrentTemperature(request);
        // have to be xxx:yyy \d{3}.\d{3}

        try {
        assertTrue(response.getCoordinatesLat().toString().matches("\\d{2}.\\d{2}")); //\d{3}.\d{3} - punane
        assertTrue(response.getCoordinatesLon().toString().matches("\\d{2}.\\d{2}"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("No data.");
        }
        }

    @Test
    public void testIfCoordinatesAreInRightFormatWithThreeDaysWeather() throws IOException {
        List<WeatherApiResponse> response = forecastRepo.ThreeDaysWeather(request);
        try {
        for (WeatherApiResponse day : response) {
            assertTrue(day.getCoordinatesLat().toString().matches("\\d{2}.\\d{4}"));
            assertTrue(day.getCoordinatesLon().toString().matches("\\d{2}.\\d{4}"));
        }
        } catch (Exception e) {
            e.printStackTrace();
            fail("No data.");
        }
    }






}