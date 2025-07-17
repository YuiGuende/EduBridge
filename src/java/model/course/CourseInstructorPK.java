/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author DELL
 */
@Embeddable
public class CourseInstructorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "course_id")
    private long courseId;
    @Basic(optional = false)
    @Column(name = "instructor_id")
    private long instructorId;

    public CourseInstructorPK() {
    }

    public CourseInstructorPK(long courseId, long instructorId) {
        this.courseId = courseId;
        this.instructorId = instructorId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(long instructorId) {
        this.instructorId = instructorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) courseId;
        hash += (int) instructorId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseInstructorPK)) {
            return false;
        }
        CourseInstructorPK other = (CourseInstructorPK) object;
        if (this.courseId != other.courseId) {
            return false;
        }
        if (this.instructorId != other.instructorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.course.CourseInstructorPK[ courseId=" + courseId + ", instructorId=" + instructorId + " ]";
    }
    
}
