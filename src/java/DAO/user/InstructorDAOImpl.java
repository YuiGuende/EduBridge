package DAO.user;


import DAO.GenericDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.user.Instructor;

public class InstructorDAOImpl extends GenericDAO<Instructor> implements IInstructorDAO {
    
    public InstructorDAOImpl(Class<Instructor> entityClass) {
        super(entityClass);
    }
    
    @Override
    public List<Instructor> findInstructorsWithFilters(String name, String email, String specialization, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT i FROM Instructor i JOIN i.user u WHERE 1=1");
            
            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                jpql.append(" AND i.specialization LIKE :specialization");
            }
            
            jpql.append(" ORDER BY u.createdAt DESC");
            
            TypedQuery<Instructor> query = em.createQuery(jpql.toString(), Instructor.class);
            
            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                query.setParameter("specialization", "%" + specialization + "%");
            }
            
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    @Override
    public int countInstructorsWithFilters(String name, String email, String specialization) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE 1=1");
            
            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                jpql.append(" AND i.specialization LIKE :specialization");
            }
            
            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            
            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                query.setParameter("specialization", "%" + specialization + "%");
            }
            
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public int countActiveInstructors() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE u.role = 'INSTRUCTOR'";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public List<Instructor> findBySpecialization(String specialization) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT i FROM Instructor i WHERE i.specialization = :specialization";
            TypedQuery<Instructor> query = em.createQuery(jpql, Instructor.class);
            query.setParameter("specialization", specialization);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
