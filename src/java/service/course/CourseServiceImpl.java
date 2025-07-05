/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import DAO.course.CourseDAO;
import agentAi.GroqService;
import java.util.List;
import java.util.stream.Collectors;
import model.course.Course;

public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO = new CourseDAO();
    private final GroqService groqService = new GroqService();

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

}
