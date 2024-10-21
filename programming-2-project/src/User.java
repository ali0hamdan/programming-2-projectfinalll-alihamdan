import java.util.*;

public abstract class User {
    private String userId;
    private String name;
    private String userRole="none";
    private String phoneNb;
    private String origin;
    private String userEmail;
    protected String password;
    private String userAddress;
    private String birthDate;
    private static List<String> existingUserIds = new ArrayList<>();

    public User() {
        this.userId = generateUserId(userRole);
    }

    public User(String userId, String name, String userRole, String phoneNb, String origin, String userEmail,
                String userAddress, String birthDate, String password) {
        setName(name);
        setUserRole(userRole);
        setUserId(userId);
        setPhoneNb(phoneNb);
        setOrigin(origin);
        setUserEmail(userEmail);
        setUserAddress(userAddress);
        setBirthDate(birthDate);
        setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password. Please follow the password criteria.");
        }
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 &&
                password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$");
    }

    public String getUserId() {
        return userId;
    }

    void setUserId(String userRole) {
        this.userId = generateUserId(userRole);
    }

    static String generateUserId(String userRole) {
        String prefix;
        switch (userRole.toLowerCase()) {
            case "customer":
                prefix = "441";
                break;
            case "reservation employee":
                prefix = "551";
                break;
            case "manager":
                prefix = "661";
                break;
            default:
                prefix = "000";
                break;
        }

        Random random = new Random();
        int randomDigits = 10000 + random.nextInt(90000);
        String generatedUserId = prefix + randomDigits;

        // Check if the generated ID already exists in the ArrayList
        while (existingUserIds.contains(generatedUserId)) {
            randomDigits = 10000 + random.nextInt(90000);
            generatedUserId = prefix + randomDigits;
        }

        existingUserIds.add(generatedUserId);
        return generatedUserId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        if (userRole != null && !userRole.trim().isEmpty()) {
            String roleLowerCase = userRole.toLowerCase();
            if (roleLowerCase.equals("manager") || roleLowerCase.equals("reservation employee") || roleLowerCase.equals("customer")) {
                this.userRole = userRole;
            } else {
                throw new IllegalArgumentException("Invalid user role. Allowed roles are Manager, Reservation Employee, or Customer.");
            }
        } else {
            throw new IllegalArgumentException("User role cannot be null or empty.");
        }
    }

    public String getPhoneNb() {
        return phoneNb;
    }

    public void setPhoneNb(String phoneNb) {
        if (isValidPhoneNumber(phoneNb)) {
            this.phoneNb = phoneNb;
        } else {
            throw new IllegalArgumentException("Invalid phone number. It must be 8 digits long and contain only numbers.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("(03|71|79|81|76|70)\\d{6}");
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        if (origin != null && !origin.trim().isEmpty() && isValidOrigin(origin)) {
            this.origin = origin;
        } else {
            throw new IllegalArgumentException("Invalid origin. Origin must not be null, empty, and should contain only letters.");
        }
    }

    private boolean isValidOrigin(String origin) {
        return origin.matches("[a-zA-Z]+");
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        if (userEmail == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        if (!isValidEmailFormat(userEmail)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.userEmail = userEmail;
    }

    private boolean isValidEmailFormat(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        if (userAddress != null && !userAddress.trim().isEmpty() && userAddress.length() >= 5) {
            this.userAddress = userAddress;
        } else {
            throw new IllegalArgumentException("Invalid user address. Address must not be null, empty, and should have at least 5 letters.");
        }
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        if (!isValidDateFormat(birthDate)) {
            throw new IllegalArgumentException("Invalid date format. Please use day/month/year.");
        }

        if (!isYearInRange(birthDate)) {
            throw new IllegalArgumentException("Birth year must be between 1920 and 2019.");
        }
        this.birthDate = birthDate;
    }

    private boolean isValidDateFormat(String date) {
        return date.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    }

    private boolean isYearInRange(String date) {
        int year = Integer.parseInt(date.split("/")[2]);
        return year >= 1920 && year <= 2019;
    }

    public String toString() {
        return "User:" +
                "\nuserId=" + userId +
                "\n name='" + name +
                "\n userRole='" + userRole +
                "\n phoneNb='" + phoneNb +
                "\n origin='" + origin +
                "\n userEmail='" + userEmail +
                "\n userAddress='" + userAddress +
                "\n birthDate='" + birthDate +
                "\n";
    }
}


