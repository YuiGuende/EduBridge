package DAO.course;

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
            if (course == null) {
                System.out.println("Không tìm thấy Course với ID: " + courseId);
                return null;
            }
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

    public List<Course> findCoursesByDynamicSql(String sql) {
        EntityManager em = JpaConfig.getEntityManager();
        System.out.println("sql to be processes" + sql);
        List<Course> results = new ArrayList<>();
        try {
            results=em.createNativeQuery(sql, Course.class).getResultList();
            for (Course result : results) {
                 Hibernate.initialize(result.getLanguages());
                 
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }

}
