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
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.UserAccount;
import property.backend.users.UserAuthentication;
import property.backend.workqueue.MaintainceRequest;
import property.backend.workqueue.WorkRequest;

/**
 *
 * @author sruth
 */
public class MaintainceWorkerWorkAreaJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    private ArrayList<MaintainceRequest> requestsList;
    
    private Color highlightColor = new Color(181, 218, 255); // Button highlight color
    private Color textColor = new Color(0, 153, 153); // Text color for labels
    private Color titleColor = new Color(0, 102, 102); // Darker teal
    private Color tableColor = new Color(240, 248, 255);
    
    private JButton refreshButton;
    private JButton updateStatusButton;
    
    /**
     * Creates new form MaintenanceWorkerWorkAreaJPanel
     */
    public MaintainceWorkerWorkAreaJPanel(RealEstateNetwork ecosystem,UserAccount account) {
        this.ecosystem=ecosystem;
        this.account=account;
        this.requestsList = new ArrayList<>();
        initComponents();

        applyCustomStyling();
        createCustomLayout();
        populateStatusComboBox();
        refreshAccountFromAuth();
        populateTable();
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
        JLabel titleLabel = new JLabel("Maintenance Worker Dashboard");
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
        
        // Create refresh button
        refreshButton = new JButton("Refresh Requests");
        refreshButton.setBackground(highlightColor);
        refreshButton.setForeground(new Color(0, 51, 102)); // Dark blue text
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshButton.setOpaque(true);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            refreshAccountFromAuth();
            populateTable();
        });
        
        // Create refresh button panel
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        refreshPanel.setBackground(highlightColor);
        refreshPanel.add(refreshButton);
        
        contentPanel.add(refreshPanel, BorderLayout.NORTH);
        
        // Make table scroll pane take up all available space
        jScrollPane1.setPreferredSize(new Dimension(Short.MAX_VALUE, 400));
        contentPanel.add(jScrollPane1, BorderLayout.CENTER);
        
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
        
        // Add Task Status label with matching color
        JLabel statusLabel = new JLabel("Task Status:");
        statusLabel.setForeground(textColor);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Create update status button
        updateStatusButton = new JButton("Update Status");
        updateStatusButton.setBackground(highlightColor);
        updateStatusButton.setForeground(new Color(0, 51, 102)); // Dark blue text
        updateStatusButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        updateStatusButton.setOpaque(true);
        updateStatusButton.setBorderPainted(false);
        updateStatusButton.setFocusPainted(false);
        updateStatusButton.addActionListener(e -> {
            btnUpdateStatusActionPerformed(null);
        });
        
        // Add all controls in sequence
        controlsPanel.add(statusLabel);
        controlsPanel.add(cmbStatus);
        controlsPanel.add(Box.createHorizontalStrut(20));
        controlsPanel.add(updateStatusButton);
        
        // Add instructions
        JPanel instructionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        instructionsPanel.setBackground(highlightColor);
        JLabel instructionsLabel = new JLabel(
                "Select a task from the table, choose a new status, and click Update Status");
        instructionsLabel.setForeground(textColor);
        instructionsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        instructionsPanel.add(instructionsLabel);
        
        // Create a container for controls
        JPanel controlsContainer = new JPanel();
        controlsContainer.setLayout(new BoxLayout(controlsContainer, BoxLayout.Y_AXIS));
        controlsContainer.setBackground(highlightColor);
        controlsContainer.add(controlsPanel);
        controlsContainer.add(Box.createVerticalStrut(10));
        controlsContainer.add(instructionsPanel);
        
        // Add controls panel to main panel
        add(controlsContainer, BorderLayout.SOUTH);
        
        // Ensure layout is applied
        revalidate();
        repaint();
    }
    
     /**
     * Apply custom styling to components
     */
    private void applyCustomStyling() {
      //  requestTable.setBackground(tableColor);
        // Apply table styling
        styleTable(tblTenants);
        
        // Style combo box
        styleComboBox();
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
            table.getColumnModel().getColumn(0).setPreferredWidth(150); // Tenant Name
            table.getColumnModel().getColumn(1).setPreferredWidth(120); // Phone
            table.getColumnModel().getColumn(2).setPreferredWidth(100); // House
            table.getColumnModel().getColumn(3).setPreferredWidth(100); // Status
            table.getColumnModel().getColumn(4).setPreferredWidth(200); // Service
        }
        
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(titleColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Make table scrollpane border look nicer
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220)));
    }
    
    /**
     * Style the combo box
     */
    private void styleComboBox() {
        cmbStatus.setBackground(new Color(240, 248, 255)); // Alice blue background
        cmbStatus.setForeground(textColor); // Teal text for consistency
        cmbStatus.setPreferredSize(new Dimension(150, 25));
    }
    
    
    private void refreshAccountFromAuth() {
        System.out.println("Refreshing worker account from UserAuthentication...");
        UserAuthentication userAuth = UserAuthentication.getInstance();
        for (UserAccount user : userAuth.getUserAccounts()) {
            if (user.getUsername().equals(account.getUsername())) {
                this.account = user;
                System.out.println("Updated worker account from UserAuthentication: " + account.getUsername());
                System.out.println("Work queue size: " + (account.getRole() != null && account.getRole().getWorkQueue() != null ? 
                        account.getRole().getWorkQueue().getWorkRequestList().size() : "null"));
                break;
            }
        }
    }
    private void populateStatusComboBox() {
        cmbStatus.removeAllItems();
        cmbStatus.addItem("In Progress");
        cmbStatus.addItem("Completed");
        cmbStatus.addItem("Need Parts");
    }
    private void populateTable() {
        System.out.println("=== Populating maintenance worker table ===");
        DefaultTableModel model = (DefaultTableModel) tblTenants.getModel();
        model.setRowCount(0);
        requestsList.clear();
        
        boolean foundAssignedRequests = false;
        
        // First check worker's own work queue
        if (account.getRole() != null && account.getRole().getWorkQueue() != null) {
            ArrayList<WorkRequest> requests = account.getRole().getWorkQueue().getWorkRequestList();
            System.out.println("Worker work queue size: " + (requests != null ? requests.size() : 0));
            
            if (requests != null && !requests.isEmpty()) {
                for (WorkRequest request : requests) {
                    if (request instanceof MaintainceRequest) {
                        MaintainceRequest maintRequest = (MaintainceRequest) request;
                        
                        // Only show APPROVED requests assigned to this worker
                        if (maintRequest.getStatus() == WorkRequest.RequestStatus.APPROVED && 
                            maintRequest.getReceiver() != null && 
                            maintRequest.getReceiver().getUsername().equals(account.getUsername()) 
                   && !isRequestCompleted(maintRequest)) {
                            
                            requestsList.add(maintRequest);
                            foundAssignedRequests = true;
                            
                            Object[] row = new Object[5];
                            row[0] = maintRequest.getSender() != null ? maintRequest.getSender().getName() : "Unknown";
                            row[1] = getPhoneFromRequest(maintRequest);
                            row[2] = maintRequest.getProperty() != null ? maintRequest.getProperty().getPropertyId() : "Unknown";
                            row[3] = getWorkerStatus(maintRequest); // Show worker-specific status
                            row[4] = maintRequest.getDescription(); // Service type from tenant request
                            
                            model.addRow(row);
                            System.out.println("Added assigned request to worker table: " + maintRequest.getRequestId());
                        }
                    }
                }
            }
        }
        
        // Also check all users' queues for requests assigned to this worker
        UserAuthentication userAuth = UserAuthentication.getInstance();
        for (UserAccount user : userAuth.getUserAccounts()) {
            if (user.getRole() != null && user.getRole().getWorkQueue() != null) {
                for (WorkRequest request : user.getRole().getWorkQueue().getWorkRequestList()) {
                    if (request instanceof MaintainceRequest) {
                        MaintainceRequest maintRequest = (MaintainceRequest) request;
                        
                        // Check if this request is assigned to this worker and not already added
                        if (maintRequest.getStatus() == WorkRequest.RequestStatus.APPROVED && 
                            maintRequest.getReceiver() != null && 
                            maintRequest.getReceiver().getUsername().equals(account.getUsername()) &&
                                !isRequestCompleted(maintRequest) &&
                            !isRequestAlreadyAdded(maintRequest)) {
                            
                            requestsList.add(maintRequest);
                            foundAssignedRequests = true;
                            
                            Object[] row = new Object[5];
                            row[0] = maintRequest.getSender() != null ? maintRequest.getSender().getName() : "Unknown";
                            row[1] = getPhoneFromRequest(maintRequest);
                            row[2] = maintRequest.getProperty() != null ? maintRequest.getProperty().getPropertyId() : "Unknown";
                            row[3] = getWorkerStatus(maintRequest);
                            row[4] = maintRequest.getDescription(); // Service type from tenant request
                            
                            model.addRow(row);
                            System.out.println("Added assigned request from user work queue: " + maintRequest.getRequestId());
                            
                            // Add to worker's queue if not already there
                            if (account.getRole() != null && account.getRole().getWorkQueue() != null) {
                                boolean alreadyInQueue = false;
                                for (WorkRequest workerReq : account.getRole().getWorkQueue().getWorkRequestList()) {
                                    if (workerReq instanceof MaintainceRequest && 
                                        ((MaintainceRequest)workerReq).getRequestId().equals(maintRequest.getRequestId())) {
                                        alreadyInQueue = true;
                                        break;
                                    }
                                }
                                
                                if (!alreadyInQueue) {
                                    account.getRole().getWorkQueue().addWorkRequest(maintRequest);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Check all organization work queues for more assigned tasks
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            for (Organization org : enterprise.getOrganizationList()) {
                if (org.getWorkQueue() != null) {
                    for (WorkRequest request : org.getWorkQueue().getWorkRequestList()) {
                        if (request instanceof MaintainceRequest) {
                            MaintainceRequest maintRequest = (MaintainceRequest) request;
                            
                            // Check if this request is assigned to this worker and not already added
                            if (maintRequest.getStatus() == WorkRequest.RequestStatus.APPROVED && 
                                maintRequest.getReceiver() != null && 
                                maintRequest.getReceiver().getUsername().equals(account.getUsername()) &&
                                    !isRequestCompleted(maintRequest) &&
                                !isRequestAlreadyAdded(maintRequest)) {
                                
                                requestsList.add(maintRequest);
                                foundAssignedRequests = true;
                                
                                Object[] row = new Object[5];
                                row[0] = maintRequest.getSender() != null ? maintRequest.getSender().getName() : "Unknown";
                                row[1] = getPhoneFromRequest(maintRequest);
                                row[2] = maintRequest.getProperty() != null ? maintRequest.getProperty().getPropertyId() : "Unknown";
                                row[3] = getWorkerStatus(maintRequest);
                                row[4] = maintRequest.getDescription(); // Service type from tenant request
                                
                                model.addRow(row);
                                System.out.println("Added assigned request from organization: " + maintRequest.getRequestId());
                                
                                // Add to worker's queue if not already there
                                if (account.getRole() != null && account.getRole().getWorkQueue() != null) {
                                    boolean alreadyInQueue = false;
                                    for (WorkRequest workerReq : account.getRole().getWorkQueue().getWorkRequestList()) {
                                        if (workerReq instanceof MaintainceRequest && 
                                            ((MaintainceRequest)workerReq).getRequestId().equals(maintRequest.getRequestId())) {
                                            alreadyInQueue = true;
                                            break;
                                        }
                                    }
                                    
                                    if (!alreadyInQueue) {
                                        account.getRole().getWorkQueue().addWorkRequest(maintRequest);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        System.out.println("Found " + requestsList.size() + " assigned requests for worker: " + account.getUsername());
    }
    
    private boolean isRequestCompleted(MaintainceRequest request) {
        return request.getWorkerStatus() != null && request.getWorkerStatus().equals("Completed");
    }
    
    // Method to get worker-specific status (different from admin status)
    private String getWorkerStatus(MaintainceRequest request) {
        // If the request has a worker status, use that
        if (request.getWorkerStatus() != null && !request.getWorkerStatus().isEmpty()) {
            return request.getWorkerStatus();
        }
        
        // Default to "Pending" if no worker status has been set
        return "Pending";
    }
    
    // Method to get phone from request
    private String getPhoneFromRequest(MaintainceRequest request) {
        String description = request.getDescription();
        if (description != null && description.contains("Phone:")) {
            int phoneIndex = description.indexOf("Phone:") + 6;
            int endIndex = description.indexOf(",", phoneIndex);
            if (endIndex == -1) endIndex = description.length();
            return description.substring(phoneIndex, endIndex).trim();
        }
        
        // If we can't extract from description, check sender's info
        UserAccount sender = request.getSender();
        if (sender != null) {
            String username = sender.getUsername();
            return "555-" + Math.abs(username.hashCode() % 1000) + "-" + Math.abs(username.hashCode() % 10000);
        }
        
        return "N/A";
    }
    
    // Check if request is already in the list
    private boolean isRequestAlreadyAdded(MaintainceRequest request) {
        for (MaintainceRequest existingRequest : requestsList) {
            if (existingRequest.getRequestId() != null && 
                existingRequest.getRequestId().equals(request.getRequestId())) {
                return true;
            }
        }
        return false;
    }
    // Method to update request status when worker completes work
    private void btnUpdateStatusActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblTenants.getSelectedRow();
        
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
        
        // Get worker status from dropdown
        String workerStatus = (String) cmbStatus.getSelectedItem();
        
        // Set the worker status
        request.setWorkerStatus(workerStatus);
        
        // If worker has completed the request, we could also update the main request status
        if (workerStatus.equals("Completed")) {
            // Optionally update main status to show completion
            // request.setStatus(WorkRequest.RequestStatus.COMPLETED);
            
            JOptionPane.showMessageDialog(this, 
                "Maintenance task has been marked as completed.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Task status has been updated to " + workerStatus, 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        // Update the status in all relevant places
        updateRequestStatusEverywhere(request);
        
        // Refresh the table
        populateTable();
    }
    
    // Update request status everywhere
    private void updateRequestStatusEverywhere(MaintainceRequest request) {
        try {
            // Update in all users' work queues
            UserAuthentication userAuth = UserAuthentication.getInstance();
            for (UserAccount user : userAuth.getUserAccounts()) {
                if (user.getRole() != null && user.getRole().getWorkQueue() != null) {
                    for (WorkRequest req : user.getRole().getWorkQueue().getWorkRequestList()) {
                        if (req instanceof MaintainceRequest && 
                            ((MaintainceRequest)req).getRequestId().equals(request.getRequestId())) {
                            // Update worker status
                            ((MaintainceRequest)req).setWorkerStatus(request.getWorkerStatus());
                            // Optionally update main status if completed
                            if (request.getWorkerStatus().equals("Completed")) {
                                // req.setStatus(WorkRequest.RequestStatus.COMPLETED);
                            }
                        }
                    }
                }
            }
            
            // Update in all organizations
            for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org.getWorkQueue() != null) {
                        for (WorkRequest req : org.getWorkQueue().getWorkRequestList()) {
                            if (req instanceof MaintainceRequest && 
                                ((MaintainceRequest)req).getRequestId().equals(request.getRequestId())) {
                                // Update worker status
                                ((MaintainceRequest)req).setWorkerStatus(request.getWorkerStatus());
                                // Optionally update main status if completed
                                if (request.getWorkerStatus().equals("Completed")) {
                                    // req.setStatus(WorkRequest.RequestStatus.COMPLETED);
                                }
                            }
                        }
                    }
                }
            }
            
            System.out.println("Updated worker status everywhere to: " + request.getWorkerStatus());
        } catch (Exception e) {
            System.out.println("Error updating request status: " + e.getMessage());
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
        tblTenants = new javax.swing.JTable();
        cmbStatus = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(181, 218, 255));

        tblTenants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tenant Name", "Tenant Phone", "House Number", "Status", "Service"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTenants);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(1163, Short.MAX_VALUE)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(262, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTenants;
    // End of variables declaration//GEN-END:variables
}
