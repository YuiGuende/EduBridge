/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.course;

import java.util.List;
import model.course.Course;

/**
 *
 * @author DELL
 */
public interface ICourseDAO {

    List<Course> findCoursesByStatus(int instructorId, String status, int offset, int limit);

    List<Course> findCoursesByKeywordAndStatus(int instructorId, String keyword, String status, int offset, int limit);

    int countCourses(int instructorId, String status, String keyword);
}
