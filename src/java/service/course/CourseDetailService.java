package service.course;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import DAO.comment.CommentDAOImpl;
import DAO.course.CourseDAOImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.course.Course;
import model.notification.Comment;
import model.user.Instructor;

/**
 *
 * @author GoniperXComputer
 */
public class CourseDetailService {

    private final CommentDAOImpl commentDAO = new CommentDAOImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final CourseDAOImpl courseDAO = new CourseDAOImpl(Course.class);

    

    public Course getCourseWithDetails(Long courseId) {
        return courseService.findCourse(courseId);
    }

    public Instructor getPrimaryInstructor(Course course) {
        if (course != null && !course.getInstructors().isEmpty()) {
            return course.getInstructors().get(0);
        }
        return null;
    }

    public List<model.course.courseContent.Module> getCourseModule(Course course) {
        if (course == null || course.getModules() == null) {
            return new ArrayList<>();
        }
        return course.getModules();

    }

    private String formatDuration(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%d.%02d Hrs", hours, minutes);
    }

    private String formatLessonDuration(int minutes) {
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }

    public List<Course> getRelatedCourses(Course course, int limit) {
        return courseService.findRelatedCourses(course, limit);
    }

    public List<Comment> SortedCommentsByCourse(Long courseId) {
        return courseService.getSortedCommentsByCourse(courseId); // khi chi co id
    }

    public double calculateAverageRatingByCourse(List<Comment> comments) {
        return comments.stream()
                .filter(c -> c.getRating() > 0).mapToInt(Comment::getRating).average().orElse(0.0);
    }

    public Map<Integer, Long> countRatingsByStars(List<Comment> comments) {
        Map<Integer, Long> ratingCountMap = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingCountMap.put(i, 0L);
        }

        for (Comment c : comments) {
            int rating = c.getRating();
            if (rating >= 1 && rating <= 5) {
                ratingCountMap.put(rating, ratingCountMap.get(rating) + 1);
            }
        }

        return ratingCountMap;
    }

    public int countRatingByCourse(Course course) {
        List<Comment> comments = commentDAO.findByCourse(course);
        int count = 0;
        for (Comment c : comments) {
            if (c.getRating() > 0) {
                count++;
            }
        }
        return count;
    }

    public List<Comment> getSortedComments(Course course) {
        return commentDAO.findByCourse(course); // đã sorted trong DAO // dùng khi co course
    }

    public List<Course> getRelatedCoursesByInstructor(Long instructorId, Long excludeCourseId, int limit) {
        return courseDAO.findCoursesByInstructorExcept(instructorId, excludeCourseId, limit);
    }

}
