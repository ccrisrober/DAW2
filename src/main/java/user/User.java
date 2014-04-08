/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

/**
 *
 * @author d
 */
public class User {
    protected int id;
    protected String name;
    protected String password;
    protected boolean isAdmin;

    public User(int id, String name, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    
    public User(int id, String name, String password) {
        this(id, name, password, false);
    }
    /*
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
    
    public boolean getIsAdmin(){
        return isAdmin;
    }
    
    
}
