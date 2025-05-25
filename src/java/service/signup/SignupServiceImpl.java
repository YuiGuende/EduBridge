/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.signup;

import dal.UserDAO;
import model.User;

/**
 *
 * @author DELL
 */
public class SignupServiceImpl implements SignupService {

    @Override
    public void signup(String email, String fullname, String password) {
        UserDAO dao = new UserDAO();
        if (dao.isEmailExisted(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User(fullname, email, password, "learner");
        dao.insertUser(user);
    }

}
