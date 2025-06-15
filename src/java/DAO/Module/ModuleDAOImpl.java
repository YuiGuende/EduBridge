package DAO.Module;

import model.course.Course;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.course.courseContent.Module;

public class ModuleDAOImpl implements IModuleDAO {

    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("coursePU");
        } catch (Exception e) {
            System.err.println("Error creating EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Module save(Module module) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (module.getId() == null) {
                em.persist(module);
            } else {
                module = em.merge(module);
            }
            em.getTransaction().commit();
            return module;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving module: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Module> findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Module module = em.find(Module.class, id);
            return Optional.ofNullable(module);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Module> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m ORDER BY m.course.title, m.index",
                    Module.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Module update(Module module) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Module updatedModule = em.merge(module);
            em.getTransaction().commit();
            return updatedModule;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error updating module: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Module module) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Module managedModule = em.merge(module);
            em.remove(managedModule);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting module: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Module module = em.find(Module.class, id);
            if (module != null) {
                em.remove(module);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting module by ID: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Module> findByCourse(Course course) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course = :course ORDER BY m.index",
                    Module.class);
            query.setParameter("course", course);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Module> findByCourseId(Long courseId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course.id = :courseId ORDER BY m.index",
                    Module.class);
            query.setParameter("courseId", courseId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Module> findPreviewModules() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.preview = true ORDER BY m.course.title, m.index",
                    Module.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Module> findByTitleContaining(String title) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE LOWER(m.title) LIKE LOWER(:title) ORDER BY m.title",
                    Module.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Module findByIdWithLessons(Long id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m LEFT JOIN FETCH m.lessons WHERE m.id = :id",
                    Module.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(m) FROM Module m", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByCourse(Course course) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(m) FROM Module m WHERE m.course = :course",
                    Long.class);
            query.setParameter("course", course);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean exists(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Module findNextModule(Module currentModule) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course = :course AND m.index > :currentIndex ORDER BY m.index",
                    Module.class);
            query.setParameter("course", currentModule.getCourse());
            query.setParameter("currentIndex", currentModule.getIndex());
            query.setMaxResults(1);
            List<Module> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Module findPreviousModule(Module currentModule) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course = :course AND m.index < :currentIndex ORDER BY m.index DESC",
                    Module.class);
            query.setParameter("course", currentModule.getCourse());
            query.setParameter("currentIndex", currentModule.getIndex());
            query.setMaxResults(1);
            List<Module> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    // Cleanup method
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
