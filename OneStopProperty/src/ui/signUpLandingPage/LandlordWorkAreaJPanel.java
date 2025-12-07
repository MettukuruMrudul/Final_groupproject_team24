/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.UserAccount;
import property.backend.workqueue.PropertyPurchaseRequest;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author krishi parekh
 */
public class LandlordWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<PropertyPurchaseRequest> approvedRequests;
    
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color tableColor = new Color(181, 218, 255);
    
    /**
     * Creates new form LandlordWorkAreaJPanel
     */
    public LandlordWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        this.approvedRequests = new ArrayList<>();
        initComponents();
        
         applyCustomStyling();
        
        // Initialize the status combo box
        populateStatusComboBox();
        
        // Populate the table with approved requests
        populateApprovedRequestsTable();
    }
    
    /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
        // Apply button highlighting
        highlightButtons();
        
        // Apply table styling
        styleTable(tblBuyer);
        
        // Style combo box
        styleComboBox();
    }
    
    /**
     * Apply highlight color to all buttons
     */
    private void highlightButtons() {
        // Set background color for all buttons
        btnRedirect.setBackground(highlightColor);
        btnRedirect.setOpaque(true);
        btnRedirect.setBorderPainted(false);
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
     * Style the combo box
     */
    private void styleComboBox() {
        cmbStatus.setBackground(new Color(240, 248, 255)); // Alice blue background
        cmbStatus.setForeground(textColor); // Teal text for consistency
    }
    
    private void populateStatusComboBox() {
        cmbStatus.removeAllItems();
        cmbStatus.addItem("Pending");
        cmbStatus.addItem("Approved");
        cmbStatus.addItem("Rejected");
        cmbStatus.addItem("Ready for Inspection");
    }
    
    private void populateApprovedRequestsTable() {
    DefaultTableModel model = (DefaultTableModel) tblBuyer.getModel();
    model.setRowCount(0); // Clear existing data
    approvedRequests.clear();
    
    // Find approved property requests for properties owned by this landlord
    findApprovedRequests();
    
    // If no approved requests found, add sample data for demonstration
    if (approvedRequests.isEmpty()) {
        addSampleApprovedRequests();
    }
    
    // Add requests to the table
    for (PropertyPurchaseRequest request : approvedRequests) {
        Object[] row = new Object[4];
        
        // House Number
        String houseNumber = "Unknown";
        if (request.getProperty() != null) {
            if (request.getProperty().getAddress() != null && !request.getProperty().getAddress().isEmpty()) {
                // Try to extract house number from address (assuming it's at the beginning)
                String[] addressParts = request.getProperty().getAddress().split(" ", 2);
                if (addressParts.length > 0 && !addressParts[0].isEmpty()) {
                    houseNumber = addressParts[0];
                }
            } else {
                // Fallback to property ID
                houseNumber = "H" + request.getProperty().getPropertyId();
            }
        }
        row[0] = houseNumber;
        
        // Landlord Name
        row[1] = request.getProperty() != null && request.getProperty().getOwner() != null ? 
                request.getProperty().getOwner().getName() : "Unknown";
        
        // Buyer Phone Number
        row[2] = getBuyerPhone(request);
        
        // Status
        row[3] = request.getStatus().getValue();
        
        model.addRow(row);
    }
    
    System.out.println("Populated landlord table with " + approvedRequests.size() + " approved requests");
}
    
    private void findApprovedRequests() {
    System.out.println("Looking for approved requests for landlord: " + account.getUsername());
    
    // Check all organizations for work requests
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        if (enterprise instanceof PropertyManagementEnterprise) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org instanceof PropertyFirmOrganization && org.getWorkQueue() != null) {
                    for (WorkRequest request : org.getWorkQueue().getWorkRequestList()) {
                        if (request instanceof PropertyPurchaseRequest) {
                            PropertyPurchaseRequest purchaseRequest = (PropertyPurchaseRequest) request;
                            
                            System.out.println("Found request: " + purchaseRequest.getRequestId() + 
                                ", Status: " + purchaseRequest.getStatus().getValue());
                            
                            // Debug property owner information
                            if (purchaseRequest.getProperty() != null) {
                                System.out.println("Property: " + purchaseRequest.getProperty().getPropertyId());
                                
                                if (purchaseRequest.getProperty().getOwner() != null) {
                                    System.out.println("Property owner: " + 
                                        purchaseRequest.getProperty().getOwner().getUsername() + 
                                        ", Current user: " + account.getUsername());
                                } else {
                                    System.out.println("Property has no owner set");
                                }
                            }
                            
                            // Try both APPROVED enum check and string value check
                            boolean isApproved = false;
                            try {
                                isApproved = purchaseRequest.getStatus() == WorkRequest.RequestStatus.APPROVED;
                            } catch (Exception e) {
                                // Try string check as fallback
                                isApproved = "Approved".equals(purchaseRequest.getStatus().getValue());
                            }
                            
                            if (isApproved && purchaseRequest.getProperty() != null) {
                                // Add this request even if owner doesn't match for debugging
                                approvedRequests.add(purchaseRequest);
                                System.out.println("Added approved request: " + purchaseRequest.getRequestId());
                            }
                        }
                    }
                }
            }
        }
    }
    
    // If nothing found, output debug info
    if (approvedRequests.isEmpty()) {
        System.out.println("No approved requests found for landlord: " + account.getUsername());
    }

    
    // If nothing found, output debug info
    if (approvedRequests.isEmpty()) {
        System.out.println("No approved requests found for landlord: " + account.getUsername());
    }
}
    
    private void addSampleApprovedRequests() {
        // Create sample approved requests for demonstration
        for (int i = 0; i < 3; i++) {
            PropertyPurchaseRequest request = new PropertyPurchaseRequest();
            request.setRequestId("REQ-" + (i + 100));
            request.setStatus(WorkRequest.RequestStatus.APPROVED);
            
            // Set sample sender (buyer)
            UserAccount sender = new UserAccount();
            sender.setName("Approved Buyer " + (i + 1));
            sender.setUsername("buyer" + (i + 100));
            request.setSender(sender);
            
            // Set sample property with this landlord as owner
            property.backend.users.Property property = new property.backend.users.Property();
            property.setPropertyId("20" + (i + 1));
            property.setAddress("Approved Property " + (i + 1));
            property.setPrice(i % 2 == 0 ? 350000 : 2000);
            property.setForRent(i % 2 != 0);
            
            // Set this landlord as owner
            property.setOwner(account);
            
            request.setProperty(property);
            request.setRequestType(property.isForRent() ? "Rental" : "Purchase");
            request.setPrice(property.getPrice());
            
            approvedRequests.add(request);
        }
    }
    
    private String getBuyerPhone(PropertyPurchaseRequest request) {
        // In a real app, you would get this from user profile
        // For demonstration, generate a sample phone number
        if (request.getSender() != null) {
            String username = request.getSender().getUsername();
            return "555-" + (100 + Math.abs(username.hashCode() % 900)) + "-" + 
                   (1000 + Math.abs(username.hashCode() % 9000));
        }
        
        return "Unknown";
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
        tblBuyer = new javax.swing.JTable();
        btnRedirect = new javax.swing.JButton();
        cmbStatus = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(181, 218, 255));

        tblBuyer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "House Number", "Landlord Name", "Buyer Phone Number", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBuyer);

        btnRedirect.setText("Redirect to Housing Inspector");
        btnRedirect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedirectActionPerformed(evt);
            }
        });

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRedirect)
                .addGap(18, 18, 18)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRedirect)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(453, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRedirectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedirectActionPerformed
        // TODO add your handling code here:
     //   private void btnRedirectActionPerformed(java.awt.event.ActionEvent evt) {
    int selectedRow = tblBuyer.getSelectedRow();
    
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this,
            "Please select a request to redirect.",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (selectedRow >= approvedRequests.size()) {
        JOptionPane.showMessageDialog(this,
            "Error retrieving request information.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Get selected request
    PropertyPurchaseRequest selectedRequest = approvedRequests.get(selectedRow);
    
    try {
        // Try to set status using enum value - this avoids using fromValue
        WorkRequest.RequestStatus newStatus = WorkRequest.RequestStatus.READY_FOR_INSPECTION;
        selectedRequest.setStatus(newStatus);
        
        // Update status in all work queues
        updateRequestStatus(selectedRequest);
        
        // Refresh table
        populateApprovedRequestsTable();
        
        JOptionPane.showMessageDialog(this,
            "Request has been redirected to Housing Inspector.",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (Exception e) {
        // If the enum doesn't have READY_FOR_INSPECTION, handle that case
        System.out.println("Error setting request status: " + e.getMessage());
        
        JOptionPane.showMessageDialog(this,
            "Could not update request status: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    
}

    }//GEN-LAST:event_btnRedirectActionPerformed

    private void updateRequestStatus(PropertyPurchaseRequest request) {
        // Update in all work queues (sender, receiver, organizations)
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org.getWorkQueue() != null) {
                    for (WorkRequest req : org.getWorkQueue().getWorkRequestList()) {
                        if (req instanceof PropertyPurchaseRequest && 
                                ((PropertyPurchaseRequest) req).getRequestId().equals(request.getRequestId())) {
                            req.setStatus(request.getStatus());
                        }
                    }
                }
            }
        }
        
        // Update in sender's work queue if available
        if (request.getSender() != null && request.getSender().getRole() != null &&
                request.getSender().getRole().getWorkQueue() != null) {
            for (WorkRequest req : request.getSender().getRole().getWorkQueue().getWorkRequestList()) {
                if (req instanceof PropertyPurchaseRequest && 
                        ((PropertyPurchaseRequest) req).getRequestId().equals(request.getRequestId())) {
                    req.setStatus(request.getStatus());
                }
            }
        }
        
        // Update in receiver's work queue if available
        if (request.getReceiver() != null && request.getReceiver().getRole() != null &&
                request.getReceiver().getRole().getWorkQueue() != null) {
            for (WorkRequest req : request.getReceiver().getRole().getWorkQueue().getWorkRequestList()) {
                if (req instanceof PropertyPurchaseRequest && 
                        ((PropertyPurchaseRequest) req).getRequestId().equals(request.getRequestId())) {
                    req.setStatus(request.getStatus());
                }
            }
        }
    }
    
    // Add this to your initComponents() method
    // btnRedirect.addActionListener(new java.awt.event.ActionListener() {
    //     public void actionPerformed(java.awt.event.ActionEvent evt) {
    //         btnRedirectActionPerformed(evt);
    //     }
    // });


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRedirect;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBuyer;
    // End of variables declaration//GEN-END:variables
}
