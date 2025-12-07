/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package property.ui.builder;

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
import property.backend.ecosystem.BuilderOrganization;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.PropertyManagementEnterprise;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.Builder;
import property.backend.users.UserAccount;
import ui.signUpLandingPage.BuyerWorkAreaJPanel;

/**
 *
 * @author meghana
 */
public class BuilderJPanel extends javax.swing.JPanel {

    private RealEstateNetwork ecosystem;
    private UserAccount account;
    
     // Define color scheme
    private static final Color TEXT_COLOR = new Color(0, 153, 153);      // Teal for text
    private static final Color TABLE_COLOR = new Color(181, 218, 255);   // Light blue for table
    private static final Color BACKGROUND_COLOR = new Color(181, 218, 255); // Light blue background
    private static final Color HEADER_COLOR = new Color(0, 153, 153);    // Teal for headers
    private static final Color WHITE = Color.WHITE;
    private static final Color BUTTON_BG = new Color(181, 218, 255);     // Light blue for button bg
    private static final Color BUTTON_FG = new Color(0, 51, 102);
    private JButton backButton;
    /**
     * Creates new form BuilderJPanel
     */
    public BuilderJPanel(RealEstateNetwork ecosystem, UserAccount account) {
        this.ecosystem = ecosystem;
        this.account = account;
        initComponents();
         addBackButton();
        applyCustomStyling();
         populateBuilderTable();
    }
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(backButton)
                .addGap(310, 310, 310)
                .addComponent(jLabel1)
                .addContainerGap(605, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(backButton))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(258, Short.MAX_VALUE))
        );
    }
    
    // Method to apply custom styling to the panel and table
    private void applyCustomStyling() {
        // Style the main panel
        this.setBackground(BACKGROUND_COLOR);
        
        // Style the title label
        jLabel1.setForeground(TEXT_COLOR);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        // Style the table
        styleTable();
        
        // Style the scroll pane
        jScrollPane1.setBackground(TABLE_COLOR);
        jScrollPane1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        System.out.println("Custom styling applied to BuilderJPanel");
    }
    
    private void styleTable() {
        // Table styling
        tblBuilder.setBackground(WHITE);
        tblBuilder.setForeground(TEXT_COLOR);
        tblBuilder.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblBuilder.setRowHeight(25);
        tblBuilder.setShowGrid(true);
        tblBuilder.setGridColor(TABLE_COLOR);
        tblBuilder.setSelectionBackground(TABLE_COLOR);
        tblBuilder.setSelectionForeground(TEXT_COLOR);
        
        // Header styling
        JTableHeader header = tblBuilder.getTableHeader();
        header.setBackground(HEADER_COLOR);
        header.setForeground(WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));
        
        // Add some padding to cells
        tblBuilder.setIntercellSpacing(new java.awt.Dimension(1, 1));
        
        // Make table look more professional
        tblBuilder.setFillsViewportHeight(true);
        tblBuilder.setAutoCreateRowSorter(true); // Enable sorting
        
        // Alternate row colors for better readability
        tblBuilder.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
    
     private void populateBuilderTable() {
        DefaultTableModel model = (DefaultTableModel) tblBuilder.getModel();
        model.setRowCount(0); // Clear existing data
        
        ArrayList<UserAccount> builders = getAllBuilders();
        
        // Add builders to the table
        for (UserAccount builder : builders) {
            if (builder.getRole() instanceof Builder) {
                Builder builderRole = (Builder) builder.getRole();
                
                Object[] row = new Object[3];
                row[0] = builderRole.getBuilderName().isEmpty() ? builder.getName() : builderRole.getBuilderName();
                row[1] = builderRole.getPhoneNumber().isEmpty() ? "Not provided" : builderRole.getPhoneNumber();
                row[2] = builderRole.getAddress().isEmpty() ? "Not provided" : builderRole.getAddress();
                
                model.addRow(row);
            }
        }
        
        System.out.println("Populated builder table with " + builders.size() + " builders");
    }
    
    private ArrayList<UserAccount> getAllBuilders() {
        ArrayList<UserAccount> builders = new ArrayList<>();
        
        // Search through all enterprises for builders
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            if (enterprise instanceof PropertyManagementEnterprise) {
                for (Organization org : enterprise.getOrganizationList()) {
                    if (org instanceof BuilderOrganization) {
                        BuilderOrganization builderOrg = (BuilderOrganization) org;
                        
                        // Add all builders from this organization
                        for (UserAccount user : builderOrg.getUserAccountList()) {
                            if (user.getRole() instanceof Builder) {
                                builders.add(user);
                                System.out.println("Found builder: " + user.getName());
                            }
                        }
                    }
                }
            }
        }
        
        return builders;
    }
    
    // Method to refresh the table (can be called from outside)
    public void refreshTable() {
        populateBuilderTable();
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
        tblBuilder = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(181, 218, 255));

        tblBuilder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Builder Name", "Phone Number", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBuilder);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Builder Details");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(508, 508, 508)
                .addComponent(jLabel1)
                .addContainerGap(605, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(258, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBuilder;
    // End of variables declaration//GEN-END:variables
}
