/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;
import model.course.CourseInstructor;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "Instructor")
@jakarta.xml.bind.annotation.XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instructor.findAll", query = "SELECT i FROM Instructor i"),
    @NamedQuery(name = "Instructor.findById", query = "SELECT i FROM Instructor i WHERE i.id = :id"),
    @NamedQuery(name = "Instructor.findByBio", query = "SELECT i FROM Instructor i WHERE i.bio = :bio"),
    @NamedQuery(name = "Instructor.findByExperienceYears", query = "SELECT i FROM Instructor i WHERE i.experienceYears = :experienceYears"),
    @NamedQuery(name = "Instructor.findBySpecialization", query = "SELECT i FROM Instructor i WHERE i.specialization = :specialization"),
    @NamedQuery(name = "Instructor.findByEducationLevel", query = "SELECT i FROM Instructor i WHERE i.educationLevel = :educationLevel"),
    @NamedQuery(name = "Instructor.findByLinkedinProfile", query = "SELECT i FROM Instructor i WHERE i.linkedinProfile = :linkedinProfile"),
    @NamedQuery(name = "Instructor.findByAvatarUrl", query = "SELECT i FROM Instructor i WHERE i.avatarUrl = :avatarUrl")})
public class Instructor implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    private List<CourseInstructor> courseInstructorList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "bio")
    private String bio;
    @Basic(optional = false)
    @Column(name = "experience_years")
    private int experienceYears;
    @Basic(optional = false)
    @Column(name = "specialization")
    private String specialization;
    @Basic(optional = false)
    @Column(name = "education_level")
    private String educationLevel;
    @Column(name = "linkedin_profile")
    private String linkedinProfile;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    @OneToOne
    private InstructorBankInfo instructorBankInfo;
    
    public Instructor() {
    }

    public Instructor(Long id) {
        this.id = id;
    }

    public Instructor(Long id, int experienceYears, String specialization, String educationLevel) {
        this.id = id;
        this.experienceYears = experienceYears;
        this.specialization = specialization;
        this.educationLevel = educationLevel;
    }

    public Instructor(String bio, int experienceYears, String specialization, String educationLevel, String linkedinProfile, String avatarUrl) {
        this.bio = bio;
        this.experienceYears = experienceYears;
        this.specialization = specialization;
        this.educationLevel = educationLevel;
        this.linkedinProfile = linkedinProfile;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InstructorBankInfo getInstructorBankInfo() {
        return instructorBankInfo;
    }

    public void setInstructorBankInfo(InstructorBankInfo instructorBankInfo) {
        this.instructorBankInfo = instructorBankInfo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Instructor)) {
            return false;
        }
        Instructor other = (Instructor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Instructor[ id=" + id + " ]";
    }

    @XmlTransient
    public List<CourseInstructor> getCourseInstructorList() {
        return courseInstructorList;
    }

    public void setCourseInstructorList(List<CourseInstructor> courseInstructorList) {
        this.courseInstructorList = courseInstructorList;
    }
    
}
