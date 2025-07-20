package controller.admin;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.DTO.ReportDetailDTO;
import model.DTO.ReportListDTO;
import model.notification.Report;
import model.notification.ReportTargetType;
import model.notification.ReportType;
import service.report.IReportService;
import service.report.ReportServiceImpl;
import util.DateUtils;
import util.JSPUtils;

@WebServlet("/admin/reports")
public class AdminReportServlet extends HttpServlet {

    private IReportService reportService = new ReportServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        request.setAttribute("PENDING", Report.ReportStatus.PENDING);
        request.setAttribute("SEEN", Report.ReportStatus.SEEN);
        request.setAttribute("RESOLVED", Report.ReportStatus.RESOLVED);
        switch (action) {
            case "list":
                listReports(request, response);
                break;
            case "details":
                showReportDetails(request, response);
                break;
            default:
                listReports(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action is required");
            return;
        }

        switch (action) {
            case "updateStatus":
                updateReportStatus(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method not supported for action: " + action);
                break;
        }
    }

    private void listReports(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 10;

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid page number: " + request.getParameter("page"));
                page = 1; // Default to page 1 on error
            }
        }

        // Get filter parameters
        ReportType filterType = null;
        String typeParam = request.getParameter("filterType");
        if (typeParam != null && !typeParam.isEmpty()) {
            try {
                filterType = ReportType.fromStringIgnoreCase(typeParam);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid report type filter: " + typeParam);
            }
        }

        Report.ReportStatus filterStatus = null;
        String statusParam = request.getParameter("filterStatus");
        if (statusParam != null && !statusParam.isEmpty()) {
            try {
                filterStatus = Report.ReportStatus.fromStringIgnoreCase(statusParam);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid report status filter: " + statusParam);
            }
        }

        ReportTargetType filterTargetType = null;
        String targetTypeParam = request.getParameter("filterTargetType");
        if (targetTypeParam != null && !targetTypeParam.isEmpty()) {
            try {
                filterTargetType = ReportTargetType.fromStringIgnoreCase(targetTypeParam);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid report target type filter: " + targetTypeParam);
            }
        }

        List<ReportListDTO> reportDTOs = reportService.getReportsList(filterType, filterStatus, filterTargetType, page, pageSize);
        long totalReports = reportService.getReportsCount(filterType, filterStatus, filterTargetType); // Use filtered count
        int totalPages = (int) Math.ceil((double) totalReports / pageSize);
        List<String> pageUrls = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            String url = JSPUtils.buildPaginationUrl(request, i);
            pageUrls.add(url);
        }
        request.setAttribute("pageUrls", pageUrls);

      
        request.setAttribute("reports", reportDTOs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalReports", totalReports);
        request.setAttribute("reportTypes", ReportType.values()); // For filter dropdown
        request.setAttribute("reportStatuses", Report.ReportStatus.values()); // For filter dropdown
        request.setAttribute("reportTargetTypes", ReportTargetType.values()); // For filter dropdown
        request.setAttribute("selectedType", typeParam != null ? JSPUtils.htmlEncode(typeParam) : "");
        request.setAttribute("selectedStatus", statusParam != null ? JSPUtils.htmlEncode(statusParam) : "");
        request.setAttribute("selectedTargetType", targetTypeParam != null ? JSPUtils.htmlEncode(targetTypeParam) : "");

        request.getRequestDispatcher("/admin/reports.jsp").forward(request, response);
    }

    private void showReportDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long reportId = null;
        try {
            reportId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report ID");
            return;
        }

        Report report = reportService.findById(reportId); // Use orElse(null)
        if (report != null) {
            ReportDetailDTO reportDetailDTO = new ReportDetailDTO(report);
            request.setAttribute("report", reportDetailDTO);
            request.setAttribute("reportStatusList", Report.ReportStatus.values());
            request.getRequestDispatcher("/admin/report-details.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Report not found");
        }
    }

    private void updateReportStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long reportId = null;
        String newStatusStr = request.getParameter("status");

        try {
            reportId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report ID");
            return;
        }

        if (newStatusStr == null || newStatusStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "New status is required");
            return;
        }

        try {
            Report.ReportStatus newStatus;
            newStatus = Report.ReportStatus.fromStringIgnoreCase(newStatusStr.toUpperCase());
            reportService.updateReportStatus(reportId, newStatus);
            response.sendRedirect(request.getContextPath() + "/admin/reports?action=details&id=" + reportId + "&message=" + JSPUtils.htmlEncode("Status updated successfully!"));
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status value: " + newStatusStr);
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating report status: " + e.getMessage());
        }
    }

    private void deleteReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long reportId = null;
        try {
            reportId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report ID");
            return;
        }

        try {
            reportService.deleteReport(reportId);
            response.sendRedirect(request.getContextPath() + "/admin/reports?message=" + JSPUtils.htmlEncode("Report deleted successfully!"));
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting report: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        // Close EntityManager if it was managed here, but JPAUtil handles it globally.
    }
}
