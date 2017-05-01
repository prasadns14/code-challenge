package Events;

import java.util.Date;

/**
 * OrderEvent - Extends the base class Event, it includes extra attributes specific to this event
 */
public class OrderEvent extends Event {
    private String customerID;
    private Double totalAmount;

    public OrderEvent(String type, String verb, String key, Date eventTime, String customerID, Double totalAmount) {
        super(type, verb, key, eventTime);
        this.customerID = customerID;
        this.totalAmount = totalAmount;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
