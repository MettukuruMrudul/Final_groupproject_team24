/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.Serializable;
import property.backend.workqueue.WorkQueue;

/**
 *
 * @author sruth
 */
public abstract class Role implements Serializable {
     private static final long serialVersionUID = 1L;
    private RoleType roleType;
    private WorkQueue workQueue;
    
    // Add a serialVersionUID to maintain compatibility between versions
   
    public enum RoleType {
        ADMIN("System Administrator"),
        PROPERTY_MANAGER("Property Manager"),
        LANDLORD("Landlord"), 
        TENANT("Tenant"),
        MAINTAINCE_ADMIN("Maintaince Administrator"),
        MAINTAINCE_WORKER("Maintaince Worker"),
        BUYER("Buyer"),
        HOUSING_INSPECTOR("Housing Inspector"),
        //FINANCIAL_OFFICER("Financial Officer"),
        NETWORK_ADMIN("Network Administrator"),
        ENTERPRISE_ADMIN("Enterprise Administrator"),
        ORGANIZATION_ADMIN("Organization Administrator"),
        BUILDER("Builder"),
        BANK("Bank");
        
        private String value;
        
        private RoleType(String value) {
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
    
    public Role(RoleType roleType) {
        this.roleType = roleType;
        workQueue = new WorkQueue();
    }
    
    public RoleType getRoleType() {
        return roleType;
    }
    
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    
    public WorkQueue getWorkQueue() {
        return workQueue;
    }
    
    @Override
    public String toString() {
        return roleType.getValue();
    }
}
