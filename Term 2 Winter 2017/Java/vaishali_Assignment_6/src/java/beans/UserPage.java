/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author c0665877
 */
public class UserPage {
    private int id;
    private String username;
    private String passhash;

    public UserPage(int id, String username, String passhash) {
        this.id = id;
        this.username = username;
        this.passhash = passhash;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    
    
    
}
