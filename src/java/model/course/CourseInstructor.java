/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import model.user.Instructor;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Course_Instructor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseInstructor.findAll", query = "SELECT c FROM CourseInstructor c"),
    @NamedQuery(name = "CourseInstructor.findByCourseId", query = "SELECT c FROM CourseInstructor c WHERE c.courseInstructorPK.courseId = :courseId"),
    @NamedQuery(name = "CourseInstructor.findByInstructorId", query = "SELECT c FROM CourseInstructor c WHERE c.courseInstructorPK.instructorId = :instructorId"),
    @NamedQuery(name = "CourseInstructor.findByCreated", query = "SELECT c FROM CourseInstructor c WHERE c.created = :created")})
public class CourseInstructor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CourseInstructorPK courseInstructorPK;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course_1 course;
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Instructor instructor;

    public CourseInstructor() {
    }

    public CourseInstructor(CourseInstructorPK courseInstructorPK) {
        this.courseInstructorPK = courseInstructorPK;
    }

    public CourseInstructor(long courseId, long instructorId) {
        this.courseInstructorPK = new CourseInstructorPK(courseId, instructorId);
    }

    public CourseInstructorPK getCourseInstructorPK() {
        return courseInstructorPK;
    }

    public void setCourseInstructorPK(CourseInstructorPK courseInstructorPK) {
        this.courseInstructorPK = courseInstructorPK;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Course_1 getCourse() {
        return course;
    }

    public void setCourse(Course_1 course) {
        this.course = course;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseInstructorPK != null ? courseInstructorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseInstructor)) {
            return false;
        }
        CourseInstructor other = (CourseInstructor) object;
        if ((this.courseInstructorPK == null && other.courseInstructorPK != null) || (this.courseInstructorPK != null && !this.courseInstructorPK.equals(other.courseInstructorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.course.CourseInstructor[ courseInstructorPK=" + courseInstructorPK + " ]";
    }
    
}
