/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import java.util.ArrayList;
import property.backend.users.Role;

import property.backend.users.UserAccount;
import property.backend.workqueue.WorkQueue;

/**
 *
 * @author sruth
 */
public abstract class Organization implements Serializable {
   private String name;
    private OrganizationType type;

    
     private WorkQueue workQueue; 
    private Enterprise enterprise;
    private ArrayList<UserAccount> userAccountList;
    private ArrayList<Role> supportedRoles;
    
    public Organization(String name, Enterprise enterprise, OrganizationType type) {
        this.name = name;
        this.enterprise = enterprise;
        this.type = type;
        userAccountList = new ArrayList<>();
        supportedRoles = new ArrayList<>();
        addSupportedRoles();
    }
    
    public enum OrganizationType {
        PROPERTY_FIRM("Property Firm"),
        MAINTAINCE_COMPANY("Maintaince Company"),
        LENDING_ORGANIZATION("Lending Organization"),
        INSPECTION_AGENCY("Inspection Agency"),
        INSURANCE_COMPANY("Insurance Company"),
        LEGAL_SERVICES("Legal Services"),
        BUILDER_ORGANIZATION("Builder Organization"),      // Add this
        BANKING_ORGANIZATION("Banking Organization"); 
         
        private String value;
        
        private OrganizationType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    protected abstract void addSupportedRoles();
    
    public UserAccount createUserAccount(String username, String password, Role role) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(password);
        userAccount.setRole(role);
        userAccount.setOrganization(this);
        userAccountList.add(userAccount);
        return userAccount;
    }
    
    public boolean isRoleSupported(Role role) {
        for (Role supportedRole : supportedRoles) {
            if (supportedRole.getRoleType().equals(role.getRoleType())) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Role> getSupportedRoles() {
        return supportedRoles;
    }
    
    public void addSupportedRole(Role role) {
        supportedRoles.add(role);
    }
    
    public ArrayList<UserAccount> getUserAccountList() {
        return userAccountList;
    }
    
    public void addUserAccount(UserAccount userAccount) {
        userAccountList.add(userAccount);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Enterprise getEnterprise() {
        return enterprise;
    }
    
    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
    
    public OrganizationType getType() {
        return type;
    }
    
    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
    
    @Override
    public String toString() {
        return name;
    }  
    
}
