package service.report;

import model.notification.Report;
import model.user.User;
import model.notification.ReportTargetType;
import java.util.List;
import model.DTO.ReportListDTO;
import model.notification.ReportType;

public interface IReportService {

    Report save(Report report);

    Report update(Report report);

    void delete(Report report);

    void deleteById(Long id);

    Report findById(Long id);

    List<Report> findAll();

    List<Report> findByReporter(User user);

    // Updated methods to use reportedItemId and reportedItemType
    List<Report> findByReportedItem(Long reportedItemId, ReportTargetType reportedItemType);

    long countByReportedItem(Long reportedItemId, ReportTargetType reportedItemType);

    long count();

    List<Report> findBySeen(boolean seen);

    List<Report> findByType(ReportType type);

    void markAsSeen(Long reportId);

    List<Report> getRecentReports(int limit);

    int getPendingReportsCount();

    // Report management
    List<ReportListDTO> getReportsList(ReportType type, Report.ReportStatus status, ReportTargetType targetType, int page, int pageSize);

    int getReportsCount(ReportType type, Report.ReportStatus status, ReportTargetType targetType);

    boolean updateReportStatus(Long reportId, Report.ReportStatus status);

    boolean deleteReport(Long reportId);

    // Report operations
    List<Report> findByStatus(Report.ReportStatus status);
}
