import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvReader {
    public List<User> readUsersFromCSV(String csvFilePath) {
        List<User> userList = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            String cvsSplitBy = ",";

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(cvsSplitBy);
                if (userData.length == 9) {
                    User user = createUserFromCSV(userData);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data from file: " + e.getMessage());
        }

        return userList;
    }

    private static User createUserFromCSV(String[] data) {
        String userId = data[0];
        String name = data[1];
        String role = data[2];
        String phoneNb = data[3];
        String origin = data[4];
        String userEmail = data[5];
        String userAddress = data[6];
        String birthDate = data[7];
        String password = data[8];

        switch (role.toLowerCase()) {
            case "customer":
                return new Customer(userId, name, role, phoneNb, origin, userEmail, userAddress, birthDate, password);
            case "reservation employee":
                return new ReservationEmployee(userId, name, role, phoneNb, origin, userEmail, userAddress, birthDate, password);
            // Handle other user types if needed
            default:
                throw new IllegalArgumentException("Unknown user role: " + role);
        }
    }


    public static List<Ticket> readTicketsFromCSV(String csvFilePath) throws IOException {
        List<Ticket> ticketsList = new ArrayList<>();
        File file = new File(csvFilePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length >= 6) { // Adjust the length based on your CSV structure
                    try {
                        Ticket ticket = createTicketFromCSV(values);
                        ticketsList.add(ticket);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return ticketsList;
    }

    private static Ticket createTicketFromCSV(String[] data) throws ParseException, IOException {
        // Extract data from CSV
        int ticketId = Integer.parseInt(data[0]);
        String ticketType = data[1];
        String baggageAllowance = data[2];
        String currentLocation = data[3];
        String destinationLocation = data[4];
        Date departDate = new SimpleDateFormat("dd/MM/yyyy").parse(data[5]);
        Date arrivalDate = new SimpleDateFormat("dd/MM/yyyy").parse(data[6]);

        // Create a Ticket object without associatedFlight and payment
        Ticket ticket = new Ticket(currentLocation, destinationLocation, null, ticketType, baggageAllowance, departDate, arrivalDate);

        // Since the associatedFlight is a static variable in the Ticket class, you can set it here
        ticket.associatedFlight = Ticket.readFlightInfoFromCsv(); // You need to implement this method in the Ticket class

        // Assuming you have a method to retrieve the payment information from a CSV file
        // Implement the readPaymentInfoFromCsv method in CsvReader
        Payment payment = readPaymentInfoFromCsv(data[7]); // Adjust the index based on your CSV structure

        // Set the payment for the ticket
        ticket.payment = payment;

        return ticket;
    }

    private static Payment readPaymentInfoFromCsv(String paymentId) throws IOException {
        String csvFilePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\payments.csv"; // Update with the correct path to your payments CSV file
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] paymentData = line.split(cvsSplitBy);

                // Assuming the payment ID is in the first column
                String storedPaymentId = paymentData[0].trim();

                if (storedPaymentId.equals(paymentId)) {
                    // Create a Payment object with the information from the CSV
                    int paymentIdValue = Integer.parseInt(paymentData[0].trim());
                    String paymentAmount = paymentData[1].trim();
                    Date paymentDate = new SimpleDateFormat("dd/MM/yyyy").parse(paymentData[2].trim());
                    int paymentCustomerId = Integer.parseInt(paymentData[3].trim());

                    return new Payment(paymentIdValue, paymentAmount, paymentDate, paymentCustomerId);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading payment data from file: " + e.getMessage());
            throw new IOException("Error reading payment data from file", e);
        }

        // Payment not found
        System.out.println("Payment not found for ID: " + paymentId);
        return null;
    }

}
