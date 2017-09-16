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
import java.util.Iterator;

public class TravelHelper {

    private static int traveltimeTransit, traveltimeCar, traveltimeBike;
    private static final String C_FILEPATH = "src//main//resources//travel//";
    private static final String C_URLCAR = "https://maps.googleapis.com/maps/api/directions/json?origin=15,muehlenbloecken,brunstorf&destination=werner-otto-strasse,hamburg";
    private static final String C_URLTRANSIT = "https://maps.googleapis.com/maps/api/directions/json?origin=15,muehlenbloecken,brunstorf&destination=werner-otto-strasse,hamburg&mode=transit";
    private static final String C_URLBIKE = "https://maps.googleapis.com/maps/api/directions/json?origin=15,muehlenbloecken,brunstorf&destination=werner-otto-strasse,hamburg&mode=bicycling";

    public enum Vehicle {
        CAR, BICYCLE, TRANSIT;
    }

    //Returns a 3D Vector containing all traveltimes
    public static Vector3 getTraveltimes() throws IOException, ParseException {
        updateFiles();

        for(Vehicle vec : Vehicle.values()) {
            switch(vec) {
                case CAR:
                    traveltimeCar = getTraveltime(vec);
                    break;
                case BICYCLE:
                    traveltimeBike = getTraveltime(vec);
                    break;
                case TRANSIT:
                    traveltimeTransit = getTraveltime(vec);
            }
        }

        return new Vector3(traveltimeCar, traveltimeBike, traveltimeTransit);
    }

    //Returns a travel time for a given vehicle
    private static int getTraveltime(Vehicle mode) throws IOException, ParseException {

        //JSON stuff
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(C_FILEPATH+getFilename(mode)+".json"));
        System.out.println("Parsing: "+C_FILEPATH+getFilename(mode)+".json");
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject currentObject;
        JSONObject currentLegsObject;
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
                System.out.println(mode+": "+durationLong);
                return  durationLong.intValue()/60;
            }
        }

        return 0;
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
