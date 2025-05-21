/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.*;
import java.sql.*;

/**
 *
 * @author LEGION
 */
public class UserDAO {

    public List<User> getALl() {
        List<User> list = new ArrayList<>();
        String sql = "select * from userTbl";
        try {
            DBContext db= new DBContext();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {

        }
        return list;
    }

    public User findByUsernameAndPassword(String username, String password) {

        String sql = "select * from userTbl where username=? and password=?";

        try {
            DBContext db= new DBContext();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));

            }
        } catch (SQLException e) {

        }
        return null;
    }

}
