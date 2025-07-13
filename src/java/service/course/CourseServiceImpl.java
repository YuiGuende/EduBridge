/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import agentAi.GroqService;
import java.util.List;
import java.util.stream.Collectors;
import DAO.course.CourseDAOImpl;
import DAO.course.ICourseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Collections;
import java.util.Comparator;

import model.course.Course;

import java.util.List;

import model.course.Course;

import agentAi.GroqService;

public class CourseServiceImpl implements CourseService {

    private final GroqService groqService = new GroqService();

    private final CourseDAOImpl courseDAO;

    public CourseServiceImpl() {
        this.courseDAO = new CourseDAOImpl(Course.class);
    }

    @Override
    public void addCourse(Course course) {
        courseDAO.save(course);
    }

    @Override
    public Course findCourse(Long id) {
        return courseDAO.findById(id);
    }

    @Override
    public String processUserMessage(String message, boolean showMore) throws Exception {
        // 1. AI sinh SQL
        String sql = groqService.generateSQLFromUserQuery(message, showMore);

        // 2. Gọi DB để lấy danh sách khóa học
        List<Course> courseList = courseDAO.findCoursesByDynamicSql(sql);
        if (courseList.isEmpty()) {
            System.out.println("list is null");
        } else {
            System.out.println("ALl title found by AI");
            for (Course course : courseList) {
                System.out.println(course.getTitle());
            }
        }

        // 3. Gộp context lại để tạo prompt cuối
        String context = buildCourseContext(courseList);

        // 4. Gửi prompt tới AI để sinh phản hồi cuối
        String finalPrompt = groqService.buildSystemPromptWithCourses(context);
        return groqService.callGroqWithPrompt(finalPrompt, message);
    }

    private String buildCourseContext(List<Course> courses) {
        if (courses.isEmpty()) {
            return "Không tìm thấy khóa học phù hợp.";
        }

        return courses.stream().map(course -> String.format(
                "- 📚 %s (%s): %s tuần - %s\nMô tả: %s",
                course.getTitle(),
                course.isPaid() ? "Trả phí" : "Miễn phí",
                course.getEstimatedTime(),
                course.getPrimaryLanguage() != null ? course.getPrimaryLanguage().getName() : "Không rõ",
                course.getHeadline()
        )).collect(Collectors.joining("\n\n"));
    }

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

    

    @Override
    public List<Course> findRelatedCourses(Course currentCourse, int limit) {
        if (currentCourse.getTags() != null && !currentCourse.getTags().isEmpty()) {
            String firstTag = currentCourse.getTags().get(0).getName();
            return courseDAO.findByTag(firstTag, limit);
        }

        // Fallback: dùng phương thức mới
        return courseDAO.findCoursesLimited(limit);
    }

    @Override
    public List<Course> findCoursesLimited(int limit) {
        return courseDAO.findCoursesLimited(limit);
    }

}
