/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.course;

import model.course.Course;

/**
 *
 * @author LEGION
 */
public interface CourseService {

    void addCourse(Course course);

    Course findCourse(Long id);
    public String processUserMessage(String message, boolean showMore) throws Exception;
}
