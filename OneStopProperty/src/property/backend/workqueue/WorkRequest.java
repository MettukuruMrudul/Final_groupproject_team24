/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.workqueue;

import java.io.Serializable;
import java.util.Date;
import property.backend.users.UserAccount;
import static property.backend.workqueue.WorkRequest.RequestStatus.PENDING;

/**
 *
 * @author sruth
 */
public abstract class WorkRequest implements Serializable {
    
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private String requestId;
    private String message;
    private UserAccount sender;
    private UserAccount receiver;
    private Date requestDate;
    private Date resolveDate;
    private RequestStatus status;
    private static int counter = 1;
    
    public enum RequestStatus {
        PENDING("Pending"),
        PROCESSING("Processing"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        COMPLETED("Completed"),
        READY_FOR_INSPECTION("Ready for Inspection"),
        INSPECTION_PASSED("Inspection Passed"),
        INSPECTION_FAILED("Inspection Failed"),
        PENDING_REPAIRS("Pending Repairs");
        
        private String value;
        
        private RequestStatus(String value) {
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
    
    public WorkRequest() {
        requestId = "REQ" + counter++;
        requestDate = new Date();
        status = RequestStatus.PENDING;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public UserAccount getSender() {
        return sender;
    }
    
    public void setSender(UserAccount sender) {
        this.sender = sender;
    }
    
    public UserAccount getReceiver() {
        return receiver;
    }
    
    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }
    
    public Date getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    
    public Date getResolveDate() {
        return resolveDate;
    }
    
    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }
    
    public RequestStatus getStatus() {
        return status;
    }
    
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return requestId;
    }
    
    public static RequestStatus fromValue(String text) {
        for (RequestStatus status : RequestStatus.values()) {
            if (status.getValue().equalsIgnoreCase(text)) {
                return status;
            }
        }
        return PENDING; // Default
    }
    
}
