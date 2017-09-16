package modules.weather;

import Helpers.Vector2;
import Helpers.Vector3;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeatherFileHandler {

    public static final String C_FILEPATH = "src//main//resources//weather//";

    //checks and downloads
    public static void updateFile() throws IOException {
        checkOldFile();
        try {
            downloadNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //checks if a file from yesterday exists and deletes it
    private static void checkOldFile() {
        File file = new File(C_FILEPATH+getFileName(-1)+".json");

        if(file.exists() && !file.isDirectory()) {
            file.delete();
        }
    }

    //downloads a new weather JSON if it doesn't already exist
    private static void downloadNewFile() throws IOException {
        File file = new File(C_FILEPATH+getFileName(0)+".json");

        if(!file.exists() && !file.isDirectory()) {
          URL weatherurl = new URL("http://api.openweathermap.org/data/2.5/forecast?id=524901&appid=3656c08ddb46f3ed4b6b25d47bf9cb62");
          FileUtils.copyURLToFile(weatherurl, new File(C_FILEPATH+getFileName(0)+".json"));
        }

    }

    //Returns the filename in relation from today
    //@param int offset Offet days from today
    private static String getFileName(int offset) {

        //Init Variables
        String name = "weatherinfo";
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        //Change the date by the offset
        calendar.add(Calendar.DAY_OF_MONTH, offset);

        //Finalize the filename
        name = name + calendar.get(Calendar.DAY_OF_MONTH) + (calendar.get(Calendar.MONTH)+1) + calendar.get(Calendar.YEAR);
        return name;
    }

    //Returns the current Filename and Path for the current day
    public static String getFilenameAndPathCurrent() {
        return C_FILEPATH+getFileName(0)+".json";
    }

}
