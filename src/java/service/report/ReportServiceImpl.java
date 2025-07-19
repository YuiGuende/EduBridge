package service.report;

import DAO.report.ReportDAOImpl;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import model.notification.Report;
import model.user.User;
import model.notification.ReportTarget;

import java.util.List;
import model.DTO.ReportListDTO;
import model.notification.ReportType;

public class ReportServiceImpl implements IReportService {

    private final ReportDAOImpl reportDAO;

    public ReportServiceImpl() {
        this.reportDAO = new ReportDAOImpl();
    }

    @Override
    public Report save(Report report) {
        return reportDAO.save(report);
    }

    @Override
    public List<Report> getRecentReports(int limit) {
        return reportDAO.findRecentReports(limit);
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
    public List<Report> findByTarget(ReportTarget target) {
        return reportDAO.findByTarget(target);
    }

    @Override
    public long countByTarget(ReportTarget target) {
        return reportDAO.countByTarget(target);
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
    public List<ReportListDTO> getReportsList(String type, String status, String targetType, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Report> reports = reportDAO.findReportsWithFilters(type, status, targetType, offset, pageSize);

        List<ReportListDTO> reportDTOs = new ArrayList<>();
        for (Report report : reports) {
            ReportListDTO dto = new ReportListDTO();
            dto.setId(report.getId());
            dto.setUser(report.getUser());
            dto.setType(report.getType().toString());
            dto.setContent(report.getContent());
            dto.setStatus(report.getStatus());
            dto.setCreatedAt(Date.from(report.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()));
            dto.setSeen(report.isSeen());
            // Set target based on report type
            // dto.setTarget(report.getTarget()); // You'll need to implement this based on your Report entity
            reportDTOs.add(dto);
        }

        return reportDTOs;
    }

    @Override
    public int getReportsCount(String type, String status, String targetType) {
        return reportDAO.countReportsWithFilters(type, status, targetType);
    }


    @Override
    public boolean updateReportStatus(Long reportId, String status) {
        try {
            Report report = reportDAO.findById(reportId);
            if (report != null) {
                report.setStatus(status);
                reportDAO.update(report);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteReport(Long reportId) {
        try {
            reportDAO.deleteById(reportId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 

    @Override
    public List<Report> findByStatus(String status) {
        return reportDAO.findByStatus(status);
    }

    @Override
    public int getPendingReportsCount() {
        return reportDAO.countPendingReports();
    }
}
