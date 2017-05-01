package JSONParser;

import Events.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class ParseJSON {

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");

    private CustomerEvent parseCustomerEvent(JSONObject jsonObject) {
        String type = "CUSTOMER";
        String verb;
        String key;
        Date eventTime = null;
        String lastName = "";
        String adrCity = "";
        String adrState = "";

        if(jsonObject.has("verb") && jsonObject.has("key") && jsonObject.has("event_time")) {
            verb = jsonObject.getString("verb");
            key = jsonObject.getString("key");
            try {
                eventTime = format.parse(jsonObject.getString("event_time"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(jsonObject.has("last_name")) {
                lastName = jsonObject.getString("last_name");
            }
            if(jsonObject.has("adr_city")) {
                adrCity = jsonObject.getString("adr_city");
            }
            if(jsonObject.has("adr_state")) {
                adrState = jsonObject.getString("adr_state");
            }
            CustomerEvent e = new CustomerEvent(type, verb, key, eventTime, lastName, adrCity, adrState);
            return e;
        }
        return null;
    }

    private ImageUploadEvent parseImageUploadEvent(JSONObject jsonObject) {
        String type = "IMAGE";
        String verb;
        String key;
        Date eventTime = null;
        String customerID;
        String cameraMake = "";
        String cameraModel = "";

        if(jsonObject.has("verb") && jsonObject.has("key") && jsonObject.has("event_time") && jsonObject.has("customer_id")) {
            verb = jsonObject.getString("verb");
            key = jsonObject.getString("key");
            try {
                eventTime = format.parse(jsonObject.getString("event_time"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            customerID = jsonObject.getString("customer_id");
            if(jsonObject.has("camera_make")) {
                cameraMake = jsonObject.getString("camera_make");
            }
            if(jsonObject.has("camera_model")) {
                cameraModel = jsonObject.getString("camera_model");
            }
            ImageUploadEvent e = new ImageUploadEvent(type, verb, key, eventTime, customerID, cameraMake, cameraModel);
            return e;
        }
        return null;
    }

    private OrderEvent parseOrderEvent(JSONObject jsonObject) {
        String type = "ORDER";
        String verb;
        String key;
        Date eventTime = null;
        String customerID;
        double totalAmount;

        if(jsonObject.has("verb") && jsonObject.has("key") && jsonObject.has("event_time") && jsonObject.has("customer_id") && jsonObject.has("total_amount")) {
            verb = jsonObject.getString("verb");
            key = jsonObject.getString("key");
            try {
                eventTime = format.parse(jsonObject.getString("event_time"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            customerID = jsonObject.getString("customer_id");
            totalAmount = Double.parseDouble(jsonObject.getString("total_amount").split(" ")[0]);
            OrderEvent e = new OrderEvent(type, verb, key, eventTime, customerID, totalAmount);
            return e;
        }
        return null;
    }

    private SiteVisitEvent parseSiteVisitEvent(JSONObject jsonObject) {
        String type = "SITE_VISIT";
        String verb;
        String key;
        Date eventTime = null;
        String customerID = "";
        HashMap<String, String> tags = new HashMap<String, String>();

        if(jsonObject.has("verb") && jsonObject.has("key") && jsonObject.has("event_time") && jsonObject.has("customer_id")) {
            verb = jsonObject.getString("verb");
            key = jsonObject.getString("key");
            try {
                eventTime = format.parse(jsonObject.getString("event_time"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            customerID = jsonObject.getString("customer_id");
            if(jsonObject.has("tags")) {
                JSONArray jTags = jsonObject.getJSONArray("tags");
                for(int i = 0; i < jTags.length(); i++) {
                    JSONObject tag = jTags.getJSONObject(i);
                    for(String tag1 : (Set<String>) tag.keySet()) {
                        tags.put(tag1, tag.getString(tag1));
                    }
                }
            }
            SiteVisitEvent e = new SiteVisitEvent(type, verb, key, eventTime, customerID, tags);
            return e;
        }
        return null;
    }

    public Event parseJson(JSONObject jsonObject) {
        if(jsonObject.has("type")) {
            String type = jsonObject.getString("type");
            if(type.equals("CUSTOMER")) {
                return parseCustomerEvent(jsonObject);
            } else if(type.equals("SITE_VISIT")) {
                return parseSiteVisitEvent(jsonObject);
            } else if(type.equals("IMAGE")) {
                return parseImageUploadEvent(jsonObject);
            } else if (type.equals("ORDER")){
                return parseOrderEvent(jsonObject);
            }
        }
        return null;
    }
}
