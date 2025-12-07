/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import property.backend.users.UserAccount;

/**
 *
 * @author krishi parekh
 */
public class Property implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    
    private String propertyId;
    private String address;
    private UserAccount owner; // Landlord
    private double price; // Can be either monthly rent or sale price
    private boolean forRent; // true for rent, false for sale
    private boolean available; // Whether the property is available
    
    // Default constructor
    public Property() {
        this.propertyId = generatePropertyId();
        this.available = true;
    }
    
    // Constructor with essential details
    public Property(String propertyId, String address, double price, boolean forRent) {
        this.propertyId = propertyId;
        this.address = address;
        this.price = price;
        this.forRent = forRent;
        this.available = true;
    }
    
    // Method to generate a unique property ID
    private String generatePropertyId() {
        // Simple implementation - in a real app, you'd use a more robust method
        return "PROP" + System.currentTimeMillis();
    }
    
    // Getters and setters
    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isForRent() {
        return forRent;
    }

    public void setForRent(boolean forRent) {
        this.forRent = forRent;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public String toString() {
        return propertyId + ": " + address;
    }
}