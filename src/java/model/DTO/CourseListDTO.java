package model.DTO;



import java.util.Date;
import java.util.List;
import model.course.CourseStatus;
import model.course.Rate;
import model.user.Instructor;

public class CourseListDTO {
    private Long id;
    private String title;
    private String headline;
    private String thumbnailUrl;
    private CourseStatus status;
    private Date createdDate;
    private List<Instructor> instructors;
    private Rate rate;
    private String createdBy;
    private Long createdById;
    
    // Constructors
    public CourseListDTO() {}

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }
    
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    
    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }
    
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    
    public List<Instructor> getInstructors() { return instructors; }
    public void setInstructors(List<Instructor> instructors) { this.instructors = instructors; }
    
    public Rate getRate() { return rate; }
    public void setRate(Rate rate) { this.rate = rate; }
}
