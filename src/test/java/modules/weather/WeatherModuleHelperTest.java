package modules.weather;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class WeatherModuleHelperTest {

    WeatherModuleHelper weatherModuleHelper;

    @Before
    public void initWeatherModuleHelperTest() throws ParseException, java.text.ParseException, IOException {
        WeatherFileHandler.updateFile();
        weatherModuleHelper = new WeatherModuleHelper();
    }

    @Test
    public void timeOfDayOffsets() {

        assertEquals("Evening with Offset 1 should return night", weatherModuleHelper.getTimeOfDayWithOffset(1, WeatherModuleHelper.TimeOfDay.EVENING), WeatherModuleHelper.TimeOfDay.NIGHT);
        assertEquals("Evening with Offset 2 should return morning", weatherModuleHelper.getTimeOfDayWithOffset(2, WeatherModuleHelper.TimeOfDay.EVENING), WeatherModuleHelper.TimeOfDay.MORNING);
        assertEquals("Evening with Offset 3 should return noon", weatherModuleHelper.getTimeOfDayWithOffset(3, WeatherModuleHelper.TimeOfDay.EVENING), WeatherModuleHelper.TimeOfDay.NOON);
        assertEquals("Evening with Offset 4 should return afternoon", weatherModuleHelper.getTimeOfDayWithOffset(4, WeatherModuleHelper.TimeOfDay.EVENING), WeatherModuleHelper.TimeOfDay.AFTERNOON);
        assertEquals("Evening with Offset 5 should return evening", weatherModuleHelper.getTimeOfDayWithOffset(5, WeatherModuleHelper.TimeOfDay.EVENING), WeatherModuleHelper.TimeOfDay.EVENING);
    }
}
