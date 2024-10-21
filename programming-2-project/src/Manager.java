import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Manager extends User {
    private List<User> users;

    public Manager() {
        this.users = new ArrayList<>();
    }

    public void removeEmployee(String employeeEmail) {
        String csvFilePath = "C:\\Users\\alihm\\OneDrive\\Documents\\users.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            List<String> updatedLines = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(cvsSplitBy);

                String userEmail = userData[5].trim(); // Column F


                String userRole = userData[2].trim(); // Column C

                if (!userEmail.equals(employeeEmail) || !userRole.equalsIgnoreCase("reservation employee")) {
                    updatedLines.add(line);
                } else {

                    System.out.println("Employee with email " + employeeEmail + " removed successfully.");
                }
            }
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(csvFilePath)))) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
            } catch (IOException e) {
                System.out.println("Error writing to user data file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error reading user data from file: " + e.getMessage());
        }
    }


    public List<String> searchUserByEmail(String userEmail) {
        String csvFilePath = "C:\\Users\\alihm\\IdeaProjects\\programming-2-projectfinal\\User.csv";
        String line;
        String cvsSplitBy = ",";
        List<String> userDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(cvsSplitBy);

                // Assuming the email is in column F (index 5)
                String storedEmail = userData[5].trim(); // Column F

                if (storedEmail.equalsIgnoreCase(userEmail)) {

                    Collections.addAll(userDataList, userData);
                    return userDataList;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data from file: " + e.getMessage());
        }

        return Collections.emptyList();
    }



    public void addUser(User newUser) {
        List<String> allLines = readCSV();
        if (allLines.isEmpty()) {
            allLines.add("UserId,Name,Role,PhoneNb,Origin,UserEmail,UserAddress,BirthDate,Password");
        }


        String userLine = String.join(",",
                generateUserId(newUser.getUserRole()),
                newUser.getName(),
                newUser.getUserRole(),
                newUser.getPhoneNb(),
                newUser.getOrigin(),
                newUser.getUserEmail(),
                newUser.getUserAddress(),
                newUser.getBirthDate(),
                newUser.getPassword()
        );

        allLines.add(userLine);

        writeCSV(allLines);
    }



    private List<String> readCSV() {
        List<String> allLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("User.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allLines;
    }

    public void removeCustomer(String customerEmail) {
        String csvFilePath = "C:\\Users\\alihm\\OneDrive\\Documents\\users.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            List<String> updatedLines = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(cvsSplitBy);

                // Assuming the email is in column F (index 5)
                String userEmail = userData[5].trim(); // Column F

                if (!userEmail.equals(customerEmail)) {
                    updatedLines.add(line);
                } else {
                    // Optionally, you can perform additional actions when a customer is found
                    // For example: Print a message or log the removal
                    System.out.println("Customer with email " + customerEmail + " removed successfully.");
                }
            }

            // Write the updated lines back to the CSV file
            try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
            } catch (IOException e) {
                System.out.println("Error writing to user data file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Error reading user data from file: " + e.getMessage());
        }
    }

    private void writeCSV(List<String> lines) {

        if (lines.isEmpty()) {
            lines.add("UserId,Name,Role,PhoneNb,Origin,UserEmail,UserAddress,BirthDate,Password");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("User.csv"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}