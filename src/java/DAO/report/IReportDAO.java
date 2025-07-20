package DAO.report;

import model.notification.Report;
import model.user.User;
import model.notification.ReportTargetType;
import java.util.List;
import java.util.Optional;
import model.notification.ReportType;

public interface IReportDAO {

    Report save(Report report);

    Report update(Report report);

    void delete(Report report);

    void deleteById(Long id);

    Report findById(Long id); // Changed to Optional

    List<Report> findAll();

    List<Report> findByReporter(User user);

    // Updated methods to use reportedItemId and reportedItemType
    List<Report> findByReportedItem(Long reportedItemId, ReportTargetType reportedItemType);

    long countByReportedItem(Long reportedItemId, ReportTargetType reportedItemType);

    List<Report> findBySeen(boolean seen);

    List<Report> findByType(ReportType type); // Changed parameter type to enum

    void markAsSeen(Long reportId);

    List<Report> findReportsWithFilters(ReportType type, Report.ReportStatus status, ReportTargetType targetType, int offset, int limit);

    int countReportsWithFilters(ReportType type, Report.ReportStatus status, ReportTargetType targetType);

    List<Report> findRecentReports(int limit);

    int countPendingReports();

    List<Report> findByStatus(Report.ReportStatus status);

    long count();
}
