package ee.ttu.weather.api.main;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.*;


/**
 * Created by User on 6.10.2017.
 */

public class WeatherApiRepository {
    private static String weatherApiUrl;
    private static String weatherApiKey;

    public WeatherApiRepository() {
        weatherApiUrl = "http://api.openweathermap.org/data/2.5/";
        weatherApiKey = "8b9a2d0e407f28dc7ebbaf5c2c76e947";
    }

    public static WeatherApi getCurrentTemperature(WeatherApiRequest request) {
        try {
            JSONObject report = askWeatherApi(request, "weather");

            if (report == null) {
                return null;
            }

            WeatherApi currentWeather = new WeatherApi();

            currentWeather.setCountryCode(report.getJSONObject("sys").getString("country"));
            currentWeather.setCityName(report.getString("name"));
            currentWeather.setTemperature(report.getJSONObject("main").getDouble("temp"));
            currentWeather.setMinTemperature(report.getJSONObject("main").getDouble("temp_min"));
            currentWeather.setMaxTemperature(report.getJSONObject("main").getDouble("temp_max"));
            currentWeather.setCoordinatesLat(report.getJSONObject("coord").getDouble("lat"));
            currentWeather.setCoordinatesLon(report.getJSONObject("coord").getDouble("lon"));
            return currentWeather;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WeatherApi> ThreeDaysWeather(WeatherApiRequest request)
    {
        List<WeatherApi> ThreeDaysWeather = new ArrayList<>();
        List<List<WeatherApi>> sortByDate = new ArrayList<>();
        List<WeatherApi> reportThreeDaysWeather = new ArrayList<>();

        //get date
        Date date = new Date();
        Calendar calendarReport = Calendar.getInstance();
        calendarReport.setTime(date);
        int currentDay = calendarReport.get(Calendar.DAY_OF_MONTH);

        JSONObject response = askWeatherApi(request, "forecast");

        if (response == null){
            return null;
        }

        try {
            JSONArray list = response.getJSONArray("list");

            for (int i = 0; i < list.length(); i++){
                JSONObject threeDaysWeatherObj = (JSONObject)list.get(i);
                Date dateOfForecast = new java.util.Date(threeDaysWeatherObj.getLong("dt")*1000);
                calendarReport.setTime(dateOfForecast);


                WeatherApi threeDaysWeather = new WeatherApi();
                threeDaysWeather.setDate(calendarReport.getTime());
                threeDaysWeather.setCityName(response.getJSONObject("city").getString("name"));
                threeDaysWeather.setCountryCode(response.getJSONObject("city").getString("country"));
                threeDaysWeather.setTemperature(threeDaysWeatherObj.getJSONObject("main").getDouble("temp"));
                threeDaysWeather.setMinTemperature(threeDaysWeatherObj.getJSONObject("main").getDouble("temp_min"));
                threeDaysWeather.setMaxTemperature(threeDaysWeatherObj.getJSONObject("main").getDouble("temp_max"));
                threeDaysWeather.setCoordinatesLat(response.getJSONObject("city").getJSONObject("coord").getDouble("lat"));
                threeDaysWeather.setCoordinatesLon(response.getJSONObject("city").getJSONObject("coord").getDouble("lon"));

                int forecastDay = calendarReport.get(Calendar.DAY_OF_MONTH);
                int day = (forecastDay - currentDay);

                if (day > 0 && day <= 3) {
                    if (sortByDate.size() < day || sortByDate.size() == 0) {
                        sortByDate.add(new ArrayList<>());

                    }
                    sortByDate.get(sortByDate.size() - 1).add(threeDaysWeather);
                }
            }

            for (List<WeatherApi> threeDays: sortByDate) {
                WeatherApi minTempOfDay = Collections.min(threeDays, Comparator.comparingDouble(WeatherApi::getTemperature));
                WeatherApi maxTempOfDay = Collections.max(threeDays, Comparator.comparingDouble(WeatherApi::getTemperature));

                WeatherApi weather = new WeatherApi();
                weather.setCountryCode(maxTempOfDay.getCountryCode());
                weather.setCityName(maxTempOfDay.getCityName());
                weather.setCoordinatesLat(maxTempOfDay.getCoordinatesLat());
                weather.setCoordinatesLon(maxTempOfDay.getCoordinatesLon());
                weather.setTemperature(maxTempOfDay.getTemperature());
                weather.setMaxTemperature(maxTempOfDay.getTemperature());
                weather.setMinTemperature(minTempOfDay.getTemperature());
                weather.setDate(maxTempOfDay.getDate());

                reportThreeDaysWeather.add(weather);
            }

            return reportThreeDaysWeather;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ThreeDaysWeather;
    }

    private static JSONObject askWeatherApi(WeatherApiRequest request, String option){
        try {

            URL url = new URL(weatherApiUrl + option + "?q=" + request.getCityName() + "," + request.getCountryCode() + "&units=" + request.getUnits() + "&APPID=" + weatherApiKey);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Accept", "application/json");

            if (c.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + c.getResponseCode());
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
            StringBuilder responseBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseBuilder.append(inputStr);

            JSONObject jsonObj = new JSONObject(responseBuilder.toString());

            c.disconnect();

            return jsonObj;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}





