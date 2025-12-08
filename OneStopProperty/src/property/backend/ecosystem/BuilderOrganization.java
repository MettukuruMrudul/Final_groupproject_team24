/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.users.Builder;

/**
 *
 * @author meghana
 */
public class BuilderOrganization extends Organization implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    
    public BuilderOrganization(String name, Enterprise enterprise) {
        super(name, enterprise, OrganizationType.PROPERTY_FIRM);
    }
    
    @Override
    protected void addSupportedRoles() {
        addSupportedRole(new Builder());
    }
}