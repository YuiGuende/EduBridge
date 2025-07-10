/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import DAO.GenericDAO;
import DAO.user.UserDAOImpl;
import dal.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import model.user.Instructor;
import model.user.User;

/**
 *
 * @author LEGION
 */
public class UserServiceImpl implements IUserService {

    private final UserDAOImpl userDAO;
    private final GenericDAO<Instructor> instructorDAO = new GenericDAO<>(Instructor.class);

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl(User.class);
    }

    @Override
    public Optional<User> login(String username, String password) {
        return userDAO.findByEmailAndPassword(username, password);
    }

    @Override
    public boolean signup(User user) {
        if (userDAO.isEmailExists(user.getEmail())) {
            return false;
        } else {
            userDAO.insert(user);
            return true;
        }
    }

    @Override
    public boolean signupForInstructor(User user, Instructor instructor) {
        if (userDAO.isEmailExists(user.getEmail())) {
            return false;
        } else {
            user.setInstructor(instructor);
            instructor.setUser(user);
            userDAO.insert(user);
            return true;
        }
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }
    
    

}
