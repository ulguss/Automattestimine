package ee.ttu.weather.api.test;

import ee.ttu.weather.api.main.DataInputAsker;
import ee.ttu.weather.api.main.DataInputWriter;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


/**
 * Created by User on 16.12.2017.
 */
public class DataInputWriterReaderTest {



    @Test
    public void testInputAskerFromFile() throws IOException {
        DataInputAsker inputAsker = new DataInputAsker();
        assertTrue(inputAsker.getCitiesFromFile().size()>0);
    }

    @Test
    public void testInputAskerFromFileMocked() throws IOException {
        DataInputAsker inputAsker = mock(DataInputAsker.class);
        inputAsker.getCitiesFromFile();
        verify(inputAsker).getCitiesFromFile();
    }

    @Test
    public void testWriteCurrentWeatherAndThreeDaysWeatherToFileByCity() throws IOException {
        DataInputWriter dataInputWriter = mock(DataInputWriter.class);
        dataInputWriter.WriteCurrentWeatherAndThreeDaysWeatherToFileByCity(anyList());

        verify(dataInputWriter).WriteCurrentWeatherAndThreeDaysWeatherToFileByCity(anyList());
    }

    @Test
    public void TestWritingToFile() throws IOException {
        DataInputWriter dataInputWriter = mock(DataInputWriter.class);
        dataInputWriter.writeDataToFile(anyString(), anyString());

        verify(dataInputWriter, times(1)).writeDataToFile(anyString(), anyString());
    }

    @Test
    public void testIfThereAnyStringInTestFile() throws IOException {
        DataInputWriter writer = new DataInputWriter();
        String fileName = "test.txt";
        writer.writeDataToFile(fileName, "working");

        String inputFile = "C:\\Users\\User\\IdeaProjects\\WeatherAPI\\src\\test\\resources" +  "\\" + fileName;
        BufferedReader fileIn = new BufferedReader(new FileReader(inputFile));
        assertNotNull(fileIn.readLine());
    }

}



