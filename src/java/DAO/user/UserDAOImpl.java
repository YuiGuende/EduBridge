/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.user;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.user.User;

/**
 *
 * @author DELL
 */
public class UserDAOImpl extends GenericDAO<User> implements IUserDAO {

    public UserDAOImpl(Class<User> entityClass) {
        super(entityClass);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email AND u.password = :password",
                    User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<User> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            System.err.println("Error finding user by email and password: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email",
                    long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        }
    }
}
