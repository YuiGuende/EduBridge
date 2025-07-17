/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.user;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    @Override
    public User findByEmail(String email) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email",
                    User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Error finding user by email: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findByRole(String role) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT u FROM User u WHERE u.role = :role";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Double getAverageRateByInstructor(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String sql = """
            SELECT AVG(rate)
            FROM comments c
            JOIN courses co ON c.course_id = co.id
            WHERE co.created_by = ?
        """;
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, instructorId);
            Double avg = (Double) query.getSingleResult();
            return avg != null ? avg : 0.0;
        }
    }
}
