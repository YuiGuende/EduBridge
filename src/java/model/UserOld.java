/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LEGION
 */
public class UserOld {

    private int id;
    private String fullname;
    private String email;
    private String password;
    private String role;

    public UserOld() {
    }

    public UserOld(int id, String fullname, String email, String password, String role) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public UserOld(String fullname, String email, String password, String role) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", fullname=" + fullname + ", email=" + email + ", password=" + password + ", role=" + role + '}';
    }

}
