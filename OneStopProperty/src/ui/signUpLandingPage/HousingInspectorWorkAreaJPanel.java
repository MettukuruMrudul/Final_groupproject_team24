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
 * @author nihar
 */
public class HousingInspectorWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<PropertyPurchaseRequest> inspectionRequests;
    
     // Define colors
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color tableColor = new Color(181, 218, 255);
    /**
     * Creates new form HousingInspectorWorkAreaJPanel
     */
    public HousingInspectorWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
         this.inspectionRequests = new ArrayList<>();
        initComponents();
         applyCustomStyling();
        // Initialize the status combo box
        populateStatusComboBox();
        
        // Populate the table with requests ready for inspection
        populateInspectionRequestsTable();
    }
    
    /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
        // Apply button highlighting
        highlightButtons();
        
        // Apply table styling
        styleTable(tblProperty);
        
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
        cmbStatus.addItem("Pass Inspection");
        cmbStatus.addItem("Fail Inspection");
        cmbStatus.addItem("Pending Repairs");
    }
    
    private void populateInspectionRequestsTable() {
        DefaultTableModel model = (DefaultTableModel) tblProperty.getModel();
        model.setRowCount(0); // Clear existing data
        inspectionRequests.clear();
        
        // Find requests ready for inspection
        findInspectionRequests();
        
        // If no requests found, add sample data for demonstration
        if (inspectionRequests.isEmpty()) {
            addSampleInspectionRequests();
        }
        
        // Add requests to the table
        for (PropertyPurchaseRequest request : inspectionRequests) {
            Object[] row = new Object[4];
            
            // Extract property name from address or use ID as fallback
            String propertyName = "Unknown";
            if (request.getProperty() != null) {
                if (request.getProperty().getAddress() != null && !request.getProperty().getAddress().isEmpty()) {
                    // Use address as property name
                    propertyName = request.getProperty().getAddress();
                } else {
                    // Fallback to property ID
                    propertyName = request.getProperty().getPropertyId();
                }
            }
            row[0] = propertyName;
            
            // Landlord name
            row[1] = request.getProperty() != null && request.getProperty().getOwner() != null ? 
                    request.getProperty().getOwner().getName() : "Unknown";
            
            // House number - extract from address
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
            row[2] = houseNumber;
            
            // Status
            row[3] = request.getStatus().getValue();
            
            model.addRow(row);
        }
        
        System.out.println("Populated inspector table with " + inspectionRequests.size() + " inspection requests");
    }
    
    private void findInspectionRequests() {
    System.out.println("Looking for inspection requests...");
    
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
                            
                            // Check for both READY_FOR_INSPECTION and inspection-related statuses
                            String status = purchaseRequest.getStatus().getValue();
                            boolean isInspectionRelated = false;
                            
                            try {
                                // Try with enum
                                isInspectionRelated = 
                                    purchaseRequest.getStatus() == WorkRequest.RequestStatus.READY_FOR_INSPECTION ||
                                    purchaseRequest.getStatus() == WorkRequest.RequestStatus.INSPECTION_PASSED ||
                                    purchaseRequest.getStatus() == WorkRequest.RequestStatus.INSPECTION_FAILED ||
                                    purchaseRequest.getStatus() == WorkRequest.RequestStatus.PENDING_REPAIRS;
                            } catch (Exception e) {
                                // Fallback to string comparison
                                isInspectionRelated = 
                                    "Ready for Inspection".equals(status) || 
                                    "Inspection Passed".equals(status) || 
                                    "Inspection Failed".equals(status) || 
                                    "Pending Repairs".equals(status);
                            }
                            
                            if (isInspectionRelated) {
                                inspectionRequests.add(purchaseRequest);
                                System.out.println("Added inspection-related request: " + purchaseRequest.getRequestId());
                            }
                        }
                    }
                }
            }
        }
    }
    
    // If nothing found, output debug info
    if (inspectionRequests.isEmpty()) {
        System.out.println("No inspection-related requests found");
    }
}
    
    private void addSampleInspectionRequests() {
        // Create sample inspection requests for demonstration
        for (int i = 0; i < 3; i++) {
            PropertyPurchaseRequest request = new PropertyPurchaseRequest();
            request.setRequestId("REQ-" + (i + 200));
           // request.setStatus(WorkRequest.RequestStatus.valueOf("READY_FOR_INSPECTION"));
            try {
                // Try to set to READY_FOR_INSPECTION
                request.setStatus(WorkRequest.RequestStatus.READY_FOR_INSPECTION);
            } catch (Exception e) {
                // Fallback to APPROVED if READY_FOR_INSPECTION doesn't exist
                request.setStatus(WorkRequest.RequestStatus.APPROVED);
                System.out.println("Used APPROVED status as fallback: " + e.getMessage());
            }
            
            // Set sample sender (buyer)
            UserAccount sender = new UserAccount();
            sender.setName("Inspection Buyer " + (i + 1));
            sender.setUsername("buyer" + (i + 200));
            request.setSender(sender);
            
            // Set sample property
            property.backend.users.Property property = new property.backend.users.Property();
            property.setPropertyId("30" + (i + 1));
            property.setAddress("Inspection Property " + (i + 1));
            property.setPrice(i % 2 == 0 ? 320000 : 1800);
            property.setForRent(i % 2 != 0);
            
            // Set sample owner
            UserAccount owner = new UserAccount();
            owner.setName("Landlord " + (i + 1));
            owner.setUsername("landlord" + (i + 1));
            property.setOwner(owner);
            
            request.setProperty(property);
            request.setRequestType(property.isForRent() ? "Rental" : "Purchase");
            request.setPrice(property.getPrice());
            
            inspectionRequests.add(request);
            System.out.println("Added sample inspection request: " + request.getRequestId());
        
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
        btnRedirect = new javax.swing.JButton();
        cmbStatus = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(181, 218, 255));

        tblProperty.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Property Name", "Landlord Name", "House Number", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProperty);

        btnRedirect.setText("Update");
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
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRedirect)
                .addGap(18, 18, 18)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRedirect)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(467, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRedirectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedirectActionPerformed
        // TODO add your handling code here:
         int selectedRow = tblProperty.getSelectedRow();
    
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this,
            "Please select a property to update.",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (selectedRow >= inspectionRequests.size()) {
        JOptionPane.showMessageDialog(this,
            "Error retrieving request information.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Get selected request
    PropertyPurchaseRequest selectedRequest = inspectionRequests.get(selectedRow);
    
    // Get inspection result from combo box
    String statusText = (String) cmbStatus.getSelectedItem();
    WorkRequest.RequestStatus newStatus;
    
    try {
        // Set appropriate status based on selection
        if ("Pass Inspection".equals(statusText)) {
            newStatus = WorkRequest.RequestStatus.INSPECTION_PASSED;
        } else if ("Fail Inspection".equals(statusText)) {
            newStatus = WorkRequest.RequestStatus.INSPECTION_FAILED;
        } else {
            newStatus = WorkRequest.RequestStatus.PENDING_REPAIRS;
        }
        
        // Update status
        selectedRequest.setStatus(newStatus);
        
        // Update status in all work queues
        updateRequestStatus(selectedRequest);
        
        // Don't remove from table, just update the status display
        DefaultTableModel model = (DefaultTableModel) tblProperty.getModel();
        model.setValueAt(newStatus.getValue(), selectedRow, 3);
        
        JOptionPane.showMessageDialog(this,
            "Inspection status has been updated to: " + statusText,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    
    } catch (Exception e) {
        System.out.println("Error setting inspection status: " + e.getMessage());
        
        JOptionPane.showMessageDialog(this,
            "Could not update inspection status: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
       }
    
    private void updateRequestStatus(PropertyPurchaseRequest request) {
        // Same implementation as in LandlordWorkAreaJPanel
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
        
        // Update in sender's work queue
        if (request.getSender() != null && request.getSender().getRole() != null &&
                request.getSender().getRole().getWorkQueue() != null) {
            for (WorkRequest req : request.getSender().getRole().getWorkQueue().getWorkRequestList()) {
                if (req instanceof PropertyPurchaseRequest && 
                        ((PropertyPurchaseRequest) req).getRequestId().equals(request.getRequestId())) {
                    req.setStatus(request.getStatus());
                }
            }
        }
        
        // Update in receiver's work queue
        if (request.getReceiver() != null && request.getReceiver().getRole() != null &&
                request.getReceiver().getRole().getWorkQueue() != null) {
            for (WorkRequest req : request.getReceiver().getRole().getWorkQueue().getWorkRequestList()) {
                if (req instanceof PropertyPurchaseRequest && 
                        ((PropertyPurchaseRequest) req).getRequestId().equals(request.getRequestId())) {
                    req.setStatus(request.getStatus());
                }
            }
        }
    

    }//GEN-LAST:event_btnRedirectActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRedirect;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProperty;
    // End of variables declaration//GEN-END:variables
}
