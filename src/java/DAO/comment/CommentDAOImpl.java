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
}
