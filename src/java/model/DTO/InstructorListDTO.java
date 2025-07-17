package model.DTO;



import java.util.List;
import model.course.Course;
import model.user.User;

public class InstructorListDTO {
    private Long id;
    private User user;
    private String specialization;
    private int experienceYears;
    private String educationLevel;
    private String linkedinProfile;
    private String bio;
    private String avatarUrl;
    private List<Course> coursesCreated;
    
    // Constructors
    public InstructorListDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    
    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    
    public String getLinkedinProfile() { return linkedinProfile; }
    public void setLinkedinProfile(String linkedinProfile) { this.linkedinProfile = linkedinProfile; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    
    public List<Course> getCoursesCreated() { return coursesCreated; }
    public void setCoursesCreated(List<Course> coursesCreated) { this.coursesCreated = coursesCreated; }
}
