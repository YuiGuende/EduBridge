package DAO.course;

import java.util.List;
import model.course.CourseLearner;

public interface ICourseLearnerDAO {

    void insert(CourseLearner courseLearner);

    boolean exists(Long courseId, Long learnerId);

    List<CourseLearner> findByLearnerId(Long learnerId);

    List<CourseLearner> findByCourseId(Long courseId);

}
