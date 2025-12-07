/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JButton;
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
import property.backend.users.UserAccount;
import property.ui.buyer.AddPropertyJPanel;
import property.ui.buyer.ManagePropertyRequestJPanel;

/**
 *
 * @author krishi parekh
 */
public class PropertyManagerWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<Property> propertyList;
    
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color tableColor = new Color(181, 218, 255);
    /**
     * Creates new form PropertyManagerWorkAreaJPanel
     */
    public PropertyManagerWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        this.propertyList = new ArrayList<>();

        initComponents();
        applyCustomStyling();
        populatePropertyTable();
    }
    
    /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
        // Apply button highlighting
        highlightButtons();
        
        // Apply table styling
        styleTable(tblProperty);
    }
    
    /**
     * Apply highlight color to all buttons
     */
    private void highlightButtons() {
        // Set background color for all buttons
        styleButton(btnAdd);
        styleButton(btnModify);
        styleButton(btnDelete);
        styleButton(btnManage);
    }
    
    /**
     * Apply styling to a specific button
     */
    private void styleButton(JButton button) {
        button.setBackground(highlightColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
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
    
    /**
     * Populates the property table with property data from the ecosystem
     */
    private void populatePropertyTable() {
        DefaultTableModel model = (DefaultTableModel) tblProperty.getModel();
        model.setRowCount(0); // Clear existing data
        propertyList.clear();
        
        // Find properties in the ecosystem
        boolean foundProperties = findProperties();
        
        // If no properties found, add sample data for demonstration
        if (!foundProperties) {
            addSampleProperties();
        }
        
        // Add properties to the table
        for (Property property : propertyList) {
            Object[] row = new Object[5];
            row[0] = property.getPropertyId();
            row[1] = property.getAddress();
            row[2] = "H" + property.getPropertyId();
            row[3] = property.getOwner() != null ? property.getOwner().getName() : "Unknown";
            row[4] = property.isForRent() ? "Rent" : "Sale";
            model.addRow(row);
        }
        
        System.out.println("Populated property table with " + propertyList.size() + " properties");
    }
    
    /**
     * Finds properties in the ecosystem
     * @return true if properties were found, false otherwise
     */
    private boolean findProperties() {
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
                                propertyList.add(property);
                                foundProperties = true;
                                System.out.println("Found property: " + property.getPropertyId() + " - " + property.getAddress());
                            }
                        }
                    }
                }
            }
        }
        
        return foundProperties;
    }
    
    /**
     * Adds sample properties for demonstration purposes
     */
    private void addSampleProperties() {
        String[] addresses = {
            "123 Main Street, Boston MA", 
            "456 Elm Avenue, Cambridge MA",
            "789 Oak Lane, Brookline MA",
            "101 Pine Road, Somerville MA",
            "234 Maple Court, Newton MA"
        };
        
        String[] propertyIds = {"101", "202", "303", "404", "505"};
        boolean[] forRent = {true, false, true, false, true};
        double[] prices = {2500.00, 450000.00, 1800.00, 550000.00, 2200.00};
        
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
            
            // Add to property list
            propertyList.add(sampleProperty);
            
            // Add to property firm if available
            addPropertyToFirm(sampleProperty);
            
            System.out.println("Added sample property: " + sampleProperty.getPropertyId() + " - " + sampleProperty.getAddress());
        }
    }
    
    /**
     * Adds a property to a PropertyFirmOrganization in the ecosystem
     * @param property The property to add
     */
    private void addPropertyToFirm(Property property) {
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                        propertyFirm.addProperty(property);
                        return;
                    }
                }
            }
        }
        
        // If no property firm exists, create one
        System.out.println("No property firm found, creating new one");
        createPropertyFirmAndAddProperty(property);
    }
    
    /**
     * Creates a new PropertyFirmOrganization and adds a property to it
     * @param property The property to add
     */
    private void createPropertyFirmAndAddProperty(Property property) {
        // Find or create PropertyManagementEnterprise
        PropertyManagementEnterprise pmEnterprise = null;
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                pmEnterprise = (PropertyManagementEnterprise) enterprise;
                break;
            }
        }
        
        if (pmEnterprise == null) {
            pmEnterprise = new PropertyManagementEnterprise("Property Management");
            ecosystem.addEnterprise(pmEnterprise);
        }
        
        // Create PropertyFirmOrganization
        PropertyFirmOrganization propertyFirm = new PropertyFirmOrganization("Property Firm", pmEnterprise);
        pmEnterprise.addOrganization(propertyFirm);
        
        // Add property to firm
        propertyFirm.addProperty(property);
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
        btnModify = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnManage = new javax.swing.JButton();

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

        btnAdd.setText("Add Property");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnModify.setText("Modify Property");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete Property");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnManage.setText("Manage Requests");
        btnManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnManage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 683, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addGap(33, 33, 33)
                .addComponent(btnModify)
                .addGap(29, 29, 29)
                .addComponent(btnDelete)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnManage)
                        .addComponent(btnModify)
                        .addComponent(btnDelete)))
                .addGap(0, 342, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        //addPropertyJPanel
   // private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
    AddPropertyJPanel panel = new AddPropertyJPanel(ecosystem, account, this.getParent(), "PropertyManagerWorkArea");
   // panel.addButtonListeners();
    Container container = this.getParent();
    container.add(panel);
    CardLayout layout = (CardLayout) container.getLayout();
    layout.next(container);

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        // TODO add your handling code here:
        //shld display pop up message to modify
        int selectedRow = tblProperty.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a property to modify.",
                "Selection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedRow >= propertyList.size()) {
            JOptionPane.showMessageDialog(this,
                "Error retrieving property information.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get selected property
        Property selectedProperty = propertyList.get(selectedRow);
        
        // Show dialog to modify property address
        String newAddress = JOptionPane.showInputDialog(this,
            "Enter new address for property:",
            selectedProperty.getAddress());
        
        if (newAddress != null && !newAddress.trim().isEmpty()) {
            selectedProperty.setAddress(newAddress);
            
            // Show dialog to modify property price
            String priceStr = JOptionPane.showInputDialog(this,
                "Enter new price for property:",
                String.valueOf(selectedProperty.getPrice()));
            
            try {
                double newPrice = Double.parseDouble(priceStr);
                selectedProperty.setPrice(newPrice);
                
                // Show dialog to modify property type (rent/sale)
                Object[] options = {"Rent", "Sale"};
                int typeChoice = JOptionPane.showOptionDialog(this,
                    "Property available for:",
                    "Modify Property Type",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    selectedProperty.isForRent() ? options[0] : options[1]);
                
                if (typeChoice != JOptionPane.CLOSED_OPTION) {
                    selectedProperty.setForRent(typeChoice == 0); // 0 = Rent, 1 = Sale
                    
                    // Update property in the ecosystem
                    updatePropertyInEcosystem(selectedProperty);
                    
                    // Refresh table to show updated information
                    populatePropertyTable();
                    
                    JOptionPane.showMessageDialog(this,
                        "Property has been updated successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid price format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnModifyActionPerformed

    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageActionPerformed
        // TODO add your handling code here:
        //managePropertyRequestJPanel
        ManagePropertyRequestJPanel manageRequestPanel = new ManagePropertyRequestJPanel(ecosystem, account);
        
        // Get parent container
        Container container = this.getParent();
        
        // Remove current panel
        container.remove(this);
        
        // Add new panel
        container.add("ManagePropertyRequestJPanel", manageRequestPanel);
        
        // Use CardLayout to show the new panel
        CardLayout layout = (CardLayout) container.getLayout();
        layout.next(container);
    }//GEN-LAST:event_btnManageActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblProperty.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a property to delete.",
                "Selection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedRow >= propertyList.size()) {
            JOptionPane.showMessageDialog(this,
                "Error retrieving property information.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get selected property
        Property selectedProperty = propertyList.get(selectedRow);
        
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete property " + selectedProperty.getPropertyId() + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Remove property from ecosystem
            removePropertyFromEcosystem(selectedProperty);
            
            // Remove from local list
            propertyList.remove(selectedRow);
            
            // Update table
            DefaultTableModel model = (DefaultTableModel) tblProperty.getModel();
            model.removeRow(selectedRow);
            
            JOptionPane.showMessageDialog(this,
                "Property has been deleted successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * Updates a property in the ecosystem
     * @param property The property to update
     */
    private void updatePropertyInEcosystem(Property property) {
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                        propertyFirm.updateProperty(property);
                    }
                }
            }
        }
    }
    
    /**
     * Removes a property from the ecosystem
     * @param property The property to remove
     */
    private void removePropertyFromEcosystem(Property property) {
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                        propertyFirm.removeProperty(property);
                    }
                }
            }
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnManage;
    private javax.swing.JButton btnModify;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProperty;
    // End of variables declaration//GEN-END:variables
}
