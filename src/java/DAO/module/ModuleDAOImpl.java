package DAO.module;

import DAO.GenericDAO;
import model.course.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.course.courseContent.Module;

public class ModuleDAOImpl extends GenericDAO<Module> implements IModuleDAO {

    public ModuleDAOImpl(Class<Module> entityClass) {
        super(entityClass);
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
    public Module findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Module module = em.find(Module.class, id);
            module.getLessons().size();
            return module;
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
//    @Override
//    public Module save(Module module) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            if (module.getId() == null) {
//                em.persist(module);
//            } else {
//                module = em.merge(module);
//            }
//            em.getTransaction().commit();
//            return module;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error saving module: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//    @Override
//    public Optional<Module> findModuleById(Long id) {
//        try (EntityManager em = getEntityManager();) {
//            Module module = em.find(Module.class, id);
//            return Optional.ofNullable(module);
//        }
//    }

//    @Override
//    public List<Module> findAll() {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Module> query = em.createQuery(
//                    "SELECT m FROM Module m ORDER BY m.course.title, m.index",
//                    Module.class);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//    @Override
//    public Module update(Module module) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Module updatedModule = em.merge(module);
//            em.getTransaction().commit();
//            return updatedModule;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error updating module: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//    @Override
//    public void delete(Module module) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Module managedModule = em.merge(module);
//            em.remove(managedModule);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting module: " + e.getMessage(), e);
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
//            Module module = em.find(Module.class, id);
//            if (module != null) {
//                em.remove(module);
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting module by ID: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public List<Module> findByCourse(Course course) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course = :course ORDER BY m.index",
                    Module.class);
            query.setParameter("course", course);
            return query.getResultList();
        }
    }

    @Override
    public List<Module> findByCourseId(Long courseId) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course.id = :courseId ORDER BY m.index",
                    Module.class);
            query.setParameter("courseId", courseId);
            return query.getResultList();
        }
    }

    @Override
    public List<Module> findPreviewModules() {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.preview = true ORDER BY m.course.title, m.index",
                    Module.class);
            return query.getResultList();
        }
    }

    @Override
    public List<Module> findByTitleContaining(String title) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE LOWER(m.title) LIKE LOWER(:title) ORDER BY m.title",
                    Module.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        }
    }

    @Override
    public Module findByIdWithLessons(Long id) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m LEFT JOIN FETCH m.lessons WHERE m.id = :id",
                    Module.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public long count(Module module) {
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Long> query = em.createQuery(
//                    "SELECT COUNT(m) FROM Module m", Long.class);
//            return query.getSingleResult();
//        } finally {
//            em.close();
//        }
//    }

    @Override
    public long countByCourse(Course course) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(m) FROM Module m WHERE m.course = :course",
                    Long.class);
            query.setParameter("course", course);
            return query.getSingleResult();
        }
    }

//    @Override
//    public boolean exists(Long id) {
//        return findModuleById(id).isPresent();
//    }

    @Override
    public Module findNextModule(Module currentModule) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course = :course AND m.index > :currentIndex ORDER BY m.index",
                    Module.class);
            query.setParameter("course", currentModule.getCourse());
            query.setParameter("currentIndex", currentModule.getIndex());
            query.setMaxResults(1);
            List<Module> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        }
    }

    @Override
    public Module findPreviousModule(Module currentModule) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.course = :course AND m.index < :currentIndex ORDER BY m.index DESC",
                    Module.class);
            query.setParameter("course", currentModule.getCourse());
            query.setParameter("currentIndex", currentModule.getIndex());
            query.setMaxResults(1);
            List<Module> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        }
    }
    @Override
    public Module findByIndexAndCourseId(int index, Long courseId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Module> query = em.createQuery(
                    "SELECT m FROM Module m WHERE m.index = :index AND m.course.id = :courseId",
                    Module.class
            );

            em.clear(); 
            query.setParameter("index", index);
            query.setParameter("courseId", courseId);
            List<Module> result = query.getResultList();
            if (result.isEmpty()) {
                return null;
            } else {
                Module module = result.get(0);

                em.refresh(module); // Làm mới module từ DB
                module.getLessons().size();
                return module;
            }

        } finally {
            em.close();
        }
    }

}
