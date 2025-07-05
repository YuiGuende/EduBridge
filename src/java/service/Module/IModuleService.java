/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.Module;

import java.util.List;
import java.util.Optional;
import model.course.Course;
import model.course.courseContent.Module;

/**
 *
 * @author LEGION
 */
public interface IModuleService {

    // Utility Methods
    long count();

    long countByCourse(Course course);

    void delete(Module module);

    void deleteById(Long id);

    boolean exists(Long id);

    List<Module> findAll();

    // Specialized Queries
    List<Module> findByCourse(Course course);

    List<Module> findByCourseId(Long courseId);

    Optional<Module> findById(Long id);

    Module findByIdWithLessons(Long id);

    List<Module> findByTitleContaining(String title);

    Module findNextModule(Module currentModule);

    List<Module> findPreviewModules();

    Module findPreviousModule(Module currentModule);

    Module save(Module module);

    Module update(Module module);

    Module findByIndexAndCourseId(int index, Long courseId);

}
