package DAO.course;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.course.CourseLearner;
import model.course.CourseLearnerPK;

public class CourseLearnerDAOImpl extends GenericDAO<CourseLearner> implements ICourseLearnerDAO {

    public CourseLearnerDAOImpl() {
        super(CourseLearner.class);
    }

    @Override
    public void insert(CourseLearner courseLearner) {
        super.insert(courseLearner);
    }

    @Override
    public boolean exists(Long courseId, Long learnerId) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(cl) FROM CourseLearner cl WHERE cl.courseLearnerPK.courseId = :courseId AND cl.courseLearnerPK.learnerId = :learnerId",
                    Long.class
            );
            query.setParameter("courseId", courseId);
            query.setParameter("learnerId", learnerId);
            return query.getSingleResult() > 0;
        }
    }

    @Override
    public List<CourseLearner> findByLearnerId(Long learnerId) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(
                    "SELECT cl FROM CourseLearner cl WHERE cl.courseLearnerPK.learnerId = :learnerId",
                    CourseLearner.class
            ).setParameter("learnerId", learnerId).getResultList();
        }
    }

    @Override
    public List<CourseLearner> findByCourseId(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(
                    "SELECT cl FROM CourseLearner cl WHERE cl.courseLearnerPK.courseId = :courseId",
                    CourseLearner.class
            ).setParameter("courseId", courseId).getResultList();
        }
    }

}
