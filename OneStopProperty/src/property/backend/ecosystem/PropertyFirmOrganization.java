/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import java.util.ArrayList;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.users.Landlord;
import property.backend.users.Property;
import property.backend.users.PropertyManager;
import property.backend.users.Tenant;

/**
 *
 * @author krishi parekh
 */
public class PropertyFirmOrganization extends Organization implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
     private ArrayList<Property> properties;
     
    public PropertyFirmOrganization(String name, Enterprise enterprise) {
        super(name, enterprise, OrganizationType.PROPERTY_FIRM);
        properties = new ArrayList<>();
    }
    // Method to add a property
    public void addProperty(Property property) {
        properties.add(property);
    }
    
    // Method to remove a property
    public void removeProperty(Property property) {
        if (properties != null) {
            properties.removeIf(p -> p.getPropertyId().equals(property.getPropertyId()));
        }
    }
    
    public void updateProperty(Property property) {
        if (properties != null) {
            for (int i = 0; i < properties.size(); i++) {
                Property p = properties.get(i);
                if (p.getPropertyId().equals(property.getPropertyId())) {
                    properties.set(i, property);
                    break;
                }
            }
        }
    }
    
    // Method to find a property by house number (property ID)
    public Property findPropertyByHouseNumber(String houseNumber) {
        for (Property property : properties) {
            // Check if the property ID matches the house number
            if (property.getPropertyId().equals(houseNumber)) {
                return property;
            }
            
            // Also check if the address contains the house number
            if (property.getAddress() != null && property.getAddress().contains(houseNumber)) {
                return property;
            }
        }
        return null; // Property not found
    }
    
    // Method to get all properties
    public ArrayList<Property> getProperties() {
        return properties;
    }
    
    // Method to set properties
    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }
    
    
    @Override
    protected void addSupportedRoles() {
        addSupportedRole(new Landlord());
        addSupportedRole(new PropertyManager());
        addSupportedRole(new Tenant());
    }
}
