package Events;

import java.util.Date;

/**
 * CustomerEvent - Extends the base class Event, it includes extra attributes specific to this event
 */
public class CustomerEvent extends Event {

    private String lastName;
    private String adrCity;
    private String adrState;

    public CustomerEvent(String type, String verb, String key, Date eventTime, String lastName, String adrCity, String adrState) {
        super(type, verb, key, eventTime);
        this.lastName = lastName;
        this.adrCity = adrCity;
        this.adrState = adrState;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdrCity() {
        return adrCity;
    }

    public void setAdrCity(String adrCity) {
        this.adrCity = adrCity;
    }

    public String getAdrState() {
        return adrState;
    }

    public void setAdrState(String adrState) {
        this.adrState = adrState;
    }
}
