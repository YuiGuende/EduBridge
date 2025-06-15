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

/**
 *
 * @author LEGION
 */
@WebServlet(name = "AIServlet", urlPatterns = {"/suggestion-ai-agent"})
public class AIServlet extends HttpServlet {

    private static final GroqAIAgent groqAgent = new GroqAIAgent();
    private static final String GROQ_API_KEY = "your-groq-api-key";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy parameters từ request
        String userMessage = request.getParameter("message");

        try {
            HttpSession session = request.getSession();
            List<Conversation> conversations = (List<Conversation>) session.getAttribute("conversations");
            if (conversations == null) {
                conversations = new ArrayList<>();
                session.setAttribute("conversations", conversations); // Quan trọng: Set vào session ngay
            }

            // Chỉ gọi AI khi có message
            if (userMessage != null && !userMessage.trim().isEmpty()) {
                String aiResponse = groqAgent.getShortResponse(userMessage);
                System.out.println("AI RESPONSE: " + aiResponse);

                conversations.add(new Conversation(userMessage, aiResponse));
                session.setAttribute("conversations", conversations); // Update lại session
            }
            request.getRequestDispatcher("testAi/request.jsp").forward(request, response);

        } catch (Exception e) {

        }
    }

//    private String getCustomizedResponse(String userMessage, String type)
//            throws IOException, InterruptedException {
//
//        String systemPrompt = getSystemPrompt(type);
//        int maxTokens = getMaxTokens(type);
//        double temperature = getTemperature(type);
//
//        String jsonBody = """
//            {
//                "messages": [
//                    {"role": "system", "content": "%s"},
//                    {"role": "user", "content": "%s"}
//                ],
//                "model": "llama3-8b-8192",
//                "temperature": %.1f,
//                "max_tokens": %d,
//                "top_p": 0.9
//            }
//            """.formatted(systemPrompt, userMessage, temperature, maxTokens);
//
//        return grogAgent.getShortResponse(systemPrompt)
//    }
//    private String getSystemPrompt(String type) {
//        return switch (type) {
//            case "short" ->
//                "Trả lời ngắn gọn trong 1-2 câu. Chỉ thông tin quan trọng nhất.";
//            case "detailed" ->
//                "Trả lời chi tiết với ví dụ cụ thể và giải thích rõ ràng.";
//            case "code" ->
//                "Trả lời bằng code example với comment ngắn gọn. Không giải thích dài dòng.";
//            case "list" ->
//                "Trả lời dưới dạng danh sách có đánh số, mỗi item 1 dòng.";
//            default ->
//                "Trả lời một cách cân bằng, không quá dài không quá ngắn.";
//        };
//    }
//
//    private int getMaxTokens(String type) {
//        return switch (type) {
//            case "short" ->
//                100;
//            case "detailed" ->
//                500;
//            case "code" ->
//                300;
//            case "list" ->
//                200;
//            default ->
//                250;
//        };
//    }
//
//    private double getTemperature(String type) {
//        return switch (type) {
//            case "code" ->
//                0.1; // Ít creative hơn cho code
//            case "short" ->
//                0.3; // Tập trung vào thông tin chính
//            default ->
//                0.7; // Cân bằng
//        };
//    }
}
