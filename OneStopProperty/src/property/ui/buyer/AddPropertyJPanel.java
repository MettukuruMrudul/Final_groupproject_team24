/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package property.ui.buyer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.Landlord;
import property.backend.users.Property;
import property.backend.users.UserAccount;
import ui.signUpLandingPage.BuyerWorkAreaJPanel;
import ui.signUpLandingPage.PropertyManagerWorkAreaJPanel;

/**
 *
 * @author krishi parekh
 */
public class AddPropertyJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private Container userProcessContainer;
    private String callingPanel;
    
     // Define color scheme
    private static final Color TEXT_COLOR = new Color(0, 153, 153);        // Teal for text
    private static final Color BACKGROUND_COLOR = new Color(181, 218, 255); // Light blue background
    private static final Color HIGHLIGHT_COLOR = new Color(181, 218, 255);  // Light blue for button highlights
    private static final Color WHITE = Color.WHITE;
    private static final Color BUTTON_ORIGINAL = new Color(238, 238, 238);  // Default button color
    private static final Color BUTTON_HOVER = HIGHLIGHT_COLOR.darker();  
    /**
     * Creates new form AddPropertyJPanel
     */
    public AddPropertyJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem = ecosystem;
        this.account = account;
        this.userProcessContainer = this.getParent();
        initComponents();
        applyCustomStyling();
        populateAvailabilityComboBox();
        generatePropertyId();
    }
    
   
    public AddPropertyJPanel(RealEstateNetwork ecosystem, UserAccount account, Container userProcessContainer, String callingPanel) {
        this.ecosystem = ecosystem;
        this.account = account;
        this.userProcessContainer = userProcessContainer;
        this.callingPanel = callingPanel;
        initComponents();
        applyCustomStyling();
        populateAvailabilityComboBox();
        generatePropertyId();
        addButtonListeners();
    }
    
    // Method to apply custom styling
    private void applyCustomStyling() {
        // Style the main panel
        this.setBackground(BACKGROUND_COLOR);
        
        // Style all labels
        styleLabels();
        
        // Style text fields
        styleTextFields();
        
        // Style combo box
        styleComboBox();
        
        // Apply button highlight effects
        applyButtonHighlights();
        
        System.out.println("Custom styling applied to AddPropertyJPanel");
    }
    
    private void styleLabels() {
        // Style title label
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        // Style all other labels
        lblId.setForeground(TEXT_COLOR);
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        lblName.setForeground(TEXT_COLOR);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        lblNumber.setForeground(TEXT_COLOR);
        lblNumber.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        lblOwnerName.setForeground(TEXT_COLOR);
        lblOwnerName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        lblAvail.setForeground(TEXT_COLOR);
        lblAvail.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }
    
    private void styleTextFields() {
        // Style all text fields
        styleTextField(txtId);
        styleTextField(txtName);
        styleTextField(txtNumber);
        styleTextField(txtOwnerName);
    }
    
    private void styleTextField(javax.swing.JTextField textField) {
        textField.setBackground(WHITE);
        textField.setForeground(TEXT_COLOR);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        // Add focus effects
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TEXT_COLOR, 2),
                    BorderFactory.createEmptyBorder(4, 7, 4, 7)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TEXT_COLOR, 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
        });
    }
    
    private void styleComboBox() {
        cmbAvail.setBackground(WHITE);
        cmbAvail.setForeground(TEXT_COLOR);
        cmbAvail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbAvail.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
    }
    
    private void applyButtonHighlights() {
        // Apply highlight effects to both buttons
        applyButtonHoverEffect(btnAdd, BUTTON_ORIGINAL, HIGHLIGHT_COLOR, BUTTON_HOVER);
        applyButtonHoverEffect(btnBack, BUTTON_ORIGINAL, HIGHLIGHT_COLOR, BUTTON_HOVER);
    }
    
    private void applyButtonHoverEffect(JButton button, Color originalColor, Color highlightColor, Color hoverColor) {
        // Set initial styling
        button.setBackground(originalColor);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        
        // Add mouse listeners for hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(highlightColor);
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                
                // Smooth transition effect
                javax.swing.Timer timer = new javax.swing.Timer(10, null);
                timer.addActionListener(new java.awt.event.ActionListener() {
                    float alpha = 0.5f;
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        alpha += 0.1f;
                        if (alpha >= 1.0f) {
                            alpha = 1.0f;
                            timer.stop();
                        }
                        Color currentColor = new Color(
                            (int)(highlightColor.getRed() * alpha + originalColor.getRed() * (1-alpha)),
                            (int)(highlightColor.getGreen() * alpha + originalColor.getGreen() * (1-alpha)),
                            (int)(highlightColor.getBlue() * alpha + originalColor.getBlue() * (1-alpha))
                        );
                        button.setBackground(currentColor);
                        button.repaint();
                    }
                });
                timer.start();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (button.contains(evt.getPoint())) {
                    button.setBackground(highlightColor);
                } else {
                    button.setBackground(originalColor);
                }
            }
        });
    }
    
     private void populateAvailabilityComboBox() {
        cmbAvail.removeAllItems();
        cmbAvail.addItem("Rent");
        cmbAvail.addItem("Sale");
        cmbAvail.addItem("Both");
    }
    
     private void generatePropertyId() {
        // Generate a unique ID for the property
        int propCount = getPropertyCount() + 1;
        txtId.setText("PROP" + propCount);
    }
     
     private int getPropertyCount() {
        int count = 0;
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                        if (propertyFirm.getProperties() != null) {
                            count += propertyFirm.getProperties().size();
                        }
                    }
                }
            }
        }
        return count;
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
        lblId = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblNumber = new javax.swing.JLabel();
        lblOwnerName = new javax.swing.JLabel();
        lblAvail = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtNumber = new javax.swing.JTextField();
        txtOwnerName = new javax.swing.JTextField();
        cmbAvail = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(181, 218, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Add property details");

        lblId.setText("Property ID");

        lblName.setText("Property Name");

        lblNumber.setText("House Number");

        lblOwnerName.setText("Landlord Name");

        lblAvail.setText("Available for");

        txtId.setEnabled(false);

        cmbAvail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAdd.setText("Add");
        btnAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNumber)
                                .addGap(228, 228, 228)
                                .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(229, 229, 229)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblAvail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblOwnerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(227, 227, 227)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOwnerName, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbAvail, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(389, 389, 389)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(488, 488, 488)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(416, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(lblTitle))
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOwnerName)
                    .addComponent(txtOwnerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAvail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAvail))
                .addGap(33, 33, 33)
                .addComponent(btnAdd)
                .addContainerGap(310, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
       if (txtName.getText().isEmpty() || txtNumber.getText().isEmpty() || txtOwnerName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields",
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create a new property
             Property newProperty = new Property();
            newProperty.setPropertyId(txtId.getText());
            
            // Construct full address from property name and house number
            String fullAddress = txtNumber.getText() + " " + txtName.getText();
            newProperty.setAddress(fullAddress);
            
            // Set availability based on selection
            String availability = cmbAvail.getSelectedItem().toString();
            if (availability.equals("Rent")) {
                newProperty.setForRent(true);
                newProperty.setPrice(2000.00); // Default monthly rent
            } else if (availability.equals("Sale")) {
                newProperty.setForRent(false);
                newProperty.setPrice(350000.00); // Default sale price
            } else { // Both
                newProperty.setForRent(true); // Set to rent by default, can be toggled later
                newProperty.setPrice(2000.00); // Default monthly rent
            }
            
            // Set owner (landlord) information
            UserAccount owner = new UserAccount();
            owner.setName(txtOwnerName.getText());
            owner.setUsername("landlord" + txtId.getText());
            Landlord landlordRole = new Landlord();
            owner.setRole(landlordRole);
            newProperty.setOwner(owner);
            
            // Set property as available
            newProperty.setAvailable(true);
            
            // Add property to the ecosystem
            addPropertyToEcosystem(newProperty);
            
            JOptionPane.showMessageDialog(this,
                "Property added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Return to the appropriate panel
            resetFields();
            generatePropertyId();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error adding property: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    // Reset form fields
    private void resetFields() {
        txtName.setText("");
        txtNumber.setText("");
        txtOwnerName.setText("");
        cmbAvail.setSelectedIndex(0);
    }
    
    /**
     * Add property to the ecosystem
     */
    private void addPropertyToEcosystem(Property property) {
        boolean added = false;
        
        // Find a property firm organization to add the property to
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization) {
                        PropertyFirmOrganization propertyFirm = (PropertyFirmOrganization) org;
                        propertyFirm.addProperty(property);
                        added = true;
                        break;
                    }
                }
                if (added) break;
            }
        }
        
        // If no property firm exists, create one and add the property
        if (!added) {
            createPropertyFirmAndAddProperty(property);
        }
    }
    
    /**
     * Create a property firm and add the property to it
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
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        goBack();
    }
    
    private void goBack() {
        if (userProcessContainer != null) {
            userProcessContainer.remove(this);
            
            // Return to the appropriate panel based on where we came from
            if ("BuyerWorkArea".equals(callingPanel)) {
                // Create a new BuyerWorkAreaJPanel to refresh the data
                ui.signUpLandingPage.BuyerWorkAreaJPanel buyerPanel = new ui.signUpLandingPage.BuyerWorkAreaJPanel(ecosystem, account);
                userProcessContainer.add(buyerPanel);
            } 
            else if ("PropertyManagerWorkArea".equals(callingPanel)) {
                // Create a new PropertyManagerWorkAreaJPanel to refresh the data
                ui.signUpLandingPage.PropertyManagerWorkAreaJPanel managerPanel = new ui.signUpLandingPage.PropertyManagerWorkAreaJPanel(ecosystem, account);
                userProcessContainer.add(managerPanel);
            }
            
            // Use CardLayout to show the panel
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        }
    }

    public void addButtonListeners() {
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
    

    
    
    }//GEN-LAST:event_btnBackActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<String> cmbAvail;
    private javax.swing.JLabel lblAvail;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblOwnerName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtOwnerName;
    // End of variables declaration//GEN-END:variables
}
