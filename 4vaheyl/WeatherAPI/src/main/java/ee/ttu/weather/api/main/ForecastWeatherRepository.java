package ee.ttu.weather.api.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created by User on 16.12.2017.
 */
public class ForecastWeatherRepository {
    public List<WeatherApiResponse> ThreeDaysWeather(WeatherApiRequest request) throws IOException {
        List<WeatherApiResponse> ThreeDaysWeather = new ArrayList<>();
        List<List<WeatherApiResponse>> sortByDate = new ArrayList<>();
        List<WeatherApiResponse> reportThreeDaysWeather = new ArrayList<>();


        Date date = new Date();
        Calendar calendarReport = Calendar.getInstance();
        calendarReport.setTime(date);
        int currentDay = calendarReport.get(Calendar.DAY_OF_MONTH);

        UrlConnectionMaker url = new UrlConnectionMaker();
        JSONObject response = url.getResponseFromWheatherApiByUrl(request, "forecast");

        if (response == null) {
            return null;
        }

        try {
            JSONArray list = response.getJSONArray("list");

            for (int i = 0; i < list.length(); i++) {
                JSONObject threeDaysWeatherObj = (JSONObject) list.get(i);
                Date dateOfForecast = new java.util.Date(threeDaysWeatherObj.getLong("dt") * 1000);
                calendarReport.setTime(dateOfForecast);


                WeatherApiResponse threeDaysWeather = new WeatherApiResponse();
                threeDaysWeather.setDate(calendarReport.getTime());
                threeDaysWeather.setCityName(response.getJSONObject("city").getString("name"));
                threeDaysWeather.setCountryCode(response.getJSONObject("city").getString("country"));
                threeDaysWeather.setTemperature(threeDaysWeatherObj.getJSONObject("main").getDouble("temp"));
                threeDaysWeather.setMinTemperature(threeDaysWeatherObj.getJSONObject("main").getDouble("temp_min"));
                threeDaysWeather.setMaxTemperature(threeDaysWeatherObj.getJSONObject("main").getDouble("temp_max"));
                threeDaysWeather.setCoordinatesLat(response.getJSONObject("city").getJSONObject("coord").getDouble("lat"));
                threeDaysWeather.setCoordinatesLon(response.getJSONObject("city").getJSONObject("coord").getDouble("lon"));

                calendarReport.set(Calendar.HOUR_OF_DAY, 12);
                calendarReport.set(Calendar.MINUTE, 0);
                calendarReport.set(Calendar.SECOND, 0);
                calendarReport.set(Calendar.MILLISECOND, 0);

                int forecastDay = calendarReport.get(Calendar.DAY_OF_MONTH);
                int day = (forecastDay - currentDay);

                if (day > 0 && day <= 3) {
                    if (sortByDate.size() < day || sortByDate.size() == 0) {
                        sortByDate.add(new ArrayList<>());

                    }
                    sortByDate.get(sortByDate.size() - 1).add(threeDaysWeather);
                }
            }

            for (List<WeatherApiResponse> threeDays : sortByDate) {
                WeatherApiResponse minTempOfDay = Collections.min(threeDays, Comparator.comparingDouble(WeatherApiResponse::getTemperature));
                WeatherApiResponse maxTempOfDay = Collections.max(threeDays, Comparator.comparingDouble(WeatherApiResponse::getTemperature));

                WeatherApiResponse weather = new WeatherApiResponse();
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
}


