/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.course;

import java.util.List;
import model.course.Course;

/**
 *
 * @author LEGION
 */
public interface CourseService {

    void addCourse(Course course);

    Course findCourse(Long id);
    public String processUserMessage(String message, boolean showMore) throws Exception;


    List<Course> getAllCourses();

    List<Course> getCoursesOfInstructorByStatus(int instructorID, String status, int offset, int limit);

    List<Course> getCourseByKeywordAndStatus(int instructorID, String keyword, String status, int offset, int limit);

    int countCoursesOfInstructor(int instructorID, String status, String keyword);

    public List<Course> sortCourses(List<Course> courses, String sort);


}
