/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.workqueue;

import java.io.Serializable;
import java.util.ArrayList;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author nihar
 */
public class WorkQueue implements Serializable {
    
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private ArrayList<WorkRequest> workRequestList;
    
    public WorkQueue() {
        workRequestList = new ArrayList<>();
    }
    
    public ArrayList<WorkRequest> getWorkRequestList() {
        return workRequestList;
    }
    
    public void addWorkRequest(WorkRequest workRequest) {
        workRequestList.add(workRequest);
    }
    
    public void removeWorkRequest(WorkRequest workRequest) {
        workRequestList.remove(workRequest);
    }
    
    public ArrayList<WorkRequest> getWorkRequestsForUser(String username) {
        ArrayList<WorkRequest> result = new ArrayList<>();
        for (WorkRequest request : workRequestList) {
            if (request.getReceiver() != null && 
                request.getReceiver().getUsername().equals(username)) {
                result.add(request);
            }
        }
        return result;
    }
    
}
