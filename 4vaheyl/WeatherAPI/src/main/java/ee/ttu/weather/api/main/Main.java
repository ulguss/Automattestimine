package ee.ttu.weather.api.main;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by User on 5.11.2017.
 */
public class Main   {

        private static String inputFile = System.getProperty("user.dir") + "\\src\\main\\input.txt";
        private static String outputFile = System.getProperty("user.dir") + "\\src\\main\\output.txt";


    public static void main(String[] args) {
        Scanner scr = null;
        BufferedWriter bfw = null;
        FileWriter fwr = null;
        try {

            WeatherApiRequest request = null;
            scr  = new Scanner(new InputStreamReader(System.in));
            WeatherApiRepository weatherApiRepository = new WeatherApiRepository();

            label:
            while (true) {
                System.out.print("Choose option: 1 - a city from console; 2 - read from file: ");
                String choice = scr.next();
                List<String> cityData = null;

                switch (choice) {
                    case "1":
                        scr = new Scanner(System.in);
                        System.out.println("Please insert cityName name: ");
                        String cityName = scr.nextLine();
                        System.out.println("Please insert cityName code: ");
                        String countryCode = scr.nextLine();
                        request = new WeatherApiRequest(cityName.substring(0, 1).toUpperCase(), countryCode.toUpperCase());
                        break label;

                    case "2":
                        BufferedReader fileWithData = new BufferedReader(new FileReader(inputFile));
                        String line;
                        while ((line = fileWithData.readLine()) != null) {
                            cityData = Arrays.asList(line.split("\\s*,\\s*"));
                            request = new WeatherApiRequest(cityData.get(0), cityData.get(1));
                        }
                        fileWithData.close();
                        break label;
                    default:
                        System.out.println("Error!");
                        continue;
                }
            }

            fwr = new FileWriter(outputFile);
            bfw = new BufferedWriter(new FileWriter(outputFile));
            List<WeatherApi> response = weatherApiRepository.ThreeDaysWeather(request);
            for (WeatherApi element : response) {
                bfw.write(element.toString());
                bfw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scr  != null) {
                try {
                    scr .close();
                    if (bfw != null)
                        bfw.close();

                    if (fwr != null)
                        fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
