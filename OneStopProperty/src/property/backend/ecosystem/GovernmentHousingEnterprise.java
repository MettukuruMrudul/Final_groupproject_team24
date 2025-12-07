/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import property.backend.ecosystem.Enterprise;

/**
 *
 * @author nihar
 */
public class GovernmentHousingEnterprise extends Enterprise implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
  public GovernmentHousingEnterprise(String name) {
        super(name, EnterpriseType.GOVERNMENT_HOUSING);  
}
}
