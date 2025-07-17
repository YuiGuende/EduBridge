package model.DTO;



import java.util.List;
import model.course.CourseLearner;
import model.user.User;

public class LearnerListDTO {
    private Long id;
    private User user;
    private String educationLevel;
    private String bio;
    private List<CourseLearner> courseLearnerList;
    
    // Constructors
    public LearnerListDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public List<CourseLearner> getCourseLearnerList() { return courseLearnerList; }
    public void setCourseLearnerList(List<CourseLearner> courseLearnerList) { this.courseLearnerList = courseLearnerList; }
}
