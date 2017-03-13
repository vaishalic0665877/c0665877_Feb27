/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author c0665877
 */
@Named
@ApplicationScoped
public class UserController {

    private List<UserPage> users = new ArrayList<>();
    
    private static UserController instance = new UserController();

    public UserController() {
        getUserFromDB();
        
    }
/**
 * Get the user using List
 * @return 
 */
    public List<UserPage> getUsers() {
        return users;
    }

    public static UserController getInstance() {
        return instance;
    }

    /**
     * Get the database connection
     */
    private void getUserFromDB() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            String sql = "Select * from users";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                UserPage u = new UserPage(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash"));
                users.add(u);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            users = new ArrayList<>();
        }
    }

    /**
     * get the username by its id
     * @param id
     * @return 
     */
    public String getUsernameById(int id) {
        for (UserPage u : users) {
            if (u.getId() == id) {
                return u.getUsername();
            }
        }
        return "";
    }

    /**
     * get the id by username
     * @param username
     * @return 
     */
    public int getIdByUsername(String username) {
        for (UserPage u : users) {
            if (u.getUsername() == username) {
                return u.getId();
            }
        }
        return -1;
    }

    /**
     * add the user
     * @param username
     * @param password 
     */
    public void addUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String passhash = DBConnection.hash(password);
            String sql = "INSERT INTO users (username, passhash) VALUES(?,?)";
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);

        }
        getUserFromDB();
    }

}
