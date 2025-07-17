package DAO.comment;


import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.notification.*;
import model.course.Course;
import java.util.List;

public class CommentDAOImpl extends GenericDAO<Comment> implements ICommentDAO {

    public CommentDAOImpl() {
        super(Comment.class);
    }
 @Override
    public List<Comment> findCommentsWithFilters(String course, String user, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Comment c JOIN c.user u JOIN c.course co WHERE 1=1");
            
            if (course != null && !course.trim().isEmpty()) {
                jpql.append(" AND co.title LIKE :course");
            }
            if (user != null && !user.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :user");
            }
            
            jpql.append(" ORDER BY c.createdAt DESC");
            
            TypedQuery<Comment> query = em.createQuery(jpql.toString(), Comment.class);
            
            if (course != null && !course.trim().isEmpty()) {
                query.setParameter("course", "%" + course + "%");
            }
            if (user != null && !user.trim().isEmpty()) {
                query.setParameter("user", "%" + user + "%");
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
    public int countCommentsWithFilters(String course, String user) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(c) FROM Comment c JOIN c.user u JOIN c.course co WHERE 1=1");
            
            if (course != null && !course.trim().isEmpty()) {
                jpql.append(" AND co.title LIKE :course");
            }
            if (user != null && !user.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :user");
            }
            
            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            
            if (course != null && !course.trim().isEmpty()) {
                query.setParameter("course", "%" + course + "%");
            }
            if (user != null && !user.trim().isEmpty()) {
                query.setParameter("user", "%" + user + "%");
            }
            
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public List<Comment> findByCourse(Course course) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Comment> query = em.createQuery(
                "SELECT c FROM Comment c WHERE c.course = :course ORDER BY c.createdAt DESC",
                Comment.class
            );
            query.setParameter("course", course);
            return query.getResultList();
        }
    }

    @Override
    public List<Comment> findByCourseId(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Comment> query = em.createQuery(
                "SELECT c FROM Comment c WHERE c.course.id = :courseId ORDER BY c.createdAt DESC",
                Comment.class
            );
            query.setParameter("courseId", courseId);
            return query.getResultList();
        }
    }

    @Override
    public long countByCourse(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(
                "SELECT COUNT(c) FROM Comment c WHERE c.course.id = :courseId",
                Long.class
            ).setParameter("courseId", courseId)
             .getSingleResult();
        }
    }

    @Override
    public List<Comment> findByCourse(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Comment c WHERE c.course.id = :courseId ORDER BY c.createdAt DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
            query.setParameter("courseId", courseId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    @Override
    public List<Comment> findByUser(Long userId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.createdAt DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
