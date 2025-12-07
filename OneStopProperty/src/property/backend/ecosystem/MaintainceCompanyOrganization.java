/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.users.MaintainceAdmin;
import property.backend.users.MaintainceWorker;

/**
 *
 * @author nihar
 */
public class MaintainceCompanyOrganization extends Organization implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
  public MaintainceCompanyOrganization(String name, Enterprise enterprise) {
        super(name, enterprise, OrganizationType.MAINTAINCE_COMPANY);
    }
    
    @Override
    protected void addSupportedRoles() {
        addSupportedRole(new MaintainceAdmin());
        addSupportedRole(new MaintainceWorker());
    }   
}
