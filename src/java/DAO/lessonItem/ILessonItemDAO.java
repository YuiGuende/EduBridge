package DAO.lessonItem;

import java.util.List;
import java.util.Optional;
import model.course.courseContent.Lesson;
import model.course.courseContent.LessonItem;

public interface ILessonItemDAO {

//    Optional<LessonItem> findById(Long id);

    List<LessonItem> findAll();

//    LessonItem update(LessonItem lessonItem);

//    void delete(LessonItem lessonItem);

//    void deleteById(Long id);

    // Specialized Queries
    List<LessonItem> findByLesson(Lesson lesson);

    List<LessonItem> findByLessonId(Long lessonId);
//    List<LessonItem> findByType(LessonItemType type);
//    List<LessonItem> findByLessonAndType(Lesson lesson, LessonItemType type);

    List<LessonItem> findByTitleContaining(String title);

    List<LessonItem> findByContentUrl(String contentUrl);

    LessonItem findNextLessonItem(LessonItem currentItem);

    LessonItem findPreviousLessonItem(LessonItem currentItem);

    List<LessonItem> findByDurationRange(int minDuration, int maxDuration);

    List<LessonItem> findByModuleId(Long moduleId);

    // Utility Methods
//    long count();

    long countByLesson(Lesson lesson);
//    long countByType(LessonItemType type);

//    boolean exists(Long id);

    LessonItem findById(Long id);
}
