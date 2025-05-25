/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import model.course.Language;

/**
 *
 * @author LEGION
 */
public class LanguageDAO {

    public Language findById(int id) {

        String sql = "select * from [Language] where id=?";

        try {
            DBContext db = new DBContext();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Language(rs.getInt("id"), rs.getString("language"));

            }
        } catch (SQLException e) {

        }
        return null;
    }
    
    public Language findByLanguage(String language) {

        String sql = "select * from [Language] where id=?";

        try {
            DBContext db = new DBContext();
            PreparedStatement st = db.getConnection().prepareStatement(sql);
            st.setString(1, language);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Language(rs.getInt("id"), rs.getString("language"));

            }
        } catch (SQLException e) {

        }
        return null;
    }
}
