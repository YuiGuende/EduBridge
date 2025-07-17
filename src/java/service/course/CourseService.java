/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.course;

import java.util.List;
import model.DTO.CourseListDTO;
import model.DTO.PendingApprovalDTO;
import model.DTO.RecentActivityDTO;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Tag;
import model.notification.Comment;

/**
 *
 * @author LEGION
 */
public interface CourseService {

    void addCourse(Course course);

    Course findCourse(Long id);

    public String processUserMessage(String message, boolean showMore) throws Exception;

    List<Course> getAllCourses();

    List<Course> getCoursesOfInstructorByStatus(long instructorID, String status, int offset, int limit);

    List<Course> getCourseByKeywordAndStatus(long instructorID, String keyword, String status, int offset, int limit);

    int countCoursesOfInstructor(long instructorID, String status, String keyword);

    public List<Course> findRelatedCourses(Course currentCourse, int limit);
    
    public List<Course> findCoursesLimited(int limit);
    List<Course> sortCourses(List<Course> courses, String sort);

    List<Course> getTrendingCourses();
    
    List<Course> getCoursesByTag(Tag tag);
    List<Comment> getSortedCommentsByCourse(Long courseId);
     int getTotalCoursesCount();
    List<RecentActivityDTO> getRecentActivities(int limit);
    List<PendingApprovalDTO> getPendingApprovals(int limit);
    
    // Course management methods
    List<CourseListDTO> getCoursesList(String title, String status, String instructor, int page, int pageSize);
    int getCoursesCount(String title, String status, String instructor);
    Course findById(Long id);
    boolean updateCourseStatus(Long courseId, CourseStatus status, String reason);
    
    // Course operations
    Course save(Course course);
    Course update(Course course);
    void delete(Long id);
    List<Course> findAll();
    List<Course> findByStatus(CourseStatus status);
    List<Course> findByInstructor(Long instructorId);

}
