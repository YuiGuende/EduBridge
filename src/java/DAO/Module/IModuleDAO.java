package DAO.Module;

import java.util.List;
import java.util.Optional;
import model.course.Course;
import model.course.courseContent.Module;

public interface IModuleDAO {

    // Specialized Queries
    List<Module> findByCourse(Course course);

    List<Module> findByCourseId(Long courseId);

    List<Module> findPreviewModules();

    List<Module> findByTitleContaining(String title);

    Module findByIdWithLessons(Long id);

    Module findNextModule(Module currentModule);

    Module findPreviousModule(Module currentModule);

    // Utility Methods

    long countByCourse(Course course);

}
