package service.lessonItem;


import model.course.courseContent.LessonItem;

import java.util.List;

public interface ILessonItemService {

    LessonItem findById(Long id);

    List<LessonItem> findAll();

    List<LessonItem> findByLessonId(Long lessonId);
}
