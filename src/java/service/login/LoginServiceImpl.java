/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.login;

import dal.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author LEGION
 */
public class LoginServiceImpl implements LoginService {

    @Override
    public User login(String email, String password) {
        UserDAO dao = new UserDAO();
        User user = dao.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new RuntimeException("Account not found!");
        }
        return user;
    }

}
