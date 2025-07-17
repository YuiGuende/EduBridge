package model.course;

import jakarta.persistence.*;
import model.user.Learner;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Course_Learner")
@NamedQueries({
    @NamedQuery(name = "CourseLearner.findAll", query = "SELECT c FROM CourseLearner c"),
    @NamedQuery(name = "CourseLearner.findByCourseId", query = "SELECT c FROM CourseLearner c WHERE c.courseLearnerPK.courseId = :courseId"),
    @NamedQuery(name = "CourseLearner.findByLearnerId", query = "SELECT c FROM CourseLearner c WHERE c.courseLearnerPK.learnerId = :learnerId"),
    @NamedQuery(name = "CourseLearner.findByEnrolledDate", query = "SELECT c FROM CourseLearner c WHERE c.enrolledDate = :enrolledDate")
})
public class CourseLearner implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected CourseLearnerPK courseLearnerPK;

    @Column(name = "enrolled_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrolledDate;

    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;

    @JoinColumn(name = "learner_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Learner learner;

    public CourseLearner() {
    }

    public CourseLearner(CourseLearnerPK courseLearnerPK) {
        this.courseLearnerPK = courseLearnerPK;
    }

    public CourseLearner(Long courseId, Long learnerId) {
        this.courseLearnerPK = new CourseLearnerPK(courseId, learnerId);
    }

    public CourseLearnerPK getId() {
        return this.courseLearnerPK;
    }

    public CourseLearnerPK getCourseLearnerPK() {
        return courseLearnerPK;
    }

    public void setCourseLearnerPK(CourseLearnerPK courseLearnerPK) {
        this.courseLearnerPK = courseLearnerPK;
    }

    public Date getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(Date enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    @Override
    public int hashCode() {
        return courseLearnerPK != null ? courseLearnerPK.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CourseLearner)) {
            return false;
        }
        CourseLearner other = (CourseLearner) object;
        return this.courseLearnerPK != null && this.courseLearnerPK.equals(other.courseLearnerPK);
    }

    @Override
    public String toString() {
        return "model.course.CourseLearner[ courseLearnerPK=" + courseLearnerPK + " ]";
    }
}
