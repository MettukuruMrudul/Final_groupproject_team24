/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import property.backend.ecosystem.Enterprise;

/**
 *
 * @author Meghana
 */
public class FinancialInstitutionEnterprise extends Enterprise implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    public FinancialInstitutionEnterprise(String name) {
        super(name, EnterpriseType.FINANCIAL_INSTITUTION);
    }
}
