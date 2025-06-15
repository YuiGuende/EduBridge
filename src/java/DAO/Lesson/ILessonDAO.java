package DAO.Lesson;

import model.course.courseContent.Module;
import java.util.List;
import java.util.Optional;
import model.course.courseContent.Lesson;

public interface ILessonDAO {

    // CRUD Operations
    Lesson save(Lesson lesson);

    Optional<Lesson> findById(Long id);

    List<Lesson> findAll();

    Lesson update(Lesson lesson);

    void delete(Lesson lesson);

    void deleteById(Long id);

    // Specialized Queries
    List<Lesson> findByModule(Module module);

    List<Lesson> findByModuleId(Long moduleId);

    List<Lesson> findByCourseId(Long courseId);

    List<Lesson> findPreviewLessons();

    List<Lesson> findByTitleContaining(String title);

    Lesson findByIdWithLessonItems(Long id);

    Lesson findNextLesson(Lesson currentLesson);

    Lesson findPreviousLesson(Lesson currentLesson);

    List<Lesson> findByDurationRange(int minDuration, int maxDuration);

    // Utility Methods
    long count();

    long countByModule(Module module);

    boolean exists(Long id);
}
