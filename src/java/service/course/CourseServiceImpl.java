/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import DAO.course.CourseDAO;
import model.course.Course;

public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    public void addCourse(Course course) {
        courseDAO.save(course);
    }

    @Override
    public Course findCourse(Long id) {
        return courseDAO.findById(id);
    }

}
