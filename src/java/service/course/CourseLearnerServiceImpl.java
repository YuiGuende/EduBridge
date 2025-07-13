package service.course;

import DAO.course.CourseLearnerDAOImpl;
import DAO.course.ICourseLearnerDAO;
import model.course.Course;
import model.course.CourseLearner;
import model.course.CourseLearnerPK;
import model.user.Learner;

import java.util.Date;

public class CourseLearnerServiceImpl implements ICourseLearnerService {

    private ICourseLearnerDAO courseLearnerDAO = new CourseLearnerDAOImpl();

    @Override
    public void enrollLearnerToCourse(Long learnerId, Long courseId) {
        if (!courseLearnerDAO.exists(courseId, learnerId)) {
            CourseLearner cl = new CourseLearner();
            cl.setCourseLearnerPK(new CourseLearnerPK(courseId, learnerId));
            cl.setEnrolledDate(new Date());

            // Ch? c?n set ID, JPA s? t? li�n k?t
            Course course = new Course();
            course.setId(courseId);
            cl.setCourse(course);

            Learner learner = new Learner();
            learner.setId(learnerId);
            cl.setLearner(learner);

            courseLearnerDAO.insert(cl);
        }
    }
}
