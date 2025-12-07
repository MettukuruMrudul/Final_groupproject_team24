/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.workqueue;

import java.io.Serializable;
import property.backend.users.Property;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author krishi parekh 
 */
public class RentalApplicationRequest extends WorkRequest implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private Property property;
    private String applicantIncome;
    private String employmentInfo;
    private String rentalHistory;
    private String references;
    private int leaseTerm; // in months
    
    public RentalApplicationRequest() {
        super();
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public String getApplicantIncome() {
        return applicantIncome;
    }
    
    public void setApplicantIncome(String applicantIncome) {
        this.applicantIncome = applicantIncome;
    }
    
    public String getEmploymentInfo() {
        return employmentInfo;
    }
    
    public void setEmploymentInfo(String employmentInfo) {
        this.employmentInfo = employmentInfo;
    }
    
    public String getRentalHistory() {
        return rentalHistory;
    }
    
    public void setRentalHistory(String rentalHistory) {
        this.rentalHistory = rentalHistory;
    }
    
    public String getReferences() {
        return references;
    }
    
    public void setReferences(String references) {
        this.references = references;
    }
    
    public int getLeaseTerm() {
        return leaseTerm;
    }
    
    public void setLeaseTerm(int leaseTerm) {
        this.leaseTerm = leaseTerm;
    }
    
}
