/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import dal.CourseDAO;
import java.util.List;
import model.course.Course;


public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    public void addCourse(Course course) {
        courseDAO.save(course);
    }

    @Override
    public Course findCourseById(int id) {
        return courseDAO.findById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    @Override
    public List<Course> getCoursesOfInstructor(int instructorID, String status, String keyword, String sort, int offset, int limit) {
        return courseDAO.findCourses(instructorID, status, keyword, sort, offset, limit);
    }

    @Override
    public int countCoursesOfInstructor(int instructorID, String status, String keyword) {
        return courseDAO.countCourses(instructorID, status, keyword);
    }
    
}
