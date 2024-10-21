import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Ticket extends Booking {

    static String ticketId;
    static String ticketType;
    String baggageAllowance;
    Date departDate;
    Date arrivalDate;
    static Flight associatedFlight;
    public static Payment payment;
    private static Set<String> generatedTicketIds = new HashSet<>();
    private static List<Ticket> ticketList = new ArrayList<>();

    public Ticket(String currentLocation, String destinationLocation, Flight associatedFlight, String ticketType, String baggageAllowance, Date departDate, Date arrivalDate, Payment payment) {        // Generate a unique 10-digit ticket ID
        this.ticketId = String.valueOf(generateUniqueId());

        if (!ticketType.equals("firstclass") && !ticketType.equals("business") && !ticketType.equals("economy")) {
            System.out.println("Error: Invalid ticket type. Allowed values are firstclass, business, or economy.");
            return;
        }

        if ((ticketType.equals("economy") && !baggageAllowance.equals("25kg")) ||
                (!ticketType.equals("economy") && !baggageAllowance.equals("30kg") && !baggageAllowance.equals("27kg"))) {
            System.out.println("Error: Invalid baggage allowance. Allowed values are 30kg or 27kg for non-economy, and 25kg for economy.");
            return;
        }

        if (!isValidDate(departDate) || !isValidDate(arrivalDate)) {
            System.out.println("Error: Invalid date format or range. Please re-enter the date.");
            return;
        }
        this.currentLocation = currentLocation;
        this.destinationLocation = destinationLocation;
        this.associatedFlight = associatedFlight;
        this.ticketType = ticketType;
        this.baggageAllowance = baggageAllowance;
        this.departDate = departDate;
        this.arrivalDate = arrivalDate;
        this.payment = payment;
        generatedTicketIds.add(String.valueOf(Integer.valueOf(ticketId)));
    }

    public Ticket(String currentLocation, String destinationLocation, Object associatedFlight, String ticketType, String baggageAllowance, Date departDate, Date arrivalDate) {
        super();
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }
    public String getCurrentLocation() {
        return currentLocation;
    }
    private int generateUniqueId() {
        long newId;
        do {
            newId = ThreadLocalRandom.current().nextLong(10000000L, 100000000L);
        } while (isTicketIdGenerated((int) newId) || newId <= 0);
        return (int) newId;
    }

    private boolean isTicketIdGenerated(int ticketId) {
        return generatedTicketIds.contains(ticketId);
    }

    private static boolean isValidDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {

            date = dateFormat.parse(dateFormat.format(date));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void addTicket() throws IOException {
        Ticket newTicket = createTicketFromUserInput();

        if (newTicket != null) {
            ticketList.add(newTicket); // Add the new ticket to the list
            System.out.println("Ticket registered successfully: " + newTicket);
            CsvWriter.writeTicketsToCsv(ticketList); // Write the entire ticketList to CSV
        } else {
            System.out.println("Failed to register ticket: Ticket is null");
        }
    }


    public static Ticket createTicketFromUserInput() throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            String currentLocation;

            do {
                System.out.print("Enter current location(Saudi Arabia, United Arab Emirates, Egypt, Iraq, Kuwait, Qatar, Oman, Bahrain, Jordan, Lebanon): ");
                currentLocation = scanner.nextLine();
            } while (!isValidLocation(currentLocation));
            String destinationLocation;

            do {
                System.out.print("Enter destination location(Saudi Arabia, United Arab Emirates, Egypt, Iraq, Kuwait, Qatar, Oman, Bahrain, Jordan, Lebanon): ");
                destinationLocation = scanner.nextLine();
            } while (!isValidLocation(destinationLocation));
            String ticketType;

            do {
                System.out.print("Enter ticket type (firstclass, business, economy): ");
                ticketType = scanner.nextLine();

                if (!ticketType.equals("firstclass") && !ticketType.equals("business") && !ticketType.equals("economy")) {
                    System.out.println("Error: Invalid ticket type. Allowed values are firstclass, business, or economy.");
                }
            } while (!ticketType.equals("firstclass") && !ticketType.equals("business") && !ticketType.equals("economy"));
            String baggageAllowance;
            if (ticketType.equals("economy")) {
                baggageAllowance = "25kg";
            } else {
                baggageAllowance = "";
                do {
                    System.out.print("Enter baggage allowance (30kg or 27kg): ");
                    baggageAllowance = scanner.nextLine();

                    if (!baggageAllowance.equals("30kg") && !baggageAllowance.equals("27kg")) {
                        System.out.println("Error: Invalid baggage allowance. Allowed values are 30kg or 27kg.");
                    }
                } while (!baggageAllowance.equals("30kg") && !baggageAllowance.equals("27kg"));
            }

            Date departDate;
            do {
                System.out.print("Enter departure date (yyyy-MM-dd): ");
                departDate = parseDate(scanner.nextLine());

                if (!isValidDate(departDate)) {
                    System.out.println("Error: Invalid date format or range. Please re-enter the date.");
                }
            } while (!isValidDate(departDate));


            Date arrivalDate;
            do {
                System.out.print("Enter arrival date (yyyy-MM-dd): ");
                arrivalDate = parseDate(scanner.nextLine());

                if (!isValidDate(arrivalDate)) {
                    System.out.println("Error: Invalid date format or range. Please re-enter the date.");
                }
            } while (!isValidDate(arrivalDate));


            Flight associatedFlight = readFlightInfoFromCsv();
            if (associatedFlight != null) {
                Payment payment = createPaymentFromUserInput(ticketType); // Placeholder for payment input
                Ticket ticket = new Ticket(currentLocation, destinationLocation, associatedFlight, ticketType, baggageAllowance, departDate, arrivalDate, payment);
                Customer.notifyTicketBooked(ticket);
                return ticket;
            } else {
                System.out.println("Error: Associated flight not found. Please check flight information.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error booking ticket");
        }

    }
    private static Payment createPaymentFromUserInput(String ticketType) {
        Scanner scanner = new Scanner(System.in);
        String paymentAmount = Payment.PaymentAmount(ticketType);
        System.out.print("\nEnter payment date (yyyy-MM-dd): ");
        Date paymentDate = parseDate(scanner.nextLine());
        System.out.print("Enter customer ID: ");
        String paymentCustomerId = scanner.nextLine();
        int paymentId = generatePaymentId();
        return new Payment(paymentId, paymentAmount, paymentDate, Integer.parseInt(paymentCustomerId));
    }

    private static int generatePaymentId() {
        return (int) (Math.random() * 900000000) + 100000000;
    }

    static Flight readFlightInfoFromCsv() throws IOException {
        String csvFilePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinal\\Flight.csv"; // Update with the correct path to your CSV file
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] flightData = line.split(cvsSplitBy);


                if (flightData.length >= 5) {
                    int flightNumber = Integer.parseInt(flightData[4].trim());

                    return new Flight(
                            flightData[0], // Company Name
                            Integer.parseInt(flightData[1]), // Available Seats
                            Boolean.parseBoolean(flightData[2]), // Available Flight
                            flightData[3], // Flight Time
                            flightData[4] // Arriving Time
                    );
                } else {
                    System.out.println("Error: Invalid data format in CSV. Expected at least 5 columns.");
                }
            }
        }
        System.out.println("Error: Associated flight not found in CSV.");
        return null; // or throw new IOException("Associated flight not found");
    }

    public static boolean isValidLocation(String location) {
        List<String> allowedLocations = Arrays.asList(
                "saudi arabia", "united arab emirates", "egypt", "iraq", "kuwait",
                "qatar", "oman", "bahrain", "jordan", "lebanon"
        );

        String lowercaseLocation = location.toLowerCase();
        List<String> lowercaseAllowedLocations = allowedLocations.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return lowercaseAllowedLocations.contains(lowercaseLocation);
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        Date parsedDate = null;
        boolean isValidDate = false;

        do {
            try {
                parsedDate = dateFormat.parse(dateString);
                if (isValidCriteria(parsedDate)) {
                    isValidDate = true;
                } else {
                    System.out.println("Error: Invalid date format or range. Please re-enter the date.");
                    dateString = askForDateInput();
                }

            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please re-enter the date.");
                dateString = askForDateInput();
            }
        } while (!isValidDate);

        return parsedDate;
    }

    private static boolean isValidCriteria(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year >= 2023 && month >= 1 && month <= 12 && day >= 1 && day <= 31;
    }

    private static String askForDateInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a valid date (yyyy-MM-dd): ");
        return scanner.nextLine();
    }

    public static void searchTicketById(String ticketId) {
        String csvFilePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\tickets.csv"; // Update with the correct path to your CSV file
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] ticketData = line.split(cvsSplitBy);
                String storedTicketId = ticketData[0].trim(); // Assuming the ticket ID is in the first column

                if (storedTicketId.equals(ticketId)) {
                    // Ticket found
                    System.out.println("Ticket found:");
                    System.out.println("Ticket Information:");
                    System.out.println("Ticket ID: " + ticketData[0]);
                    System.out.println("Current Location: " + ticketData[1]);
                    System.out.println("Destination Location: " + ticketData[2]);
                    System.out.println("Ticket Type: " + ticketData[3]);
                    System.out.println("Baggage Allowance: " + ticketData[4]);
                    System.out.println("Departure Date: " + ticketData[5]);
                    System.out.println("Arrival Date: " + ticketData[6]);
                    // Add more fields as needed
                    return;
                }
            }
            // Ticket not found
            System.out.println("Ticket not found for ID: " + ticketId);
        } catch (IOException e) {
            System.out.println("Error reading ticket data from file: " + e.getMessage());
        }
    }
    public Payment getPayment() {
        return payment;
    }

    public String getTicketId() {
        return ticketId;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public String getTicketType() {

        return ticketType;
    }

    public String getBaggageAllowance() {
        return baggageAllowance;
    }

    public Date getDepartDate() {

        return departDate;
    }

    public Date getArrivalDate() {

        return arrivalDate;
    }

    @Override
    public String toString() {
        String flightInfo = (associatedFlight != null) ? associatedFlight.toString() : "No associated flight information";


        if (payment != null) {
            return "Ticket Information:\n" +
                    // Existing code...
                    "Payment Information:\n" +
                    "Payment ID: " + payment.getPaymentId() + "\n" +
                    "Payment Amount: " + payment.getPaymentAmount() + "\n" +
                    "Payment Date: " + payment.getPaymentDate() + "\n" +
                    "Customer ID: " + payment.getPaymentCustomerId() + "\n" +
                    "-------------------------";
        }

        return "Ticket Information:\n" +
                "Ticket ID: " + ticketId + "\n" +
                "Current Location: " + currentLocation + "\n" +
                "Destination Location: " + destinationLocation + "\n" +
                "Ticket Type: " + ticketType + "\n" +
                "Baggage Allowance: " + baggageAllowance + "\n" +
                "Departure Date: " + isValidDate(departDate) + "\n" +
                "Arrival Date: " + isValidDate(arrivalDate) + "\n" +
                "Associated Flight Information:\n" + flightInfo +

                // Add more fields as needed
                "-------------------------";

    }
}
