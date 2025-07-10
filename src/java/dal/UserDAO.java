///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package dal;
//
//import util.DBContext;
//import java.util.ArrayList;
//import java.util.List;
//import model.*;
//import java.sql.*;
//
///**
// *
// * @author LEGION
// */
//public class UserDAO {
//
//    public List<UserOld> getALl() {
//        List<UserOld> list = new ArrayList<>();
//        String sql = "select * from [user]";
//        try {
//            DBContext db = new DBContext();
//            PreparedStatement st = db.getConnection().prepareStatement(sql);
//            ResultSet rs = st.executeQuery();
//            while (rs.next()) {
//                UserOld user = new UserOld(rs.getInt("id"), rs.getNString("fullname"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
//                list.add(user);
//            }
//        } catch (SQLException e) {
//
//        }
//        return list;
//    }
//
//    public UserOld findByEmailAndPassword(String email, String password) {
//
//        String sql = "select * from [user] where email=? and password=?";
//
//        try {
//            DBContext db = new DBContext();
//            PreparedStatement st = db.getConnection().prepareStatement(sql);
//            st.setString(1, email);
//            st.setString(2, password);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                return new UserOld(rs.getInt("id"), rs.getNString("fullname"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
//
//            }
//        } catch (SQLException e) {
//
//        }
//        return null;
//    }
//
//    public boolean isEmailExisted(String email) {
//        String sql = "select * from [user] where email=?";
//
//        try {
//            DBContext db = new DBContext();
//            PreparedStatement st = db.getConnection().prepareStatement(sql);
//            st.setString(1, email);
//            ResultSet rs = st.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//
//        }
//        return false;
//    }
//
//    public void insertUser(UserOld user) {
//        String sql = "insert into [user] (email, fullname, password, role) values (?, ?, ?, ?)";
//
//        try {
//            System.out.println("user" + user);
//            DBContext db = new DBContext();
//            PreparedStatement st = db.getConnection().prepareStatement(sql);
//            st.setString(1, user.getEmail());
//            st.setNString(2, user.getFullname());
//            st.setString(3, user.getPassword());
//            st.setString(4, user.getRole());
//            st.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
