package service.course;

import model.course.CourseLearner;
import model.course.CourseLearnerPK;


public interface ICourseLearnerService {
    void enrollLearnerToCourse(Long learnerId, Long courseId);
    int countStudentInCourse(Long courseId);
    boolean findCourseLearner(CourseLearnerPK courseLearnerPK);
}
