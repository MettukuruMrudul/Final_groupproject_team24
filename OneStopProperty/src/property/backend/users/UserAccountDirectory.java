/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package property.backend.users;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author uday
 */
public class UserAccountDirectory implements Serializable {
    
    // Add a serialVersionUID to maintain compatibility between versions
    private static final long serialVersionUID = 1L;
    private ArrayList<UserAccount> userAccountList;
    
    public UserAccountDirectory() {
        userAccountList = new ArrayList<>();
    }
    
    public ArrayList<UserAccount> getUserAccountList() {
        return userAccountList;
    }
    
    public UserAccount createUserAccount(String username, String password, Role role) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(password);
        userAccount.setRole(role);
        userAccountList.add(userAccount);
        return userAccount;
    }
    
    public boolean checkIfUsernameExists(String username) {
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    public UserAccount authenticateUser(String username, String password) {
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equals(username) && ua.getPassword().equals(password)) {
                return ua;
            }
        }
        return null;
    }
    
    public void removeUserAccount(UserAccount userAccount) {
        userAccountList.remove(userAccount);
    }
    
}
