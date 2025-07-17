package model.DTO;


public class DashboardStatsDTO {
    private int totalCourses;
    private int activeInstructors;
    private int totalStudents;
    private int pendingReports;
    
    // Constructors
    public DashboardStatsDTO() {}
    
    public DashboardStatsDTO(int totalCourses, int activeInstructors, int totalStudents, int pendingReports) {
        this.totalCourses = totalCourses;
        this.activeInstructors = activeInstructors;
        this.totalStudents = totalStudents;
        this.pendingReports = pendingReports;
    }
    
    // Getters and Setters
    public int getTotalCourses() { return totalCourses; }
    public void setTotalCourses(int totalCourses) { this.totalCourses = totalCourses; }
    
    public int getActiveInstructors() { return activeInstructors; }
    public void setActiveInstructors(int activeInstructors) { this.activeInstructors = activeInstructors; }
    
    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }
    
    public int getPendingReports() { return pendingReports; }
    public void setPendingReports(int pendingReports) { this.pendingReports = pendingReports; }
}
