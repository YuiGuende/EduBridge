/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.user;

import dao.GenericDAO;
import dao.user.UserDAOImpl;
import java.util.Optional;
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
    public void signup(User user) {
        userDAO.insert(user);
    }

    @Override
    public void signupForInstructor(User user, Instructor instructor) {
        user.setInstructor(instructor);
        instructor.setUser(user);
        userDAO.insert(user);
    }

    @Override
    public boolean isEmailExists(String emails) {
        return userDAO.isEmailExists(emails);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

}
