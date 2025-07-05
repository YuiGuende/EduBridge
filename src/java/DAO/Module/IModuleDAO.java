package DAO.Module;

import java.util.List;
import java.util.Optional;
import model.course.Course;
import model.course.courseContent.Module;

public interface IModuleDAO {

    // CRUD Operations
    Module save(Module module);

    Optional<Module> findById(Long id);

    List<Module> findAll();

    Module update(Module module);

    void delete(Module module);

    void deleteById(Long id);

    // Specialized Queries
    List<Module> findByCourse(Course course);

    List<Module> findByCourseId(Long courseId);

    List<Module> findPreviewModules();

    List<Module> findByTitleContaining(String title);
    
    Module findByIndexAndCourseId(int index, Long courseId);

    Module findByIdWithLessons(Long id);

    Module findNextModule(Module currentModule);

    Module findPreviousModule(Module currentModule);

    // Utility Methods
    long count();

    long countByCourse(Course course);

    boolean exists(Long id);
}
