/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import DAO.course.CourseDAOImpl;
import DAO.course.ICourseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Collections;
import java.util.Comparator;
import model.course.Course;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private final CourseDAOImpl courseDAO;

    public CourseServiceImpl() {
        this.courseDAO = new CourseDAOImpl(Course.class);
    }

    @Override
    public void addCourse(Course course) {
        courseDAO.insert(course);
    }

    @Override
    public Course findCourse(Long id) {
        return courseDAO.findById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    @Override
    public List<Course> getCoursesOfInstructorByStatus(int instructorID, String status, int offset, int limit) {
        return courseDAO.findCoursesByStatus(instructorID, status, offset, limit);
    }

    @Override
    public int countCoursesOfInstructor(int instructorID, String status, String keyword) {
        return courseDAO.countCourses(instructorID, status, keyword);
    }

    @Override
    public List<Course> getCourseByKeywordAndStatus(int instructorID, String keyword, String status, int offset, int limit) {
        return courseDAO.findCoursesByKeywordAndStatus(instructorID, keyword, status, offset, limit);
    }

    @Override
    public List<Course> sortCourses(List<Course> courses, String sort) {
        if (courses == null) {
            return Collections.emptyList();
        }

        switch (sort != null ? sort : "") {
            case "az":
                courses.sort(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER));
                break;
            case "za":
                courses.sort(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());
                break;
            case "newest":
                courses.sort(Comparator.comparing(Course::getPublishedTime, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "oldest":
                courses.sort(Comparator.comparing(Course::getPublishedTime, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
//            case "popular":
//                courses.sort(Comparator.comparing(Course::getEnrollCount, Comparator.nullsLast(Comparator.reverseOrder())));
//                break;
//            case "rating":
//                courses.sort(Comparator.comparing(Course::getRating, Comparator.nullsLast(Comparator.reverseOrder())));
//                break;
            default:
                courses.sort(Comparator.comparing(Course::getId));
                break;
        }

        return courses;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Persistence.class.getProtectionDomain().getCodeSource());
            System.out.println(Class.forName("org.eclipse.persistence.jpa.PersistenceProvider"));
            // Tạo EntityManagerFactory từ persistence.xml
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EduBridgePU");
            EntityManager em = emf.createEntityManager();

            // Bắt đầu transaction (không cần nếu chỉ test select)
            em.getTransaction().begin();

            // Thử lấy course ID = 1
            Course c = em.find(Course.class, 1L);
            if (c != null) {
                System.out.println("Course found: " + c.getTitle());
            } else {
                System.out.println("Course not found.");
            }

            em.getTransaction().commit();
            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println("JPA test failed: " + e.getMessage());
            
            e.printStackTrace();
        }
    }
}
