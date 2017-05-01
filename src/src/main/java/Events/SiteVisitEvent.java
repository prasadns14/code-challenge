package Events;

import java.util.Date;
import java.util.HashMap;

/**
 * SiteVisitEvent - Extends the base class Event, it includes extra attributes specific to this event
 */
public class SiteVisitEvent extends Event {

    private String customerID;
    private HashMap<String, String> tags;

    public SiteVisitEvent(String type, String verb, String key, Date eventTime, String customerID, HashMap<String, String> tags) {
        super(type, verb, key, eventTime);
        this.customerID = customerID;
        this.tags = tags;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }
}
