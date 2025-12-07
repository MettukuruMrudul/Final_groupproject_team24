/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.workqueue;

import java.io.Serializable;
import java.util.Date;
import property.backend.users.Property;
import property.backend.users.UserAccount;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author krishi parekh
 */
public class PropertyPurchaseRequest extends WorkRequest implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private Property property;
    private String requestId;
    private double price;
    private String requestType; // "Purchase" or "Rental"
    private Date requestDate;
    private String notes; // Additional notes or comments from buyer
    
    /**
     * Default constructor
     */
    public PropertyPurchaseRequest() {
        super();
        this.requestId = generateRequestId();
        this.requestDate = new Date();
    }
    
    /**
     * Constructor with essential details
     * @param sender The buyer submitting the request
     * @param receiver The property manager receiving the request
     * @param property The property being requested
     * @param price The offered/listed price
     * @param requestType Type of request ("Purchase" or "Rental")
     */
    public PropertyPurchaseRequest(UserAccount sender, UserAccount receiver, Property property, 
            double price, String requestType) {
        super();
        this.setSender(sender);
        this.setReceiver(receiver);
        this.property = property;
        this.price = price;
        this.requestType = requestType;
        this.requestId = generateRequestId();
        this.requestDate = new Date();
        this.setStatus(RequestStatus.PENDING);
    }
    
    /**
     * Generate a unique request ID
     * @return A unique request ID
     */
    private String generateRequestId() {
        return "PROP-REQ-" + System.currentTimeMillis();
    }
    
    // Getters and setters
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getRequestType() {
        return requestType;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    public Date getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return requestType + " Request #" + requestId + " for property " + 
                (property != null ? property.getPropertyId() : "Unknown");
    }

}
