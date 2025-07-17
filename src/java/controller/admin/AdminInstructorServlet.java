package controller.admin;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.DTO.InstructorListDTO;
import model.user.Instructor;
import service.notification.INotificationService;
import service.notification.NotificationServiceImpl;
import service.user.IUserService;
import service.user.UserServiceImpl;

@WebServlet(name = "AdminInstructorServlet", urlPatterns = {"/admin/instructors"})
public class AdminInstructorServlet extends HttpServlet {
    
    private IUserService userService;
    private INotificationService notificationService;
    
    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("view".equals(action)) {
            viewInstructorDetails(request, response);
        } else if ("edit".equals(action)) {
            getInstructorForEdit(request, response);
        } else {
            listInstructors(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("updateStatus".equals(action)) {
            updateInstructorStatus(request, response);
        } else if ("update".equals(action)) {
            updateInstructor(request, response);
        }
    }
    
    private void listInstructors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Get search parameters
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String specialization = request.getParameter("specialization");
            
            // Pagination
            int page = 1;
            int pageSize = 10;
            
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
            
            // Get instructors with filters
            List<InstructorListDTO> instructors = userService.getInstructorsList(name, email, specialization, page, pageSize);
            int totalInstructors = userService.getInstructorsCount(name, email, specialization);
            int totalPages = (int) Math.ceil((double) totalInstructors / pageSize);
            
            request.setAttribute("instructors", instructors);
            request.setAttribute("totalInstructors", totalInstructors);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            
            request.getRequestDispatcher("/admin/instructors.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading instructors: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
    
    private void viewInstructorDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Long instructorId = Long.parseLong(request.getParameter("id"));
            Instructor instructor = userService.findInstructorById(instructorId);
            
            if (instructor == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Instructor not found");
                return;
            }
            
            // Generate HTML for modal content
            StringBuilder html = new StringBuilder();
            html.append("<div class='row'>");
            html.append("<div class='col-md-4 text-center'>");
            if (instructor.getAvatarUrl() != null && !instructor.getAvatarUrl().isEmpty()) {
                html.append("<img src='").append(instructor.getAvatarUrl()).append("' class='img-fluid rounded-circle mb-3' style='width: 150px; height: 150px; object-fit: cover;'>");
            } else {
                html.append("<div class='bg-secondary rounded-circle mx-auto mb-3 d-flex align-items-center justify-content-center text-white' style='width: 150px; height: 150px;'>");
                html.append("<i class='fas fa-user fa-3x'></i></div>");
            }
            html.append("</div>");
            html.append("<div class='col-md-8'>");
            html.append("<h4>").append(instructor.getUser().getFullname()).append("</h4>");
            html.append("<p class='text-muted'>").append(instructor.getUser().getEmail()).append("</p>");
            html.append("<hr>");
            html.append("<p><strong>Specialization:</strong> ").append(instructor.getSpecialization()).append("</p>");
            html.append("<p><strong>Experience:</strong> ").append(instructor.getExperienceYears()).append(" years</p>");
            html.append("<p><strong>Education:</strong> ").append(instructor.getEducationLevel()).append("</p>");
            if (instructor.getLinkedinProfile() != null && !instructor.getLinkedinProfile().isEmpty()) {
                html.append("<p><strong>LinkedIn:</strong> <a href='").append(instructor.getLinkedinProfile()).append("' target='_blank'>View Profile</a></p>");
            }
            html.append("<p><strong>Courses Created:</strong> ").append(instructor.getCoursesCreated().size()).append("</p>");
            if (instructor.getBio() != null && !instructor.getBio().isEmpty()) {
                html.append("<p><strong>Bio:</strong></p>");
                html.append("<p>").append(instructor.getBio()).append("</p>");
            }
            html.append("</div></div>");
            
            response.setContentType("text/html");
            response.getWriter().write(html.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading instructor details");
        }
    }
    
    private void getInstructorForEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        
        try {
            Long instructorId = Long.parseLong(request.getParameter("id"));
            Instructor instructor = userService.findInstructorById(instructorId);
            
            if (instructor == null) {
                out.print(gson.toJson(new ApiResponse(false, "Instructor not found")));
                return;
            }
            
            out.print(gson.toJson(instructor));
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print(gson.toJson(new ApiResponse(false, "Error: " + e.getMessage())));
        }
    }
    
    private void updateInstructorStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        
        try {
            Long instructorId = Long.parseLong(request.getParameter("instructorId"));
            String status = request.getParameter("status");
            
            boolean success = userService.updateUserRole(instructorId, status);
            
            if (success) {
                // Send notification to instructor
                String message = "DISABLED".equals(status) ? 
                    "Your instructor account has been disabled by admin." :
                    "Your instructor account has been reactivated.";
                    
                notificationService.sendNotificationToUser(instructorId, message, "/instructor/profile");
                
                out.print(gson.toJson(new ApiResponse(true, "Instructor status updated successfully")));
            } else {
                out.print(gson.toJson(new ApiResponse(false, "Failed to update instructor status")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print(gson.toJson(new ApiResponse(false, "Error: " + e.getMessage())));
        }
    }
    
    private void updateInstructor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        
        try {
            Long instructorId = Long.parseLong(request.getParameter("instructorId"));
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String specialization = request.getParameter("specialization");
            int experienceYears = Integer.parseInt(request.getParameter("experienceYears"));
            String educationLevel = request.getParameter("educationLevel");
            String linkedinProfile = request.getParameter("linkedinProfile");
            String bio = request.getParameter("bio");
            
            boolean success = userService.updateInstructor(instructorId, fullname, email, specialization, 
                                                         experienceYears, educationLevel, linkedinProfile, bio);
            
            if (success) {
                out.print(gson.toJson(new ApiResponse(true, "Instructor updated successfully")));
            } else {
                out.print(gson.toJson(new ApiResponse(false, "Failed to update instructor")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print(gson.toJson(new ApiResponse(false, "Error: " + e.getMessage())));
        }
    }
    
    // Helper class for JSON responses
    private static class ApiResponse {
        private boolean success;
        private String message;
        
        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}
