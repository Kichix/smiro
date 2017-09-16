package modules.travel;

import Helpers.Vector3;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TravelModuleHelper {

    private static int traveltimeTransit, traveltimeCar, traveltimeBike;
    public static final String C_FILEPATH = "src//main//resources//travel//";
    private static final String C_URLCAR = "https://maps.googleapis.com/maps/api/directions/json?origin=15,muehlenbloecken,brunstorf&destination=werner-otto-strasse,hamburg";
    private static final String C_URLTRANSIT = "https://maps.googleapis.com/maps/api/directions/json?origin=15,muehlenbloecken,brunstorf&destination=werner-otto-strasse,hamburg&mode=transit";
    private static final String C_URLBIKE = "https://maps.googleapis.com/maps/api/directions/json?origin=15,muehlenbloecken,brunstorf&destination=werner-otto-strasse,hamburg&mode=bicycling";

    public enum Vehicle {
        CAR, BICYCLE, TRANSIT;
    }

    //Returns the icon name of a vehicle enum for the GUI
    public static String getTransitIconName(Vehicle vec) {
        switch(vec) {
            case CAR:
                return "caricon.png";
            case BICYCLE:
                return "bikeicon.png";
            case TRANSIT:
                return "transiticon.png";
        }
        return "";
    }

    //Returns a travel time for a given vehicle
    public static TravelPanel getTraveltime(Vehicle vec) throws IOException, ParseException, java.text.ParseException {

        //Variable for getting the next transit
        SimpleDateFormat format = new SimpleDateFormat("H:m");
        Date date;

        //Panel to return
        TravelPanel panel = new TravelPanel(vec);

        //JSON stuff
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(C_FILEPATH+getFilename(vec)+".json"));
        System.out.println("Parsing: "+C_FILEPATH+getFilename(vec)+".json");
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject currentObject;
        JSONObject currentLegsObject;
        JSONObject currentDepartureTime;
        Long durationLong;
        JSONArray arr = (JSONArray) jsonObject.get("routes");
        Iterator<JSONObject> iterator = arr.iterator();

        while(iterator.hasNext()) {
            currentObject = iterator.next();
            JSONArray arr2 = (JSONArray) currentObject.get("legs");
            Iterator<JSONObject> iteratorLegs = arr2.iterator();
            while(iteratorLegs.hasNext()) {
                currentLegsObject = iteratorLegs.next();
                JSONObject durationObject = (JSONObject) currentLegsObject.get("duration");
                durationLong = (Long) durationObject.get("value");
                panel.setTravelTime(durationLong/60);

                if(vec == Vehicle.TRANSIT) {
                    currentDepartureTime = (JSONObject) currentLegsObject.get("departure_time");
                    panel.setNextTransit(format.parse((String) currentDepartureTime.get("text")));

                }
            }
        }

        return panel;
    }

    //Returns the filename for the given vehicle
    private static String getFilename(Vehicle mode) {
        return "travel_"+getVehicleString(mode);
    }

    //Returns the url for a give vehicle
    private static String getURL(Vehicle mode) {
        switch(mode) {
            case CAR:
                return C_URLCAR;
            case BICYCLE:
                return C_URLBIKE;
            case TRANSIT:
                return C_URLTRANSIT;
        }
        return "";
    }

    //Updates all travelfiles
    private static void updateFiles() throws IOException {

        for(Vehicle vec : Vehicle.values()) {
            File file = new File(C_FILEPATH+getFilename(vec)+".json");
            URL url = new URL(getURL(vec));
            FileUtils.copyURLToFile(url, file);
        }
    }

    //Returns a String for an vehicle enum
    private static String getVehicleString(Vehicle mode) {
        switch(mode) {
            case CAR:
                return "car";
            case BICYCLE:
                return "bike";
            case TRANSIT:
                return "transit";
        }

        return "";
    }

}
