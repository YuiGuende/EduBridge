/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Lesson.LessonDAOImpl;
import DAO.LessonItem.LessonItemDAOImpl;
import DAO.Module.ModuleDAOImpl;
import DAO.course.CourseDAOImpl;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Language;
import model.course.courseContent.Lesson;
import model.course.courseContent.Module;
import model.course.courseContent.Reading;

/**
 *
 * @author LEGION
 */
public class Main1 {

    public static void main(String[] args) throws Exception {
        // Chỉ cần dòng này là đủ để tạo bảng (nếu cấu hình hbm2ddl.auto là create hoặc update)
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("coursePU");
//        CourseDAOImpl courseDAO = new CourseDAOImpl();
//        ModuleDAOImpl moduleDAO = new ModuleDAOImpl();
//        LessonDAOImpl lessonDAO = new LessonDAOImpl();
//        LessonItemDAOImpl lessonItemDAOImpl = new LessonItemDAOImpl();

//        courseDAO.save(new Course("title", "headline", "description", new Language("vietnamese", "vn"),
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiMqZVVGlRTAVjBLCvg6oT1MzJHBUDK-dSkNszSADNlo4-OOSiDppD--ZCUzKS1jL8mHYrpE7gugPM_HLCsIFErYRZsi8yuqranpB64Pwuaw",
//                CourseStatus.DRAFT));
//        Course courseOptional = courseDAO.findById(Long.valueOf("10"));
//        System.out.println("Course found! "+courseOptional.getLearningOutcomes().get(0));
//        Optional<Lesson> lessonOptional = lessonDAO.findById(0L);
//        if(lessonOptional.isPresent()){
//            lessonItemDAOImpl.save( new Reading("this is lesson content", 0, "this is reading title", 20));
//        }

//        moduleDAO.save(new Module(courseOptional, "this is module 1 of course 1", 30, 0, "this is title of module 1 of course 1"));
//        Optional<Module> moduleOptional = moduleDAO.findById(1L);
//        if (moduleOptional.isPresent()) {
//            Module module = moduleOptional.get();
//            lessonDAO.save(new Lesson(module, 0, "lesson 1 of module 1"));
//        }
    }
}
