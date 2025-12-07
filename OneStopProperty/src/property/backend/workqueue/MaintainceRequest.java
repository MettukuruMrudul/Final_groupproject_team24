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
public class MaintainceRequest extends WorkRequest implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private Property property;
    private String description;
    private MaintainceType type;
    private int priority; // 1-5, 5 being highest
    private Date scheduledDate;
    private String workerStatus;
    
    public enum MaintainceType {
        PLUMBING("Plumbing"),
        ELECTRICAL("Electrical"),
        HVAC("HVAC"),
        STRUCTURAL("Structural"),
        APPLIANCE("Appliance"),
        GENERAL("General");
        
        private String value;
        
        private MaintainceType(String value) {
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
    
    public String getWorkerStatus() {
    return workerStatus;
}

    public void setWorkerStatus(String workerStatus) {
    this.workerStatus = workerStatus;
}

    
    public MaintainceRequest() {
        super();
        priority = 3; // Default priority
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public MaintainceType getType() {
        return type;
    }
    
    public void setType(MaintainceType type) {
        this.type = type;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public Date getScheduledDate() {
        return scheduledDate;
    }
    
    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
    
    
    
}
