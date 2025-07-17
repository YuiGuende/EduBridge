package DAO.lesson;

import DAO.GenericDAO;
import model.course.courseContent.Module;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.course.courseContent.Lesson;

public class LessonDAOImpl extends GenericDAO<Lesson> implements ILessonDAO {

    public LessonDAOImpl(Class<Lesson> entityClass) {
        super(entityClass);
    }


//
//    @Override
//    public Lesson save(Lesson lesson) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            if (lesson.getId() == null) {
//                em.persist(lesson);
//            } else {
//                lesson = em.merge(lesson);
//            }
//            em.getTransaction().commit();
//            return lesson;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error saving lesson: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public Optional<Lesson> findById(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            Lesson lesson = em.find(Lesson.class, id);
//            return Optional.ofNullable(lesson);
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public List<Lesson> findAll() {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Lesson> query = em.createQuery(
//                    "SELECT l FROM Lesson l ORDER BY l.module.course.title, l.module.index, l.index",
//                    Lesson.class);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public Lesson update(Lesson lesson) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Lesson updatedLesson = em.merge(lesson);
//            em.getTransaction().commit();
//            return updatedLesson;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error updating lesson: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public void delete(Lesson lesson) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Lesson managedLesson = em.merge(lesson);
//            em.remove(managedLesson);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting lesson: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Lesson lesson = em.find(Lesson.class, id);
//            if (lesson != null) {
//                em.remove(lesson);
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting lesson by ID: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
    @Override
    public List<Lesson> findByModule(Module module) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.module = :module ORDER BY l.index",
                    Lesson.class);
            query.setParameter("module", module);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Lesson> findByModuleId(Long moduleId) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.module.id = :moduleId ORDER BY l.index",
                    Lesson.class);
            query.setParameter("moduleId", moduleId);
            return query.getResultList();
        }
    }

    @Override
    public List<Lesson> findByCourseId(Long courseId) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.module.course.id = :courseId ORDER BY l.module.index, l.index",
                    Lesson.class);
            query.setParameter("courseId", courseId);
            return query.getResultList();
        }
    }

    @Override
    public List<Lesson> findPreviewLessons() {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.preview = true ORDER BY l.module.course.title, l.module.index, l.index",
                    Lesson.class);
            return query.getResultList();
        }
    }

    @Override
    public List<Lesson> findByTitleContaining(String title) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE LOWER(l.title) LIKE LOWER(:title) ORDER BY l.title",
                    Lesson.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        }
    }

    @Override
    public Lesson findByIdWithLessonItems(Long id) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l LEFT JOIN FETCH l.lessonItems WHERE l.id = :id",
                    Lesson.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public long count() {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Long> query = em.createQuery(
//                    "SELECT COUNT(l) FROM Lesson l", Long.class);
//            return query.getSingleResult();
//        } finally {
//            em.close();
//        }
//    }
    @Override
    public long countByModule(Module module) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(l) FROM Lesson l WHERE l.module = :module",
                    Long.class);
            query.setParameter("module", module);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

//    @Override
//    public boolean exists(Long id) {
//        return findById(id).isPresent();
//    }
    @Override
    public Lesson findNextLesson(Lesson currentLesson) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.module = :module AND l.index > :currentIndex ORDER BY l.index",
                    Lesson.class);
            query.setParameter("module", currentLesson.getModule());
            query.setParameter("currentIndex", currentLesson.getIndex());
            query.setMaxResults(1);
            List<Lesson> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Lesson findPreviousLesson(Lesson currentLesson) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.module = :module AND l.index < :currentIndex ORDER BY l.index DESC",
                    Lesson.class);
            query.setParameter("module", currentLesson.getModule());
            query.setParameter("currentIndex", currentLesson.getIndex());
            query.setMaxResults(1);
            List<Lesson> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Lesson> findByDurationRange(int minDuration, int maxDuration) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Lesson> query = em.createQuery(
                    "SELECT l FROM Lesson l WHERE l.estimatedDuration BETWEEN :minDuration AND :maxDuration ORDER BY l.estimatedDuration",
                    Lesson.class);
            query.setParameter("minDuration", minDuration);
            query.setParameter("maxDuration", maxDuration);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
