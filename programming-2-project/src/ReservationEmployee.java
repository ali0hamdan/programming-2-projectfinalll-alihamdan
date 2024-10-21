import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationEmployee extends User {
    private int employeeId;

    public ReservationEmployee( String userId, String name, String userRole, String phoneNb, String origin, String userEmail,
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


}
