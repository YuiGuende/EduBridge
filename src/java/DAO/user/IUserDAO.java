/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.user;

import java.util.List;
import java.util.Optional;
import model.user.User;

/**
 *
 * @author DELL
 */
public interface IUserDAO {


    void insert(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean isEmailExists(String email);

    User findByEmail(String email);


    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

    List<User> findAll();

    List<User> findByRole(String role);

    long count();

    Double getAverageRateByInstructor(Long instructorId);

}
