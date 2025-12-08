/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package property.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import property.backend.ecosystem.Enterprise;
import property.backend.ecosystem.MaintainceCompanyOrganization;
import property.backend.ecosystem.MaintainceServiceEnterprise;
import property.backend.ecosystem.Organization;
import property.backend.ecosystem.RealEstateNetwork;
import property.backend.users.MaintainceAdmin;
import property.backend.users.Role;
import property.backend.users.UserAccount;
import property.backend.users.UserAuthentication;
import ui.signUpLandingPage.AdminWorkAreaJPanel;
//import ui.signUpLandingPage.AdminWorkAreaJPanel;
import ui.signUpLandingPage.BankWorkAreaJPanel;
import ui.signUpLandingPage.BuilderWorkAreaJPanel;
import ui.signUpLandingPage.BuyerWorkAreaJPanel;
import ui.signUpLandingPage.HousingInspectorWorkAreaJPanel;
import ui.signUpLandingPage.LandlordWorkAreaJPanel;
import ui.signUpLandingPage.MaintainceAdminWorkAreaJPanel;
import ui.signUpLandingPage.MaintainceWorkerWorkAreaJPanel;
import ui.signUpLandingPage.PropertyManagerWorkAreaJPanel;
import ui.signUpLandingPage.SignUpJPanel;
import ui.signUpLandingPage.TenantWorkAreaJPanel;

/**
 *
 * @author uday
 */
public class MainJFrame extends javax.swing.JFrame {
    private RealEstateNetwork ecosystem;
    private UserAccount currentUser;
    private UserAuthentication userAuth;
    private LayoutManager originalLayout; // To store the original layout
    private static final String DATA_FILE = "ecosystem.ser"; // The filename where data will be saved
    
    private void applyButtonHighlightEffects() {
    // Define colors
    Color highlightColor = new Color(181, 218, 255); // Light blue highlight
    Color originalColor = new Color(238, 238, 238); // Default button color
    Color hoverColor = highlightColor.darker(); // Slightly darker for pressed effect
    
    // Apply highlight effects to Login button
    applyHoverEffect(btnLogin, originalColor, highlightColor, hoverColor);
    
    // Apply highlight effects to Signup button
    applyHoverEffect(btnSign, originalColor, highlightColor, hoverColor);
    
    // Apply highlight effects to Logout button (different color scheme)
    Color logoutOriginal = new Color(231, 76, 60); // Red color for logout
    Color logoutHighlight = new Color(241, 148, 138); // Light red highlight
    Color logoutHover = logoutOriginal.darker();
    applyHoverEffect(btnLogout, logoutOriginal, logoutHighlight, logoutHover);
}

// Helper method to apply hover effects to individual buttons
private void applyHoverEffect(JButton button, Color originalColor, Color highlightColor, Color hoverColor) {
    // Set initial styling
    button.setBackground(originalColor);
    button.setOpaque(true);
    button.setBorderPainted(true);
    button.setFocusPainted(false);
    
    // Add mouse listeners for hover effects
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(highlightColor);
            button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            
            // Add subtle animation effect
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
                    // Create a slightly transparent effect
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
            // Check if mouse is still over the button
            if (button.contains(evt.getPoint())) {
                button.setBackground(highlightColor);
            } else {
                button.setBackground(originalColor);
            }
        }
    });
}

// Alternative simpler version without animation
private void applySimpleButtonHighlightEffects() {
    Color highlightColor = new Color(181, 218, 255); // Your specified highlight color
    Color originalColor = btnLogin.getBackground(); // Get current background
    
    // Apply to all buttons
    JButton[] buttons = {btnLogin, btnSign, btnLogout};
    
    for (JButton button : buttons) {
        // Store original color
        Color btnOriginalColor = button.getBackground();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(highlightColor);
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(btnOriginalColor);
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
    }
}

// Enhanced version with glow effect
private void applyGlowButtonEffects() {
    Color highlightColor = new Color(181, 218, 255);
    
    JButton[] buttons = {btnLogin, btnSign, btnLogout};
    
    for (JButton button : buttons) {
        Color originalColor = button.getBackground();
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Create glow effect with border
                button.setBackground(highlightColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(highlightColor.darker(), 2),
                    BorderFactory.createEmptyBorder(3, 8, 3, 8)
                ));
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
                button.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(highlightColor.darker());
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (button.contains(evt.getPoint())) {
                    button.setBackground(highlightColor);
                }
            }
        });
    }
}
    
    
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
        applyButtonHighlightEffects();
        userProcessContainer.setLayout(new BorderLayout());
        loadEcosystemFromFile();
        ecosystem = RealEstateNetwork.getInstance();
        userAuth = UserAuthentication.getInstance();
       // applyButtonHighlightEffects();
        loadEcosystemFromFile();
        loadUserAuthenticationFromFile();
        
        // Important: After loading ecosystem, sync user accounts with UserAuthentication
        syncUserAccountsWithAuthentication();
        
        
        originalLayout = userProcessContainer.getLayout();
        userProcessContainer.setLayout(new CardLayout());
        JPanel initialPanel = new JPanel(new BorderLayout());
  //  initialPanel.add(lblTitle, BorderLayout.CENTER);
 //   userProcessContainer.add(initialPanel, "InitialPanel");
    
    // Add this to your MainJFrame constructor
this.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        syncUserAccountsWithAuthentication(); // Add sync before save[1]
        saveEcosystemToFile();
        saveUserAuthenticationToFile();
        System.exit(0);
    }
});
    
    }
    
    
    
    private void loadEcosystemFromFile() {

// Modify your loadEcosystemFromFile method
 try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ecosystem.ser"))) {
            ecosystem = (RealEstateNetwork) ois.readObject();
            System.out.println("Ecosystem loaded from ecosystem.ser");
        } catch (Exception e) {
            ecosystem = new RealEstateNetwork();
            System.out.println("No existing ecosystem found, created new.");
        }
    }
    
 // Modify your saveEcosystemToFile method
private void saveEcosystemToFile() {
    
       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ecosystem.ser"))) {
            oos.writeObject(ecosystem);
            System.out.println("Ecosystem saved to ecosystem.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
}


private void loadUserAuthenticationFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userauth.ser"))) {
            userAuth = (UserAuthentication) ois.readObject();
            System.out.println("UserAuthentication loaded from userauth.ser");
        } catch (Exception e) {
            userAuth = UserAuthentication.getInstance();
            System.out.println("No existing user authentication found, created new.");
        }
    }

// Add this method to sync user accounts from ecosystem to UserAuthentication
// Updated sync method (bi-directional)
// Bi-directional sync method
// Add this to MainJFrame.java
public void syncUserAccountsWithAuthentication() {
    // Add users from ecosystem to userAuth
    for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
        for (Organization org : enterprise.getOrganizationList()) {
            for (UserAccount account : org.getUserAccountList()) {
                boolean exists = false;
                for (UserAccount authAccount : userAuth.getUserAccounts()) {
                    if (authAccount.getUsername().equals(account.getUsername())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    userAuth.getUserAccounts().add(account);
                    System.out.println("Added " + account.getUsername() + " to UserAuth");
                }
            }
        }
    }

    // Add users from userAuth to the appropriate organization in ecosystem 
    for (UserAccount authAccount : userAuth.getUserAccounts()) {
        boolean found = false;
        for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
            for (Organization org : enterprise.getOrganizationList()) {
                for (UserAccount orgAccount : org.getUserAccountList()) {
                    if (orgAccount.getUsername().equals(authAccount.getUsername())) {
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (found) break;
        }
        
        if (!found) {
            // If user not found in any organization, add to the appropriate one
            if (authAccount.getRole() instanceof MaintainceAdmin) {
                // Find or create Maintenance Service Enterprise
                MaintainceServiceEnterprise maintEnterprise = null;
                for (Enterprise enterprise : ecosystem.getEnterpriseList()) {
                    if (enterprise instanceof MaintainceServiceEnterprise) {
                        maintEnterprise = (MaintainceServiceEnterprise) enterprise;
                        break;
                    }
                }
                
                if (maintEnterprise == null) {
                    maintEnterprise = new MaintainceServiceEnterprise("Maintaince Services");
                    ecosystem.addEnterprise(maintEnterprise);
                }
                
                // Find or create Maintenance Company Organization
                MaintainceCompanyOrganization maintOrg = null;
                for (Organization org : maintEnterprise.getOrganizationList()) {
                    if (org instanceof MaintainceCompanyOrganization) {
                        maintOrg = (MaintainceCompanyOrganization) org;
                        break;
                    }
                }
                
                if (maintOrg == null) {
                    maintOrg = new MaintainceCompanyOrganization("Maintaince Company", maintEnterprise);
                    maintEnterprise.addOrganization(maintOrg);
                }
                
                // Add user to organization
                maintOrg.getUserAccountList().add(authAccount);
                authAccount.setOrganization(maintOrg);
                System.out.println("Added " + authAccount.getUsername() + " to MaintainceCompanyOrganization");
            }
            // Handle other roles similarly if needed
        }
    }
    
    System.out.println("Sync completed: Auth users = " + userAuth.getUserAccounts().size());

}



// Call this method after successful signup
public void handleSuccessfulSignup(UserAccount account) {
 UserAuthentication userAuth = UserAuthentication.getInstance();
    boolean exists = false;
    for (UserAccount authAccount : userAuth.getUserAccounts()) {
        if (authAccount.getUsername().equals(account.getUsername())) {
            exists = true;
            break;
        }
    }
    if (!exists) {
        userAuth.getUserAccounts().add(account);
    }
    syncUserAccountsWithAuthentication();
    // Save changes
    saveEcosystemToFile();
    saveUserAuthenticationToFile();
    
    // Refresh any open tenant panels
    refreshAllTenantPanels();
}

// New method to refresh all tenant panels
public void refreshAllTenantPanels() {
   Component[] components = userProcessContainer.getComponents();
    for (Component comp : components) {
        if (comp instanceof TenantWorkAreaJPanel) {
            TenantWorkAreaJPanel panel = (TenantWorkAreaJPanel) comp;
            panel.populateMaintAdmins();
            System.out.println("Refreshed tenant panel admin list");
        }
    }
}




private void saveUserAuthenticationToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userauth.ser"))) {
            oos.writeObject(userAuth);
            System.out.println("UserAuthentication saved to userauth.ser");
        } catch (IOException e) {
            e.printStackTrace();
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

        jSplitPane2 = new javax.swing.JSplitPane();
        loginWorkArea = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        passPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        lblNew = new javax.swing.JLabel();
        btnSign = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();
        userProcessContainer = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginWorkArea.setBackground(new java.awt.Color(153, 153, 255));
        loginWorkArea.setMaximumSize(new java.awt.Dimension(250, 250));
        loginWorkArea.setMinimumSize(new java.awt.Dimension(250, 250));
        loginWorkArea.setPreferredSize(new java.awt.Dimension(250, 250));

        lblUser.setText("Username");

        lblPass.setText("Password");

        btnLogin.setText("Login");
        btnLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblNew.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNew.setText("New to Platform??");

        btnSign.setText("Signup");
        btnSign.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginWorkAreaLayout = new javax.swing.GroupLayout(loginWorkArea);
        loginWorkArea.setLayout(loginWorkAreaLayout);
        loginWorkAreaLayout.setHorizontalGroup(
            loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginWorkAreaLayout.createSequentialGroup()
                .addGroup(loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginWorkAreaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addComponent(lblNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addGroup(loginWorkAreaLayout.createSequentialGroup()
                        .addGroup(loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(loginWorkAreaLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(passPass)))
                            .addGroup(loginWorkAreaLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(loginWorkAreaLayout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(btnSign, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(loginWorkAreaLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        loginWorkAreaLayout.setVerticalGroup(
            loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginWorkAreaLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(loginWorkAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPass)
                    .addComponent(passPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(btnLogin)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(lblNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSign)
                .addGap(29, 29, 29)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(loginWorkArea);

        userProcessContainer.setBackground(new java.awt.Color(181, 218, 255));
        userProcessContainer.setLayout(new java.awt.CardLayout());

        lblTitle.setBackground(new java.awt.Color(255, 153, 204));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Property Management System");
        lblTitle.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        userProcessContainer.add(lblTitle, "card2");

        jSplitPane2.setRightComponent(userProcessContainer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        String username = txtUser.getText();
        String password = new String(passPass.getPassword());
      //  UserAccount userAccount = userAuth.authenticateUser(username, password);
        // Debug: Print all users in authentication system
//    System.out.println("------ Attempting login for: " + username + " ------");
//    System.out.println("Total users in auth system: " + userAuth.getUserAccounts().size());
//    for (UserAccount acc : userAuth.getUserAccounts()) {
//        System.out.println("User: " + acc.getUsername() + ", Role: " + 
//            (acc.getRole() != null ? acc.getRole().getClass().getSimpleName() : "null"));
//    }
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
             javax.swing.JOptionPane.showMessageDialog(this, 
                "Username and password cannot be empty", 
                "Login Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        // Authenticate user
        UserAccount account = userAuth.authenticateUser(username, password);
        
        if (account != null) {
            // Authentication successful
            currentUser = account;
            //lblError.setText("");
            
            // Navigate to appropriate workspace based on role
            navigateToWorkspace(account);
        } else {
            // Authentication failed
           // lblError.setText("Invalid username or password");
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Invalid username or password", 
                "Login Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
        }
    }                                        

    private void navigateToWorkspace(UserAccount account) {
        // Clear the user process container
        userProcessContainer.removeAll();
        
        // Determine user's role and load appropriate panel
        Role role = account.getRole();
        String roleName = role.getClass().getSimpleName();
        
        JPanel workspacePanel = null;
        
        switch (roleName) {
            case "Admin":
                workspacePanel = new AdminWorkAreaJPanel(ecosystem, account);
                break;
                
            case "PropertyManager":
                workspacePanel = new PropertyManagerWorkAreaJPanel(ecosystem, account);
                break;
                
            case "Landlord":
                workspacePanel = new LandlordWorkAreaJPanel(ecosystem, account);
                break;
                
            case "Tenant":
                workspacePanel = new TenantWorkAreaJPanel(ecosystem, account);
                break;
                
            case "MaintainceAdmin":
                workspacePanel = new MaintainceAdminWorkAreaJPanel(ecosystem, account);
                break;
                
            case "MaintainceWorker":
                workspacePanel = new MaintainceWorkerWorkAreaJPanel(ecosystem, account);
                break;
                
            case "Buyer":
                workspacePanel = new BuyerWorkAreaJPanel(ecosystem, account);
                break;
                
            case "Builder":
                workspacePanel = new BuilderWorkAreaJPanel(ecosystem, account);
                break;
                
            case "Bank":
                workspacePanel = new BankWorkAreaJPanel(ecosystem, account);
                break;
                
            case "HousingInspector":
                workspacePanel = new HousingInspectorWorkAreaJPanel(ecosystem, account);
                break;
                
            default:
                // Default to a generic work area if role not implemented
                JOptionPane.showMessageDialog(this, "Workspace for role " + roleName + " is not implemented yet");
                return;
        }
        
        if (workspacePanel != null) {
            userProcessContainer.add("WorkspacePanel", workspacePanel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
            
            // Hide login controls
        lblTitle.setVisible(false);
        lblUser.setVisible(false);
        txtUser.setVisible(false);
        lblPass.setVisible(false);
        passPass.setVisible(false);
        btnLogin.setVisible(false);
        btnSign.setVisible(false);
        lblNew.setVisible(false);
        jSeparator1.setVisible(false);
        jSeparator2.setVisible(false);
           // lblError.setVisible(false);
           btnLogout.setVisible(true);
        }



    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignActionPerformed
        // TODO add your handling code here:
    lblTitle.setVisible(false);
    lblUser.setVisible(false);
    txtUser.setVisible(false);
    lblPass.setVisible(false);
    passPass.setVisible(false);
    btnLogin.setVisible(false);
    btnSign.setVisible(false);
    lblNew.setVisible(false);
    jSeparator1.setVisible(false);
    jSeparator2.setVisible(false);
    // Show only logout button
    btnLogout.setVisible(true);
        SignUpJPanel signUpPanel = new SignUpJPanel(userProcessContainer, ecosystem);
         userProcessContainer.removeAll();
        userProcessContainer.add("SignUpJPanel", signUpPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnSignActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to logout?",
        "Confirm Logout",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    // Proceed with logout if user confirms
    if (response == JOptionPane.YES_OPTION) {
        saveEcosystemToFile();
        currentUser=null;
        this.dispose();
        new MainJFrame().setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed
    }

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSign;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel lblNew;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel loginWorkArea;
    private javax.swing.JPasswordField passPass;
    private javax.swing.JTextField txtUser;
    private javax.swing.JPanel userProcessContainer;
    // End of variables declaration//GEN-END:variables
}
