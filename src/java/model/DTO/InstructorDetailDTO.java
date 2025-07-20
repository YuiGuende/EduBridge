package model.DTO;

import model.user.Instructor;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.user.UserStatus;

public class InstructorDetailDTO {

    private Long id;
    private String fullname;
    private String email;
    private String bio;
    private int experienceYears;
    private String specialization;
    private String educationLevel;
    private String linkedinProfile;
    private String avatarUrl;
    private Date createdAt;
    private String role; // From User entity
    private List<CourseInstructorDTO> courses; // New field for courses
    private UserStatus status; // Thay đổi kiểu dữ liệu thành UserStatus

    public InstructorDetailDTO(Instructor instructor) {
        this.id = instructor.getId();
        if (instructor.getUser() != null) {
            this.fullname = instructor.getUser().getFullname();
            this.email = instructor.getUser().getEmail();
            this.createdAt = instructor.getUser().getCreatedAt();
            this.role = instructor.getUser().getRole();
            this.status = instructor.getUser().getStatus(); // Lấy status từ Use
        } else {
            this.fullname = "N/A";
            this.email = "N/A";
            this.createdAt = null;
            this.role = "N/A";
            this.status = UserStatus.ACTIVE; // Lấy status từ Use
        }
        this.bio = instructor.getBio();
        this.experienceYears = instructor.getExperienceYears();
        this.specialization = instructor.getSpecialization();
        this.educationLevel = instructor.getEducationLevel();
        this.linkedinProfile = instructor.getLinkedinProfile();
        this.avatarUrl = instructor.getAvatarUrl();
        if (instructor.getCoursesCreated() != null) {
            this.courses = instructor.getCoursesCreated().stream()
                    .map(CourseInstructorDTO::new)
                    .collect(Collectors.toList());
        }

    }

    public UserStatus getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public List<CourseInstructorDTO> getCourses() {
        return courses;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setters (optional)
    public void setId(Long id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
