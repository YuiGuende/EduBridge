package DAO.user;


import DAO.GenericDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.user.Learner;

public class LearnerDAOImpl extends GenericDAO<Learner> implements ILearnerDAO {
    
    public LearnerDAOImpl(Class<Learner> entityClass) {
        super(entityClass);
    }
    
    @Override
    public List<Learner> findLearnersWithFilters(String name, String email, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT l FROM Learner l JOIN l.user u WHERE 1=1");
            
            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }
            
            jpql.append(" ORDER BY u.createdAt DESC");
            
            TypedQuery<Learner> query = em.createQuery(jpql.toString(), Learner.class);
            
            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
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
    public int countLearnersWithFilters(String name, String email) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(l) FROM Learner l JOIN l.user u WHERE 1=1");
            
            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }
            
            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            
            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public int countActiveLearners() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(l) FROM Learner l JOIN l.user u WHERE u.role = 'LEARNER'";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
