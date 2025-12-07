/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package property.ui.buyer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyFirmOrganization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.UserAccount;
import property.backend.workqueue.PropertyPurchaseRequest;
import property.backend.workqueue.WorkRequest;
import ui.signUpLandingPage.PropertyManagerWorkAreaJPanel;

/**
 *
 * @author krishi parekh
 */
public class ManagePropertyRequestJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<PropertyPurchaseRequest> requestList;
    
    // Define color scheme
    private static final Color TEXT_COLOR = new Color(0, 153, 153);        // Teal for text
    private static final Color TABLE_COLOR = new Color(181, 218, 255);     // Light blue for table
    private static final Color BACKGROUND_COLOR = new Color(181, 218, 255); // Light blue background
    private static final Color HEADER_COLOR = new Color(0, 153, 153);      // Teal for headers
    private static final Color WHITE = Color.WHITE;
    private static final Color HIGHLIGHT_COLOR = new Color(181, 218, 255);  // Light blue for button highlights
    private static final Color BUTTON_ORIGINAL = new Color(238, 238, 238);  // Default button color
    private static final Color BUTTON_HOVER = HIGHLIGHT_COLOR.darker();
    /**
     * Creates new form ManagePropertyRequestJPanel
     */
    public ManagePropertyRequestJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        this.requestList = new ArrayList<>();
        initComponents();
         applyCustomStyling();
        populateStatusComboBox();
        
        // Populate the request table
        populateRequestTable();
    }
    
    // Method to apply custom styling
    private void applyCustomStyling() {
        // Style the main panel
        this.setBackground(BACKGROUND_COLOR);
        
        // Style the table
        styleTable();
        
        // Style the scroll pane
        jScrollPane1.setBackground(TABLE_COLOR);
        jScrollPane1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Style combo box
        styleComboBox();
        
        // Apply button highlight effects
        applyButtonHighlights();
        
        System.out.println("Custom styling applied to ManagePropertyRequestJPanel");
    }
    
    private void styleTable() {
        // Table styling
        tblManage.setBackground(WHITE);
        tblManage.setForeground(TEXT_COLOR);
        tblManage.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblManage.setRowHeight(25);
        tblManage.setShowGrid(true);
        tblManage.setGridColor(TABLE_COLOR);
        tblManage.setSelectionBackground(TABLE_COLOR);
        tblManage.setSelectionForeground(TEXT_COLOR);
        
        // Header styling
        JTableHeader header = tblManage.getTableHeader();
        header.setBackground(HEADER_COLOR);
        header.setForeground(WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
        
        // Add some padding to cells
        tblManage.setIntercellSpacing(new java.awt.Dimension(1, 1));
        
        // Make table look more professional
        tblManage.setFillsViewportHeight(true);
        tblManage.setAutoCreateRowSorter(true); // Enable sorting
        
        // Alternate row colors for better readability
        tblManage.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(WHITE);
                    } else {
                        c.setBackground(new Color(240, 248, 255)); // Very light blue for alternate rows
                    }
                    c.setForeground(TEXT_COLOR);
                } else {
                    c.setBackground(TABLE_COLOR);
                    c.setForeground(TEXT_COLOR);
                }
                
                // Center align text for better appearance
                ((javax.swing.table.DefaultTableCellRenderer) c).setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                
                return c;
            }
        });
    }
    
    private void styleComboBox() {
        cmbStatus.setBackground(WHITE);
        cmbStatus.setForeground(TEXT_COLOR);
        cmbStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbStatus.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
    }
    
    private void applyButtonHighlights() {
        // Apply highlight effects to both buttons
        applyButtonHoverEffect(btnStatus, BUTTON_ORIGINAL, HIGHLIGHT_COLOR, BUTTON_HOVER);
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
        private void populateStatusComboBox() {
        cmbStatus.removeAllItems();
        cmbStatus.addItem("Pending");
        cmbStatus.addItem("Approved");
        cmbStatus.addItem("Rejected");
    }
        
        
    private void populateRequestTable() {
    DefaultTableModel model = (DefaultTableModel) tblManage.getModel();
    model.setRowCount(0); // Clear existing data
    requestList.clear();
    
    // Find real property purchase requests in work queues
    boolean foundRequests = findRequests();
    
    // If no requests found, add sample data for demonstration
    if (!foundRequests) {
        addSampleRequests();
    }
    
    // Add requests to the table
    for (PropertyPurchaseRequest request : requestList) {
        Object[] row = new Object[4]; // Update to match your column count
        
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
    
    System.out.println("Populated request table with " + requestList.size() + " requests");
}
    
    private boolean findRequests() {
        boolean foundRequests = false;
        
        // First check current user's work queue for requests
        if (account.getRole() != null && account.getRole().getWorkQueue() != null) {
            for (WorkRequest request : account.getRole().getWorkQueue().getWorkRequestList()) {
                if (request instanceof PropertyPurchaseRequest) {
                    PropertyPurchaseRequest purchaseRequest = (PropertyPurchaseRequest) request;
                    requestList.add(purchaseRequest);
                    foundRequests = true;
                    System.out.println("Found real request in user's work queue: " + purchaseRequest.getRequestId());
                }
            }
        }
        
        // Check organization work queues
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof PropertyFirmOrganization && org.getWorkQueue() != null) {
                        for (WorkRequest request : org.getWorkQueue().getWorkRequestList()) {
                            if (request instanceof PropertyPurchaseRequest) {
                                PropertyPurchaseRequest purchaseRequest = (PropertyPurchaseRequest) request;
                                
                                // Check if this request is directed to the current user
                                if (purchaseRequest.getReceiver() != null && 
                                    purchaseRequest.getReceiver().getUsername().equals(account.getUsername())) {
                                    
                                    // Check if request is already in the list
                                    boolean exists = false;
                                    for (PropertyPurchaseRequest existingRequest : requestList) {
                                        if (existingRequest.getRequestId().equals(purchaseRequest.getRequestId())) {
                                            exists = true;
                                            break;
                                        }
                                    }
                                    
                                    if (!exists) {
                                        requestList.add(purchaseRequest);
                                        foundRequests = true;
                                        System.out.println("Found real request in organization work queue: " + purchaseRequest.getRequestId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return foundRequests;
    }
    
    private void addSampleRequests() {
        // Create sample requests for demonstration
        for (int i = 0; i < 3; i++) {
            PropertyPurchaseRequest request = new PropertyPurchaseRequest();
            request.setRequestId("REQ-" + (i + 1));
            request.setStatus(WorkRequest.RequestStatus.PENDING);
            
            // Set sample sender (buyer)
            UserAccount sender = new UserAccount();
            sender.setName("Buyer " + (i + 1));
            sender.setUsername("buyer" + (i + 1));
            request.setSender(sender);
            
            // Set receiver to current user
            request.setReceiver(account);
            
            // Set sample property
            property.backend.users.Property property = new property.backend.users.Property();
            property.setPropertyId("10" + (i + 1));
            property.setAddress("Sample Address " + (i + 1));
            property.setPrice(i % 2 == 0 ? 350000 : 2000);
            property.setForRent(i % 2 != 0);
            
            // Set sample owner
            UserAccount owner = new UserAccount();
            owner.setName("Landlord " + (i + 1));
            owner.setUsername("landlord" + (i + 1));
            property.setOwner(owner);
            
            request.setProperty(property);
            request.setRequestType(property.isForRent() ? "Rental" : "Purchase");
            request.setPrice(property.getPrice());
            
            requestList.add(request);
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
        tblManage = new javax.swing.JTable();
        btnStatus = new javax.swing.JButton();
        cmbStatus = new javax.swing.JComboBox<>();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(181, 218, 255));

        tblManage.setModel(new javax.swing.table.DefaultTableModel(
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
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblManage);

        btnStatus.setText("Update Status");
        btnStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatusActionPerformed(evt);
            }
        });

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnBack.setText("Back");
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStatus)
                        .addGap(18, 18, 18)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStatus)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack))
                .addContainerGap(380, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatusActionPerformed
        // TODO add your handling code here:
       // Add/modify this method in your ManagePropertyRequestJPanel class
//private void btnStatusActionPerformed(java.awt.event.ActionEvent evt) {
    int selectedRow = tblManage.getSelectedRow();
    
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this,
            "Please select a request to update.",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (selectedRow >= requestList.size()) {
        JOptionPane.showMessageDialog(this,
            "Error retrieving request information.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Get selected request
    PropertyPurchaseRequest selectedRequest = requestList.get(selectedRow);
    
    // Get new status from combo box
    String statusText = (String) cmbStatus.getSelectedItem();
    WorkRequest.RequestStatus newStatus;
    
    switch (statusText) {
        case "Approved":
            newStatus = WorkRequest.RequestStatus.APPROVED;
            break;
        case "Rejected":
            newStatus = WorkRequest.RequestStatus.REJECTED;
            break;
        default:
            newStatus = WorkRequest.RequestStatus.PENDING;
            break;
    }
    
    // Update status
    selectedRequest.setStatus(newStatus);
    
    // Update status in all work queues
    updateRequestStatus(selectedRequest);
    
    // If approved, mark property as unavailable
    if (newStatus == WorkRequest.RequestStatus.APPROVED && selectedRequest.getProperty() != null) {
        selectedRequest.getProperty().setAvailable(false);
    }
    
    // Refresh table
    populateRequestTable();
    
    JOptionPane.showMessageDialog(this,
        "Request status has been updated to " + statusText + ".\n" +
        (newStatus == WorkRequest.RequestStatus.APPROVED ? 
            "The landlord can now see this request and redirect it to a housing inspector." : ""),
        "Success",
        JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnStatusActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        Container container = this.getParent();
        container.remove(this);
        
        PropertyManagerWorkAreaJPanel managerPanel = new PropertyManagerWorkAreaJPanel(ecosystem, account);
        container.add("PropertyManagerWorkAreaJPanel", managerPanel);
        
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }//GEN-LAST:event_btnBackActionPerformed
    
    private void updateRequestStatus(PropertyPurchaseRequest request) {
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
        
        // Update in organization work queues
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
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnStatus;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblManage;
    // End of variables declaration//GEN-END:variables
}
