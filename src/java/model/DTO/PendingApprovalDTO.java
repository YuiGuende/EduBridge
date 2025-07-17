package model.DTO;


public class PendingApprovalDTO {
    private Long courseId;
    private String courseTitle;
    private String instructorName;
    
    // Constructors
    public PendingApprovalDTO() {}
    
    public PendingApprovalDTO(Long courseId, String courseTitle, String instructorName) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;
    }
    
    // Getters and Setters
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
}
