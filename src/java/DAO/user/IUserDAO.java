/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.user;

import java.util.Optional;
import model.user.User;

/**
 *
 * @author DELL
 */
public interface IUserDAO {
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean isEmailExists(String email);
}
