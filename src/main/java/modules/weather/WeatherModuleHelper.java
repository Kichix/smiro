package modules.weather;

import Helpers.Vector2;
import Helpers.Vector3;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.tools.JavaCompiler;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.lang.Math.*;

public class WeatherModuleHelper {

    Weatherdata wdNight, wdMorning, wdNoon, wdAfternoon, wdEvening;
    TimeOfDay currentTimeOfDay;

    public WeatherModuleHelper() throws ParseException, java.text.ParseException, IOException {
        currentTimeOfDay = getCurrentTimeOfDay();
        updateValues();
    }

    //The ENUM for a time of day
    public enum TimeOfDay {
        NIGHT, MORNING, NOON, AFTERNOON, EVENING;
    }

    //Gets the current time of day enum
    public TimeOfDay getCurrentTimeOfDay() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if(hour > 0 && hour < 6) {
            return TimeOfDay.NIGHT;
        } else if (hour >= 6 && hour < 12) {
            return TimeOfDay.MORNING;
        } else if (hour >= 12 && hour < 15) {
            return TimeOfDay.NOON;
        } else if (hour >= 15 && hour < 18) {
            return TimeOfDay.AFTERNOON;
        } else if (hour >= 18 && hour <24) {
            return TimeOfDay.EVENING;
        } else return TimeOfDay.MORNING;
    }

    //Updates the current time of day variable
    private void updateCurrentTimeOfDay() {

        this.currentTimeOfDay = getCurrentTimeOfDay();
    }

    //Returns an offset, to determine if weatherinfo needs to be taken from next day
    public int getTimeOfDayOffset(TimeOfDay tod) {

        if(getIntToTimeOfDay(tod)-getIntToTimeOfDay(currentTimeOfDay)<0) {
            return 1;
        }
        return 0;
    }

    //Turns the TimeOfDay Enum into an int
    public int getIntToTimeOfDay(TimeOfDay tod) {

        switch(tod) {
            case NIGHT:
                return 0;
            case MORNING:
                return 1;
            case NOON:
                return 2;
            case AFTERNOON:
                return 3;
            case EVENING:
                return 4;
        }
        return -1;
    }

    //Return the timespan for a time of day
    private Vector2 getTimespan(TimeOfDay tod) {

        switch(tod) {
            case AFTERNOON:
                return new Vector2(15,18);
            case EVENING:
                return new Vector2(18, 24);
            case MORNING:
                return new Vector2(6,12);
            case NIGHT:
                return new Vector2(0,6);
            case NOON:
                return new Vector2(12,15);
        }
        return new Vector2(-1,-1);
    }

    //Updates the Weatherdata objects
    public void updateValues() throws ParseException, java.text.ParseException, IOException {
        updateCurrentTimeOfDay();

        for (TimeOfDay tod : TimeOfDay.values()) {
            if(tod == TimeOfDay.AFTERNOON) {
                wdAfternoon = parseWeatherdata(tod);
            } else if (tod == TimeOfDay.EVENING) {
                wdEvening = parseWeatherdata(tod);
            } else if (tod == TimeOfDay.MORNING) {
                wdMorning = parseWeatherdata(tod);
            } else if (tod == TimeOfDay.NIGHT) {
                wdNight = parseWeatherdata(tod);
            } else if (tod == TimeOfDay.NOON) {
                wdNoon = parseWeatherdata(tod);
            }
        }
    }

    //Parses the JSON with weather data for a given Time of Day
    public Weatherdata parseWeatherdata(TimeOfDay tod) throws IOException, ParseException, java.text.ParseException {

        //Parservariables
        Vector2 timespan = getTimespan(tod);
        JSONParser parser = new JSONParser();
        Vector3 dateVector = getVector3Date(getTimeOfDayOffset(tod));
        JSONObject currentObj;
        JSONObject currentWeather;
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("y-M-d H:m:s");
        String currentString;
        Calendar calendar = new GregorianCalendar();

        //Weatherdata variables
        double temperature = 0;
        String icon = "";
        int iconId = 0;
        int wind = 0;

        //Init JSON file to parse
        Object obj = parser.parse(new FileReader(WeatherFileHandler.getFilenameAndPathCurrent()));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray arr = (JSONArray) jsonObject.get("list");
        Iterator<JSONObject> iterator = arr.iterator();
        Iterator<JSONObject> iterator2;

        //Parse through the JSON
        while (iterator.hasNext()) {
            currentObj = iterator.next();
            currentString = (String) currentObj.get("dt_txt");
            date = format.parse(currentString);
            calendar.setTime(date);

            //Look for date from given timespan from Time Of Day Enum
            if(calendar.get(Calendar.DAY_OF_MONTH) == dateVector.getX() &&
               calendar.get(Calendar.MONTH) == dateVector.getY()        &&
               calendar.get(Calendar.YEAR) == dateVector.getZ()           ) {
                if(calendar.get(Calendar.HOUR_OF_DAY) >= timespan.getX() &&
                   calendar.get(Calendar.HOUR_OF_DAY) < timespan.getY()     ) {
                    JSONObject main = (JSONObject) currentObj.get("main");
                    Double tempVal = ((Number)main.get("temp")).doubleValue();
                    temperature = convertKtoC(tempVal);

                    JSONArray weather = (JSONArray) currentObj.get("weather");
                    iterator2 = weather.iterator();
                    while (iterator2.hasNext()) {
                        currentWeather = iterator2.next();
                        icon = getIconName(Math.toIntExact((Long) currentWeather.get("id")), tod);
                    }
                }
            }
        }

        return new Weatherdata((float) temperature, wind, icon);
    }

    //Return the name of the icon
    private String getIconName(int id, TimeOfDay tod) {
        //Prefix first
        String prefix;
        if(tod == TimeOfDay.MORNING || tod == TimeOfDay.NOON || tod == TimeOfDay.AFTERNOON) {
            prefix = "day_";
        } else {
            prefix = "day_";
        }

        String suffix = "";
        switch (id) {
            case 200: suffix = "thunderrain";
                      break;
            case 201: suffix = "heavythunderrain";
                      break;
            case 202: suffix = "heavythunderrain";
                      break;
            case 210: suffix = "thunder";
                      break;
            case 211: suffix = "thunder";
                      break;
            case 212: suffix = "thunder";
                      break;
            case 221: suffix = "thunder";
                      break;
            case 230: suffix = "thunderrain";
                      break;
            case 231: suffix = "thunderrain";
                      break;
            case 232: suffix = "heavythunderrain";
                      break;
            case 300: suffix = "lightrain";
                      break;
            case 301: suffix = "lightrain";
                      break;
            case 302: suffix = "lightrain";
                      break;
            case 310: suffix = "lightrain";
                      break;
            case 312: suffix = "lightrain";
                      break;
            case 313: suffix = "lightrain";
                      break;
            case 314: suffix = "rain";
                      break;
            case 321: suffix = "lightrain";
                      break;
            case 500: suffix = "rain";
                      break;
            case 501: suffix = "rain";
                      break;
            case 502: suffix = "heavyrain";
                      break;
            case 503: suffix = "heavyrain";
                      break;
            case 504: suffix = "heavyrain";
                      break;
            case 511: suffix = "snowrain";
                      break;
            case 520: suffix = "lightrain";
                      break;
            case 521: suffix = "rain";
                      break;
            case 522: suffix = "heavyrain";
                      break;
            case 531: suffix = "rain";
                      break;
            case 600: suffix = "snow";
                      break;
            case 601: suffix = "snow";
                      break;
            case 602: suffix = "snow";
                      break;
            case 611: suffix = "snow";
                      break;
            case 612: suffix = "snow";
                      break;
            case 615: suffix = "snowrain";
                      break;
            case 616: suffix = "snow";
                      break;
            case 620: suffix = "snow";
                      break;
            case 621: suffix = "snow";
                      break;
            case 622: suffix = "snow";
                      break;
            case 701: suffix = "verycloudy";
                      break;
            case 711: suffix = "verycloudy";
                      break;
            case 721: suffix = "verycloudy";
                      break;
            case 741: suffix = "verycloudy";
                      break;
            case 800: suffix = "sunny";
                      break;
            case 801: suffix = "cloudysun";
                      break;
            case 802: suffix = "cloudy";
                      break;
            case 803: suffix = "cloudy";
                      break;
            case 804: suffix = "verycloudy";
                      break;
        }

        return prefix + suffix +".png";
    }


    //Returns the date relative to today with given offset
    public Vector3 getVector3Date(int offset) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        //Change the date by the offset
        calendar.add(Calendar.DAY_OF_MONTH, offset);

        return new Vector3(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
    }

    //Converts Fahrenheit to Celsius
    public double convertKtoC(double kelvin) {

        return kelvin - 273.15;
    }

    //Return weatherdata related to the timeofday enum
    public Weatherdata getWeatherdataEnum(TimeOfDay tod) {
        switch(tod) {
            case AFTERNOON:
                return wdAfternoon;
            case EVENING:
                return wdEvening;
            case MORNING:
                return wdMorning;
            case NIGHT:
                return wdNight;
            case NOON:
                return wdNoon;
        }   return wdAfternoon;
    }

    //Returns TimeOfDay Enum with given offset
    public TimeOfDay getTimeOfDayWithOffset(int offset, TimeOfDay tod) {
        if(offset != 0) {
            TimeOfDay todInternal = tod;

            for(int i = 0; i<offset; i++) {
                todInternal = getNextTimeOfDay(todInternal);
            }

            return todInternal;
        } else return tod;
    }

    //Returns a head for a weathersegment given the position of the segment
    public String getHeader(int pos) {
        if(pos == 0)  {
            return "Aktuell";
        } else return getHeaderEnum(getTimeOfDayWithOffset(pos, getCurrentTimeOfDay()));
    }

    //Returns a header for a given enum
    private String getHeaderEnum(TimeOfDay tod) {
        switch(tod) {
            case AFTERNOON:
                return "Nachmittags";
            case EVENING:
                return "Abends";
            case MORNING:
                return "Morgens";
            case NIGHT:
                return "Nachts";
            case NOON:
                return "Mittags";
        }

        return "Default";
    }

    //Return the next TimeOfDay
    public TimeOfDay getNextTimeOfDay(TimeOfDay tod) {
        switch(tod) {
            case AFTERNOON:
                return TimeOfDay.EVENING;
            case EVENING:
                return TimeOfDay.NIGHT;
            case MORNING:
                return TimeOfDay.NOON;
            case NIGHT:
                return TimeOfDay.MORNING;
            case NOON:
                return TimeOfDay.AFTERNOON;
        } return TimeOfDay.AFTERNOON;
    }

    //Returns the weatherdata relative to the position in the weathermodule and time of day
    public Weatherdata getWeatherdataPos(int pos) {

        return getWeatherdataEnum(getTimeOfDayWithOffset(pos, getCurrentTimeOfDay()));
    }

}
