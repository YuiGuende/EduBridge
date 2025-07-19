package DAO.course;

import DAO.GenericDAO;
import jakarta.persistence.*;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Tag;
import model.notification.Comment;

import util.JpaConfig;

public class CourseDAOImpl extends GenericDAO<Course> implements ICourseDAO {

    public CourseDAOImpl(Class<Course> entityClass) {
        super(entityClass);
    }

    // Existing methods from your implementation
    @Override
    public List<Course> findCoursesByStatus(Long instructorId, String status, int offset, int limit) {
        try (EntityManager em = getEntityManager();) {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Course c JOIN c.createdBy ci "
                    + "WHERE ci.id = :instructorId");
            CourseStatus enumStatus = null;

            if (status != null && !"all".equals(status)) {
                try {
                    enumStatus = CourseStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    enumStatus = null;
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
    public Course findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Course course = em.find(Course.class, id);
            if (course != null) {
                em.refresh(course); // Làm mới dữ liệu từ DB
            }
            return course;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Course> findCoursesByKeywordAndStatus(long instructorId, String keyword, String status, int offset, int limit) {
        try (EntityManager em = getEntityManager();) {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Course c JOIN c.createdBy ci "
                    + "WHERE ci.id = :instructorId AND c.title LIKE :keyword");
            CourseStatus enumStatus = null;

            if (status != null && !"all".equals(status)) {
                try {
                    enumStatus = CourseStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    enumStatus = null;
                }
                jpql.append(" AND c.status = :status");
            }
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
    public int countCourses(long instructorId, String status, String keyword) {
        try (EntityManager em = getEntityManager();) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(c) FROM Course c JOIN c.createdBy ci WHERE ci.id = :instructorId");
            CourseStatus enumStatus = null;
            if (status != null && !"all".equals(status)) {
                try {
                    enumStatus = CourseStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    enumStatus = null;
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

    public List<Course> findCoursesByDynamicSql(String sql) {
        EntityManager em = getEntityManager();
        System.out.println("sql to be processes" + sql);
        List<Course> results = new ArrayList<>();
        try {
            results = em.createNativeQuery(sql, Course.class).getResultList();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Course> getTrendingCourses() {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
        SELECT c FROM Course c
        WHERE c.status = :status
        ORDER BY c.rate.rateQuantity DESC, c.rate.rate DESC
    """;
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("status", CourseStatus.PUBLIC);
            query.setMaxResults(10);
            return query.getResultList();
        }
    }

    @Override
    public List<Course> getCoursesByTag(Tag tag) {
        try (EntityManager em = getEntityManager()) {

            String jpql = "SELECT DISTINCT c FROM Course c JOIN c.tags t WHERE t.name = :name AND t.type = :type AND c.status = :status";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("name", tag.getName());
            query.setParameter("type", tag.getType());
            query.setParameter("status", CourseStatus.PUBLIC);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Course> findCoursesLimited(int limit) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c ORDER BY FUNCTION('NEWID')", Course.class);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    @Override
    public List<Course> findByTag(String tagName, int limit) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT DISTINCT c FROM Course c JOIN c.tags t WHERE t.name = :tagName ORDER BY c.id";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("tagName", tagName);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    // Additional methods for admin panel
    @Override
    public List<Course> findCoursesWithFilters(String title, String status, String instructor, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT DISTINCT c FROM Course c LEFT JOIN c.instructors i LEFT JOIN i.user u WHERE 1=1");

            if (title != null && !title.trim().isEmpty()) {
                jpql.append(" AND c.title LIKE :title");
            }
            if (status != null && !status.trim().isEmpty()) {
                jpql.append(" AND c.status = :status");
            }
            if (instructor != null && !instructor.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :instructor");
            }

            jpql.append(" ORDER BY c.createdDate DESC");

            TypedQuery<Course> query = em.createQuery(jpql.toString(), Course.class);

            if (title != null && !title.trim().isEmpty()) {
                query.setParameter("title", "%" + title + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                query.setParameter("status", CourseStatus.valueOf(status.toUpperCase()));
            }
            if (instructor != null && !instructor.trim().isEmpty()) {
                query.setParameter("instructor", "%" + instructor + "%");
            }

            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int countCoursesWithFilters(String title, String status, String instructor) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(DISTINCT c) FROM Course c LEFT JOIN c.instructors i LEFT JOIN i.user u WHERE 1=1");

            if (title != null && !title.trim().isEmpty()) {
                jpql.append(" AND c.title LIKE :title");
            }
            if (status != null && !status.trim().isEmpty()) {
                jpql.append(" AND c.status = :status");
            }
            if (instructor != null && !instructor.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :instructor");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

            if (title != null && !title.trim().isEmpty()) {
                query.setParameter("title", "%" + title + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                query.setParameter("status", CourseStatus.valueOf(status.toUpperCase()));
            }
            if (instructor != null && !instructor.trim().isEmpty()) {
                query.setParameter("instructor", "%" + instructor + "%");
            }

            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Course> findRecentCourses(int limit) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Course c ORDER BY c.createdDate DESC";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Course> findByStatus(CourseStatus status, int limit) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Course c WHERE c.status = :status ORDER BY c.createdDate DESC";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("status", status);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Course> findByStatus(CourseStatus status) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Course c WHERE c.status = :status";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("status", status);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Course> findByInstructor(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Course c JOIN c.instructors i WHERE i.id = :instructorId";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("instructorId", instructorId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Comment> sortByCreated(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Comment c WHERE c.course.id = :courseId ORDER BY c.createdAt DESC";
            return em.createQuery(jpql, Comment.class)
                    .setParameter("courseId", courseId)
                    .getResultList();

        }
    }

    @Override
    public List<Course> findCoursesByInstructorExcept(Long instructorId, Long excludeCourseId, int limit) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT c FROM Course c WHERE c.createdBy.id = :instructorId AND c.id <> :excludeCourseId";
            TypedQuery<Course> query = em.createQuery(jpql, Course.class);
            query.setParameter("instructorId", instructorId);
            query.setParameter("excludeCourseId", excludeCourseId);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

}
