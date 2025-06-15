/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentAi;

/**
 *
 *
 * @author LEGION
 */
//import java.net.http.*;
//import java.net.URI;
//import java.io.IOException;
//import util.ResponseFormatter;
//
//public class GroqAIAgent {
//
//    private final String GROQ_API_KEY = "day la api cua toi";
//    private final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
//    private final ResponseFormatter formatter = new ResponseFormatter();
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        String prompt = "Tôi là người mới bắt đầu học lập trình backend. Dưới đây là danh sách khóa học tôi có:"
//                + "- Java cơ bản , Spring Boot nâng cao,  SQL , MySQL , Docker"
//                + "Hãy tạo một roadmap học backend hiệu quả từ các khóa học này.";
//
//        String jsonBody = """
//            {
//                "messages": [
//                    {
//                        "role": "user",
//                        "content": "%s"
//                    }
//                ],
//                "model": "llama3-8b-8192",
//                "temperature": 0.7,
//                "max_tokens": 1000
//            }
//            """.formatted(prompt);
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(GROQ_URL))
//                .header("Authorization", "Bearer " + GROQ_API_KEY)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("Groq Response:\n" + response.body());
//    }
//    public String getShortResponse(String userPrompt) throws IOException, InterruptedException {
//        // Thêm system prompt để yêu cầu trả lời ngắn gọn
//        String courses = "Java cơ bản , Spring Boot nâng cao,  SQL , MySQL , Docker, React, Introduction to network programming";
//        String systemPrompt = "Bạn là một AI assistant chuyên về hướng dẫn các khóa học và roadmap. "
//                + "Hãy trả lời ngắn gọn, súc tích, tối đa 3-4 câu. "
//                + "Chỉ đưa ra thông tin quan trọng nhất."
//                + "Dựa vào các khóa học sau đây:" + courses;
//
//        String jsonBody = """
//            {
//                "messages": [
//                    {
//                        "role": "system",
//                        "content": "%s"
//                    },
//                    {
//                        "role": "user", 
//                        "content": "%s"
//                    }
//                ],
//                "model": "llama3-8b-8192",
//                "temperature": 0.3,
//                "max_tokens": 150,
//                "top_p": 0.9,
//                "stop": ["\\n\\n", "Kết luận:", "Tóm lại:"]
//            }
//            """.formatted(systemPrompt, userPrompt);
//
//        return makeRequest(jsonBody);
//    }
//
//    private String makeRequest(String jsonBody) throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(GROQ_URL))
//                .header("Authorization", "Bearer " + GROQ_API_KEY)
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return formatter.parseAndFormat(jsonBody, "clean");
//    }
//}
import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import util.ResponseFormatter;

public class GroqAIAgent {

    private final String GROQ_API_KEY = "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";
    private final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final ResponseFormatter formatter = new ResponseFormatter();

    public String getShortResponse(String userPrompt) throws IOException, InterruptedException {
        String courses = "Java cơ bản, Spring Boot nâng cao, SQL, MySQL, Docker, React, Introduction to network programming";
        //chạy sql để lấy list tên các khóa học trong db
        String systemPrompt
                = "Bạn là một AI assistant chuyên về hướng dẫn các khóa học và roadmap. "
                + "Hãy trả lời ngắn gọn, súc tích, tối đa 3-4 câu. "
                + "Chỉ đưa ra thông tin quan trọng nhất. "
                + "Dựa vào các khóa học sau đây: " + courses;
   
        String jsonBody = String.format("""
            {
                "messages": [
                    {
                        "role": "system",
                        "content": "%s"
                    },
                    {
                        "role": "user", 
                        "content": "%s"
                    }
                ],
                "model": "llama3-8b-8192",
                "temperature": 0.3,
                "max_tokens": 150,
                "top_p": 0.9
            }
            """, systemPrompt.replace("\"", "\\\""), userPrompt.replace("\"", "\\\""));

        return makeRequest(jsonBody);
    }

    private String makeRequest(String jsonBody) throws IOException, InterruptedException {
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GROQ_URL))
                .header("Authorization", "Bearer " + GROQ_API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Debug
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Raw Response: " + response.body());

        // QUAN TRỌNG: Truyền response.body() chứ không phải jsonBody
        return formatter.safeParseContent(response.body());
    }
}
