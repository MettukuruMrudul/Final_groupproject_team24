/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.workqueue;

import java.io.Serializable;
import java.util.Date;
import property.backend.users.Property;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author nihar
 */
public class InspectionRequest extends WorkRequest implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private Property property;
    private InspectionType type;
    private Date preferredDate;
    private String inspectionReport;
    private boolean passed;
    
    public enum InspectionType {
        ROUTINE("Routine Inspection"),
        COMPLIANCE("Compliance Inspection"),
        DAMAGE("Damage Assessment"),
        PRE_PURCHASE("Pre-Purchase Inspection"),
        MOVE_IN("Move-In Inspection"),
        MOVE_OUT("Move-Out Inspection");
        
        private String value;
        
        private InspectionType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }
    
    public InspectionRequest() {
        super();
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public InspectionType getType() {
        return type;
    }
    
    public void setType(InspectionType type) {
        this.type = type;
    }
    
    public Date getPreferredDate() {
        return preferredDate;
    }
    
    public void setPreferredDate(Date preferredDate) {
        this.preferredDate = preferredDate;
    }
    
    public String getInspectionReport() {
        return inspectionReport;
    }
    
    public void setInspectionReport(String inspectionReport) {
        this.inspectionReport = inspectionReport;
    }
    
    public boolean isPassed() {
        return passed;
    }
    
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    
}
