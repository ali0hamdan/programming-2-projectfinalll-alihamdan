import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Customer extends User {
    public String currentLocation;
    public String destinationLocation;
    public int dayStaying;
    public String flightReason;

    private Ticket ticket;

    public Customer(){}
    public Customer(String userId, String name, String userRole, String phoneNb, String origin, String userEmail,
                    String userAddress, String birthDate, String password) {
        super(userId, name, userRole, phoneNb, origin, userEmail, userAddress, birthDate, password);

    }


    public static void notifyTicketBooked(Ticket ticket) {
        System.out.println("Notification: Your ticket has been booked. Ticket details:\n" + ticket.toString());
    }




    @Override
    public String toString() {
        return "Customer{" +
                "currentLocation='" + currentLocation + '\'' +
                ", destinationLocation='" + destinationLocation + '\'' +
                ", dayStaying=" + dayStaying +
                ", flightReason='" + flightReason + '\'' +
                ", ticket=" + (ticket != null ? ticket.toString() : "null") +
                '}';
    }


}


