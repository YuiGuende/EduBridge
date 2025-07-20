package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.user.UserServiceImpl;
import util.JSPUtils;

import model.DTO.InstructorDetailDTO;
import model.DTO.InstructorListDTO;
import model.user.Instructor;
import model.user.User;
import model.user.UserStatus; // Import UserStatus
import service.user.IInstructorService;
import service.user.InstructorServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.user.IUserService;

@WebServlet("/admin/instructors")
public class AdminInstructorServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AdminInstructorServlet.class.getName());
    private IInstructorService instructorService;
    private IUserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        instructorService = new InstructorServiceImpl();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listInstructors(request, response);
                break;
            case "view":
                viewInstructor(request, response);
                break;
            case "createForm":
                showCreateForm(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            default:
                listInstructors(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                createInstructor(request, response);
                break;
            case "update":
                updateInstructor(request, response);
                break;
            case "updateStatus": // Action mới để cập nhật trạng thái
                updateInstructorStatus(request, response);
                break;
            default:
                listInstructors(request, response);
                break;
        }
    }

    private void listInstructors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int size = 10;
        UserStatus statusFilter = UserStatus.ACTIVE; // Mặc định chỉ hiển thị ACTIVE

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid page number format: " + request.getParameter("page"), e);
                page = 1;
            }
        }
        if (request.getParameter("size") != null) {
            try {
                size = Integer.parseInt(request.getParameter("size"));
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid page size format: " + request.getParameter("size"), e);
                size = 10;
            }
        }
        if (request.getParameter("statusFilter") != null) {
            try {
                statusFilter = UserStatus.valueOf(request.getParameter("statusFilter").toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Invalid status filter: " + request.getParameter("statusFilter"), e);
                statusFilter = UserStatus.ACTIVE; // Fallback
            }
        }

        List<InstructorListDTO> instructors = instructorService.getInstructorsWithPaginationAndStatus(page, size, statusFilter);
        long totalInstructors = instructorService.getTotalInstructorsByStatus(statusFilter);
        int totalPages = (int) Math.ceil((double) totalInstructors / size);

        request.setAttribute("instructors", instructors);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", size);
        request.setAttribute("statusFilter", statusFilter.name()); // Truyền tên enum sang JSP

        // Generate pagination URLs
        List<String> pageUrls = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageUrls.add(JSPUtils.buildPaginationUrl(request.getRequestURI(), request.getParameterMap(), i, size, "statusFilter", statusFilter.name()));
        }
        request.setAttribute("pageUrls", pageUrls);

        request.getRequestDispatcher("/admin/instructors.jsp").forward(request, response);
    }

    private void viewInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid instructor ID format: " + request.getParameter("id"), e);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Invalid instructor ID."));
            return;
        }

        InstructorDetailDTO instructorOptional = new InstructorDetailDTO(instructorService.getInstructorById(id));
        if (instructorOptional != null) {
            request.setAttribute("instructor", instructorOptional);
            request.getRequestDispatcher("/admin/instructor-details.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Instructor not found."));
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/instructor-form.jsp").forward(request, response);
    }

    private void createInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String bio = request.getParameter("bio");

        User newUser = new User(fullname, email, password, "INSTRUCTOR");
        // Status được set mặc định là ACTIVE trong constructor của User
        User savedUser = userService.save(newUser);

        Instructor newInstructor = new Instructor();
        newInstructor.setUser(savedUser);
        newInstructor.setBio(bio);
        instructorService.saveInstructor(newInstructor);

        response.sendRedirect(request.getContextPath() + "/admin/instructors?message=" + JSPUtils.encodeURL("Instructor created successfully!"));
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid instructor ID format for edit: " + request.getParameter("id"), e);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Invalid instructor ID for editing."));
            return;
        }

        InstructorDetailDTO instructorOptional = new InstructorDetailDTO(instructorService.getInstructorById(id));
        if (instructorOptional != null) {
            request.setAttribute("instructor", instructorOptional);
            request.getRequestDispatcher("/admin/instructor-form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Instructor not found for editing."));
        }
    }

    private void updateInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid instructor ID format for update: " + request.getParameter("id"), e);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Invalid instructor ID for update."));
            return;
        }

        InstructorDetailDTO instructorDTO = new InstructorDetailDTO(instructorService.getInstructorById(id));

        if (instructorDTO != null) {

            User userOptional = userService.findById(instructorDTO.getId());
            if (userOptional != null) {
                User user = userOptional;
                user.setFullname(request.getParameter("fullname"));
                user.setEmail(request.getParameter("email"));
                // Password update is optional, only if provided
                String newPassword = request.getParameter("password");
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                }
                userService.save(user);
            }

            Instructor instructor = new Instructor();
            instructor.setId(instructorDTO.getId());
            instructor.setBio(request.getParameter("bio"));
            instructor.setUser(userService.findById(instructorDTO.getId()));
            instructor.setEducationLevel(request.getParameter("educationLevel"));
            instructor.setSpecialization(request.getParameter("specialization"));
            instructor.setAvatarUrl(request.getParameter("avatarUrl"));
            instructor.setLinkedinProfile(request.getParameter("linkedinProfile"));
            instructor.setExperienceYears(Integer.parseInt(request.getParameter("experienceYears")));
            instructorService.saveInstructor(instructor);

            response.sendRedirect(request.getContextPath() + "/admin/instructors?message=" + JSPUtils.encodeURL("Instructor updated successfully!"));
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Instructor not found for update."));
        }
    }

    private void updateInstructorStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        UserStatus newStatus = null;
        try {
            id = Long.parseLong(request.getParameter("id"));
            newStatus = UserStatus.valueOf(request.getParameter("status").toUpperCase());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid instructor ID format for status update: " + request.getParameter("id"), e);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Invalid instructor ID for status update."));
            return;
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid status value: " + request.getParameter("status"), e);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Invalid status value provided."));
            return;
        }

        try {
            instructorService.updateInstructorStatus(id, newStatus);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?message=" + JSPUtils.encodeURL("Instructor status updated to " + newStatus.name() + " successfully!"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating instructor status for ID: " + id + " to " + newStatus.name(), e);
            response.sendRedirect(request.getContextPath() + "/admin/instructors?error=" + JSPUtils.encodeURL("Failed to update instructor status: " + e.getMessage()));
        }
    }
}
