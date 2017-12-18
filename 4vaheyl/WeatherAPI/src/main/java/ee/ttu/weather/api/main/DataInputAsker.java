package ee.ttu.weather.api.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by User on 14.12.2017.
 */
public class DataInputAsker {

    private static String inputFile = "C:\\Users\\User\\IdeaProjects\\WeatherAPI\\src\\main\\input.txt";


    public static List<String> getCityFromConsole() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter a city name and country code like: [CityName, CC] example: Tallinn, EE:  ");
        List<String> CityList = new ArrayList<>();
        CityList.add(reader.nextLine().trim());
        return  CityList;
    }

    public List<String> getCitiesFromFile() throws IOException {
        List<String> countryInfo = new ArrayList<>();
        BufferedReader fileIn = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = fileIn.readLine()) != null) {
            countryInfo.add(line);
        }
        fileIn.close();

        return countryInfo;
    }

    public List<String> getCityFromConsoleOrFromFile() throws IOException {
        Scanner scr;
        System.out.println("Read city name from: [C]onsole | [F]ile. \n");
        scr = new Scanner(System.in);
        String input = scr.nextLine();

        if(input.equalsIgnoreCase("C")) {
            return getCityFromConsole();
        }
        if (input.equalsIgnoreCase("F")) {
            try {
                return getCitiesFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid input. Cities will be read from the File");
            return getCitiesFromFile();
        }

        System.out.println("Sorry! Something went wrong! :(");
        return null;
    }

}

