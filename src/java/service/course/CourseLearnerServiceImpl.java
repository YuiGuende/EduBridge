package service.course;

import DAO.course.CourseLearnerDAOImpl;
import DAO.course.ICourseLearnerDAO;
import model.course.Course;
import model.course.CourseLearner;
import model.course.CourseLearnerPK;
import model.user.Learner;

import java.util.Date;

public class CourseLearnerServiceImpl implements ICourseLearnerService {

    private CourseLearnerDAOImpl courseLearnerDAO = new CourseLearnerDAOImpl();

    @Override
    public void enrollLearnerToCourse(Long learnerId, Long courseId) {
        if (!courseLearnerDAO.exists(courseId, learnerId)) {
            CourseLearner cl = new CourseLearner();
            cl.setCourseLearnerPK(new CourseLearnerPK(courseId, learnerId));
            cl.setEnrolledDate(new Date());


       
            Course course = new Course();
            course.setId(courseId);
            cl.setCourse(course);

            Learner learner = new Learner();
            learner.setId(learnerId);
            cl.setLearner(learner);

            courseLearnerDAO.save(cl);
        }
    }


    @Override
    public int countStudentInCourse(Long courseId) {
        return courseLearnerDAO.findByCourseId(courseId).size();

    }

    @Override
    public boolean findCourseLearner(CourseLearnerPK courseLearnerPK) {
        return courseLearnerDAO.exists(courseLearnerPK.getCourseId(), courseLearnerPK.getLearnerId());
    }
}
