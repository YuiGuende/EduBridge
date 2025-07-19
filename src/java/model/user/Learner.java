package model.user;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import model.course.CourseLearner;

@Entity
@Table(name = "Learner")
@NamedQueries({
    @NamedQuery(name = "Learner.findAll", query = "SELECT l FROM Learner l"),
    @NamedQuery(name = "Learner.findById", query = "SELECT l FROM Learner l WHERE l.id = :id"),
    @NamedQuery(name = "Learner.findByEducationLevel", query = "SELECT l FROM Learner l WHERE l.educationLevel = :educationLevel"),
    @NamedQuery(name = "Learner.findByBio", query = "SELECT l FROM Learner l WHERE l.bio = :bio")
})
public class Learner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "bio")
    private String bio;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "learner")
    private List<CourseLearner> courseLearnerList;

    public Learner() {
    }

    public Learner(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CourseLearner> getCourseLearnerList() {
        return courseLearnerList;
    }

    public void setCourseLearnerList(List<CourseLearner> courseLearnerList) {
        this.courseLearnerList = courseLearnerList;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Learner)) {
            return false;
        }
        Learner other = (Learner) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "model.user.Learner[ id=" + id + " ]";
    }
}
