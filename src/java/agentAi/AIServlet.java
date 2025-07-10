/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package agentAi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.DTO.Conversation;
import service.course.CourseService;
import service.course.CourseServiceImpl;

/**
 *
 * @author LEGION
 */
@WebServlet(name = "AIServlet", urlPatterns = {"/suggestion-ai-agent"})
public class AIServlet extends HttpServlet {

    private final CourseService courseService = new CourseServiceImpl(); // Dùng service có tích hợp RAG

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userMessage = request.getParameter("message");
        String showMoreParam = request.getParameter("showMore");

        boolean showMore = showMoreParam != null && showMoreParam.equalsIgnoreCase("true");

        try {
            // Lưu session
            HttpSession session = request.getSession();
            List<Conversation> conversations = (List<Conversation>) session.getAttribute("conversations");

            if (conversations == null) {
                conversations = new ArrayList<>();
                session.setAttribute("conversations", conversations);
            }

            // Chỉ xử lý nếu có message từ người dùng
            if (userMessage != null && !userMessage.trim().isEmpty()) {
                // Gọi service để sinh phản hồi dựa trên khóa học
                String aiResponse = courseService.processUserMessage(userMessage, showMore);

                System.out.println("AI RESPONSE: " + aiResponse);

                // Thêm vào lịch sử hội thoại
                conversations.add(new Conversation(userMessage, aiResponse));
                session.setAttribute("conversations", conversations); // Cập nhật lại session
            }

            // Forward tới trang hiển thị
            request.getRequestDispatcher("testAi/request.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Đã có lỗi xảy ra khi xử lý AI");
        }
    }
}

