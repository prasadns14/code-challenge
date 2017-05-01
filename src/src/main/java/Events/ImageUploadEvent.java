package Events;

import java.util.Date;

/**
 * ImageUploadEvent - Extends the base class Event, it includes extra attributes specific to this event
 */
public class ImageUploadEvent extends Event {
    private String customerID;
    private String cameraMake;
    private String cameraModel;

    public ImageUploadEvent(String type, String verb, String key, Date eventTime, String customerID, String cameraMake, String cameraModel) {
        super(type, verb, key, eventTime);
        this.customerID = customerID;
        this.cameraMake = cameraMake;
        this.cameraModel = cameraModel;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCameraMake() {
        return cameraMake;
    }

    public void setCameraMake(String cameraMake) {
        this.cameraMake = cameraMake;
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }
}
