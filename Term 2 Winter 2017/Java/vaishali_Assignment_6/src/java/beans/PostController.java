/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class PostController {

    private List<PostPage> posts = new ArrayList<>();
    private PostPage currentPost;

    public PostController() {
        currentPost = new PostPage(-1, -1, "", null, "");
        getPostFromDB();
    }

    public List<PostPage> getPosts() {
        return posts;
    }

    public PostPage getCurrentPost() {
        return currentPost;
    }

    /**
     * get the connection to the database
     */
    private void getPostFromDB() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM posts ";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PostPage p = new PostPage(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getTimestamp("created_time"),
                        rs.getString("contents"));
                posts.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
            posts = new ArrayList<>();
        }
    }

    /**
     * get the id
     *
     * @param id
     * @return null
     */
    public PostPage getPostByID(int id) {
        for (PostPage p : posts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public PostPage getPostByTitle(String title) {
        for (PostPage p : posts) {
            if (p.getTitle() == title) {
                return p;
            }
        }
        return null;
    }

    public String viewPost(PostPage post) {
        currentPost = post;
        return "viewPost";
    }

    public String addPost() {
        currentPost = new PostPage(-1, -1, "", null, "");
        return "editPost";
    }

    /**
     * Editing the post
     *
     * @return
     */
    public String editPost() {
        return "editPost";
    }

    /**
     * Cancel Post
     *
     * @return virePost
     */
    public String cancelPost() {
        int id = currentPost.getId();
        getPostFromDB();
        currentPost = getPostByID(id);
        return "viewPost";
    }

    /**
     * Saving the post
     *
     * @param user
     * @return
     */
    public String savePost(UserPage user) {
        try (Connection conn = DBConnection.getConnection()) {
            if (currentPost.getId() >= 0) {
                String sql = "UPDATE posts SET title = ?, contents= ? , WHERE id= ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, currentPost.getTitle());
                pstmt.setString(2, currentPost.getContents());
                pstmt.setInt(3, currentPost.getId());
                pstmt.executeUpdate();
            } else {
                String sql = "INSERT INTO posts (user_id, title, created_time,contents) VALUES (?,?,NOW(),?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, user.getId());
                pstmt.setString(2, currentPost.getTitle());
                pstmt.setString(3, currentPost.getContents());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getPostFromDB();
        currentPost = getPostByTitle(currentPost.getTitle());
        return "viewPost";
    }

    /**
     * Deleting the post
     *
     * @return
     */
    public String delete() {
        try (Connection conn = DBConnection.getConnection()) {
            if (currentPost.getId() >= 0) {
                String sql = "DELETE FROM posts WHERE id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, currentPost.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getPostFromDB();
        return "index";
    }
}
