package ee.ttu.weather.api.main;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 16.12.2017.
 */
public class UrlConnectionMaker {

    public static JSONObject getResponseFromWheatherApiByUrl(WeatherApiRequest request, String option){

        String weatherApiUrl = "http://api.openweathermap.org/data/2.5/";
        String weatherApiKey = "627ab471b34e311b9cdf7fbbfbd18e1c";


        try {

            URL url = new URL(weatherApiUrl + option + "?q=" + request.getCityName() + "," + request.getCountryCode() + "&units=" + request.getUnits() + "&APPID=" + weatherApiKey);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Accept", "application/json");

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
            StringBuilder responseBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseBuilder.append(inputStr);

            JSONObject jsonObj = new JSONObject(responseBuilder.toString());

            connect.disconnect();

            return jsonObj;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
