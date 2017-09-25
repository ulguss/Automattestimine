package ee.ttu.weather.api.test;

/**
 * Created by User on 22.09.2017.
 */

import static org.junit.Assert.*;

import ee.ttu.weather.api.main.WeatherApi;
import org.junit.Before;
import org.junit.Test;

public class WeatherApiTest {
    WeatherApi weatherApi;

    @Before
    public void setUp() throws Exception {
        weatherApi = new WeatherApi();
    }
    @Test
    public void testGetCurrentTemperature(){
        assertTrue(false);
    }

    @Test
    public void testGetThreeDaysWeather(){
        assertTrue(false);
    }



    @Test
    public void testGetCoordinatesInRightForm(){
        assertTrue(false);
    }



    @Test
    public void testConnectionExist() {

        fail("Not yet implemented");
    }
}
