package Events;

import java.util.Date;

/**
 * Base event class. Holds the mandatory attributes of all the events
 */
public class Event {
    private String type;
    private String verb;
    private String key;
    private Date eventTime;

    public Event() {
    }

    public Event(String type, String verb, String key, Date eventTime) {
        this.type = type;
        this.verb = verb;
        this.key = key;
        this.eventTime = eventTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
}
