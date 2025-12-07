/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package property.ui.bank;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import property.backend.ecosystem.BankingOrganization;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.FinancialInstitutionEnterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.Bank;
import property.backend.users.UserAccount;
import ui.signUpLandingPage.BuyerWorkAreaJPanel;

/**
 *
 * @author meghana
 */
public class BankJPanel extends javax.swing.JPanel {
    private RealEstateNetwork ecosystem;
    private UserAccount account;
    
    private static final Color TEXT_COLOR = new Color(0, 153, 153);      // Teal for text
    private static final Color TABLE_COLOR = new Color(181, 218, 255);   // Light blue for table
    private static final Color BACKGROUND_COLOR = new Color(181, 218, 255); // Light blue background
    private static final Color HEADER_COLOR = new Color(0, 153, 153);    // Teal for headers
    private static final Color WHITE = Color.WHITE;
    private static final Color BUTTON_BG = new Color(181, 218, 255);     // Light blue for button bg
    private static final Color BUTTON_FG = new Color(0, 51, 102);  
    private JButton backButton;
    /**
     * Creates new form BankJPanel
     */
    public BankJPanel(RealEstateNetwork ecosystem, UserAccount account) {
        this.ecosystem = ecosystem;
        this.account = account;
        initComponents();
        addBackButton();
        applyCustomStyling();
        debugEcosystemStructure();
        populateBankTable();
    }
    // Method to add back button
    private void addBackButton() {
        backButton = new JButton("Back to Buyer Dashboard");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backButton.setBackground(BUTTON_BG);
        backButton.setForeground(BUTTON_FG);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        
        // Add action listener
        backButton.addActionListener(e -> {
            // Go back to BuyerWorkAreaJPanel
            Container container = this.getParent();
            CardLayout layout = (CardLayout) container.getLayout();
            
            // Remove current panel
            container.remove(this);
            
            // Add BuyerWorkAreaJPanel
            BuyerWorkAreaJPanel buyerPanel = new BuyerWorkAreaJPanel(ecosystem, account);
            container.add("BuyerWorkArea", buyerPanel);
            
            // Show BuyerWorkAreaJPanel
            layout.show(container, "BuyerWorkArea");
        });
        
        // Add the button to layout
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) getLayout();
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(backButton)
                .addGap(310, 310, 310)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(617, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(backButton))
                .addGap(71, 71, 71)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );
    }
    
     // Method to apply custom styling to the panel and table
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
        
        System.out.println("Custom styling applied to BankJPanel");
    }
    
    private void styleTable() {
        // Table styling
        tblBank.setBackground(WHITE);
        tblBank.setForeground(TEXT_COLOR);
        tblBank.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblBank.setRowHeight(25);
        tblBank.setShowGrid(true);
        tblBank.setGridColor(TABLE_COLOR);
        tblBank.setSelectionBackground(TABLE_COLOR);
        tblBank.setSelectionForeground(TEXT_COLOR);
        
        // Header styling
        JTableHeader header = tblBank.getTableHeader();
        header.setBackground(HEADER_COLOR);
        header.setForeground(WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
        
        // Add some padding to cells
        tblBank.setIntercellSpacing(new java.awt.Dimension(1, 1));
        
        // Make table look more professional
        tblBank.setFillsViewportHeight(true);
        tblBank.setAutoCreateRowSorter(true); // Enable sorting
        
        // Alternate row colors for better readability
        tblBank.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
    
    public void debugEcosystemStructure() {
    System.out.println("\n=== ECOSYSTEM DEBUG REPORT ===");
    System.out.println("Total enterprises: " + ecosystem.getEnterpriseList().size());
    
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        System.out.println("\nEnterprise: " + enterprise.getClass().getSimpleName() + " - " + enterprise.getName());
        System.out.println("  Organizations: " + enterprise.getOrganizationList().size());
        
        for (Organization org : enterprise.getOrganizationList()) {
            System.out.println("    Org: " + org.getClass().getSimpleName() + " - " + org.getName());
            System.out.println("    Users in org: " + org.getUserAccountList().size());
            
            for (UserAccount user : org.getUserAccountList()) {
                System.out.println("      User: " + user.getUsername() + " (" + user.getName() + ")");
                System.out.println("      Role: " + user.getRole().getClass().getSimpleName());
                if (user.getRole() instanceof Bank) {
                    Bank bankRole = (Bank) user.getRole();
                    System.out.println("        Bank Details: " + bankRole.getFullDetails());
                }
            }
        }
    }
    System.out.println("=== END ECOSYSTEM DEBUG REPORT ===\n");
}
    
    private void populateBankTable() {
        DefaultTableModel model = (DefaultTableModel) tblBank.getModel();
        model.setRowCount(0); // Clear existing data
        
        ArrayList<UserAccount> banks = getAllBanks();
        
        // Add banks to the table
        for (UserAccount bank : banks) {
            if (bank.getRole() instanceof Bank) {
                Bank bankRole = (Bank) bank.getRole();
                
                Object[] row = new Object[3];
                row[0] = bankRole.getBankName().isEmpty() ? bank.getName() : bankRole.getBankName();
                row[1] = bankRole.getPhoneNumber().isEmpty() ? "Not provided" : bankRole.getPhoneNumber();
                row[2] = bankRole.getAddress().isEmpty() ? "Not provided" : bankRole.getAddress();
                
                model.addRow(row);
            }
        }
        
        System.out.println("Populated bank table with " + banks.size() + " banks");
    }
    
   private ArrayList<UserAccount> getAllBanks() {
    System.out.println("=== Starting to get all banks ===");
    ArrayList<UserAccount> banks = new ArrayList<>();
    
    System.out.println("Number of enterprises: " + ecosystem.getEnterpriseList().size());
    
    // Search through ALL enterprises for banks (not just PropertyManagementEnterprise)
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        System.out.println("Checking enterprise: " + enterprise.getClass().getSimpleName() + " - " + enterprise.getName());
        
        // Check ALL enterprise types, not just PropertyManagementEnterprise
        for (Organization org : enterprise.getOrganizationList()) {
            System.out.println("Checking organization: " + org.getClass().getSimpleName() + " - " + org.getName());
            
            if (org instanceof BankingOrganization) {
                BankingOrganization bankOrg = (BankingOrganization) org;
                System.out.println("Found BankingOrganization with " + bankOrg.getUserAccountList().size() + " users");
                
                // Add all banks from this organization
                for (UserAccount user : bankOrg.getUserAccountList()) {
                    System.out.println("Checking user: " + user.getName() + " (" + user.getUsername() + ") with role: " + user.getRole().getClass().getSimpleName());
                    
                    if (user.getRole() instanceof Bank) {
                        banks.add(user);
                        System.out.println("Added bank: " + user.getName());
                    } else {
                        System.out.println("User " + user.getName() + " does not have Bank role");
                    }
                }
            } else {
                System.out.println("Organization " + org.getName() + " is not a BankingOrganization");
            }
        }
    }
    
    System.out.println("=== Finished getting all banks. Total found: " + banks.size() + " ===");
    return banks;
}
    
    // Method to refresh the table (can be called from outside)
    public void refreshTable() {
        populateBankTable();
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
        tblBank = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(181, 218, 255));

        tblBank.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Bank Name", "Phone Number", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBank);
        if (tblBank.getColumnModel().getColumnCount() > 0) {
            tblBank.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bank Details");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(494, 494, 494)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(617, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(71, 71, 71)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBank;
    // End of variables declaration//GEN-END:variables
}
