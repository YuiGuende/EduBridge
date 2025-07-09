package service.report;

import model.notification.Report;
import model.user.User;
import model.notification.ReportTarget;

import java.util.List;
import model.notification.ReportType;

public interface IReportService {

    Report save(Report report);

    Report update(Report report);

    void delete(Report report);

    void deleteById(Long id);

    Report findById(Long id);

    List<Report> findAll();

    List<Report> findByReporter(User user);

    List<Report> findByTarget(ReportTarget target);

    long countByTarget(ReportTarget target);

    List<Report> findBySeen(boolean seen);

    List<Report> findByType(ReportType type);

    void markAsSeen(Long reportId);

}
