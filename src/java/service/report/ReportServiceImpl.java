package service.report;

import DAO.report.ReportDAOImpl;
import model.notification.Report;
import model.user.User;
import model.notification.ReportTarget;

import java.util.List;
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

}
