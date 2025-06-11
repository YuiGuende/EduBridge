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
    Course findCourseById(int id);
    List<Course> getAllCourses();
    List<Course> getCoursesOfInstructor(int instructorID, String status, String keyword, String sort, int offset, int limit);
    int countCoursesOfInstructor(int instructorID, String status, String keyword);
}
