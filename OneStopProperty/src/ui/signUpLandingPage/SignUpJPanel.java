/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import property.backend.ecosystem.BankingOrganization;
import property.backend.ecosystem.BuilderOrganization;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.FinancialInstitutionEnterprise;
import property.backend.ecosystem.GovernmentHousingEnterprise;
import property.backend.ecosystem.InspectionAgencyOrganization;
import property.backend.ecosystem.LendingOrganization;
import property.backend.ecosystem.MaintainceCompanyOrganization;
import property.backend.ecosystem.MaintainceServiceEnterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.Bank;
import property.backend.users.Builder;
import property.backend.users.Buyer;
import property.backend.users.HousingInspector;
import property.backend.users.Landlord;
import property.backend.users.MaintainceAdmin;
import property.backend.users.MaintainceWorker;
import property.backend.users.PropertyManager;
import property.backend.users.Role;
import property.backend.users.Tenant;
import property.backend.users.UserAccount;
import property.backend.users.UserAuthentication;
import property.ui.MainJFrame;

/**
 *
 * @author uday
 */
public class SignUpJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private RealEstateNetwork ecosystem;
    
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color titleColor = new Color(0, 102, 102); 
    
    /**
     * Creates new form SignUpJPanel
     */
    public SignUpJPanel(JPanel userProcessContainer,RealEstateNetwork ecosystem) {
        this.userProcessContainer=userProcessContainer;
        this.ecosystem=ecosystem;
        initComponents();
        applyCustomStyling();
    cmbRole.removeAllItems();
    cmbRole.addItem("Tenant");
    cmbRole.addItem("Landlord");
    cmbRole.addItem("Property Manager");
    cmbRole.addItem("Maintaince Worker");
    cmbRole.addItem("Maintaince Admin");
    cmbRole.addItem("Housing Inspector");
    cmbRole.addItem("Buyer");
    cmbRole.addItem("Builder");        // Added Builder
    cmbRole.addItem("Bank");           // Added Bank
    }
    
    /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
        // Apply text color to labels
        applyColorToLabels();
        
        // Apply styling to titles
        styleTitles();
        
        // Apply styling to form fields
        styleFormFields();
        
        // Apply button highlighting
        highlightButtons();
    }
    
    /**
     * Apply text color to all labels in the panel
     */
    private void applyColorToLabels() {
        // Set color for field labels
        lblName.setForeground(textColor);
        lblUser.setForeground(textColor);
        lblPass.setForeground(textColor);
        lblRole.setForeground(textColor);
        
        // Make labels bold for better visibility
        lblName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblUser.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblPass.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblRole.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
    
    /**
     * Apply styling to title labels
     */
    private void styleTitles() {
        // Apply color and styling to title
        lblTitle.setForeground(titleColor);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        
        // Apply color and styling to subtitle
        lblSubtitle.setForeground(titleColor);
        lblSubtitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD | java.awt.Font.ITALIC, 16));
    }
    
    /**
     * Apply styling to form fields
     */
    private void styleFormFields() {
        // Style text fields
        styleTextField(txtName);
        styleTextField(txtUser);
        
        // Style password field
        passPass.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(100, 149, 237)));
        passPass.setBackground(new Color(240, 248, 255)); // Light blue background
        
        // Style combo box
        cmbRole.setBackground(new Color(240, 248, 255)); // Light blue background
        cmbRole.setForeground(textColor);
        cmbRole.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(100, 149, 237)));
    }
    
    /**
     * Apply styling to a text field
     */
    private void styleTextField(JTextField textField) {
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(100, 149, 237)));
        textField.setBackground(new Color(240, 248, 255)); // Light blue background
    }
    
    /**
     * Apply highlight color to all buttons
     */
    private void highlightButtons() {
        // Style the signup button
        btnSignup.setBackground(highlightColor);
        btnSignup.setForeground(new Color(0, 51, 102)); // Dark blue text
        btnSignup.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnSignup.setOpaque(true);
        btnSignup.setBorderPainted(false);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        passPass = new javax.swing.JPasswordField();
        cmbRole = new javax.swing.JComboBox<>();
        btnSignup = new javax.swing.JButton();

        setBackground(new java.awt.Color(181, 218, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Looking For Registration??");

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSubtitle.setText("Signup Below to get started!!!");

        lblUser.setText("Username");

        lblPass.setText("Password");

        lblRole.setText("Role");

        lblName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblName.setText("Name");

        passPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passPassActionPerformed(evt);
            }
        });

        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSignup.setText("SignUp");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(352, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblSubtitle)
                        .addGap(415, 415, 415))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblUser)
                                .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(240, 240, 240)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtName)
                            .addComponent(txtUser)
                            .addComponent(passPass)
                            .addComponent(cmbRole, 0, 135, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 375, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitle)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPass)
                    .addComponent(passPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRole)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btnSignup)
                .addContainerGap(267, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void passPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passPassActionPerformed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        // TODO add your handling code here:
       //private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {
    // Validate input fields
    String name = txtName.getText();
    String username = txtUser.getText();
    String password = new String(passPass.getPassword());
    String roleName = (String) cmbRole.getSelectedItem();

    if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Please fill in all fields",
            "Incomplete Information",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Check if username already exists
    UserAuthentication userAuth = UserAuthentication.getInstance();
    if (userAuth.isUsernameTaken(username)) {
        JOptionPane.showMessageDialog(this,
            "Username already exists. Please choose a different username.",
            "Username Taken",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Create the appropriate Role
    Role role = createRole(roleName);

    // Create user account
    UserAccount account = new UserAccount();
    account.setName(name);
    account.setUsername(username);
    account.setPassword(password);
    account.setRole(role);

    // Process based on role type
    switch (roleName) {
        case "Tenant":
        case "Landlord":
        case "Property Manager":
            // Find or create Property Management Enterprise
            PropertyManagementEnterprise propertyEnterprise = null;
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                if (enterprise instanceof PropertyManagementEnterprise) {
                    propertyEnterprise = (PropertyManagementEnterprise) enterprise;
                    System.out.println("Found existing PropertyManagementEnterprise");
                    break;
                }
            }
            
            if (propertyEnterprise == null) {
                propertyEnterprise = new PropertyManagementEnterprise("Property Management");
                ecosystem.addEnterprise(propertyEnterprise);
                System.out.println("Created new PropertyManagementEnterprise");
            }
            
            // Find or create Property Firm Organization
            PropertyFirmOrganization propertyFirm = null;
            for (Organization org : propertyEnterprise.getOrganizationList()) {
                if (org instanceof PropertyFirmOrganization) {
                    propertyFirm = (PropertyFirmOrganization) org;
                    System.out.println("Found existing PropertyFirmOrganization");
                    break;
                }
            }
            
            if (propertyFirm == null) {
                propertyFirm = new PropertyFirmOrganization("Property Firm", propertyEnterprise);
                propertyEnterprise.addOrganization(propertyFirm);
                System.out.println("Created new PropertyFirmOrganization");
            }
            
            // Add user to organization
            propertyFirm.getUserAccountList().add(account);
            account.setOrganization(propertyFirm);
            System.out.println("Added " + account.getUsername() + " to PropertyFirmOrganization");
            break;
            
        case "Maintaince Worker":
        case "Maintaince Admin":
            // Find or create Maintenance Service Enterprise
            MaintainceServiceEnterprise maintEnterprise = null;
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                if (enterprise instanceof MaintainceServiceEnterprise) {
                    maintEnterprise = (MaintainceServiceEnterprise) enterprise;
                    System.out.println("Found existing MaintainceServiceEnterprise");
                    break;
                }
            }
            
            if (maintEnterprise == null) {
                maintEnterprise = new MaintainceServiceEnterprise("Maintenance Services");
                ecosystem.addEnterprise(maintEnterprise);
                System.out.println("Created new MaintainceServiceEnterprise");
            }
            
            // Find or create Maintenance Company Organization
            MaintainceCompanyOrganization maintOrg = null;
            for (Organization org : maintEnterprise.getOrganizationList()) {
                if (org instanceof MaintainceCompanyOrganization) {
                    maintOrg = (MaintainceCompanyOrganization) org;
                    System.out.println("Found existing MaintainceCompanyOrganization");
                    break;
                }
            }
            
            if (maintOrg == null) {
                maintOrg = new MaintainceCompanyOrganization("Maintenance Company", maintEnterprise);
                maintEnterprise.addOrganization(maintOrg);
                System.out.println("Created new MaintainceCompanyOrganization");
            }
            
            // Add user to organization
            maintOrg.getUserAccountList().add(account);
            account.setOrganization(maintOrg);
            System.out.println("Added " + account.getUsername() + " to MaintainceCompanyOrganization");
            break;
            
        case "Housing Inspector":
            // Find or create Government Housing Enterprise
            GovernmentHousingEnterprise govEnterprise = null;
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                if (enterprise instanceof GovernmentHousingEnterprise) {
                    govEnterprise = (GovernmentHousingEnterprise) enterprise;
                    System.out.println("Found existing GovernmentHousingEnterprise");
                    break;
                }
            }
            
            if (govEnterprise == null) {
                govEnterprise = new GovernmentHousingEnterprise("Government Housing");
                ecosystem.addEnterprise(govEnterprise);
                System.out.println("Created new GovernmentHousingEnterprise");
            }
            
            // Find or create Inspection Agency Organization
            InspectionAgencyOrganization inspectionOrg = null;
            for (Organization org : govEnterprise.getOrganizationList()) {
                if (org instanceof InspectionAgencyOrganization) {
                    inspectionOrg = (InspectionAgencyOrganization) org;
                    System.out.println("Found existing InspectionAgencyOrganization");
                    break;
                }
            }
            
            if (inspectionOrg == null) {
                inspectionOrg = new InspectionAgencyOrganization("Inspection Agency", govEnterprise);
                govEnterprise.addOrganization(inspectionOrg);
                System.out.println("Created new InspectionAgencyOrganization");
            }
            
            // Add user to organization
            inspectionOrg.getUserAccountList().add(account);
            account.setOrganization(inspectionOrg);
            System.out.println("Added " + account.getUsername() + " to InspectionAgencyOrganization");
            break;
            
        case "Buyer":
            // Find or create Financial Institution Enterprise
            FinancialInstitutionEnterprise finEnterprise = null;
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                if (enterprise instanceof FinancialInstitutionEnterprise) {
                    finEnterprise = (FinancialInstitutionEnterprise) enterprise;
                    System.out.println("Found existing FinancialInstitutionEnterprise");
                    break;
                }
            }
            
            if (finEnterprise == null) {
                finEnterprise = new FinancialInstitutionEnterprise("Financial Institution");
                ecosystem.addEnterprise(finEnterprise);
                System.out.println("Created new FinancialInstitutionEnterprise");
            }
            
            // Find or create Lending Organization
            LendingOrganization lendingOrg = null;
            for (Organization org : finEnterprise.getOrganizationList()) {
                if (org instanceof LendingOrganization) {
                    lendingOrg = (LendingOrganization) org;
                    System.out.println("Found existing LendingOrganization");
                    break;
                }
            }
            
            if (lendingOrg == null) {
                lendingOrg = new LendingOrganization("Lending Organization", finEnterprise);
                finEnterprise.addOrganization(lendingOrg);
                System.out.println("Created new LendingOrganization");
            }
            
            // Add user to organization
            lendingOrg.getUserAccountList().add(account);
            account.setOrganization(lendingOrg);
            System.out.println("Added " + account.getUsername() + " to LendingOrganization");
            break;
            
        case "Builder":
          PropertyManagementEnterprise builderEnterprise = null;
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                builderEnterprise = (PropertyManagementEnterprise) enterprise;
                System.out.println("Found existing PropertyManagementEnterprise");
                break;
            }
        }
        
        if (builderEnterprise == null) {
            builderEnterprise = new PropertyManagementEnterprise("Property Management");
            ecosystem.addEnterprise(builderEnterprise);
            System.out.println("Created new PropertyManagementEnterprise");
        }
        
        // Find or create Builder Organization
        BuilderOrganization builderOrg = null;
        for (Organization org : builderEnterprise.getOrganizationList()) {
            if (org instanceof BuilderOrganization) {
                builderOrg = (BuilderOrganization) org;
                System.out.println("Found existing BuilderOrganization");
                break;
            }
        }
        
        if (builderOrg == null) {
            builderOrg = new BuilderOrganization("Builder Organization", builderEnterprise);
            builderEnterprise.addOrganization(builderOrg);
            System.out.println("Created new BuilderOrganization");
        }
        
        // Add user to organization
        builderOrg.getUserAccountList().add(account);
        account.setOrganization(builderOrg);
        System.out.println("Added " + account.getUsername() + " to BuilderOrganization");
        break;
        
        case "Bank":
        FinancialInstitutionEnterprise bankEnterprise = null;
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof FinancialInstitutionEnterprise) {
                bankEnterprise = (FinancialInstitutionEnterprise) enterprise;
                System.out.println("Found existing FinancialInstitutionEnterprise");
                break;
            }
        }
        
        if (bankEnterprise == null) {
            bankEnterprise = new FinancialInstitutionEnterprise("Financial Institution");
            ecosystem.addEnterprise(bankEnterprise);
            System.out.println("Created new FinancialInstitutionEnterprise");
        }
        
        // Find or create Banking Organization
        BankingOrganization bankingOrg = null;
        for (Organization org : bankEnterprise.getOrganizationList()) {
            if (org instanceof BankingOrganization) {
                bankingOrg = (BankingOrganization) org;
                System.out.println("Found existing BankingOrganization");
                break;
            }
        }
        
        if (bankingOrg == null) {
            bankingOrg = new BankingOrganization("Banking Organization", bankEnterprise);
            bankEnterprise.addOrganization(bankingOrg);
            System.out.println("Created new BankingOrganization");
        }
        
        // Add user to organization
        bankingOrg.getUserAccountList().add(account);
        account.setOrganization(bankingOrg);
        System.out.println("Added " + account.getUsername() + " to BankingOrganization");
        
   
            
        default:
            // For any other role, use default organization
            Organization defaultOrg = createDefaultOrganization();
            defaultOrg.getUserAccountList().add(account);
            account.setOrganization(defaultOrg);
            System.out.println("Added " + account.getUsername() + " to default organization");
            break;
    }
    
      // Add to UserAuthentication as well
    userAuth.getUserAccounts().add(account);
    System.out.println("Added " + account.getUsername() + " to UserAuthentication");
    
    // IMPORTANT: Save both ecosystem and UserAuthentication
    try {
        // Save ecosystem
        saveEcosystemToFile();
        
        // Save UserAuthentication
        saveUserAuthenticationToFile();
        
        System.out.println("Successfully saved both ecosystem and user authentication data");
        
    } catch (Exception e) {
        System.err.println("Error saving data: " + e.getMessage());
        e.printStackTrace();
    }
    
    // Save ecosystem changes through MainJFrame
    MainJFrame mainFrame = (MainJFrame) SwingUtilities.getWindowAncestor(this);
    if (mainFrame != null) {
        mainFrame.handleSuccessfulSignup(account);
    }
    
    // Display success message
    JOptionPane.showMessageDialog(this,
        "Account created successfully! You can now login with your credentials.",
        "Account Created",
        JOptionPane.INFORMATION_MESSAGE);

    // Go back to login screen
    CardLayout layout = (CardLayout) userProcessContainer.getLayout();
    userProcessContainer.remove(this);
    layout.previous(userProcessContainer);

    }

    private Role createRole(String roleName) {
        switch (roleName) {
            case "Tenant":      return new Tenant();
            case "Landlord":    return new Landlord();
            case "Property Manager": return new PropertyManager();
            case "Maintaince Worker":   return new MaintainceWorker();
            case "Maintaince Admin":    return new MaintainceAdmin();
            case "Housing Inspector":   return new HousingInspector();
            case "Buyer":       return new Buyer();
            case "Builder":             return new Builder();        // Added Builder
            case "Bank":                return new Bank(); 
            default:            return new Tenant(); // Default to tenant
        }
    }

    private Organization findOrganizationForRole(String roleName) {
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if ((roleName.equals("Tenant") || roleName.equals("Landlord") || roleName.equals("Property Manager"))
                    && enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        return org;
                    }
                }
            } else if ((roleName.equals("Maintaince Worker") || roleName.equals("Maintaince Admin"))
                    && enterprise instanceof MaintainceServiceEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof MaintainceCompanyOrganization) {
                        return org;
                    }
                }
            } else if (roleName.equals("Housing Inspector")
                    && enterprise instanceof GovernmentHousingEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof InspectionAgencyOrganization) {
                        return org;
                    }
                }
            } else if (roleName.equals("Buyer")
                    && enterprise instanceof FinancialInstitutionEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof LendingOrganization) {
                        return org;
                    }
                }
            }else if (roleName.equals("Builder")
                    && enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof BuilderOrganization) {
                        return org;
                    }
                }
            } else if (roleName.equals("Bank")
                    && enterprise instanceof FinancialInstitutionEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof BankingOrganization) {
                        return org;
        }
                }
            }
        }
        return null;
    }

    private Organization createDefaultOrganization() {
        // Find or create default PropertyManagementEnterprise
        PropertyManagementEnterprise pmEnterprise = null;
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                pmEnterprise = (PropertyManagementEnterprise) enterprise;
                break;
            }
        }

        if (pmEnterprise == null) {
            pmEnterprise = new PropertyManagementEnterprise("Default Property Management");
            ecosystem.addEnterprise(pmEnterprise);
        }

        // Find or create default PropertyFirmOrganization
        PropertyFirmOrganization propertyFirm = null;
        for (Organization org : pmEnterprise.getOrganizationList()) {
            if (org instanceof PropertyFirmOrganization) {
                propertyFirm = (PropertyFirmOrganization) org;
                break;
            }
        }

        if (propertyFirm == null) {
            propertyFirm = new PropertyFirmOrganization("Default Property Firm", pmEnterprise);
            pmEnterprise.addOrganization(propertyFirm);
        }

        return propertyFirm;

}
    // In your signup success handler
private void onSignupSuccess(UserAccount account) {
   MainJFrame mainFrame = (MainJFrame) SwingUtilities.getWindowAncestor(this);
    mainFrame.handleSuccessfulSignup(account); // Pass the account
    JOptionPane.showMessageDialog(this, "Signup successful! You can now login.");
}

// Method to save UserAuthentication to file
private void saveUserAuthenticationToFile() {
    try {
        UserAuthentication userAuth = UserAuthentication.getInstance();
        FileOutputStream fileOut = new FileOutputStream("userauth.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(userAuth);
        out.close();
        fileOut.close();
        System.out.println("UserAuthentication saved to userauth.ser");
    } catch (IOException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Error saving user authentication data: " + e.getMessage(), 
            "Save Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}


// Method to save ecosystem to file
private void saveEcosystemToFile() {
    try {
        FileOutputStream fileOut = new FileOutputStream("ecosystem.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(ecosystem);
        out.close();
        fileOut.close();
        System.out.println("Ecosystem saved to ecosystem.ser");
        // Debug: Print what's being saved
        System.out.println("Saving ecosystem with " + ecosystem.getEnterpriseList().size() + " enterprises");
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            System.out.println("Enterprise: " + enterprise.getName() + " with " + 
                enterprise.getOrganizationList().size() + " organizations");
            for (Organization org : enterprise.getOrganizationList()) {
                System.out.println("  Organization: " + org.getName() + " with " + 
                    org.getUserAccountList().size() + " users");
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Error saving data: " + e.getMessage(), 
            "Save Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnSignupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSignup;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField passPass;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
