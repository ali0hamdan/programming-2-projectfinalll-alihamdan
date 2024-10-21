import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CsvWriter {
    static Scanner sc=new Scanner(System.in);
    public static void writeDataToCsv(List<String[]> data) throws IOException {
        FileWriter csvWriter = new FileWriter("C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\airline.csv");


        // Write data starting from the second row, first column
        for (String[] row : data) {
            csvWriter.append(",");
            csvWriter.append(String.join(",", row));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
    public static void writeUsersToCsv(User[] users) throws IOException {
        FileWriter csvWriter = new FileWriter("C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\users.csv");

        // Write header
        csvWriter.append("UserId,UserRole,UserName,UserphoneNb,UserEmail,UserAddress,password");
        csvWriter.append("\n");

        // Write data
        for (User user : users) {
            csvWriter.append(String.valueOf(user.getUserId()));
            csvWriter.append(",");
            csvWriter.append(user.getUserRole());
            csvWriter.append(",");
            csvWriter.append(user.getName());
            csvWriter.append(",");
            csvWriter.append(user.getPhoneNb());
            csvWriter.append(",");
            csvWriter.append(user.getUserEmail());
            csvWriter.append(",");
            csvWriter.append(user.getUserAddress());
            csvWriter.append(",");
            csvWriter.append(user.getPassword());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
    public static void writeTicketsToCsv(List<Ticket> tickets) throws IOException {
        FileWriter csvWriter = new FileWriter("C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\tickets.csv", true); // Use append mode

        // Iterate through tickets and append each ticket to CSV
        for (Ticket ticket : tickets) {
            csvWriter.append(ticketToCsvString(ticket));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    private static String ticketToCsvString(Ticket ticket) {
        // Convert ticket information to CSV format
        StringBuilder csvString = new StringBuilder();

        if (ticket != null) {
            // Handle Ticket ID
            csvString.append(ticket.getTicketId()).append(",");

            // Handle other fields with commas
            csvString.append(ticket.getTicketType()).append(",")
                    .append(ticket.getBaggageAllowance()).append(",")
                    .append(formatDate(ticket.getDepartDate())).append(",")
                    .append(formatDate(ticket.getArrivalDate())).append(",")
                    .append(ticket.getCurrentLocation()).append(",")
                    .append(ticket.getDestinationLocation()).append(",");
        }
        return csvString.toString();
    }




    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static Customer readCustomerFromInput(Scanner scanner) {
        System.out.println("Enter customer details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number (8 digits): ");
        String phoneNb = scanner.nextLine();
        while (!isValidPhoneNumber(phoneNb)) {
            System.out.print("Invalid phone number. Please enter a valid 8-digit number and i should start with(71|03|76|78|79): ");
            phoneNb = scanner.nextLine();
        }

        System.out.print("Origin: ");
        String origin = scanner.nextLine();

        System.out.print("Email: ");
        String userEmail = scanner.nextLine();
        while (!isValidEmailFormat(userEmail)) {
            System.out.print("Invalid email format. Please enter a valid email: ");
            userEmail = scanner.nextLine();
        }

        System.out.print("Address (at least 5 characters): ");
        String userAddress = scanner.nextLine();
        while (userAddress.length() < 5) {
            System.out.print("Invalid address. Please enter at least 5 characters: ");
            userAddress = scanner.nextLine();
        }

        System.out.print("Birth Date (DD/MM/YYYY): ");
        String birthDate = scanner.nextLine();
        while (!isValidDateFormat(birthDate) || !isYearInRange(birthDate)) {
            System.out.print("Invalid date format or year out of range. Please use day/month/year: ");
            birthDate = scanner.nextLine();
        }

        System.out.print("Password (at least 8 characters, including letters, numbers, and special characters): ");
        String password = scanner.nextLine();
        while (!isValidPassword(password)) {
            System.out.print("Invalid password. Please follow the password criteria: ");
            password = scanner.nextLine();
        }
        System.out.println("Customer added successfully");
        return new Customer("1000", name, "customer", phoneNb, origin, userEmail, userAddress, birthDate, password);
    }
    public static ReservationEmployee readEmployeeFromInput(Scanner scanner) {
        System.out.println("Enter employee details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number (8 digits): ");
        String phoneNb = scanner.nextLine();
        while (!isValidPhoneNumber(phoneNb)) {
            System.out.print("Invalid phone number. Please enter a valid 8-digit number: ");
            phoneNb = scanner.nextLine();
        }

        System.out.print("Origin: ");

        String origin;
        do {
            System.out.print("Origin: ");
            origin = scanner.nextLine();

            if (isValidOrigin(origin)) {
                System.out.println("Valid origin: " + origin);
            } else {
                System.out.println("Invalid origin. Origin must not be null, empty, and should contain only letters. Input: " + origin);
            }

        } while (!isValidOrigin(origin));

        System.out.print("Email: ");
        String userEmail = scanner.nextLine();
        while (!isValidEmailFormat(userEmail)) {
            System.out.print("Invalid email format. Please enter a valid email: ");
            userEmail = scanner.nextLine();
        }


        System.out.print("Address (at least 5 characters): ");
        String userAddress = scanner.nextLine();
        while (userAddress.length() < 5) {
            System.out.print("Invalid address. Please enter at least 5 characters: ");
            userAddress = scanner.nextLine();
        }


        System.out.print("Birth Date (DD/MM/YYYY): ");
        String birthDate = scanner.nextLine();
        while (!isValidDateFormat(birthDate) || !isYearInRange(birthDate)) {
            System.out.print("Invalid date format or year out of range. Please use day/month/year: ");
            birthDate = scanner.nextLine();
        }


        System.out.print("Password (at least 8 characters, including letters, numbers, and special characters): ");
        String password = scanner.nextLine();
        while (!isValidPassword(password)) {
            System.out.print("Invalid password. Please follow the password criteria: ");
            password = scanner.nextLine();
        }
        System.out.println("Employee added successfully");
        return new ReservationEmployee("1000", name, "reservation employee", phoneNb, origin, userEmail, userAddress, birthDate, password);
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Check if phoneNumber is not null, is 8 digits long, and starts with one of the Lebanese mobile prefixes
        return phoneNumber != null && phoneNumber.matches("(71|03|76|78|79)\\d{6}");
    }


    public static boolean isValidEmailFormat(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


    public static boolean isValidDateFormat(String date) {
        return date.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    }


    public static boolean isYearInRange(String date) {
        int year = Integer.parseInt(date.split("/")[2]);
        return year >= 1920 && year <= 2019;
    }

    private static boolean isValidOrigin(String origin) {
        // Check if origin is not null, not empty, and contains only letters
        return origin != null && !origin.trim().isEmpty() && origin.matches("[a-zA-Z]+");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 &&
                password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$");
    }



    public static void writeFlightToCsv(Flight flight) {
        String filePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\Flight.csv";

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(flightToCsvString(flight));
            writer.append("\n");
            writer.flush(); // Flush to ensure the content is written immediately
            System.out.println("Flight data written to CSV successfully.");
        } catch (IOException e) {
            System.out.println("Error writing flight data to CSV: " + e.getMessage());
        }
    }


    public static String flightToCsvString(Flight flight) {
        // Adapt the following line based on your Flight class fields
        return String.format("%s,%d,%s,%s,%d",
                flight.getCompanyName(),
                flight.getAvailableSeats(),
                flight.getFlightTime(),
                flight.getArrivingTime(),
                flight.getFlightNb());
    }





}