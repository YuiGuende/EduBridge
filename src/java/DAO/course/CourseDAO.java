package DAO.course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Language;
import model.course.Tag;
import org.hibernate.Hibernate;

import model.course.Course;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.JpaConfig;

public class CourseDAO {

    public Course findById(Long courseId) {
        EntityManager emna = JpaConfig.getEntityManager();
        try {
            Course course = emna.find(Course.class, courseId);
            course.getModules().size();
            course.getLearningOutcomes().size();
            course.getRequirements().size();
            course.getCourseFor().size();
            course.getLanguages().size();
            course.getPrimaryLanguage().getText();
            return course;
        } catch (Exception e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            emna.close();
        }
        return null;
    }

    public Course save(Course course) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Course savedCourse;
            if (course.getId() == null) {
                // New course
                em.persist(course);
                savedCourse = course;
                System.out.println("New course persisted: " + course.getTitle());
            } else {
                // Update existing course
                System.out.println("Merging course: " + course.getTitle());

                // Make sure the entity is in managed state
                Course managedCourse = em.find(Course.class, course.getId());
                if (managedCourse == null) {
                    throw new RuntimeException("Course not found for update: " + course.getId());
                }

                // Update fields
                managedCourse.setTitle(course.getTitle());
                managedCourse.setHeadline(course.getHeadline());
                managedCourse.setDescription(course.getDescription());
                managedCourse.setThumbnailUrl(course.getThumbnailUrl());
                managedCourse.setLanguage(course.getLanguage());
                managedCourse.setCourseFor(course.getCourseFor());
                managedCourse.setRequirements(course.getRequirements());
                managedCourse.setLearningOutcomes(course.getLearningOutcomes());
                managedCourse.setStatus(course.getStatus());

                // Flush to ensure changes are written
                em.flush();
                savedCourse = managedCourse;
                System.out.println("Course merged successfully: " + savedCourse.getTitle());
            }

            transaction.commit();
            System.out.println("Transaction committed successfully");
            return savedCourse;

        } catch (Exception e) {
            System.err.println("Error saving course: " + e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
                System.out.println("Transaction rolled back");
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to save course: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void delete(Long courseId) throws Exception {
        EntityManager emna = JpaConfig.getEntityManager();
        EntityTransaction trans = emna.getTransaction();
        try {
            trans.begin();
            Course course = findById(courseId); // Gọi hàm mới

            if (course != null) {
                emna.remove(emna.merge(course)); // cần merge nếu entity detached
            } else {
                throw new Exception("Không tìm thấy");
            }

            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            emna.close();
        }
    }

    public List<Course> findAll() {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        } finally {
            em.close();
        }
    }
//    public List<Course> findCourses(int instructorID, String status, String keyword, String sort, int offset, int limit) {
//        List<Course> list = new ArrayList<>();
//        StringBuilder sql = new StringBuilder(
//            "SELECT c.* FROM Course c " +
//            "INNER JOIN Course_Instructor ci ON c.id = ci.course_id " +
//            "WHERE ci.instructor_id = ? "
//        );
//
//        if (status != null && !status.equals("all")) {
//            sql.append("AND c.status = ? ");
//        }
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            sql.append("AND c.title LIKE ? ");
//        }
//
//        switch (sort) {
//            case "az": sql.append("ORDER BY c.title ASC "); break;
//            case "za": sql.append("ORDER BY c.title DESC "); break;
//            case "newest": sql.append("ORDER BY c.published_time DESC "); break;
//            case "oldest": sql.append("ORDER BY c.published_time ASC "); break;
//            case "popular": sql.append("ORDER BY c.enrollCount DESC "); break;
//            case "rating": sql.append("ORDER BY c.rating DESC "); break;
//            default: sql.append("ORDER BY c.id ASC "); break;
//        }
//
//        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
//
//        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
//            int paramIndex = 1;
//            ps.setInt(paramIndex++, instructorID);
//
//            if (status != null && !status.equals("all")) {
//                ps.setString(paramIndex++, status);
//            }
//
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                ps.setString(paramIndex++, "%" + keyword + "%");
//            }
//
//            ps.setInt(paramIndex++, offset);
//            ps.setInt(paramIndex, limit);
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                list.add(mapResultSetToCourse(rs));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    public int countCourses(int instructorID, String status, String keyword) {
//        StringBuilder sql = new StringBuilder(
//            "SELECT COUNT(*) FROM Course c " +
//            "INNER JOIN Course_Instructor ci ON c.id = ci.course_id " +
//            "WHERE ci.instructor_id = ? "
//        );
//
//        if (status != null && !status.equals("all")) {
//            sql.append("AND c.status = ? ");
//        }
//
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            sql.append("AND c.title LIKE ? ");
//        }
//
//        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
//            int paramIndex = 1;
//            ps.setInt(paramIndex++, instructorID);
//
//            if (status != null && !status.equals("all")) {
//                ps.setString(paramIndex++, status);
//            }
//
//            if (keyword != null && !keyword.trim().isEmpty()) {
//                ps.setString(paramIndex++, "%" + keyword + "%");
//            }
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return 0;
//    }

    public List<Course> findCoursesByStatus(int instructorId, String status, int offset, int limit) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Course c JOIN c.instructors ci WHERE ci.id = :instructorId AND c.status = :status";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("instructorId", instructorId);
            query.setParameter("status", status);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Course> findCoursesByKeywordAndStatus(int instructorId, String keyword, String status, int offset, int limit) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Course c JOIN c.instructors ci "
                    + "WHERE ci.id = :instructorId AND c.title LIKE :keyword AND c.status = :status";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("instructorId", instructorId);
            query.setParameter("keyword", "%" + keyword + "%");
            query.setParameter("status", status);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int countCourses(int instructorId, String status, String keyword) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(c) FROM Course c JOIN c.instructors ci WHERE ci.id = :instructorId");

            if (status != null && !"all".equals(status)) {
                jpql.append(" AND c.status = :status");
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql.append(" AND c.title LIKE :keyword");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            query.setParameter("instructorId", instructorId);
            if (status != null && !"all".equals(status)) {
                query.setParameter("status", status);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword + "%");
            }

            return query.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }
}
