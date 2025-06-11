/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.course;

import DAO.course.CourseDAO;
import java.util.Collections;
import java.util.Comparator;
import model.course.Course;

import java.util.List;

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

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    @Override
    public List<Course> getCoursesOfInstructorByStatus(int instructorID, String status, int offset, int limit) {
        return courseDAO.findCoursesByStatus(instructorID, status, offset, limit);
    }

    @Override
    public int countCoursesOfInstructor(int instructorID, String status, String keyword) {
        return courseDAO.countCourses(instructorID, status, keyword);
    }

    @Override
    public List<Course> getCourseByKeywordAndStatus(int instructorID, String keyword, String status, int offset, int limit) {
        return courseDAO.findCoursesByKeywordAndStatus(instructorID, keyword, status, offset, limit);
    }

    @Override
    public List<Course> sortCourses(List<Course> courses, String sort) {
        if (courses == null) {
            return Collections.emptyList();
        }

        switch (sort != null ? sort : "") {
            case "az":
                courses.sort(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER));
                break;
            case "za":
                courses.sort(Comparator.comparing(Course::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());
                break;
            case "newest":
                courses.sort(Comparator.comparing(Course::getPublishedTime, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "oldest":
                courses.sort(Comparator.comparing(Course::getPublishedTime, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
//            case "popular":
//                courses.sort(Comparator.comparing(Course::getEnrollCount, Comparator.nullsLast(Comparator.reverseOrder())));
//                break;
//            case "rating":
//                courses.sort(Comparator.comparing(Course::getRating, Comparator.nullsLast(Comparator.reverseOrder())));
//                break;
            default:
                courses.sort(Comparator.comparing(Course::getId));
                break;
        }

        return courses;
    }
}
