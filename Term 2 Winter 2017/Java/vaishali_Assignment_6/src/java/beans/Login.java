/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author c0665877
 */

@Named
@SessionScoped
public class Login implements Serializable{
    private String username;
    private String password;
    private boolean loggedIn;
    private UserPage currentUser;

    public Login() {
        username = null;
        password = null;
        loggedIn = false;
        currentUser = null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public UserPage getCurrentUser() {
        return currentUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

      
    public String  login(){
        String passhash = DBConnection.hash(password);
        
        for (UserPage u:UserController.getInstance().getUsers()){
            if (username.equals(u.getUsername()) && (passhash.equals(u.getPasshash()))){
                loggedIn= true;
                currentUser =u;
                return "index";
            }
        }
        currentUser=null;
        loggedIn=false;
        return "index";
    }
            
}
