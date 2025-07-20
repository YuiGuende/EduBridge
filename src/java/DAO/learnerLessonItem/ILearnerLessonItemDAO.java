package DAO.learnerLessonItem;

import java.util.List;
import model.course.LearningStatus;
import model.course.courseContent.LearnerLessonItem;

public interface ILearnerLessonItemDAO {

    List<LearnerLessonItem> findByLearnerAndCourse(Long learnerId, Long courseId);

    void updateLearningStatus(Long learnerId, Long lessonItemId, LearningStatus status, Double progress);

    LearnerLessonItem findByLearnerAndLessonItem(Long learnerId, Long lessonItemId);

    long countLessonItemsInCourse(Long courseId);

    long countCompletedLessonItems(Long learnerId, Long courseId);
}
