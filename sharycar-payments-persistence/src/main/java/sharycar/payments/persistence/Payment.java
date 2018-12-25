package sharycar.payments.persistence;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
@NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;


    private Double price;

    private String currency;

    private Integer reservationId; // This field is used if payment is connected to reservation

    private Integer orderId; // this field is used if payment is connected to order

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionTime; // Time of transaction - when

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }



}
