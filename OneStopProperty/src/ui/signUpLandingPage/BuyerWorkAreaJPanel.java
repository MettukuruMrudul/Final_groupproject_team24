/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.Landlord;
import property.backend.users.Property;
import property.backend.users.PropertyManager;
import property.backend.users.UserAccount;
import property.backend.users.UserAuthentication;
import property.backend.workqueue.PropertyPurchaseRequest;
import property.backend.workqueue.WorkQueue;
import property.backend.workqueue.WorkRequest;
import property.ui.bank.BankJPanel;
import property.ui.builder.BuilderJPanel;
import property.ui.buyer.AddPropertyJPanel;
import property.ui.buyer.PropertyJPanel;

/**
 *
 * @author krishi
 */
public class BuyerWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<Property> availableProperties;
    private ArrayList<Property> cartProperties;
    private HashMap<Property, Double> cartPriceMap;
    
    // Define colors
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color tableColor = new Color(181, 218, 255); // Table background color
    
    /**
     * Creates new form BuyerWorkAreaJPanel
     */
    public BuyerWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        this.availableProperties = new ArrayList<>();
        this.cartProperties = new ArrayList<>();
        this.cartPriceMap = new HashMap<>();
        initComponents();
        applyCustomStyling();
        populatePropertyTable();
        populateCartTable();
        populatePropertyManagerComboBox();
    
    // Set empty message for cart table when it's empty
    if (cartProperties.isEmpty()) {
        ((DefaultTableModel) tblCart.getModel()).setRowCount(0);
        ((DefaultTableModel) tblCart.getModel()).addRow(new Object[]{"No properties added", "", ""});
    }
    }
    
    /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
        // Apply text color to all labels
        applyColorToLabels(this, textColor);
        
        // Apply button highlighting
        highlightButtons();
        
        // Apply table styling
        styleTable(tblProperty);
        styleTable(tblCart);
    }
    
    /**
     * Apply text color to all labels in the panel
     */
    private void applyColorToLabels(javax.swing.JPanel panel, Color color) {
        // Set color for specific labels
        lblReady.setForeground(color);
        lblApproved.setForeground(color);
        
        // Set font for labels to make them more visible with the new color
        lblReady.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        lblApproved.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
    
    /**
     * Apply highlight color to all buttons
     */
    private void highlightButtons() {
        // Set background color for all buttons
        btnAdd.setBackground(highlightColor);
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(false);
        
        btnProperty.setBackground(highlightColor);
        btnProperty.setOpaque(true);
        btnProperty.setBorderPainted(false);
        
        btnRemove.setBackground(highlightColor);
        btnRemove.setOpaque(true);
        btnRemove.setBorderPainted(false);
        
        btnSubmit.setBackground(highlightColor);
        btnSubmit.setOpaque(true);
        btnSubmit.setBorderPainted(false);
        
        btnClick.setBackground(highlightColor);
        btnClick.setOpaque(true);
        btnClick.setBorderPainted(false);
        
        btnBank.setBackground(highlightColor);
        btnBank.setOpaque(true);
        btnBank.setBorderPainted(false);
    }
    
    /**
     * Apply styling to tables
     */
    private void styleTable(JTable table) {
        table.setBackground(tableColor);
        table.setForeground(new Color(0, 0, 0)); // Black text for readability
        table.setSelectionBackground(new Color(135, 206, 250)); // Light blue selection
        table.setSelectionForeground(new Color(0, 0, 0)); // Black text for selected rows
        table.setGridColor(new Color(100, 149, 237)); // Cornflower blue grid
        table.getTableHeader().setBackground(new Color(51, 153, 255)); // Darker blue header
        table.getTableHeader().setForeground(new Color(255, 255, 255)); // White text for header
    }
    
    private void populatePropertyTable() {
    DefaultTableModel model = (DefaultTableModel) tblProperty.getModel();
    model.setRowCount(0); // Clear existing data
    availableProperties.clear();
    
    // Find real properties in ecosystem
    boolean foundRealProperties = findRealProperties();
    
    // If no real properties found or for demo purposes, add some sample data
    if (!foundRealProperties) {
        addSampleProperties();
    }
     else {
        System.out.println("Found " + availableProperties.size() + " real properties");
        // Uncomment the next line if you want to show sample properties alongside real ones
         addSampleProperties();
        System.out.println("Total properties after samples: " + availableProperties.size());
    }
    
    // Add properties to table
    for (Property property : availableProperties) {
            Object[] row = new Object[5];
            row[0] = property.getPropertyId();
            row[1] = property.getAddress();
            row[2] = "H" + property.getPropertyId();
            row[3] = property.getOwner() != null ? property.getOwner().getName() : "Sample Owner";
            row[4] = property.isForRent() ? "Rent" : "Sale";
            model.addRow(row);
        }
    
    System.out.println("Populated property table with " + availableProperties.size() + " properties");
}

private boolean findRealProperties() {
    boolean foundProperties = false;
    
    // Search for properties in PropertyFirmOrganizations
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof PropertyManagementEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof PropertyFirmOrganization) {
                    PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                    ArrayList<Property> properties = propertyFirm.getProperties();
                    
                    if (properties != null && !properties.isEmpty()) {
                        for (Property property : properties) {
                            if (property.isAvailable()) {
                                availableProperties.add(property);
                                foundProperties = true;
                                System.out.println("Found real property: " + property.getPropertyId() + " - " + property.getAddress());
                            }
                        }
                    }
                }
            }
        }
    }
    
    return foundProperties;
}

private void addSampleProperties() {
    String[] addresses = {
        "123 Main Street, Boston MA", 
        "456 Elm Avenue, Cambridge MA",
        "789 Oak Lane, Brookline MA",
        "101 Pine Road, Somerville MA",
        "234 Maple Court, Newton MA",
        "567 Cedar Boulevard, Medford MA",
        "890 Birch Street, Watertown MA"
    };
    
    String[] propertyIds = {"101", "202", "303", "404", "505", "606", "707"};
    boolean[] forRent = {true, false, true, false, true, false, true};
    double[] prices = {2500.00, 450000.00, 1800.00, 550000.00, 2200.00, 380000.00, 3000.00};
    
    for (int i = 0; i < addresses.length; i++) {
        Property sampleProperty = new Property();
        sampleProperty.setPropertyId(propertyIds[i]);
        sampleProperty.setAddress(addresses[i]);
        sampleProperty.setForRent(forRent[i]);
        sampleProperty.setAvailable(true);
        sampleProperty.setPrice(prices[i]);
        
        // Create sample owner (landlord)
        UserAccount owner = new UserAccount();
        owner.setName("Sample Owner " + (i+1));
        owner.setUsername("landlord" + (i+1));
        Landlord landlordRole = new Landlord();
        owner.setRole(landlordRole);
        sampleProperty.setOwner(owner);
        
        availableProperties.add(sampleProperty);
        System.out.println("Added sample property: " + sampleProperty.getPropertyId() + " - " + sampleProperty.getAddress());
    }
}

private void populateCartTable() {
    DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
    model.setRowCount(0); // Clear existing data
    
    if (cartProperties.isEmpty()) {
        model.addRow(new Object[]{"No properties added", "", ""});
    } else {
        for (Property property : cartProperties) {
            Object[] row = new Object[3];
            row[0] = property.getAddress();
            row[1] = "H" + property.getPropertyId();
            
            // Get price from map or property
            Double price = cartPriceMap.get(property);
            if (price == null) {
                price = property.getPrice();
                cartPriceMap.put(property, price);
            }
            
            // Format price based on whether it's for rent or sale
            String priceStr = property.isForRent() ? 
                "$" + String.format("%.2f", price) + "/month" : 
                "$" + String.format("%,.2f", price);
            
            row[2] = priceStr;
            model.addRow(row);
        }
    }
}

private void populatePropertyManagerComboBox() {
    cmbPropManager.removeAllItems();
    HashMap<String, UserAccount> propertyManagers = new HashMap<>();
    
    // Find property managers in ecosystem
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof PropertyManagementEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof PropertyFirmOrganization) {
                    for (UserAccount user : org.getUserAccountList()) {
                        if (user.getRole() instanceof PropertyManager) {
                            propertyManagers.put(user.getUsername(), user);
                            System.out.println("Found property manager in ecosystem: " + user.getName());
                        }
                    }
                }
            }
        }
    }
    
    // Also check UserAuthentication for property managers
    UserAuthentication userAuth = UserAuthentication.getInstance();
    for (UserAccount user : userAuth.getUserAccounts()) {
        if (user.getRole() instanceof PropertyManager) {
            propertyManagers.put(user.getUsername(), user);
            System.out.println("Found property manager in UserAuth: " + user.getName());
        }
    }
    
    // Add managers to combo box
    if (!propertyManagers.isEmpty()) {
        for (UserAccount manager : propertyManagers.values()) {
            cmbPropManager.addItem(manager.getName() + " (" + manager.getUsername() + ")");
        }
    } else {
        // Add sample property managers if none found
        cmbPropManager.addItem("John Smith (pm1)");
        cmbPropManager.addItem("Maria Garcia (pm2)");
        cmbPropManager.addItem("Wei Chen (pm3)");
    }
}





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProperty = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        btnProperty = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lblReady = new javax.swing.JLabel();
        lblApproved = new javax.swing.JLabel();
        cmbPropManager = new javax.swing.JComboBox<>();
        btnSubmit = new javax.swing.JButton();
        btnClick = new javax.swing.JButton();
        btnBank = new javax.swing.JButton();

        setBackground(new java.awt.Color(181, 218, 255));

        tblProperty.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Property Id", "Property Name", "House Number", "Landlord Name", "Available for"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProperty);

        btnAdd.setText("Add to List");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Property Name", "House Number", "Amount"
            }
        ));
        jScrollPane2.setViewportView(tblCart);

        btnProperty.setText("Property Details");
        btnProperty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropertyActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        lblReady.setText("Ready for purchasing?");

        lblApproved.setText("Lets get it approved");

        cmbPropManager.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPropManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPropManagerActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnClick.setText("Click here to explore Builders option available");
        btnClick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClickActionPerformed(evt);
            }
        });

        btnBank.setText("Click here to explore Banks");
        btnBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBankActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblReady)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemove))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblApproved)
                        .addGap(18, 18, 18)
                        .addComponent(cmbPropManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmit)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addGap(35, 35, 35)
                .addComponent(btnProperty)
                .addGap(54, 54, 54))
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(btnClick)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBank, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnProperty))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemove)
                    .addComponent(lblReady))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApproved, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPropManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClick)
                    .addComponent(btnBank))
                .addContainerGap(80, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    int selectedRow = tblProperty.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a property to add to your list.",
                "Selection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedRow >= availableProperties.size()) {
            JOptionPane.showMessageDialog(this,
                "Error retrieving property information.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get selected property
        Property selectedProperty = availableProperties.get(selectedRow);
        
        // Check if property is already in cart
        if (cartProperties.contains(selectedProperty)) {
            JOptionPane.showMessageDialog(this,
                "This property is already in your list.",
                "Duplicate",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Add to cart
        cartProperties.add(selectedProperty);
        cartPriceMap.put(selectedProperty, selectedProperty.getPrice());
        
        // Remove from available properties list and table
    availableProperties.remove(selectedRow);
    DefaultTableModel model = (DefaultTableModel) tblProperty.getModel();
    model.removeRow(selectedRow);
        
        // Refresh cart table
        populateCartTable();
        
        JOptionPane.showMessageDialog(this,
            "Property added to your list.",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);       

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnPropertyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropertyActionPerformed
        // TODO add your handling code here:
        //this shld go to properjpanel
        int selectedRow = tblProperty.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a property to view details.",
                "Selection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedRow >= availableProperties.size()) {
            JOptionPane.showMessageDialog(this,
                "Error retrieving property information.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get selected property
        Property selectedProperty = availableProperties.get(selectedRow);
        
        // Navigate to PropertyJPanel with selected property
        PropertyJPanel propertyPanel = new PropertyJPanel(ecosystem, account, selectedProperty);
        
        // Get parent container (should be userProcessContainer in MainJFrame)
        Container container = this.getParent();
        
        // Remove current panel
        container.remove(this);
        
        // Add property panel with selected property
        container.add("PropertyJPanel", propertyPanel);
        
        // Use CardLayout to show the new panel
        CardLayout layout = (CardLayout) container.getLayout();
        layout.next(container);
    }//GEN-LAST:event_btnPropertyActionPerformed

    private void cmbPropManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPropManagerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPropManagerActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblCart.getSelectedRow();
    
    if (selectedRow < 0 || cartProperties.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Please select a property to remove from your list.",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (selectedRow >= cartProperties.size()) {
        JOptionPane.showMessageDialog(this,
            "Error retrieving property information.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Remove from cart
    Property selectedProperty = cartProperties.remove(selectedRow);
    cartPriceMap.remove(selectedProperty);
    
     availableProperties.add(selectedProperty);
     
     // Update property table
    DefaultTableModel propertyModel = (DefaultTableModel) tblProperty.getModel();
    Object[] row = new Object[5];
    row[0] = selectedProperty.getPropertyId();
    row[1] = selectedProperty.getAddress();
    row[2] = "H" + selectedProperty.getPropertyId();
    row[3] = selectedProperty.getOwner() != null ? selectedProperty.getOwner().getName() : "Sample Owner";
    row[4] = selectedProperty.isForRent() ? "Rent" : "Sale";
    propertyModel.addRow(row);
     
    // Refresh cart table
    populateCartTable();
    
    JOptionPane.showMessageDialog(this,
        "Property removed from your list.",
        "Success",
        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
         if (cartProperties.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Your cart is empty. Please add properties before submitting a request.",
                "Empty Cart",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
         
         // Check if a cart item is selected
    int selectedRow = tblCart.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this,
            "Please select a property from your list to submit.",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
        
        String selectedManager = (String) cmbPropManager.getSelectedItem();
        if (selectedManager == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a property manager to process your request.",
                "Manager Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create a property purchase request
        UserAccount propertyManager = null;
        
        try {
            // Extract username from selection (format: "Name (username)")
            String username = selectedManager.substring(selectedManager.indexOf("(") + 1, selectedManager.indexOf(")"));
            
            // Find manager in ecosystem and UserAuthentication
            propertyManager = findUserByUsername(username);
            
            if (propertyManager == null) {
                // For demo purposes, create a dummy manager
                propertyManager = new UserAccount();
                propertyManager.setName(selectedManager.substring(0, selectedManager.indexOf("(")));
                propertyManager.setUsername(username);
                PropertyManager managerRole = new PropertyManager();
                propertyManager.setRole(managerRole);
            }
            
            // Create requests for all properties in cart
            submitPurchaseRequests(propertyManager);
            
            JOptionPane.showMessageDialog(this,
                "Your property request has been submitted!\nThe property manager will review your request soon.",
                "Request Submitted",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear cart after submission
            cartProperties.clear();
            cartPriceMap.clear();
            populateCartTable();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error submitting request: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnClickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClickActionPerformed
        // TODO add your handling code here:
         BuilderJPanel builderPanel = new BuilderJPanel(ecosystem, account);
    
    // Get the parent container (usually the main content panel)
    Container container = this.getParent();
    
    // Remove current panel
    container.remove(this);
    
    // Add the BuilderJPanel
    container.add("BuilderJPanel", builderPanel);
    
    // Use CardLayout to show the new panel
    CardLayout layout = (CardLayout) container.getLayout();
    layout.next(container);
    }//GEN-LAST:event_btnClickActionPerformed

    private void btnBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBankActionPerformed
        // TODO add your handling code here:
        BankJPanel bankPanel = new BankJPanel(ecosystem, account);
    
    // Get the parent container (usually the main content panel)
    Container container = this.getParent();
    
    // Remove current panel
    container.remove(this);
    
    // Add the BankJPanel
    container.add("BankJPanel", bankPanel);
    
    // Use CardLayout to show the new panel
    CardLayout layout = (CardLayout) container.getLayout();
    layout.next(container);
    }//GEN-LAST:event_btnBankActionPerformed
    // Helper method to find user by username
    private UserAccount findUserByUsername(String username) {
        // Check in ecosystem first
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            for (Organization org : enterprise.getOrganizationList()) {
                for (UserAccount user : org.getUserAccountList()) {
                    if (user.getUsername().equals(username)) {
                        return user;
                    }
                }
            }
        }
        
        // Then check in UserAuthentication
        UserAuthentication userAuth = UserAuthentication.getInstance();
        for (UserAccount user : userAuth.getUserAccounts()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        
        return null; // Not found
    }

    // Helper method to submit purchase requests
    private void submitPurchaseRequests(UserAccount propertyManager) {
        for (Property property : cartProperties) {
            // Create request
            PropertyPurchaseRequest request = new PropertyPurchaseRequest();
            request.setSender(account);
            request.setReceiver(propertyManager);
            request.setProperty(property);
            request.setRequestDate(new Date());
            request.setStatus(WorkRequest.RequestStatus.PENDING);
            request.setPrice(cartPriceMap.get(property));
            request.setRequestType(property.isForRent() ? "Rental" : "Purchase");
            request.setRequestId("PROP-REQ-" + System.currentTimeMillis() + "-" + property.getPropertyId());
            
            // Add to sender's work queue
            if (account.getRole() != null && account.getRole().getWorkQueue() != null) {
                account.getRole().getWorkQueue().addWorkRequest(request);
            }
            
            // Add to receiver's work queue
            if (propertyManager.getRole() != null && propertyManager.getRole().getWorkQueue() != null) {
                propertyManager.getRole().getWorkQueue().addWorkRequest(request);
            }
            
            // Add to organization work queue
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                if (enterprise instanceof PropertyManagementEnterprise) {
                    for (Organization org : enterprise.getOrganizationList()) {
                        if (org instanceof PropertyFirmOrganization) {
                            if (org.getWorkQueue() == null) {
                                org.setWorkQueue(new WorkQueue());
                            }
                            org.getWorkQueue().addWorkRequest(request);
                        }
                    }
                }
            }
        }
    }
    private void btnAddPropertyActionPerformed(java.awt.event.ActionEvent evt) {
    AddPropertyJPanel panel = new AddPropertyJPanel(ecosystem, account, this.getParent(), "BuyerWorkArea");
    panel.addButtonListeners();
    Container container = this.getParent();
    container.add(panel);
    CardLayout layout = (CardLayout) container.getLayout();
    layout.next(container);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBank;
    private javax.swing.JButton btnClick;
    private javax.swing.JButton btnProperty;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox<String> cmbPropManager;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApproved;
    private javax.swing.JLabel lblReady;
    private javax.swing.JTable tblCart;
    private javax.swing.JTable tblProperty;
    // End of variables declaration//GEN-END:variables
}
