/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.signUpLandingPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.MaintainceCompanyOrganization;
import property.backend.ecosystem.MaintainceServiceEnterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.MaintainceWorker;
import property.backend.users.Property;
import property.backend.users.UserAccount;
import property.backend.users.UserAuthentication;
import property.backend.workqueue.MaintainceRequest;
//import property.backend.workqueue.MaintenanceRequest;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author sruth
 */
public class MaintainceAdminWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<MaintainceRequest> requestsList;
    
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color titleColor = new Color(0, 102, 102); // Darker teal
    private Color tableColor = new Color(240, 248, 255);
    private JButton refreshButton;
//    private JLabel titleLabel;
//    private JTable requestTable;
//    private JScrollPane tableScrollPane;
//    private JButton refreshButton;
//    private JButton updateButton;
//    private JComboBox<String> statusCombo;
//    private JComboBox<String> managerCombo;
//    private JLabel managerLabel;
    /**
     * Creates new form MaintenanceAdminWorkAreaJPanel
     */
    public MaintainceAdminWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        this.requestsList = new ArrayList<>();
        initComponents();
 // Create a refresh button
        refreshButton = new JButton("Refresh Requests");
        refreshButton.addActionListener(e -> {
            refreshAccountFromAuth();
            populateTable();
        });
        
        // Apply custom styling and populate data
        applyCustomStyling();
        createCustomLayout();
        populateStatusComboBox();
        populateManagerComboBox();
        
        refreshAccountFromAuth();
        populateTable(); // Load data when panel initializes
    } 
        
   
    
    /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
                // Apply text color to all labels
        applyColorToLabels();
        
        // Apply button highlighting
        highlightButtons();
        
        // Apply table styling
        styleTable(tblTenant);
        
        // Style combo boxes
      //  styleComboBoxes();

    }
    
     private void createCustomLayout() {
        // Clear existing layout
        this.removeAll();
        
        // Set main panel properties
        setBackground(highlightColor);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(highlightColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Create title label
        JLabel titleLabel = new JLabel("Maintenance Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(titleColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        
        // Add header panel to main panel
        add(headerPanel, BorderLayout.NORTH);
        
        // Create container panel for refresh button and table
        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setBackground(highlightColor);
        
        // Create refresh button panel
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        refreshPanel.setBackground(highlightColor);
        
        // Style refresh button
        refreshButton.setBackground(highlightColor);
        refreshButton.setForeground(new Color(0, 51, 102)); // Dark blue text
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);
        refreshPanel.add(refreshButton);
        
        contentPanel.add(refreshPanel, BorderLayout.NORTH);
        
        // Add table to content panel
        contentPanel.add(jScrollPane2, BorderLayout.CENTER);
        
        // Add content panel to main panel
        add(contentPanel, BorderLayout.CENTER);
        
        // Create controls panel
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(highlightColor);
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 220)),
                BorderFactory.createEmptyBorder(15, 0, 0, 0)));
        
        // Use FlowLayout to keep everything on one line
        controlsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        
        // Add Request Status label with matching color
        JLabel statusLabel = new JLabel("Request Status:");
        statusLabel.setForeground(textColor);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Add all controls in sequence to ensure they appear on the same line
        controlsPanel.add(statusLabel);
        controlsPanel.add(cmbStatus);
        controlsPanel.add(lblManager);
        controlsPanel.add(cmbManager);
        controlsPanel.add(Box.createHorizontalStrut(15));
        controlsPanel.add(btnUpdate);
        
        // Add controls panel to main panel
        add(controlsPanel, BorderLayout.SOUTH);
        
        // Ensure layout is applied
        revalidate();
        repaint();
    }
    
    /**
     * Apply text color to all labels in the panel
     */
    private void applyColorToLabels() {
        // Set color for specific labels
        lblManager.setForeground(textColor);
        
        // Set font for labels to make them more visible with the new color
        lblManager.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
    }
    
    /**
     * Apply highlight color to all buttons
     */
    private void highlightButtons() {
        // Set background color for all buttons
       btnUpdate.setBackground(highlightColor);
        btnUpdate.setForeground(new Color(0, 51, 102)); // Dark blue text
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUpdate.setOpaque(true);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setFocusPainted(false);
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
        table.setRowHeight(25); // Taller rows for better readability
        table.setAutoCreateRowSorter(true); // Enable sorting
        
        // Set column widths
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(200); // Tenant Name
            table.getColumnModel().getColumn(1).setPreferredWidth(150); // Phone
            table.getColumnModel().getColumn(2).setPreferredWidth(100); // House
            table.getColumnModel().getColumn(3).setPreferredWidth(120); // Status
        }
        
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(titleColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Make table scrollpane border look nicer
        jScrollPane2.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220)));
    }
    
    /**
     * Style the combo boxes
     */
    private void styleComboBoxes() {
        // Status combo box
        cmbStatus.setBackground(new Color(240, 248, 255)); // Alice blue background
        cmbStatus.setForeground(textColor); // Teal text for consistency
        
        // Manager combo box
        cmbManager.setBackground(new Color(240, 248, 255)); // Alice blue background
        cmbManager.setForeground(textColor); // Teal text for consistency
    }
    
     private void refreshAccountFromAuth() {
    System.out.println("Refreshing account from UserAuthentication...");
        UserAuthentication userAuth = UserAuthentication.getInstance();
        for (UserAccount user : userAuth.getUserAccounts()) {
            if (user.getUsername().equals(account.getUsername())) {
                this.account = user;
                System.out.println("Updated account from UserAuthentication: " + account.getUsername());
                System.out.println("Work queue size: " + (account.getRole() != null && account.getRole().getWorkQueue() != null ? 
                        account.getRole().getWorkQueue().getWorkRequestList().size() : "null"));
                break;
            }
        }
       // findRequestsForThisAdmin(); 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblTenant = new javax.swing.JTable();
        cmbStatus = new javax.swing.JComboBox<>();
        lblManager = new javax.swing.JLabel();
        cmbManager = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();

        setBackground(new java.awt.Color(181, 218, 255));

        tblTenant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tenant Name", "Tenant Phone", "House", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblTenant);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblManager.setText("Manager");

        cmbManager.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnUpdate.setText("Update Status");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblManager)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnUpdate)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(361, 361, 361))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManager)
                    .addComponent(cmbManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(348, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
       int selectedRow = tblTenant.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, 
                "Please select a request to update.", 
                "Selection Required", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get selected request
        MaintainceRequest request = null;
        if (selectedRow < requestsList.size()) {
            request = requestsList.get(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error retrieving the selected request.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get new status from dropdown
        String statusText = (String) cmbStatus.getSelectedItem();
        WorkRequest.RequestStatus newStatus = WorkRequest.RequestStatus.PENDING; // Default
        
        // Convert to enum
        switch (statusText) {
            case "Approved":
                newStatus = WorkRequest.RequestStatus.APPROVED;
                break;
            case "Rejected":
                newStatus = WorkRequest.RequestStatus.REJECTED;
                break;
            default:
                newStatus = WorkRequest.RequestStatus.PENDING;
        }
        
        // Set the new status
        request.setStatus(newStatus);
        
        // If status is approved, assign to selected maintenance worker
        if (newStatus == WorkRequest.RequestStatus.APPROVED) {
            String selectedManager = (String) cmbManager.getSelectedItem();
            
            if (selectedManager != null && !selectedManager.equals("No Maintenance Workers Available")) {
                // Extract username from the selected item (format: "Name (username)")
                String username = selectedManager.substring(selectedManager.indexOf("(") + 1, selectedManager.indexOf(")"));
                
                // Find the manager account
                UserAccount managerAccount = findUserByUsername(username);
                
                if (managerAccount != null) {
                    // Assign the request to the manager
                    request.setReceiver(managerAccount);
                    
                    // Add to manager's work queue
                    if (managerAccount.getRole() != null && managerAccount.getRole().getWorkQueue() != null) {
                        managerAccount.getRole().getWorkQueue().addWorkRequest(request);
                        
                        // Update the request in sender's work queue as well
                        if (request.getSender() != null && request.getSender().getRole() != null && 
                            request.getSender().getRole().getWorkQueue() != null) {
                            for (WorkRequest r : request.getSender().getRole().getWorkQueue().getWorkRequestList()) {
                                if (r instanceof MaintainceRequest && 
                                    ((MaintainceRequest)r).getRequestId().equals(request.getRequestId())) {
                                    r.setStatus(newStatus);
                                    break;
                                }
                            }
                        }
                        
                        JOptionPane.showMessageDialog(this, 
                            "Request has been updated and assigned to " + managerAccount.getName(), 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Manager's work queue is not available. Status updated but assignment failed.", 
                            "Partial Success", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Selected manager could not be found. Status updated but assignment failed.", 
                        "Partial Success", 
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No maintenance worker selected for assignment. Status updated only.", 
                    "Partial Success", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // For statuses other than Approved
            
            // Update the request in sender's work queue
            if (request.getSender() != null && request.getSender().getRole() != null && 
                request.getSender().getRole().getWorkQueue() != null) {
                for (WorkRequest r : request.getSender().getRole().getWorkQueue().getWorkRequestList()) {
                    if (r instanceof MaintainceRequest && 
                        ((MaintainceRequest)r).getRequestId().equals(request.getRequestId())) {
                        r.setStatus(newStatus);
                        break;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Request status has been updated to " + statusText, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Update the status in all places the request might exist
        updateRequestStatusEverywhere(request);
        
        // Refresh the table to show updated status
        populateTable();
    }    
    // Method to update a request's status in all possible locations
    private void updateRequestStatusEverywhere(MaintainceRequest request) {
        try {
            // Update in all organizations
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org.getWorkQueue() != null) {
                        ArrayList<WorkRequest> requests = org.getWorkQueue().getWorkRequestList();
                        if (requests != null) {
                            for (WorkRequest req : requests) {
                                if (req instanceof MaintainceRequest && 
                                    ((MaintainceRequest)req).getRequestId().equals(request.getRequestId())) {
                                    req.setStatus(request.getStatus());
                                }
                            }
                        }
                    }
                }
            }
            
            // Update in all user work queues
            UserAuthentication userAuth = UserAuthentication.getInstance();
            for (UserAccount user : userAuth.getUserAccounts()) {
                if (user.getRole() != null && user.getRole().getWorkQueue() != null) {
                    ArrayList<WorkRequest> requests = user.getRole().getWorkQueue().getWorkRequestList();
                    if (requests != null) {
                        for (WorkRequest req : requests) {
                            if (req instanceof MaintainceRequest && 
                                ((MaintainceRequest)req).getRequestId().equals(request.getRequestId())) {
                                req.setStatus(request.getStatus());
                            }
                        }
                    }
                }
            }
            
            // Make sure to save the ecosystem to persist these changes if a method exists
            try {
                // This is a placeholder - your actual save method may be different
                System.out.println("Attempting to save ecosystem with updated request status");
                // ecosystem.saveToFile("ecosystem.ser"); // Uncomment if this method exists
            } catch (Exception e) {
                System.out.println("Error saving ecosystem: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error updating request status: " + e.getMessage());
        }
    }                    
    
    

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
        

    }//GEN-LAST:event_btnUpdateActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbManager;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblManager;
    private javax.swing.JTable tblTenant;
    // End of variables declaration//GEN-END:variables

    private void populateStatusComboBox() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        cmbStatus.removeAllItems();
        cmbStatus.addItem("Pending");
        cmbStatus.addItem("Approved");
        cmbStatus.addItem("Rejected");
    }

    private void populateManagerComboBox() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      cmbManager.removeAllItems();
        System.out.println("Populating manager combo box...");
        // Create a HashMap to store managers (username as key to avoid duplicates)
        HashMap<String, UserAccount> managers = new HashMap<>();
        
        // First find maintenance workers in the ecosystem
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof MaintainceServiceEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof MaintainceCompanyOrganization) {
                        for (UserAccount user : org.getUserAccountList()) {
                            if (user.getRole() instanceof MaintainceWorker) {
                                managers.put(user.getUsername(), user);
                            }
                        }
                    }
                }
            }
        }
        
        // Also add maintenance workers from UserAuthentication
        UserAuthentication userAuth = UserAuthentication.getInstance();
        for (UserAccount user : userAuth.getUserAccounts()) {
            if (user.getRole() instanceof MaintainceWorker) {
                // This will overwrite any duplicates by username
                managers.put(user.getUsername(), user);
                System.out.println("Found manager in UserAuth: " + user.getName());
            }
        }
        
        // Populate combo box from the combined list
        if (!managers.isEmpty()) {
            for (UserAccount manager : managers.values()) {
                cmbManager.addItem(manager.getName() + " (" + manager.getUsername() + ")");
            }
        } else {
            // If no managers found, add a default option
            cmbManager.addItem("No Maintenance Workers Available");
        }  
    }

    private void populateTable() {
   System.out.println("=== Populating maintenance admin table ===");
        System.out.println("Admin: " + account.getUsername());
        System.out.println("Role: " + (account.getRole() != null ? account.getRole().getClass().getSimpleName() : "null"));
        System.out.println("Work queue: " + (account.getRole() != null && account.getRole().getWorkQueue() != null));
        
        DefaultTableModel model = (DefaultTableModel) tblTenant.getModel();
        model.setRowCount(0); // Clear existing data
        requestsList.clear();
        
        boolean foundRealRequests = false;
        
        
        // 1. First check admin's work queue
        if (account.getRole() != null && account.getRole().getWorkQueue() != null) {
            ArrayList<WorkRequest> requests = account.getRole().getWorkQueue().getWorkRequestList();
            System.out.println("Admin work queue size: " + (requests != null ? requests.size() : 0));
            
            if (requests != null && !requests.isEmpty()) {
                for (WorkRequest request : requests) {
                    System.out.println("Request type: " + request.getClass().getName());
                    if (request instanceof MaintainceRequest) {
                        MaintainceRequest maintRequest = (MaintainceRequest) request;
                        requestsList.add(maintRequest);
                        foundRealRequests = true;
                        
                        Object[] row = new Object[4];
                        row[0] = maintRequest.getSender() != null ? maintRequest.getSender().getName() : "Unknown";
                        row[1] = getPhoneFromRequest(maintRequest); 
                        row[2] = "H" + (maintRequest.getProperty() != null ? maintRequest.getProperty().getPropertyId() : "Unknown");
                        row[3] = maintRequest.getStatus() != null ? maintRequest.getStatus().getValue() : "Pending";
                        
                        model.addRow(row);
                        System.out.println("Added real request to table: " + maintRequest.getRequestId());
                    }
                }
            }
        }

        // 2. Check all maintenance organizations for requests assigned to this admin
        try {
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                if (enterprise instanceof MaintainceServiceEnterprise) {
                    for (Organization org : enterprise.getOrganizationList()) {
                        if (org instanceof MaintainceCompanyOrganization && org.getWorkQueue() != null) {
                            ArrayList<WorkRequest> orgRequests = org.getWorkQueue().getWorkRequestList();
                            if (orgRequests != null) {
                                for (WorkRequest req : orgRequests) {
                                    if (req instanceof MaintainceRequest) {
                                        MaintainceRequest maintReq = (MaintainceRequest) req;
                                        
                                        // Check if this admin is the receiver
                                        if (maintReq.getReceiver() != null && 
                                            maintReq.getReceiver().getUsername().equals(account.getUsername())) {
                                            
                                            if (!isRequestAlreadyAdded(maintReq)) {
                                                requestsList.add(maintReq);
                                                foundRealRequests = true;
                                                
                                                Object[] row = new Object[4];
                                                row[0] = maintReq.getSender() != null ? maintReq.getSender().getName() : "Unknown";
                                                row[1] = getPhoneFromRequest(maintReq);
                                                row[2] = maintReq.getProperty() != null ? maintReq.getProperty().getPropertyId() : "Unknown";
                                                row[3] = maintReq.getStatus() != null ? maintReq.getStatus().getValue() : "Pending";
                                                
                                                model.addRow(row);
                                                System.out.println("Added real request from organization: " + maintReq.getRequestId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking organization work queues: " + e.getMessage());
        }

        // 3. Check all tenant work queues for requests directed to this admin
        try {
            System.out.println("Checking all tenant work queues for requests to this admin...");
            UserAuthentication userAuth = UserAuthentication.getInstance();
            for (UserAccount user : userAuth.getUserAccounts()) {
                if (user.getRole() != null && user.getRole().getWorkQueue() != null) {
                    ArrayList<WorkRequest> userRequests = user.getRole().getWorkQueue().getWorkRequestList();
                    if (userRequests != null) {
                        for (WorkRequest request : userRequests) {
                            if (request instanceof MaintainceRequest) {
                                MaintainceRequest maintRequest = (MaintainceRequest) request;
                                
                                // Check if this admin is the receiver
                                if (maintRequest.getReceiver() != null && 
                                    maintRequest.getReceiver().getUsername().equals(account.getUsername())) {
                                    
                                    if (!isRequestAlreadyAdded(maintRequest)) {
                                        requestsList.add(maintRequest);
                                        foundRealRequests = true;
                                        
                                        Object[] row = new Object[4];
                                        row[0] = maintRequest.getSender() != null ? maintRequest.getSender().getName() : "Unknown";
                                        row[1] = getPhoneFromRequest(maintRequest);
                                        row[2] = maintRequest.getProperty() != null ? maintRequest.getProperty().getPropertyId() : "Unknown";
                                        row[3] = maintRequest.getStatus() != null ? maintRequest.getStatus().getValue() : "Pending";
                                        
                                        model.addRow(row);
                                        System.out.println("Added real request from tenant work queue: " + maintRequest.getRequestId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking user work queues: " + e.getMessage());
        }
        
        // If no real requests found, add dummy data
        if (!foundRealRequests) {
        System.out.println("No maintenance requests found for this admin");
        // Optionally display a message on the UI
        JOptionPane.showMessageDialog(this, 
            "No maintenance requests currently assigned to you.", 
            "No Requests", 
            JOptionPane.INFORMATION_MESSAGE);
    }
        
        System.out.println("Final request list size: " + requestsList.size());
        System.out.println("Table row count: " + model.getRowCount());
    }
    
    // Helper method to add dummy maintenance requests
    private void addDummyData(DefaultTableModel model) {
        String[] tenantNames = {"John Smith", "Maria Garcia", "Wei Chen", "Olivia Johnson", "Ahmed Hassan"};
        String[] phoneNumbers = {"555-123-4567", "555-234-5678", "555-345-6789", "555-456-7890", "555-567-8901"};
        String[] houseIds = {"H101", "H202", "H303", "H404", "H505"};
        String[] repairTypes = {"Plumbing", "Electrical", "HVAC", "Structural", "Pest Control"};
        String[] statuses = {"Pending", "Pending", "Approved", "Rejected", "Pending"};
        
        // Create dummy requests
        for (int i = 0; i < 5; i++) {
            // Create new request
            MaintainceRequest dummyRequest = new MaintainceRequest();
            dummyRequest.setRequestId("REQ-DUMMY-" + (i+1));
            
            // Create dummy sender
            UserAccount dummySender = new UserAccount();
            dummySender.setName(tenantNames[i]);
            dummySender.setUsername("tenant" + (i+1));
            dummyRequest.setSender(dummySender);
            
            // Create dummy property
            Property dummyProperty = new Property();
            dummyProperty.setPropertyId(houseIds[i]);
            dummyProperty.setAddress("123 Main St, Unit " + (i+1));
            dummyRequest.setProperty(dummyProperty);
            
            // Set request details
            dummyRequest.setDescription(repairTypes[i] + " - Phone: " + phoneNumbers[i]);
            dummyRequest.setRequestDate(new Date());
            
            // Set status
            switch (statuses[i]) {
                case "Approved":
                    dummyRequest.setStatus(WorkRequest.RequestStatus.APPROVED);
                    break;
                case "Rejected":
                    dummyRequest.setStatus(WorkRequest.RequestStatus.REJECTED);
                    break;
                default:
                    dummyRequest.setStatus(WorkRequest.RequestStatus.PENDING);
            }
            
            // Set receiver to this admin
            dummyRequest.setReceiver(account);
            
            // Add to request list
            requestsList.add(dummyRequest);
            
            // Add to table
            Object[] row = new Object[4];
            row[0] = tenantNames[i];
            row[1] = phoneNumbers[i];
            row[2] = houseIds[i];
            row[3] = statuses[i];
            model.addRow(row);
        }
    }
    
    // Method to extract phone from a request (try to get it from description or other metadata)
    private String getPhoneFromRequest(MaintainceRequest request) {
        // In a real system, you would store the phone in the request or retrieve it from the sender's profile
        // First try to extract from the description
        String description = request.getDescription();
        if (description != null && description.contains("Phone:")) {
            int phoneIndex = description.indexOf("Phone:") + 6;
            int endIndex = description.indexOf(",", phoneIndex);
            if (endIndex == -1) endIndex = description.length();
            return description.substring(phoneIndex, endIndex).trim();
        }
        
        // If we can't extract from description, look for the sender in UserAuthentication
        UserAccount sender = request.getSender();
        if (sender != null) {
            String username = sender.getUsername();
            // This would be your implementation to get phone from user's profile
            // For now, just return a placeholder based on username
            return "555-" + Math.abs(username.hashCode() % 1000) + "-" + Math.abs(username.hashCode() % 10000);
        }
        
        return "N/A";
    }
    
    private boolean isRequestAlreadyAdded(MaintainceRequest request) {
        for (MaintainceRequest existingRequest : requestsList) {
            if (existingRequest.getRequestId() != null && 
                existingRequest.getRequestId().equals(request.getRequestId())) {
                return true;
            }
        }
        return false;
    }
}
