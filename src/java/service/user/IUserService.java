/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import java.util.Optional;
import model.user.Instructor;
import model.user.User;

/**
 *
 * @author LEGION
 */
public interface IUserService {

    Optional<User> login(String username, String password);

    boolean signup(User user);

    boolean signupForInstructor(User user, Instructor instructor);
    
    User findById(Long id);
    
    
}
