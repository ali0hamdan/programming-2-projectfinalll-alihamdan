import java.util.Arrays;
import java.util.List;

public abstract class Booking  {

    public String currentLocation;
    public String destinationLocation;

    public String getCurrentLocation() {
        return currentLocation;
    }
    protected static boolean isValidLocation(String location) {
        // List of allowed locations
        List<String> allowedLocations = Arrays.asList(
                "Saudi Arabia", "United Arab Emirates", "Egypt", "Iraq", "Kuwait",
                "Qatar", "Oman", "Bahrain", "Jordan", "Lebanon"
        );

        // Check if the provided location is in the allowed list
        return allowedLocations.contains(location);
    }



    public String getDestinationLocation() {
        return destinationLocation;
    }





}