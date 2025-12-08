/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.Serializable;
import property.backend.users.Role;

/**
 *
 * @author meghana
 */
public class Builder extends Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String builderName;
    private String address;
    private String phoneNumber;
    
    public Builder() {
        super(RoleType.BUILDER);
        this.builderName = "";
        this.address = "";
        this.phoneNumber = "";
    }
    
    public Builder(String builderName, String address, String phoneNumber) {
        super(RoleType.BUILDER);
        this.builderName = builderName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public String getBuilderName() {
        return builderName;
    }
    
    public void setBuilderName(String builderName) {
        this.builderName = builderName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    // Utility methods
    public boolean isValidBuilder() {
        return builderName != null && !builderName.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               phoneNumber != null && !phoneNumber.trim().isEmpty();
    }
    
    public String getFullDetails() {
        return "Builder: " + builderName + ", Address: " + address + ", Phone: " + phoneNumber;
    }
    
    @Override
    public String toString() {
        if (builderName != null && !builderName.isEmpty()) {
            return builderName + (phoneNumber != null && !phoneNumber.isEmpty() ? " (" + phoneNumber + ")" : "");
        }
        return "Builder";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Builder builder = (Builder) obj;
        
        // Handle null values properly
        boolean nameEquals = (builderName != null ? builderName.equals(builder.builderName) : builder.builderName == null);
        boolean phoneEquals = (phoneNumber != null ? phoneNumber.equals(builder.phoneNumber) : builder.phoneNumber == null);
        
        return nameEquals && phoneEquals;
    }
    
    @Override
    public int hashCode() {
        int result = builderName != null ? builderName.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}