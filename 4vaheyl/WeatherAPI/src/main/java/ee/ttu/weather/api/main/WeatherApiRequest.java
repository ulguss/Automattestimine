package ee.ttu.weather.api.main;

/**
 * Created by User on 5.10.2017.
 */
public class WeatherApiRequest {
    public String cityName;
    public String countryCode;
    public String units;

    public WeatherApiRequest(String cityName, String countryCode){
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.units = "metric";
    }

    public String getCityName(){

        return cityName;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public String getUnits(){
        return units;
    }


}
