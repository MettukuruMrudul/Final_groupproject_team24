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
public class Bank extends Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String bankName;
    private String address;
    private String phoneNumber;
    
    public Bank() {
        super(RoleType.BANK);
        this.bankName = "";
        this.address = "";
        this.phoneNumber = "";
    }
    
    public Bank(String bankName, String address, String phoneNumber) {
         super(RoleType.BANK);
        this.bankName = bankName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
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
    public boolean isValidBank() {
        return bankName != null && !bankName.trim().isEmpty() &&
               address != null && !address.trim().isEmpty() &&
               phoneNumber != null && !phoneNumber.trim().isEmpty();
    }
    
    public String getFullDetails() {
        return "Bank: " + bankName + ", Address: " + address + ", Phone: " + phoneNumber;
    }
    
    @Override
    public String toString() {
        return bankName + " (" + phoneNumber + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Bank bank = (Bank) obj;
        return bankName.equals(bank.bankName) && 
               phoneNumber.equals(bank.phoneNumber);
    }
    
    @Override
    public int hashCode() {
        return bankName.hashCode() + phoneNumber.hashCode();
    }
    
}
