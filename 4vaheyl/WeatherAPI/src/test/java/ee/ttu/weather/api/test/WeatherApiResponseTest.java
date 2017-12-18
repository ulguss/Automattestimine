package ee.ttu.weather.api.test;

/**
 * Created by User on 22.09.2017.
 */

import ee.ttu.weather.api.main.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

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

//    @Test
//    public void testIfCoordinatesAreInRightFormatWithThreeDaysWeather() throws IOException {
//        List<WeatherApiResponse> response = forecastRepo.ThreeDaysWeather(request);
//        try {
//        for (WeatherApiResponse day : response) {
//            assertTrue(day.getCoordinatesLat().toString().matches("\\d{2}.\\d{2}"));
//            assertTrue(day.getCoordinatesLon().toString().matches("\\d{2}.\\d{2}"));
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("No data.");
//        }
//    }

    @Test
    public void testCurrentWeatherIfFilesCreated() throws IOException {
        CurrentWeatherRepository currentWeather = new CurrentWeatherRepository();
        DataInputWriter writer = new DataInputWriter();
        UrlConnectionMaker url = mock(UrlConnectionMaker.class);

        WeatherApiRequest request = new WeatherApiRequest("Tallinn", "EE");
        WeatherApiResponse response = new WeatherApiResponse();
        response.setCityName("Tallinn");
        response.setCountryCode("EE");
        response.setCoordinatesLon(00.00);
        response.setCoordinatesLat(00.00);
        response.setDate(new Date());

        when(currentWeather.getCurrentTemperature(request)).thenReturn(response);

        response = currentWeather.getCurrentTemperature(request);

        verify(url,times(1)).getResponseFromWheatherApiByUrl(request, String.valueOf(response));
//        verify(url,times(1)).currentWeather.getCurrentTemperature(request);

//        when(currentWeather.getCurrentTemperature(request)).thenReturn(response);
//
//        response = currentWeather.getCurrentTemperature(request);
//        verify(currentWeather, times(1)).getCurrentTemperature(request);
//
//
        writer.writeDataToFile(request.getCityName().toLowerCase() + ".txt", response.toString());
       File file = new File("C:\\Users\\User\\IdeaProjects\\WeatherAPI\\src\\test\\resources" + "\\" + request.getCityName().toLowerCase() + ".txt");



//        assertTrue(file.exists());
    }




}