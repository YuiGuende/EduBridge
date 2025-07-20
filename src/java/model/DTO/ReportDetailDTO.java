package model.DTO;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.notification.Report;
import model.notification.ReportTargetType; // Import ReportTargetType
import model.notification.ReportType; // Import ReportType
import model.user.User; // Import User
import util.DateUtils;

public class ReportDetailDTO {
    private Long id;
    private Long reporterId; // Added reporterId
    private String reporterName;
    private ReportTargetType reportedItemType; // Changed to enum
    private Long reportedItemId;
    private String reason; // Maps to content in Report
    private ReportType reportType; // Maps to type in Report
    private String reportDateFormatted;
    private Report.ReportStatus status;
    private boolean seen; // Added seen status

    public ReportDetailDTO() {
    }

    public ReportDetailDTO(Report report) {
        this.id = report.getId();
        this.reporterId = report.getUser() != null ? report.getUser().getId() : null; // Get reporter ID
        this.reporterName = report.getUser() != null ? report.getUser().getFullname() : "N/A";
        this.reportedItemType = report.getReportedItemType();
        this.reportedItemId = report.getTarget().getId();
        this.reason = report.getContent();
        this.reportType = report.getType(); // Map to ReportType enum
        this.reportDateFormatted = formatDate(report.getCreatedAt());
        this.status = report.getStatus();
        this.seen = report.isSeen();
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public ReportTargetType getReportedItemType() {
        return reportedItemType;
    }

    public Long getReportedItemId() {
        return reportedItemId;
    }

    public String getReason() {
        return reason;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getReportDateFormatted() {
        return reportDateFormatted;
    }

    public Report.ReportStatus getStatus() {
        return status;
    }

    public boolean isSeen() {
        return seen;
    }
}