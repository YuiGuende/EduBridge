package service.course;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import DAO.course.CourseDAOImpl;
import java.util.ArrayList;
import java.util.List;
import model.course.Course;
import model.user.Instructor;

/**
 *
 * @author GoniperXComputer
 */
public class CourseDetailService {
     private final CourseServiceImpl courseService;

    public CourseDetailService(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }
    
    public Course getCourseWithDetails(Long courseId){
        return courseService.findCourse(courseId);
    }
    public Instructor getPrimaryInstructor(Course course){
        if(course != null && !course.getInstructors().isEmpty()){
            return course.getInstructors().get(0);
        }
        return null;
    }
    public List<model.course.courseContent.Module> getCourseModule(Course course){
        if(course == null || course.getModules() == null){
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
    
    
    
}