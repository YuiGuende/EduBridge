package model.DTO;


import model.course.Course;
import model.course.CourseStatus;
import java.io.Serializable;

public class CourseInstructorDTO implements Serializable {
    private Long id;
    private String title;
    private CourseStatus status;
    private String thumbnailUrl;

    public CourseInstructorDTO() {
    }

    public CourseInstructorDTO(Course course) {
        if (course != null) {
            this.id = course.getId();
            this.title = course.getTitle();
            this.status = course.getStatus();
            this.thumbnailUrl = course.getThumbnailUrl();
        }
    }

    // Getters and Setters
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

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "CourseInstructorDTO{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", status=" + status +
               ", thumbnailUrl='" + thumbnailUrl + '\'' +
               '}';
    }
}
