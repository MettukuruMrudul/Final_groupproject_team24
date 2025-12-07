/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.Serializable;
import property.backend.users.Role;

/**
 *
 * @author krishi parekh
 */
public class Buyer extends Role implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    public Buyer() {
        super(RoleType.BUYER);
    }
    
}
