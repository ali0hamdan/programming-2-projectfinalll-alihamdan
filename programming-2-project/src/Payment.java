import java.util.Date;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Payment {
    private int paymentId;
    private String paymentAmount;
    private Date paymentDate;
    private int paymentCustomerId;


    public Payment(int paymentId, String paymentAmount, Date paymentDate, int paymentCustomerId) {
        this.paymentId = paymentId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentCustomerId = paymentCustomerId;
    }

    public static String PaymentAmount(String ticketType) {
        // Set payment amount based on ticket type
        if ("firstclass".equalsIgnoreCase(ticketType)) {
            return "8000";
        } else if ("business".equalsIgnoreCase(ticketType)) {
            return "6000";
        } else if ("economy".equalsIgnoreCase(ticketType)) {
            return "2000";
        } else {
            throw new IllegalArgumentException("Invalid ticket type");
        }
    }

    public int getPaymentId() {
        return paymentId;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }


    public int getPaymentCustomerId() {
        return paymentCustomerId;
    }

}

