/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.GovernmentHousingEnterprise;
import property.backend.ecosystem.InspectionAgencyOrganization;
import property.backend.ecosystem.LendingOrganization;
import property.backend.ecosystem.MaintainceCompanyOrganization;
import property.backend.ecosystem.MaintainceServiceEnterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;

/**
 *
 * @author sruth
 */
public class UserAuthentication implements Serializable {
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private static UserAuthentication instance;
    private ArrayList<UserAccount> userAccounts;
    private RealEstateNetwork ecosystem;
  //  private static final String DATA_FILE = "ecosystem.ser"; // The filename where data will be saved
    
    private UserAuthentication() {
        userAccounts = new ArrayList<>();
        ecosystem = RealEstateNetwork.getInstance();
        populateUsers(); // Add default users for testing
    }
    
    public static UserAuthentication getInstance() {
        if (instance == null) {
            instance = new UserAuthentication();
        }
        return instance;
    }
    
    /**
     * Authenticates a user
     * @param username Username
     * @param password Password
     * @return UserAccount if authentication successful, null otherwise
     */
    public UserAccount authenticateUser(String username, String password) {
        for (UserAccount user : userAccounts) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Adds a new user account
     * @param username Username
     * @param password Password
     * @param name Full name
     * @param email Email address
     * @param role User's role
     * @param organization User's organization
     * @return The created user account
     */
    public UserAccount createUserAccount(String username, String password, String name, 
            String email, Role role, Organization organization) {
        
        // Check if username already exists
        for (UserAccount user : userAccounts) {
            if (user.getUsername().equals(username)) {
                return null; // Username already taken
            }
        }
        
        // Create new user account
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(password);
        userAccount.setName(name);
        userAccount.setEmail(email);
        userAccount.setRole(role);
        userAccount.setOrganization(organization);
        
        userAccounts.add(userAccount);
        return userAccount;
    }
    
    // New method to check if a username is already taken
    public boolean isUsernameTaken(String username) {
        for (UserAccount user : userAccounts) {
            if (user.getUsername().equals(username)) {
                return true; // Username is already taken
            }
        }
        return false; // Username is available
    }
    
    /**
     * Populate system with default users for testing
     */
    private void populateUsers() {
        // Create enterprises if they don't exist
        PropertyManagementEnterprise pmEnterprise = null;
        MaintainceServiceEnterprise msEnterprise = null;
       // FinancialInstitutionEnterprise fiEnterprise = null;
        GovernmentHousingEnterprise ghEnterprise = null;
        
        // Check existing enterprises
        for (Enterprise e : ecosystem.getEnterpriseList()) {
            if (e instanceof PropertyManagementEnterprise) {
                pmEnterprise = (PropertyManagementEnterprise) e;
            } else if (e instanceof MaintainceServiceEnterprise) {
                msEnterprise = (MaintainceServiceEnterprise) e;
//            } else if (e instanceof FinancialInstitutionEnterprise) {
//                fiEnterprise = (FinancialInstitutionEnterprise) e;
            } else if (e instanceof GovernmentHousingEnterprise) {
                ghEnterprise = (GovernmentHousingEnterprise) e;
            }
        }
        
        // Create enterprises if they don't exist
        if (pmEnterprise == null) {
            pmEnterprise = new PropertyManagementEnterprise("ABC Property Management");
            ecosystem.addEnterprise(pmEnterprise);
        }
        
        if (msEnterprise == null) {
            msEnterprise = new MaintainceServiceEnterprise("XYZ Maintenance Services");
            ecosystem.addEnterprise(msEnterprise);
        }
        
//        if (fiEnterprise == null) {
//            fiEnterprise = new FinancialInstitutionEnterprise("Finance Solutions Inc.");
//            ecosystem.addEnterprise(fiEnterprise);
//        }
        
        if (ghEnterprise == null) {
            ghEnterprise = new GovernmentHousingEnterprise("Housing Regulatory Authority");
            ecosystem.addEnterprise(ghEnterprise);
        }
        
        // Create organizations if they don't exist
        PropertyFirmOrganization propertyFirm = null;
        MaintainceCompanyOrganization maintainceCompany = null;
        LendingOrganization lendingOrg = null;
        InspectionAgencyOrganization inspectionAgency = null;
        
        // Check existing organizations in property management enterprise
        for (Organization org : pmEnterprise.getOrganizationList()) {
            if (org instanceof PropertyFirmOrganization) {
                propertyFirm = (PropertyFirmOrganization) org;
                break;
            }
        }
        
        // Check existing organizations in maintenance service enterprise
        for (Organization org : msEnterprise.getOrganizationList()) {
            if (org instanceof MaintainceCompanyOrganization) {
                maintainceCompany = (MaintainceCompanyOrganization) org;
                break;
            }
        }
        
        // Check existing organizations in financial institution enterprise
//        for (Organization org : fiEnterprise.getOrganizationList()) {
//            if (org instanceof LendingOrganization) {
//                lendingOrg = (LendingOrganization) org;
//                break;
//            }
//        }
        
        // Check existing organizations in government housing enterprise
        for (Organization org : ghEnterprise.getOrganizationList()) {
            if (org instanceof InspectionAgencyOrganization) {
                inspectionAgency = (InspectionAgencyOrganization) org;
                break;
            }
        }
        
        // Create organizations if they don't exist
        if (propertyFirm == null) {
            propertyFirm = new PropertyFirmOrganization("Downtown Properties", pmEnterprise);
            pmEnterprise.addOrganization(propertyFirm);
        }
        
        if (maintainceCompany == null) {
            maintainceCompany = new MaintainceCompanyOrganization("Fast Repairs Co.", msEnterprise);
            msEnterprise.addOrganization(maintainceCompany);
        }
        
//        if (lendingOrg == null) {
//            lendingOrg = new LendingOrganization("Home Loans Division", fiEnterprise);
//            fiEnterprise.addOrganization(lendingOrg);
//        }
        
        if (inspectionAgency == null) {
            inspectionAgency = new InspectionAgencyOrganization("Property Standards Division", ghEnterprise);
            ghEnterprise.addOrganization(inspectionAgency);
        }
        
        // Create default user accounts
        
        // Admin user
        UserAccount adminAccount = new UserAccount();
        adminAccount.setUsername("admin");
        adminAccount.setPassword("admin");
        adminAccount.setName("System Administrator");
        adminAccount.setEmail("admin@ecosystem.com");
        adminAccount.setRole(new Admin());
        adminAccount.setOrganization(propertyFirm);
        userAccounts.add(adminAccount);
        
        // Property Manager user
        UserAccount pmAccount = new UserAccount();
        pmAccount.setUsername("pm");
        pmAccount.setPassword("pm");
        pmAccount.setName("John Manager");
        pmAccount.setEmail("john@abc.com");
        pmAccount.setRole(new PropertyManager());
        pmAccount.setOrganization(propertyFirm);
        userAccounts.add(pmAccount);
        
        // Landlord user
        UserAccount landlordAccount = new UserAccount();
        landlordAccount.setUsername("landlord");
        landlordAccount.setPassword("landlord");
        landlordAccount.setName("Sarah Owner");
        landlordAccount.setEmail("sarah@abc.com");
        landlordAccount.setRole(new Landlord());
        landlordAccount.setOrganization(propertyFirm);
        userAccounts.add(landlordAccount);
        
        // Tenant user
        UserAccount tenantAccount = new UserAccount();
        tenantAccount.setUsername("tenant");
        tenantAccount.setPassword("tenant");
        tenantAccount.setName("Mike Renter");
        tenantAccount.setEmail("mike@gmail.com");
        tenantAccount.setRole(new Tenant());
        tenantAccount.setOrganization(propertyFirm);
        userAccounts.add(tenantAccount);
        
        // Maintenance Admin user
        UserAccount maintAdminAccount = new UserAccount();
        maintAdminAccount.setUsername("maintadmin");
        maintAdminAccount.setPassword("maintadmin");
        maintAdminAccount.setName("Robert Coordinator");
        maintAdminAccount.setEmail("robert@xyz.com");
        maintAdminAccount.setRole(new MaintainceAdmin());
        maintAdminAccount.setOrganization(maintainceCompany);
        userAccounts.add(maintAdminAccount);
        
        // Maintenance Worker user
        UserAccount workerAccount = new UserAccount();
        workerAccount.setUsername("worker");
        workerAccount.setPassword("worker");
        workerAccount.setName("Tom Technician");
        workerAccount.setEmail("tom@xyz.com");
        workerAccount.setRole(new MaintainceWorker());
        workerAccount.setOrganization(maintainceCompany);
        userAccounts.add(workerAccount);
        
        // Buyer user
        UserAccount buyerAccount = new UserAccount();
        buyerAccount.setUsername("buyer");
        buyerAccount.setPassword("buyer");
        buyerAccount.setName("Alice Buyer");
        buyerAccount.setEmail("alice@gmail.com");
        buyerAccount.setRole(new Buyer());
        buyerAccount.setOrganization(lendingOrg);
        userAccounts.add(buyerAccount);
        
//        // Financial Officer user
//        UserAccount financeAccount = new UserAccount();
//        financeAccount.setUsername("finance");
//        financeAccount.setPassword("finance");
//        financeAccount.setName("Lisa Finance");
//        financeAccount.setEmail("lisa@finance.com");
//        financeAccount.setRole(new FinancialOfficerRole());
//        financeAccount.setOrganization(lendingOrg);
//        userAccounts.add(financeAccount);
        
        // Housing Inspector user
        UserAccount inspectorAccount = new UserAccount();
        inspectorAccount.setUsername("inspector");
        inspectorAccount.setPassword("inspector");
        inspectorAccount.setName("Inspector James");
        inspectorAccount.setEmail("james@housing.gov");
        inspectorAccount.setRole(new HousingInspector());
        inspectorAccount.setOrganization(inspectionAgency);
        userAccounts.add(inspectorAccount);
    
    
 
}
    /**
     * Get all user accounts
     * @return List of all user accounts
     */
    public ArrayList<UserAccount> getUserAccounts() {
        return userAccounts;
    }
    
}
