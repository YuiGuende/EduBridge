package DAO.course;

import DAO.GenericDAO;
import jakarta.persistence.*;

import model.course.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.course.CourseStatus;
import util.JpaConfig;

public class CourseDAOImpl extends GenericDAO<Course> implements ICourseDAO {

    public CourseDAOImpl(Class<Course> entityClass) {
        super(entityClass);
    }

//    public Course findLanguageById(Long courseId) {
//        EntityManager emna = JpaConfig.getEntityManager();
//        try {
//            Course course = emna.find(Course.class, courseId);
//            course.getModules().size();
//            course.getLearningOutcomes().size();
//            course.getRequirements().size();
//            course.getCourseFor().size();
//            course.getLanguages().size();
//            course.getPrimaryLanguage().getText();
//            return course;
//        } catch (Exception e) {
//            Logger.getLogger(CourseDAOImpl.class.getName()).log(Level.SEVERE, null, e);
//        } finally {
//            emna.close();
//        }
//        return null;
//    }
//
//    public Course save(Course course) {
//        EntityManager em = JpaConfig.getEntityManager();
//        EntityTransaction transaction = em.getTransaction();
//        try {
//            transaction.begin();
//
//            Course savedCourse;
//            if (course.getId() == null) {
//                // New course
//                em.persist(course);
//                savedCourse = course;
//                System.out.println("New course persisted: " + course.getTitle());
//            } else {
//                // Update existing course
//                System.out.println("Merging course: " + course.getTitle());
//
//                // Make sure the entity is in managed state
//                Course managedCourse = em.find(Course.class, course.getId());
//                if (managedCourse == null) {
//                    throw new RuntimeException("Course not found for update: " + course.getId());
//                }
//
//                // Update fields
//                managedCourse.setTitle(course.getTitle());
//                managedCourse.setHeadline(course.getHeadline());
//                managedCourse.setDescription(course.getDescription());
//                managedCourse.setThumbnailUrl(course.getThumbnailUrl());
//                managedCourse.setLanguage(course.getLanguage());
//                managedCourse.setCourseFor(course.getCourseFor());
//                managedCourse.setRequirements(course.getRequirements());
//                managedCourse.setLearningOutcomes(course.getLearningOutcomes());
//                managedCourse.setStatus(course.getStatus());
//
//                // Flush to ensure changes are written
//                em.flush();
//                savedCourse = managedCourse;
//                System.out.println("Course merged successfully: " + savedCourse.getTitle());
//            }
//
//            transaction.commit();
//            System.out.println("Transaction committed successfully");
//            return savedCourse;
//
//        } catch (Exception e) {
//            System.err.println("Error saving course: " + e.getMessage());
//            if (transaction.isActive()) {
//                transaction.rollback();
//                System.out.println("Transaction rolled back");
//            }
//            e.printStackTrace();
//            throw new RuntimeException("Failed to save course: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
//
//    public void deleteById(Long courseId) throws Exception {
//        EntityManager emna = JpaConfig.getEntityManager();
//        EntityTransaction trans = emna.getTransaction();
//        try {
//            trans.begin();
//            Course course = findLanguageById(courseId); // Gọi hàm mới
//
//            if (course != null) {
//                emna.remove(emna.merge(course)); // cần merge nếu entity detached
//            } else {
//                throw new Exception("Không tìm thấy");
//            }
//
//            trans.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            trans.rollback();
//            throw e;
//        } finally {
//            emna.close();
//        }
//    }
//
//    public List<Course> findAll() {
//        EntityManager em = JpaConfig.getEntityManager();
//        try {
//            return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
//        } finally {
//            em.close();
//        }
//    }
    @Override
    public List<Course> findCoursesByStatus(int instructorId, String status, int offset, int limit) {
        try (EntityManager em = getEntityManager();) {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Course c JOIN c.instructors ci "
                    + "WHERE ci.id = :instructorId");
            CourseStatus enumStatus = null;

            if (status != null && !"all".equals(status)) {
                try {
                    enumStatus = CourseStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Nếu không hợp lệ, có thể set mặc định hoặc throw lỗi
                    enumStatus = null; // hoặc return empty list
                }
                jpql.append(" AND c.status = :status");
            }

            TypedQuery<Course> query = em.createQuery(jpql.toString(), Course.class);
            query.setParameter("instructorId", instructorId);
            if (status != null && !"all".equals(status)) {
                query.setParameter("status", enumStatus);
            }
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    @Override
    public List<Course> findCoursesByKeywordAndStatus(int instructorId, String keyword, String status, int offset, int limit) {
        try (EntityManager em = getEntityManager();) {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Course c JOIN c.instructors ci "
                    + "WHERE ci.id = :instructorId AND c.title LIKE :keyword");
            CourseStatus enumStatus = null;

            if (status != null && !"all".equals(status)) {
                try {
                    enumStatus = CourseStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Nếu không hợp lệ, có thể set mặc định hoặc throw lỗi
                    enumStatus = null; // hoặc return empty list
                }
                jpql.append(" AND c.status = :status");
            }

//            String jpql = "SELECT c FROM Course c JOIN c.instructors ci "
//                    + "WHERE ci.id = :instructorId AND c.title LIKE :keyword AND c.status = :status";
            TypedQuery<Course> query = em.createQuery(jpql.toString(), Course.class);
            query.setParameter("instructorId", instructorId);
            query.setParameter("keyword", "%" + keyword + "%");
            if (status != null && !"all".equals(status)) {
                query.setParameter("status", enumStatus);
            }
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    @Override
    public int countCourses(int instructorId, String status, String keyword) {
        try (EntityManager em = getEntityManager();) {

            StringBuilder jpql = new StringBuilder("SELECT COUNT(c) FROM Course c JOIN c.instructors ci WHERE ci.id = :instructorId");
            CourseStatus enumStatus = null;

            if (status != null && !"all".equals(status)) {
                try {
                    enumStatus = CourseStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Nếu không hợp lệ, có thể set mặc định hoặc throw lỗi
                    enumStatus = null; // hoặc return empty list
                }
                jpql.append(" AND c.status = :status");
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql.append(" AND c.title LIKE :keyword");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            query.setParameter("instructorId", instructorId);
            if (status != null && !"all".equals(status)) {
                query.setParameter("status", enumStatus);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword + "%");
            }

            return query.getSingleResult().intValue();
        }
    }
}
