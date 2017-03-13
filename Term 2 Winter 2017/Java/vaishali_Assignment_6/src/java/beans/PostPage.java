/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author c0665877
 */
public class PostPage {
    private int id;
    private int userId;
    private String title;
    private Date createdTime;
    private String contents;

    public PostPage(int id, int userId, String title, Date createdTime, String contents) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.createdTime = createdTime;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getContents() {
        return contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    
    
    
    
}
