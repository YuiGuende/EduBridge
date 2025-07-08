/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course_1.findAll", query = "SELECT c FROM Course_1 c"),
    @NamedQuery(name = "Course_1.findById", query = "SELECT c FROM Course_1 c WHERE c.id = :id"),
    @NamedQuery(name = "Course_1.findByTitle", query = "SELECT c FROM Course_1 c WHERE c.title = :title"),
    @NamedQuery(name = "Course_1.findByHeadline", query = "SELECT c FROM Course_1 c WHERE c.headline = :headline"),
    @NamedQuery(name = "Course_1.findByDescription", query = "SELECT c FROM Course_1 c WHERE c.description = :description"),
    @NamedQuery(name = "Course_1.findByRequirement", query = "SELECT c FROM Course_1 c WHERE c.requirement = :requirement"),
    @NamedQuery(name = "Course_1.findByCourseFor", query = "SELECT c FROM Course_1 c WHERE c.courseFor = :courseFor"),
    @NamedQuery(name = "Course_1.findByLearningOutcomes", query = "SELECT c FROM Course_1 c WHERE c.learningOutcomes = :learningOutcomes"),
    @NamedQuery(name = "Course_1.findByUrlThumbnail", query = "SELECT c FROM Course_1 c WHERE c.urlThumbnail = :urlThumbnail"),
    @NamedQuery(name = "Course_1.findByIsPaid", query = "SELECT c FROM Course_1 c WHERE c.isPaid = :isPaid"),
    @NamedQuery(name = "Course_1.findByStatus", query = "SELECT c FROM Course_1 c WHERE c.status = :status"),
    @NamedQuery(name = "Course_1.findByPublishedTime", query = "SELECT c FROM Course_1 c WHERE c.publishedTime = :publishedTime"),
    @NamedQuery(name = "Course_1.findByLastUpdate", query = "SELECT c FROM Course_1 c WHERE c.lastUpdate = :lastUpdate")})
public class Course_1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "headline")
    private String headline;
    @Column(name = "description")
    private String description;
    @Column(name = "requirement")
    private String requirement;
    @Column(name = "course_for")
    private String courseFor;
    @Column(name = "learning_outcomes")
    private String learningOutcomes;
    @Column(name = "url_thumbnail")
    private String urlThumbnail;
    @Basic(optional = false)
    @Column(name = "is_paid")
    private boolean isPaid;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "published_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedTime;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<CourseInstructor> courseInstructorList;
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    @ManyToOne
    private Language_1 languageId;

    public Course_1() {
    }

    public Course_1(Long id) {
        this.id = id;
    }

    public Course_1(Long id, String title, boolean isPaid, String status) {
        this.id = id;
        this.title = title;
        this.isPaid = isPaid;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getCourseFor() {
        return courseFor;
    }

    public void setCourseFor(String courseFor) {
        this.courseFor = courseFor;
    }

    public String getLearningOutcomes() {
        return learningOutcomes;
    }

    public void setLearningOutcomes(String learningOutcomes) {
        this.learningOutcomes = learningOutcomes;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @XmlTransient
    public List<CourseInstructor> getCourseInstructorList() {
        return courseInstructorList;
    }

    public void setCourseInstructorList(List<CourseInstructor> courseInstructorList) {
        this.courseInstructorList = courseInstructorList;
    }

    public Language_1 getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Language_1 languageId) {
        this.languageId = languageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course_1)) {
            return false;
        }
        Course_1 other = (Course_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.course.Course_1[ id=" + id + " ]";
    }
    
}
