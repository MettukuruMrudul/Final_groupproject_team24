/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author uday
 */
public class Enterprise implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
   private String name;
    private EnterpriseType type;
    private ArrayList<Organization> organizationList;
    
    public Enterprise(String name, EnterpriseType type) {
        this.name = name;
        this.type = type;
        organizationList = new ArrayList<>();
    }
    
    public enum EnterpriseType {
        PROPERTY_MANAGEMENT("Property Management"),
        MAINTAINCE_SERVICE("Maintaince Service"),
        FINANCIAL_INSTITUTION("Financial Institution"),
        GOVERNMENT_HOUSING("Government Housing");
        
        private String value;
        
        private EnterpriseType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public void addOrganization(Organization organization) {
        organizationList.add(organization);
    }
    
    public Organization findOrganization(String name) {
        for (Organization organization : organizationList) {
            if (organization.getName().equals(name)) {
                return organization;
            }
        }
        return null;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public EnterpriseType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return name;
    } 
    
}
