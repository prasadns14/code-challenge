package Data;

import Events.*;
import org.joda.time.DateTime;
import org.joda.time.Weeks;
import java.util.*;

/**
 * Data pertaining to customers, orders, images uploaded and number of site visits
 * Assumption -
 * 1) Customer lifetime starts from the date he first registered. The date of the last event
 *    is the end date of all customers for LTV calculation (i.e. Number of weeks is taken as the joda difference
 *    between the customer's first visit and last event data)
 * 2) Order, ImageUpload or SiteVisit events are neglected if they appear before the Customer Event (for a new customer)
 *
 */
public class Data {
    HashMap<String, CustomerEvent> customers = new HashMap<String, CustomerEvent>();
    HashMap<String, OrderEvent> orders = new HashMap<String, OrderEvent>();
    HashMap<String, Set<String>> customerOrders = new HashMap<String, Set<String>>();
    HashMap<String, ImageUploadEvent> imageUploads = new HashMap<String, ImageUploadEvent>();
    HashMap<String, Set<String>> customerImageUploads = new HashMap<String, Set<String>>();
    HashMap<String, SiteVisitEvent> siteVisits = new HashMap<String, SiteVisitEvent>();
    HashMap<String, Set<String>> customerSiteVisits = new HashMap<String, Set<String>>();
    HashMap<String, Date> firstVisit = new HashMap<String, Date>();
    Date lastVisit = null;
    int t = 10; //Average customer lifespan

    public static class CustomerLTV {
        String customerID;
        double LTVScore;

        CustomerLTV(String customerID, double LTVScore) {
            this.customerID = customerID;
            this.LTVScore = LTVScore;
        }
    }

    /**
     *
     * @param e - Ingest event e
     */
    public void IngestData(Event e) {
        if(e instanceof CustomerEvent) {
            customers.put(e.getKey(), (CustomerEvent) e);
            firstVisit.put(e.getKey(), e.getEventTime());
            if(lastVisit == null) {
                lastVisit = e.getEventTime();
            } else {
                if(lastVisit.compareTo(e.getEventTime()) < 0) {
                    lastVisit = e.getEventTime();
                }
            }
        } else if(e instanceof OrderEvent && customers.containsKey(((OrderEvent) e).getCustomerID())) {
            if(!orders.containsKey(e.getKey())) {
                Set<String> myOrders;
                if (customerOrders.containsKey(((OrderEvent) e).getCustomerID())) {
                    myOrders = customerOrders.get(((OrderEvent) e).getCustomerID());
                } else {
                    myOrders = new HashSet<String>();
                }
                myOrders.add(e.getKey());
                customerOrders.put(((OrderEvent) e).getCustomerID(), myOrders);
            }
            orders.put(e.getKey(), (OrderEvent)e);
            if(lastVisit.compareTo(e.getEventTime()) < 0) {
                lastVisit = e.getEventTime();
            }
        } else if(e instanceof SiteVisitEvent && customers.containsKey(((SiteVisitEvent) e).getCustomerID())) {
            if(!siteVisits.containsKey(e.getKey())) {
                Set<String> myVisites;
                if (customerSiteVisits.containsKey(((SiteVisitEvent) e).getCustomerID())) {
                    myVisites = customerSiteVisits.get(((SiteVisitEvent) e).getCustomerID());
                } else {
                    myVisites = new HashSet<String>();
                }
                myVisites.add(e.getKey());
                customerSiteVisits.put(((SiteVisitEvent) e).getCustomerID(), myVisites);
            }
            siteVisits.put(e.getKey(), (SiteVisitEvent) e);
            if(lastVisit.compareTo(e.getEventTime()) < 0) {
                lastVisit = e.getEventTime();
            }
        } else if (e instanceof ImageUploadEvent && customers.containsKey(((ImageUploadEvent) e).getCustomerID())){
            if(!imageUploads.containsKey(e.getKey())) {
                Set<String> myUploads;
                if (customerImageUploads.containsKey(((ImageUploadEvent) e).getCustomerID())) {
                    myUploads = customerImageUploads.get(((ImageUploadEvent) e).getCustomerID());
                } else {
                    myUploads = new HashSet<String>();
                }
                myUploads.add(e.getKey());
                customerImageUploads.put(((ImageUploadEvent) e).getCustomerID(), myUploads);
            }
            imageUploads.put(e.getKey(), (ImageUploadEvent) e);
            if(lastVisit.compareTo(e.getEventTime()) < 0) {
                lastVisit = e.getEventTime();
            }
        }
    }

    /**
     *
     * @param x - Number of customers
     * @return - x Customer IDs
     */
    public List<String> TopXSimpleLTVCustomers(int x) {
        ArrayList<String> result = new ArrayList<String>();
        PriorityQueue<CustomerLTV> queue = new PriorityQueue<CustomerLTV>(x, new Comparator<CustomerLTV>() {
            public int compare(CustomerLTV o1, CustomerLTV o2) {
                return Double.compare(o2.LTVScore, o1.LTVScore);
            }
        });

        for(String custID : customers.keySet()) {
            int numSiteVisits;
            double totalOrder = 0;
            int numWeeks = Weeks.weeksBetween(new DateTime(firstVisit.get(custID)), new DateTime(lastVisit)).getWeeks();
            // Get the number of site visits
            if(customerSiteVisits.containsKey(custID)) {
                numSiteVisits = customerSiteVisits.get(custID).size();
            }
            if(customerOrders.containsKey(custID)) {
                Set<String> set = customerOrders.get(custID);
                for (String orderID : set) {
                    totalOrder += orders.get(orderID).getTotalAmount();
                }
            }
            if(numWeeks == 0) {
                numWeeks = 1;
            }
            queue.offer(new CustomerLTV(custID, 52 * (totalOrder/numWeeks) * t));
        }
        while(!queue.isEmpty() && x > 0) {
            result.add(queue.poll().customerID);
            x--;
        }
        return result;
    }
}
