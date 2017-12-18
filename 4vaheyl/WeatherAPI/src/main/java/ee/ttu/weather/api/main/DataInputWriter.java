package ee.ttu.weather.api.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by User on 16.12.2017.
 */
public class DataInputWriter {

    private String outputTxt = System.getProperty("user.dir") + "\\src\\main\\";



    public void WriteCurrentWeatherAndThreeDaysWeatherToFileByCity(List<WeatherApiResponse> weather) throws IOException {
        Map<String, List<WeatherApiResponse>> numberOfLines = weather.stream().collect(Collectors.groupingBy(WeatherApiResponse::getCityName));


        for (Map.Entry<String, List<WeatherApiResponse>> numberOfCities : numberOfLines.entrySet()){
            String fileName = numberOfCities.getKey() + ".txt";
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < numberOfCities.getValue().size(); i++) {
                WeatherApiResponse element = numberOfCities.getValue().get(i);
                if (i == 0) {
                    builder.append(Calendar.getInstance().getTime()+ " \n");
                    builder.append(element.toString() + " \n");
                } else {
                    builder.append(element.getDate() + " \n");
                    builder.append("Minimum temperature: " + String.valueOf(element.getMinTemperature()) + " \n");
                    builder.append("Maximum temperature: " + String.valueOf(element.getMaxTemperature()) + " \n");
                }
            }
            writeDataToFile(fileName,  String.valueOf(builder));
        }
    }


    public void writeDataToFile(String fileName, String data) throws IOException {
        String file = outputTxt + fileName;
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(data);
        bw.close();
    }


}
