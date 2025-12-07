/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.ecosystem;

import java.io.Serializable;
import java.util.ArrayList;
import property.backend.ecosystem.Enterprise;

/**
 *
 * @author sruth
 */
public class RealEstateNetwork implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
 
  private String name;
    private ArrayList<Enterprise> enterpriseList;
    private static RealEstateNetwork network;
    
    public RealEstateNetwork() {
        name = "Real Estate Management Network";
        enterpriseList = new ArrayList<>();
    }
    
    public static RealEstateNetwork getInstance() {
        if (network == null) {
            network = new RealEstateNetwork();
        }
        return network;
    }
    
    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }
    
    public void addEnterprise(Enterprise enterprise) {
        enterpriseList.add(enterprise);
    }
    
    public Enterprise findEnterprise(String name) {
        for (Enterprise enterprise : enterpriseList) {
            if (enterprise.getName().equals(name)) {
                return enterprise;
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
}
