package DAO.lessonItem;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.course.courseContent.Lesson;
import model.course.courseContent.LessonItem;

public class LessonItemDAOImpl extends GenericDAO<LessonItem> implements ILessonItemDAO {

    public LessonItemDAOImpl(Class<LessonItem> entityClass) {
        super(entityClass);
    }


//    @Override
//    public LessonItem save(LessonItem lessonItem) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            if (lessonItem.getId() == null) {
//                em.persist(lessonItem);
//            } else {
//                lessonItem = em.merge(lessonItem);
//            }
//            em.getTransaction().commit();
//            return lessonItem;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error saving lesson item: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

//    @Override
//    public Optional<LessonItem> findById(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            LessonItem lessonItem = em.find(LessonItem.class, id);
//            return Optional.ofNullable(lessonItem);
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<LessonItem> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li ORDER BY li.lesson.module.course.title, li.lesson.module.index, li.lesson.index, li.index",
                    LessonItem.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

//    @Override
//    public LessonItem update(LessonItem lessonItem) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            LessonItem updatedLessonItem = em.merge(lessonItem);
//            em.getTransaction().commit();
//            return updatedLessonItem;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error updating lesson item: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

//    @Override
//    public void delete(LessonItem lessonItem) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            LessonItem managedLessonItem = em.merge(lessonItem);
//            em.remove(managedLessonItem);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting lesson item: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

//    @Override
//    public void deleteById(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            LessonItem lessonItem = em.find(LessonItem.class, id);
//            if (lessonItem != null) {
//                em.remove(lessonItem);
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting lesson item by ID: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<LessonItem> findByLesson(Lesson lesson) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.lesson = :lesson ORDER BY li.index",
                    LessonItem.class);
            query.setParameter("lesson", lesson);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<LessonItem> findByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.lesson.id = :lessonId ORDER BY li.index",
                    LessonItem.class);
            query.setParameter("lessonId", lessonId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

//    @Override
//    public List<LessonItem> findByType(LessonItemType type) {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<LessonItem> query = em.createQuery(
//                    "SELECT li FROM LessonItem li WHERE li.type = :type ORDER BY li.lesson.module.course.title, li.lesson.title, li.index",
//                    LessonItem.class);
//            query.setParameter("type", type);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public List<LessonItem> findByLessonAndType(Lesson lesson, LessonItemType type) {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<LessonItem> query = em.createQuery(
//                    "SELECT li FROM LessonItem li WHERE li.lesson = :lesson AND li.type = :type ORDER BY li.index",
//                    LessonItem.class);
//            query.setParameter("lesson", lesson);
//            query.setParameter("type", type);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<LessonItem> findByTitleContaining(String title) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE LOWER(li.title) LIKE LOWER(:title) ORDER BY li.title",
                    LessonItem.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<LessonItem> findByContentUrl(String contentUrl) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.contentUrl = :contentUrl",
                    LessonItem.class);
            query.setParameter("contentUrl", contentUrl);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
//
//    @Override
//    public long count() {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Long> query = em.createQuery(
//                    "SELECT COUNT(li) FROM LessonItem li", Long.class);
//            return query.getSingleResult();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public long countByLesson(Lesson lesson) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(li) FROM LessonItem li WHERE li.lesson = :lesson",
                    Long.class);
            query.setParameter("lesson", lesson);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
//
//    @Override
//    public long countByType(LessonItemType type) {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Long> query = em.createQuery(
//                    "SELECT COUNT(li) FROM LessonItem li WHERE li.type = :type",
//                    Long.class);
//            query.setParameter("type", type);
//            return query.getSingleResult();
//        } finally {
//            em.close();
//        }
//    }

//    @Override
//    public boolean exists(Long id) {
//        return findById(id).isPresent();
//    }

    @Override
    public LessonItem findNextLessonItem(LessonItem currentItem) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.lesson = :lesson AND li.index > :currentIndex ORDER BY li.index",
                    LessonItem.class);
            query.setParameter("lesson", currentItem.getLesson());
            query.setParameter("currentIndex", currentItem.getIndex());
            query.setMaxResults(1);
            List<LessonItem> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public LessonItem findPreviousLessonItem(LessonItem currentItem) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.lesson = :lesson AND li.index < :currentIndex ORDER BY li.index DESC",
                    LessonItem.class);
            query.setParameter("lesson", currentItem.getLesson());
            query.setParameter("currentIndex", currentItem.getIndex());
            query.setMaxResults(1);
            List<LessonItem> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<LessonItem> findByDurationRange(int minDuration, int maxDuration) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.estimatedDuration BETWEEN :minDuration AND :maxDuration ORDER BY li.estimatedDuration",
                    LessonItem.class);
            query.setParameter("minDuration", minDuration);
            query.setParameter("maxDuration", maxDuration);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<LessonItem> findByModuleId(Long moduleId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<LessonItem> query = em.createQuery(
                    "SELECT li FROM LessonItem li WHERE li.lesson.module.id = :moduleId ORDER BY li.lesson.index, li.index",
                    LessonItem.class);
            query.setParameter("moduleId", moduleId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


}
