package model.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.notification.Report;
import model.notification.ReportTargetType; // Import ReportTargetType
import model.notification.ReportType; // Import ReportType
import model.user.User; // Import User

public class ReportListDTO {

    private Long id;
    private Long reporterId; // Added reporterId
    private String reporterName;
    private ReportType type; // Changed to enum
    private String contentPreview; // Shortened content for list view
    private ReportTargetType reportedItemType; // Changed to enum
    private Long reportedItemId;
    private Report.ReportStatus status; // Changed to enum
    private LocalDateTime createdAt; // Changed to LocalDateTime
    private String createdAtFormatted; // Formatted string for display
    private boolean seen;

    // Constructors
    public ReportListDTO() {
    }

    public ReportListDTO(Report report) {
        this.id = report.getId();
        this.reporterId = report.getUser() != null ? report.getUser().getId() : null;
        this.reporterName = report.getUser() != null ? report.getUser().getFullname() : "N/A";
        this.type = report.getType();
        this.contentPreview = report.getContent().length() > 100 ? report.getContent().substring(0, 100) + "..." : report.getContent();//lỗi ở đây
        this.reportedItemType = report.getReportedItemType();
        this.reportedItemId = report.getTarget().getId();
        this.status = report.getStatus();
        this.createdAt = report.getCreatedAt();
        this.createdAtFormatted = formatDate(report.getCreatedAt());
        this.seen = report.isSeen();
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getContentPreview() {
        return contentPreview;
    }

    public void setContentPreview(String contentPreview) {
        this.contentPreview = contentPreview;
    }

    public ReportTargetType getReportedItemType() {
        return reportedItemType;
    }

    public void setReportedItemType(ReportTargetType reportedItemType) {
        this.reportedItemType = reportedItemType;
    }

    public Long getReportedItemId() {
        return reportedItemId;
    }

    public void setReportedItemId(Long reportedItemId) {
        this.reportedItemId = reportedItemId;
    }

    public Report.ReportStatus getStatus() {
        return status;
    }

    public void setStatus(Report.ReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtFormatted() {
        return createdAtFormatted;
    }

    public void setCreatedAtFormatted(String createdAtFormatted) {
        this.createdAtFormatted = createdAtFormatted;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}