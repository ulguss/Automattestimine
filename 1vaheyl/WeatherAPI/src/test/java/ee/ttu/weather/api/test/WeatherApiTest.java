package ee.ttu.weather.api.test;

/**
 * Created by User on 22.09.2017.
 */

import static org.junit.Assert.*;

import ee.ttu.weather.api.main.WeatherApi;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class WeatherApiTest {
    WeatherApi weatherApi;

    @Before
    public void setUp() throws Exception {
        weatherApi = new WeatherApi();
    }

    @Test
    public void testIfCurrentTemperatureIsNotNull(){
        try{
            assertNotNull(weatherApi.getCurrentTemperature());
    }catch(Exception e){
                fail("Failed because: " + e.getMessage());
        }}

    @Test
    public void testGetCurrentTemperature() {
        try{
            assertEquals(0, weatherApi.getCurrentTemperature());
        }catch(Exception e){
                fail("Failed because: " + e.getMessage());
            }}

    @Test
    public void testIfCurrentTemperatureIsRealistic() {
        try{
        assertTrue(weatherApi.getCurrentTemperature() < 50 && weatherApi.getCurrentTemperature() > -50  );
        }catch(Exception e){
            fail("Failed because: " + e.getMessage());
        }}

    @Test
   public void testIfThreeDaysWeatherIsAvailable(){
        assertNotNull(weatherApi.getTodayTemperature());
        assertNotNull(weatherApi.getTomorrowTemperature());
        assertNotNull(weatherApi.getAfterTomorrowTemperature());

   }

    @Test
    public void testIfMaxTemperatureIsHigherThanMinTemperature() {
        assertTrue(weatherApi.getTodayTemperature() > weatherApi.getTodayTemperature());
        assertTrue(weatherApi.getTomorrowTemperature() > weatherApi.getTomorrowTemperature());
        assertTrue(weatherApi.getAfterTomorrowTemperature() > weatherApi.getAfterTomorrowTemperature());
    }

//    @Test
//    public void testMaxTemp(){
//
//    }
//
//    @Test
//    public void testMinTemp(){
//}

    @Test
    public void testIfCoordinatesAreInRightFormat(){
        try{
        boolean isRigthForm = weatherApi.getCoordinates().matches("\\d{3}.\\d{3}");
        assertEquals(isRigthForm, true);
    }catch(Exception e){
        fail("Failed because: " + e.getMessage());
    }}

    @Test
    public void testIfCoordinatesLenght(){
        try{
            assertNotNull(WeatherApi.getCoordinates());
            assertTrue (WeatherApi.getCoordinates().length()==7);
        }catch(Exception e){
            fail("Failed because: " + e.getMessage());
        }}

    @Test
    public void testIfCoordinatesHasLatAndLon() {
        try{
        assertNotNull(weatherApi.getCoordinates());
        assertTrue(weatherApi.getCoordinates().contains("lat:"));
        assertTrue(weatherApi.getCoordinates().contains("lon:"));
    }catch(Exception e){
            fail("Failed because: " + e.getMessage());
        }}
/*    @Test
    public void testIfConnectionExist() {

        fail("Not yet implemented");
    }*/
}
