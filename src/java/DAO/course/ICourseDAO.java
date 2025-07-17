/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.course;

import java.util.List;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Tag;
import model.notification.Comment;

/**
 *
 * @author DELL
 */
public interface ICourseDAO {

 List<Course> findCoursesByInstructorExcept(Long instructorId, Long excludeCourseId, int limit);
    List<Comment> sortByCreated(Long courseId);

    List<Course> findCoursesByStatus(Long instructorId, String status, int offset, int limit);

    List<Course> findCoursesByKeywordAndStatus(long instructorId, String keyword, String status, int offset, int limit);

    int countCourses(long instructorId, String status, String keyword);

    List<Course> getTrendingCourses();

    List<Course> getCoursesByTag(Tag tag);

    public List<Course> findByTag(String tagName, int limit);

    public List<Course> findCoursesLimited(int limit);

    List<Course> findCoursesWithFilters(String title, String status, String instructor, int offset, int limit);

    int countCoursesWithFilters(String title, String status, String instructor);

    List<Course> findRecentCourses(int limit);

    List<Course> findByStatus(CourseStatus status, int limit);

    List<Course> findByStatus(CourseStatus status);

    List<Course> findByInstructor(Long instructorId);
}
