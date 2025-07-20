/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.learnerLessonItem;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import model.course.LearningStatus;
import model.course.courseContent.LearnerLessonItem;

/**
 *
 * @author DELL
 */
public class LearnerLessonItemDAO extends GenericDAO<LearnerLessonItem> implements ILearnerLessonItemDAO {

    public LearnerLessonItemDAO(Class<LearnerLessonItem> entityClass) {
        super(entityClass);
    }

    @Override
    public List<LearnerLessonItem> findByLearnerAndCourse(Long learnerId, Long courseId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
            SELECT lli FROM LearnerLessonItem lli
            JOIN lli.lessonItem li
            JOIN li.lesson l
            JOIN l.module m
            WHERE lli.learner.id = :learnerId AND m.course.id = :courseId
        """;
            TypedQuery<LearnerLessonItem> query = em.createQuery(jpql, LearnerLessonItem.class);
            query.setParameter("learnerId", learnerId);
            query.setParameter("courseId", courseId);
            return query.getResultList();
        }

    }

    @Override
    public void updateLearningStatus(Long learnerId, Long lessonItemId, LearningStatus status, Double progress) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            LearnerLessonItem lli = findByLearnerAndLessonItem(learnerId, lessonItemId);
            if (lli != null) {
                lli.setLearningStatus(status);
                lli.setProgressPercent(progress);
                if (LearningStatus.COMPLETED.equals(status)) {
                    lli.setCompletedAt(LocalDateTime.now());
                } else {
                    lli.setCompletedAt(null);
                }
                em.merge(lli);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Override
    public LearnerLessonItem findByLearnerAndLessonItem(Long learnerId, Long lessonItemId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
            SELECT lli FROM LearnerLessonItem lli
            WHERE lli.learner.id = :learnerId AND lli.lessonItem.id = :lessonItemId
        """;
            TypedQuery<LearnerLessonItem> query = em.createQuery(jpql, LearnerLessonItem.class
            );
            query.setParameter("learnerId", learnerId);
            query.setParameter("lessonItemId", lessonItemId);
            List<LearnerLessonItem> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        }
    }

    @Override
    public long countLessonItemsInCourse(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
            SELECT COUNT(li.id)
            FROM LessonItem li
            JOIN li.lesson l
            JOIN l.module m
            WHERE m.course.id = :courseId
        """;
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("courseId", courseId);
            return query.getSingleResult();
        }
    }

    @Override
    public long countCompletedLessonItems(Long learnerId, Long courseId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
            SELECT COUNT(lli.id)
            FROM LearnerLessonItem lli
            JOIN lli.lessonItem li
            JOIN li.lesson l
            JOIN l.module m
            WHERE lli.learner.id = :learnerId
            AND lli.learningStatus = :status
            AND m.course.id = :courseId
        """;
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("learnerId", learnerId);
        query.setParameter("courseId", courseId);
        query.setParameter("status", LearningStatus.COMPLETED);
        return query.getSingleResult();
        }
    }

}
