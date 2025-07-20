package service.report;

import DAO.report.IReportDAO;
import DAO.report.ReportDAOImpl;
import model.DTO.ReportListDTO;
import model.notification.Report;
import model.notification.ReportTargetType;
import model.notification.ReportType;
import model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements IReportService {

    private IReportDAO reportDAO;

    public ReportServiceImpl() {
        this.reportDAO = new ReportDAOImpl();
    }

    public ReportServiceImpl(IReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public Report save(Report report) {
        return reportDAO.save(report);
    }

    @Override
    public Report update(Report report) {
        return reportDAO.update(report);
    }

    @Override
    public void delete(Report report) {
        reportDAO.delete(report);
    }

    @Override
    public void deleteById(Long id) {
        reportDAO.deleteById(id);
    }

    @Override
    public Report findById(Long id) {
        return reportDAO.findById(id);
    }

    @Override
    public List<Report> findAll() {
        return reportDAO.findAll();
    }

    @Override
    public List<Report> findByReporter(User user) {
        return reportDAO.findByReporter(user);
    }

    @Override
    public List<Report> findByReportedItem(Long reportedItemId, ReportTargetType reportedItemType) {
        return reportDAO.findByReportedItem(reportedItemId, reportedItemType);
    }

    @Override
    public long countByReportedItem(Long reportedItemId, ReportTargetType reportedItemType) {
        return reportDAO.countByReportedItem(reportedItemId, reportedItemType);
    }

    @Override
    public long count() {
        return reportDAO.count();
    }

    @Override
    public List<Report> findBySeen(boolean seen) {
        return reportDAO.findBySeen(seen);
    }

    @Override
    public List<Report> findByType(ReportType type) {
        return reportDAO.findByType(type);
    }

    @Override
    public void markAsSeen(Long reportId) {
        reportDAO.markAsSeen(reportId);
    }

    @Override
    public List<Report> getRecentReports(int limit) {
        return reportDAO.findRecentReports(limit);
    }

    @Override
    public int getPendingReportsCount() {
        return reportDAO.countPendingReports();
    }

    @Override
    public List<ReportListDTO> getReportsList(ReportType type, Report.ReportStatus status, ReportTargetType targetType, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Report> reports = reportDAO.findReportsWithFilters(type, status, targetType, offset, pageSize);

        return reports.stream()
                .map(ReportListDTO::new) // Use the updated DTO constructor
                .collect(Collectors.toList());
    }

    @Override
    public int getReportsCount(ReportType type, Report.ReportStatus status, ReportTargetType targetType) {
        return reportDAO.countReportsWithFilters(type, status, targetType);
    }

    @Override
    public boolean updateReportStatus(Long reportId, Report.ReportStatus status) {
        Report report = reportDAO.findById(reportId);
        if (report != null) {
            report.setStatus(status);
            reportDAO.update(report);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReport(Long reportId) {
        Report report = reportDAO.findById(reportId);
        if (report != null) {
            reportDAO.delete(report);
            return true;
        }
        return false;
    }

    @Override
    public List<Report> findByStatus(Report.ReportStatus status) {
        return reportDAO.findByStatus(status);
    }
}
