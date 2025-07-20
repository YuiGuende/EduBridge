package service.LearnerLessonItem;
import java.util.List;
import model.course.LearningStatus;
import model.course.courseContent.LearnerLessonItem;

public interface ILearnerLessonItemService {

    LearnerLessonItem getByLearnerAndLessonItem(Long learnerId, Long lessonItemId);

    List<LearnerLessonItem> getByLearnerAndCourse(Long learnerId, Long courseId);

    LearnerLessonItem save(LearnerLessonItem lli);

    void updateProgress(Long learnerId, Long lessonItemId, LearningStatus status, Double progress);
    
    double calculateCourseProgress(Long learnerId, Long courseId);
}