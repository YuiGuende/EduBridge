package model.course;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseLearnerPK implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "learner_id")
    private Long learnerId;

    public CourseLearnerPK() {
    }

    public CourseLearnerPK(Long courseId, Long learnerId) {
        this.courseId = courseId;
        this.learnerId = learnerId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(Long learnerId) {
        this.learnerId = learnerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, learnerId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CourseLearnerPK)) {
            return false;
        }
        CourseLearnerPK other = (CourseLearnerPK) obj;
        return Objects.equals(this.courseId, other.courseId)
                && Objects.equals(this.learnerId, other.learnerId);
    }
}
