/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.MaintainceCompanyOrganization;
import property.backend.ecosystem.MaintainceServiceEnterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.MaintainceAdmin;
import property.backend.users.Property;
import property.backend.users.UserAccount;
import property.backend.users.UserAuthentication;
import property.backend.workqueue.MaintainceRequest;
//import property.backend.workqueue.MaintenanceRequest;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author krishi parekh
 */
public class TenantWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    
     private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color titleColor = new Color(0, 102, 102);
    
    /**
     * Creates new form TenantWorkAreaJPanel
     */
    public TenantWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        initComponents();
        applyCustomStyling();
    cmbRepair.removeAllItems();
    cmbRepair.addItem("Plumbing");
    cmbRepair.addItem("Electrical");
    cmbRepair.addItem("HVAC");
    cmbRepair.addItem("Structural");
    cmbRepair.addItem("Appliance Repair");
    cmbRepair.addItem("Pest Control");
    cmbRepair.addItem("Painting");
    cmbRepair.addItem("Landscaping");
    cmbRepair.addItem("General Maintaince");
    
    cmbMaintAdmin.removeAllItems();
    populateMaintAdmins();
    
    if (account.getName() != null) {
        txtTenant.setText(account.getName());
        txtTenant.setEditable(false); // Don't allow editing tenant name if it's from the account
    }
    }
    
 
    private void applyCustomStyling() {
        // Apply text color to labels
        applyColorToLabels();
        
        // Apply styling to title
        styleTitle();
        
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
        lblTenant.setForeground(textColor);
        lblTPhone.setForeground(textColor);
        lblHouse.setForeground(textColor);
        lblRepair.setForeground(textColor);
        lblMaintAdmin.setForeground(textColor);
        
        // Make labels bold for better visibility
        lblTenant.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblTPhone.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblHouse.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblRepair.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblMaintAdmin.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
    
    /**
     * Apply styling to title label
     */
    private void styleTitle() {
        // Apply color and styling to title
        lblTitle.setForeground(titleColor);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
    }
    
    /**
     * Apply styling to form fields
     */
    private void styleFormFields() {
        // Style text fields
        styleTextField(txtTenant);
        styleTextField(txtPhone);
        styleTextField(txtHouse);
        
        // Style combo boxes
        styleComboBox(cmbRepair);
        styleComboBox(cmbMaintAdmin);
    }
    
    /**
     * Apply styling to a text field
     */
    private void styleTextField(JTextField textField) {
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(100, 149, 237)));
        textField.setBackground(new Color(240, 248, 255)); // Light blue background
    }
    
    /**
     * Apply styling to a combo box
     */
    private void styleComboBox(JComboBox comboBox) {
        comboBox.setBackground(new Color(240, 248, 255)); // Light blue background
        comboBox.setForeground(textColor);
        comboBox.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(100, 149, 237)));
    }
    
    /**
     * Apply highlight color to all buttons
     */
    private void highlightButtons() {
        // Style the repair button
        btnRepair.setBackground(highlightColor);
        btnRepair.setForeground(new Color(0, 51, 102)); // Dark blue text
        btnRepair.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        btnRepair.setOpaque(true);
        btnRepair.setBorderPainted(false);
    }
    
    // Method to populate maintenance admin dropdown
public void populateMaintAdmins() {
   // Modified populateMaintAdmins() method

    HashMap<String, UserAccount> admins = new HashMap<>();
    
    // 1. First check UserAuthentication - this captures all users including newly signed up ones
    UserAuthentication userAuth = UserAuthentication.getInstance();
    for (UserAccount user : userAuth.getUserAccounts()) {
        if (user.getRole() instanceof MaintainceAdmin) {
            admins.put(user.getUsername(), user);
            System.out.println("Found admin in UserAuth: " + user.getUsername());
        }
    }

    // 2. Then check Ecosystem Organizations to catch any that might only exist there
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof MaintainceServiceEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof MaintainceCompanyOrganization) {
                    for (UserAccount user : org.getUserAccountList()) {
                        if (user.getRole() instanceof MaintainceAdmin) {
                            admins.put(user.getUsername(), user);
                            System.out.println("Found admin in Org: " + user.getUsername());
                        }
                    }
                }
            }
        }
    }

    // Update ComboBox
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    if (!admins.isEmpty()) {
        for (UserAccount admin : admins.values()) {
            model.addElement(admin.getName() + " (" + admin.getUsername() + ")");
        }
    } else {
        model.addElement("No Maintenance Admins Available");
    }
    
    cmbMaintAdmin.setModel(model);
    System.out.println("Populated " + admins.size() + " maintenance admins in dropdown");
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
        lblTenant = new javax.swing.JLabel();
        lblTPhone = new javax.swing.JLabel();
        lblHouse = new javax.swing.JLabel();
        lblRepair = new javax.swing.JLabel();
        txtTenant = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtHouse = new javax.swing.JTextField();
        cmbRepair = new javax.swing.JComboBox<>();
        btnRepair = new javax.swing.JButton();
        lblMaintAdmin = new javax.swing.JLabel();
        cmbMaintAdmin = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(181, 218, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Register For Maintaince");

        lblTenant.setText("Tenant Name");

        lblTPhone.setText("Tenant Phone Number");

        lblHouse.setText("House Number");

        lblRepair.setText("Repair that needs to be done");

        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        txtHouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHouseActionPerformed(evt);
            }
        });

        cmbRepair.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnRepair.setText("Register Service");
        btnRepair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepairActionPerformed(evt);
            }
        });

        lblMaintAdmin.setText("Maintaince Admin");

        cmbMaintAdmin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(350, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRepair)
                        .addGap(519, 519, 519))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblTenant, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(243, 243, 243))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblHouse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblRepair, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMaintAdmin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbMaintAdmin, 0, 233, Short.MAX_VALUE)
                                    .addComponent(cmbRepair, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTenant, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtHouse, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(264, 264, 264))))
            .addGroup(layout.createSequentialGroup()
                .addGap(503, 503, 503)
                .addComponent(lblTitle)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblTitle)
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTPhone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHouse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRepair))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMaintAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaintAdmin))
                .addGap(18, 18, 18)
                .addComponent(btnRepair)
                .addContainerGap(337, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void txtHouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHouseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHouseActionPerformed

    private void btnRepairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepairActionPerformed
        // TODO add your handling code here:
    // private void btnRepairActionPerformed(java.awt.event.ActionEvent evt) {
    // Validate input fields
    if (txtTenant.getText().isEmpty() || txtPhone.getText().isEmpty() || txtHouse.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please fill in all fields", 
            "Incomplete Information", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // ← INSERT PHONE VALIDATION HERE
    String phone = txtPhone.getText().trim();
    if (!phone.matches("\\d{10}")) {
        JOptionPane.showMessageDialog(this, 
            "Please enter a valid 10-digit phone number.", 
            "Invalid Phone Number", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }    
    
    // Check if maintenance admin is available
    if (cmbMaintAdmin.getSelectedItem() == null || cmbMaintAdmin.getSelectedItem().toString().equals("No Maintenance Admins Available")) {
        JOptionPane.showMessageDialog(this,
            "No maintenance administrator is available to process your request.\nPlease contact support.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Create a maintenance request
    MaintainceRequest request = new MaintainceRequest();
    request.setSender(account);
    request.setDescription(cmbRepair.getSelectedItem().toString() + " - Phone: " + txtPhone.getText());
    request.setRequestId("REQ" + System.currentTimeMillis());   
    
    String house = txtHouse.getText().trim();
    if (!house.matches("\\d{3}")) {
        JOptionPane.showMessageDialog(this, 
            "Please enter a valid 3-digit house number.", 
            "Invalid House Number", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Find property by house number
    Property property = null;
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof PropertyManagementEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof PropertyFirmOrganization) {
                    property = ((PropertyFirmOrganization) org).findPropertyByHouseNumber(txtHouse.getText());
                    if (property != null) break;
                }
            }
        }
        if (property != null) break;
    }
    
    if (property == null) {
        // Create a new property if not found
        property = new Property();
        property.setPropertyId(txtHouse.getText());
        property.setAddress("Unknown Address for " + txtHouse.getText());
        
        // Add property to a property firm organization
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        ((PropertyFirmOrganization) org).addProperty(property);
                        break;
                    }
                }
            }
        }
    }
    request.setProperty(property);
    request.setDescription(
        cmbRepair.getSelectedItem().toString() 
        + " - Phone: " + phone 
        + ", - House: H" + house  // Add "H" prefix to house number
    );
    
    //request.setProperty(property);
    request.setStatus(WorkRequest.RequestStatus.PENDING);
    request.setRequestDate(new Date());
    
    String selectedAdmin = cmbMaintAdmin.getSelectedItem().toString();
    String adminUsername = selectedAdmin.substring(selectedAdmin.indexOf("(") + 1, selectedAdmin.indexOf(")"));
    
    // Find maintenance admin to assign request
    UserAccount maintAdmin = null;
    // FIRST: Try to find admin in UserAuthentication (more reliable)
    UserAuthentication userAuth = UserAuthentication.getInstance();
    for (UserAccount user : userAuth.getUserAccounts()) {
        if (user.getUsername().equals(adminUsername)&& user.getRole() instanceof MaintainceAdmin) {
            maintAdmin = user;
            System.out.println("Found admin in UserAuth: " + user.getUsername());
            break;
        }
    }
    
    
    
    
    
    
    // SECOND: If not found, try ecosystem
    if (maintAdmin == null) {
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof MaintainceServiceEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof MaintainceCompanyOrganization) {
                    for (UserAccount user : org.getUserAccountList()) {
                        if (user.getRole() instanceof MaintainceAdmin  && 
                            user.getUsername().equals(adminUsername)) {
                            maintAdmin = user;
                            break;
                        }
                    }
                }
            }
                if (maintAdmin != null) break;
        }
    
    }

    }
    

System.out.println("=== Tenant creating maintenance request ===");
System.out.println("Tenant: " + account.getUsername());
System.out.println("Maintenance admin found: " + (maintAdmin != null));
System.out.println("=== Admin Selection Debug ===");
System.out.println("Selected from dropdown: " + selectedAdmin);
System.out.println("Extracted username: " + adminUsername);
System.out.println("Found admin: " + (maintAdmin != null ? maintAdmin.getUsername() : "null"));


    if (maintAdmin != null) {
        System.out.println("Admin name: " + maintAdmin.getName());
    System.out.println("Admin role: " + maintAdmin.getRole().getClass().getSimpleName());
    System.out.println("Admin work queue size before: " + 
        maintAdmin.getRole().getWorkQueue().getWorkRequestList().size());
        request.setReceiver(maintAdmin);
       // request.setRequestId("REQ" + System.currentTimeMillis());
        
        // Add request to sender's work queue
        account.getRole().getWorkQueue().addWorkRequest(request);
        
        // Add request to receiver's work queue
        maintAdmin.getRole().getWorkQueue().addWorkRequest(request);
        // userAuth.saveUserAccounts();
        // Also add to the organization's work queue if applicable
    // Add to the organization's work queue if applicable - THIS IS WHERE THE ERROR OCCURS
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof MaintainceServiceEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof MaintainceCompanyOrganization) {
                    // FIXED: Check if work queue exists, create it if not
                    if (org.getWorkQueue() == null) {
                        // Create a new work queue for the organization
                        org.setWorkQueue(new property.backend.workqueue.WorkQueue());
                        System.out.println("Created new work queue for organization: " + org.getName());
                    }
                    org.getWorkQueue().addWorkRequest(request);
                    break;
                }
            }
        }
    }
        
        
        System.out.println("Admin work queue size after: " + 
        maintAdmin.getRole().getWorkQueue().getWorkRequestList().size());
        
        // Show success message
        JOptionPane.showMessageDialog(this,
            "Maintenance request has been registered successfully!\nYour request has been forwarded to " + maintAdmin.getName() + ".",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Clear form fields
        txtTenant.setText(account.getName()); // Reset to account name since we don't want to clear it completely
        txtPhone.setText("");
        txtHouse.setText("");
        cmbRepair.setSelectedIndex(0);
    } else {
        JOptionPane.showMessageDialog(this,
            "Error finding the selected maintenance administrator.\nPlease try again.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    
//UserAuthentication.getInstance().saveUserAccounts();
    }
    }//GEN-LAST:event_btnRepairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRepair;
    private javax.swing.JComboBox<String> cmbMaintAdmin;
    private javax.swing.JComboBox<String> cmbRepair;
    private javax.swing.JLabel lblHouse;
    private javax.swing.JLabel lblMaintAdmin;
    private javax.swing.JLabel lblRepair;
    private javax.swing.JLabel lblTPhone;
    private javax.swing.JLabel lblTenant;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtHouse;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtTenant;
    // End of variables declaration//GEN-END:variables
}
