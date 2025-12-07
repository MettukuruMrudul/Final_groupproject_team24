/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.Serializable;

/**
 *
 * @author uday
 */
public class EnterpriseAdmin extends Role implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    public EnterpriseAdmin() {
        super(RoleType.ENTERPRISE_ADMIN);
    }
    
}
