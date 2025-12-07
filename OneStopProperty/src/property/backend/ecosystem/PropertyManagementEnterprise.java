/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import property.backend.ecosystem.Enterprise;

/**
 *
 * @author krishi parekh
 */
public class PropertyManagementEnterprise extends Enterprise implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
 public PropertyManagementEnterprise(String name) {
        super(name, EnterpriseType.PROPERTY_MANAGEMENT);
    }   
}
