import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {







    //These codes for authentication
    private static boolean authenticateManager(String email, String password) {
        // Hardcoded manager credentials

        return authenticate(email, password, "manager");    }
    private static boolean authenticateCustomer(String email, String password) {
        return authenticate(email, password, "customer");
    }
    private static boolean authenticateEmployee(String email, String password) {
        return authenticate(email, password, "reservation employee");
    }
    private static boolean authenticate(String email, String password, String role) {
        String csvFile = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinalll-alihamdan-sharbelstephan\\User.csv";
        String line;
        String cvsSplitBy = ",";
        List<String[]> userDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(cvsSplitBy);
                userDataList.add(userData);
            }
        } catch (IOException e) {
            System.out.println("Error reading user data from file: " + e.getMessage());
            return false;
        }

        for (String[] userData : userDataList) {
            // Assuming columns B (1), C (2), and I (8) for email, role, and password
            if (userData.length >= 9) {
                String userEmail = userData[5].trim(); // Column F (5)
                String storedRole = userData[2].trim(); // Column C (2)
                String storedPassword = userData[8].trim(); // Column I (8)

                if (email.equalsIgnoreCase(userEmail) && password.equals(storedPassword) && role.equalsIgnoreCase(storedRole)) {
                    // Authentication successful for email, password, and role
                    return true;
                }
            }
        }

        // Authentication failed for email, password, and/or role
        return false;
    }



    public static Flight readFlightFromInput(Scanner scanner) {
        System.out.print("Enter the company name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter the number of available seats: ");
        int availableSeats = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the flight time (HH:mm): ");
        String flightTime = scanner.nextLine();

        System.out.print("Enter the arriving time (HH:mm): ");
        String arrivingTime = scanner.nextLine();



        // Create a new Flight object using the constructor
        Flight flight = new Flight(companyName, availableSeats, true, flightTime, arrivingTime);  // Assuming the last argument is the flight number

        // Set the flight number using the set method
        flight.setFlightNb();

        // Return the created Flight object
        return flight;
    }

    // Add this function to your CsvWriter class or a utility class
    public static Flight validateFlightInput(Scanner scanner) {
        System.out.println("Enter Flight Details:");

        String companyName;
        // Validate Company Name (only letters)
        do {
            System.out.print("Company Name: ");
            companyName = scanner.nextLine();
        } while (!isValidCompanyName(companyName));

        // Validate Available Seats
        int availableSeats;
        do {
            availableSeats = validateIntegerInput("Available Seats: ", "Invalid input. Please enter a valid integer for Available Seats.", 150, 200);
        } while (!isValidAvailableSeats(availableSeats));

        // Validate Flight Time
        String flightTime;
        do {
            flightTime = validateTimeInput("Flight Time (HH:mm): ", "Invalid flight time. Please enter a valid time in HH:mm format.");
        } while (!Flight.isValidFlightTime(flightTime, null));

        // Validate Arriving Time
        String arrivingTime;
        do {
            arrivingTime = validateTimeInput("Arriving Time (HH:mm): ", "Invalid arriving time. Please enter a valid time in HH:mm format.");
        } while (!Flight.isValidFlightTime(null, arrivingTime));

        // Validate Flight Phone Number


        // Set the availability logic according to your requirements
        boolean availableFlight = true;

        // Create and return a new Flight object
        return new Flight(companyName, availableSeats, availableFlight, flightTime, arrivingTime);
    }

    private static int validateIntegerInput(String prompt, String errorMessage, int minValue, int maxValue) {
        Scanner scanner = new Scanner(System.in);
        int userInput;

        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println(errorMessage);
                scanner.next(); // Consume the invalid input
            }
            userInput = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()
        } while (userInput < minValue || userInput > maxValue);

        return userInput;
    }

    private static String validateTimeInput(String prompt, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        String userInput;

        do {
            System.out.print(prompt);
            userInput = scanner.nextLine();
            if (!Flight.isValidFlightTime(userInput, null)) {
                System.out.println(errorMessage);
            }
        } while (!Flight.isValidFlightTime(userInput, null));

        return userInput;
    }

    private static String validatePhoneNumberInput(String prompt, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        String userInput;

        do {
            System.out.print(prompt);
            userInput = scanner.nextLine();
            if (!Flight.isValidPhoneNb(userInput)) {
                System.out.println(errorMessage);
            }
        } while (!Flight.isValidPhoneNb(userInput));

        return userInput;
    }

    private static boolean isValidCompanyName(String companyName) {
        return companyName != null && companyName.matches("[a-zA-Z]+");
    }

    private static boolean isValidAvailableSeats(int availableSeats) {
        return availableSeats >= 150 && availableSeats <= 200;
    }


    private static int generateRandomThreeDigitNumber() {
        Random random = new Random();
        return random.nextInt(900) + 100; // Generates a random number between 100 and 999 (inclusive)
    }



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);
        int choice,choice1;
        String email, password,role;
        Customer customer = new Customer();
        Manager m=new Manager();


        try {
            do {
                System.out.println("Please select your role:\n1. Customer\n2. Reservation Employee\n3. Manager\n4. Exit");
                choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter your email: ");
                        email = sc.nextLine();
                        System.out.print("Enter your password: ");
                        password = sc.nextLine();
                        if (authenticateCustomer(email, password)) {
                            System.out.println("Authentication successful. User is a customer.");
                            do{
                                System.out.print("1.View my ticket \n2.book a ticket \n3.View flights \n4.Exit");
                                choice1 = sc.nextInt();
                                switch (choice1) {
                                    case 1:
                                        Scanner scc = new Scanner(System.in);
                                        System.out.print("Enter your ticket ID to view: ");
                                        String ticketIdToSearch = scc.nextLine();
                                        Ticket.searchTicketById(ticketIdToSearch);
                                        break;
                                    case 2:
                                        Ticket.addTicket();
                                        break;
                                    case 3:
                                        Flight.showAllFlights();
                                        break;
                                    case 4:
                                        System.out.println("Exiting...");
                                        break;
                                    default:
                                        System.out.print("Theres no such an option");
                                }
                            }while(choice1!=4);
                        } else {
                            System.out.println("Authentication failed or user does not have the required role.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter your email: ");
                        email = sc.nextLine();
                        System.out.print("Enter your password: ");
                        password = sc.nextLine();
                        if (authenticateEmployee(email, password)) {
                            System.out.println("Authentication successful. User is a reservation employee.");
                            do {
                                System.out.print("1.View ticket by id\n2.View flights\n3.Book a ticket\n" +
                                        "4.Search Customer\n5.Add customer\n6.Exit\n");
                                choice1 = sc.nextInt();
                                switch (choice1) {
                                    case 1:
                                        System.out.print("Enter the ticket ID to search: ");
                                        String ticketIdToSearch = sc.nextLine().trim();
                                        Ticket.searchTicketById(ticketIdToSearch);
                                        break;
                                    case 2:
                                        Flight.showAllFlights();
                                        break;
                                    case 3:
                                        Ticket.addTicket();
                                        break;
                                    case 4:
                                        System.out.print("Enter the email of the user to search: ");
                                        sc.nextLine(); // Consume the newline character
                                        String userEmailToSearch = sc.nextLine();
                                        List<String> userData = manager.searchUserByEmail(userEmailToSearch);
                                        if (userData != null) {
                                            System.out.println("User found. Details:");
                                            for (String data : userData) {
                                                System.out.println(data);
                                            }
                                        } else {
                                            System.out.println("User not found.");
                                        }
                                        break;
                                    case 5:
                                        Customer newCustomer = CsvWriter.readCustomerFromInput(scanner);
                                        manager.addUser(newCustomer);
                                        System.out.print("Customer added successfully\n");
                                        break;
                                    case 6:
                                        System.out.println("Exiting...");
                                        break;
                                    default:
                                        System.out.print("Theres no such an option");
                                        break;
                                }
                            } while (choice1 !=6 );
                        } else {
                            System.out.println("Authentication failed or user does not have the required role.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter your email: ");
                        email = sc.nextLine();
                        System.out.print("Enter your password: ");
                        password = sc.nextLine();
                        if (authenticateManager(email, password)) {
                            System.out.println("Authentication successful. User is a manager.");
                            do {
                                System.out.print("\n1.View ticket by id\n2.View flights\n3.Book a ticket\n" +
                                        "4.Search User\n5.Add Customer\n6.Add Employee\n7.Remove Employee\n8.Remove Customer" +
                                        "\n9.Add Flight\n10.Remove Flightl\n11.Exit\n");
                                choice1 = sc.nextInt();
                                sc.nextLine();
                                switch (choice1) {
                                    case 1:
                                        System.out.print("Enter the ticket ID to search: ");
                                        String ticketIdToSearch = sc.nextLine().trim();
                                        Ticket.searchTicketById(ticketIdToSearch);
                                        break;
                                    case 2:
                                        Flight.showAllFlights();
                                        break;
                                    case 3:
                                        Ticket.addTicket();
                                        break;
                                    case 4:
                                        System.out.print("Enter the email of the user to search: ");
                                        String userEmailToSearch = sc.nextLine().trim();  // Remove the extra sc.nextLine() that consumes the newline character
                                        List<String> userData = manager.searchUserByEmail(userEmailToSearch);
                                        if (userData != null && !userData.isEmpty()) {
                                            System.out.println("User found. Details:");
                                            for (String data : userData) {
                                                System.out.println(data);
                                            }
                                        } else {
                                            System.out.println("User not found.");
                                        }
                                        break;
                                    case 5:
                                        Customer newCustomer = CsvWriter.readCustomerFromInput(scanner);
                                        manager.addUser(newCustomer);
                                        System.out.print("Customer added successfully\n");
                                        break;
                                    case 6:
                                        ReservationEmployee newEmployee = CsvWriter.readEmployeeFromInput(scanner);
                                        manager.addUser(newEmployee);
                                        System.out.print("Employee added successfully\n");
                                        break;
                                    case 7:
                                        System.out.print("Enter the email of the employee to remove: ");
                                        String employeeEmailToRemove = sc.nextLine();
                                        manager.removeEmployee(employeeEmailToRemove);
                                        break;
                                    case 8:
                                        System.out.print("Enter the email of the customer to remove: ");
                                        String customerEmailToRemove = sc.nextLine();
                                        manager.removeCustomer(customerEmailToRemove);
                                        break;
                                    case 9:
                                        // Input for adding a new flight
                                        Flight newFlight = validateFlightInput(scanner);
                                        Flight.addFlight(newFlight);
                                        System.out.println(newFlight);
                                        CsvWriter.writeFlightToCsv(newFlight);
                                        System.out.println("Flight added successfully.");
                                        break;
                                    case 10:
                                        Flight.removeFlight();
                                        break;
                                    case 11:
                                        System.out.println("Exiting...");
                                        break;
                                    default:
                                        System.out.print("Theres no such an option");
                                        break;
                                }
                            } while (choice1 != 11);
                        } else {
                            System.out.println("Authentication failed or user does not have the required role.");
                        }
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("There is no such option.");
                        break;
                }
            } while (choice != 4);
            System.out.println("Thanks for choosing our company");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            sc.close(); // Close the Scanner
        }

    }

}