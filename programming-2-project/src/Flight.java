import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
public class Flight extends Booking {
    private String companyName;
    private int availableSeats;
    private boolean availableFlight;
    private String flightTime;
    private String arrivingTime;
    private int flightNb;
    private static List<Flight> flights = new ArrayList<>();
    Scanner sc=new Scanner(System.in);

    public Flight(String companyName, int availableSeats, boolean availableFlight, String flightTime, String arrivingTime) {
        super();
        this.companyName = companyName;
        this.availableSeats = availableSeats;
        this.availableFlight = availableFlight;
        this.flightTime = flightTime;
        this.arrivingTime = arrivingTime;
        this.flightNb = generateRandomThreeDigitNumber();
    }

    public String getCompanyName() {
        return companyName;
    }

    public static void showAllFlights() {
        String csvFilePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinal\\Flight.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    // Skip the header row
                    headerSkipped = true;
                    continue;
                }

                String[] flightData = line.split(cvsSplitBy);

                // Print information with modified column labels
                System.out.println("Company Name: " + flightData[0]); // Column A
                System.out.println("Seats: " + flightData[1]); // Column B
                System.out.println("Flight Time: " + flightData[2]); // Column C
                System.out.println("Arrival Time: " + flightData[3]); // Column D
                System.out.println("Flight Number: " + flightData[4]); // Column E
                System.out.println(); // Add a newline for better readability
            }

        } catch (IOException e) {
            System.out.println("Error reading flight data from file: " + e.getMessage());
        }
    }



    public static void addFlight(Flight flight) {
        flights.add(flight);
        System.out.println("Flight added successfully.");
    }

public static void removeFlight() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the flight number to remove: ");

    while (!sc.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid flight number.");
        sc.nextLine();
    }

    int flightNumberToRemove = sc.nextInt();
    sc.nextLine();

    String csvFilePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinal\\Flight.csv";
    String line;
    String cvsSplitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
        List<String> updatedLines = new ArrayList<>();
        boolean headerSkipped = false;

        while ((line = br.readLine()) != null) {
            if (!headerSkipped) {
                // Skip the header row
                headerSkipped = true;
                continue;
            }

            String[] flightData = line.split(cvsSplitBy);


            int flightNumber;
            try {
                flightNumber = Integer.parseInt(flightData[4].trim()); // Column E
            } catch (NumberFormatException e) {

                System.out.println("Invalid flight number format: " + flightData[4]);
                continue;
            }

            if (flightNumber != flightNumberToRemove) {
                updatedLines.add(line);
            } else {

                System.out.println("Flight with number " + flightNumberToRemove + " removed successfully.");
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(csvFilePath)))) {
            writer.println("Company Name,Available Seats,Available Flight,Flight Time,Arriving Time,Flight Phone Number,Flight Number");

            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
        } catch (IOException e) {
            System.out.println("Error writing to flight data file: " + e.getMessage());
        }

    } catch (IOException e) {
        System.out.println("Error reading flight data from file: " + e.getMessage());
    }
}


    public int getAvailableSeats() {
        return availableSeats;
    }




    public static boolean isValidPhoneNb(String phoneNumber) {
        // Check if phoneNumber is not null, is 8 digits long, and starts with one of the Lebanese mobile prefixes
        return phoneNumber != null && phoneNumber.matches("(71|03|76|78|79)\\d{6}");
    }

    public static boolean isValidFlightTime(String flightTime, String arrivingTime) {
        try {
            if (flightTime != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime.parse(flightTime, formatter);
                // ... other validation logic
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format.");
            return false;
        }
        return true;
    }


    public String getFlightTime() {
        return flightTime;
    }

    public String getArrivingTime() {
        return arrivingTime;
    }

    public int getFlightNb() {
        return flightNb;
    }
    public void setFlightNb() {
        // Generate a three-digit flight number
        int generatedFlightNb = generateRandomThreeDigitNumber();

        // Set the generated flight number
        this.flightNb = generatedFlightNb;
    }
    private int generateRandomThreeDigitNumber() {
        Random random = new Random();
        return random.nextInt(900) + 100; // Generates a random number between 100 and 999 (inclusive)
    }

    @Override
    public  String toString() {
        return "Flight{" +
                "companyName='" + companyName + '\'' +
                ", availableSeats=" + availableSeats +
                ", availableFlight=" + availableFlight +
                ", flightTime='" + flightTime + '\'' +
                ", arrivingTime='" + arrivingTime + '\'' +
                ", flightNb='" + flightNb + '\'' +
                '}';
    }
}
