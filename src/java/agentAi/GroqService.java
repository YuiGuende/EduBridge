package agentAi;

import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;

class AiResponse {

    private String sql;         // null nếu không phải câu truy vấn
    private String message;     // null nếu là câu SQL

    public AiResponse() {
    }

    public AiResponse(String sql, String message) {
        this.sql = sql;
        this.message = message;
    }
    
    public static AiResponse fromAI(String aiOutput) {
        aiOutput = aiOutput.trim();
        if (aiOutput.toUpperCase().startsWith("SELECT")) {
            return new AiResponse(aiOutput, null);
        } else {
            return new AiResponse(null, aiOutput);
        }
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

public class GroqService {

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String API_KEY = "";

    public String generateSQLFromUserQuery(String message, boolean showMore) throws IOException {
        int limit = showMore ? 6 : 3;

        String prompt = String.format("""
    Bạn là chuyên gia SQL. Viết SQL truy vấn trong bảng `courses` để tìm các course phù hợp.

    Quy tắc:
    - Sql của sql server
    - Chỉ lấy khóa học `status = 'PUBLIC'`
    - Cột hợp lệ: [id]
          ,[created_by]
          ,[created_date]
          ,[description]
          ,[headline]
          ,[last_update]
          ,[is_paid]
          ,[published_time]
          ,[status]
          ,[thumbnail_url]
          ,[title]
          ,[updated_by]
          ,[version]
          ,[primary_language_id]
          ,[language_id]
          ,[estimated_time]
          ,[rate]
          ,[rateQuantity]
    - Phải select toàn bộ các trường ở trên và không được dùng *
    - Không dùng JOIN
    - Giới hạn TOP %d bằng cú pháp SQL Server (`SELECT TOP %d`)
    - Nếu người dùng hỏi một thông tin cụ thể ví dụ như (html, statistic, spring,..) thì LIKE phải cho cả headline,description,title và phải tìm cả tiếng anh và tiếng việt của thông tin cụ thể đó
    Yêu cầu từ người dùng: "%s"

Trả về SQL duy nhất, không thêm lời giải thích, bắt đầu bằng SELECT.
""", limit, limit, message);

        String response = callGroq("user", prompt);

        // Trích xuất SQL duy nhất
        System.out.println("sql response form groq");
        String sql = extractSQL(response);
        System.out.println("sql after extract");
        System.out.println("sql to be processed: " + sql); // ✅ in ra để kiểm tra

        if (!sql.trim().toUpperCase().startsWith("SELECT")) {
            throw new IllegalArgumentException("AI không trả về SQL hợp lệ: " + sql);
        }

        return sql;
    }
    
    

    public String buildSystemPromptWithCourses(String courseContext) {
        return String.format("""
        Bạn là một AI assistant chuyên tư vấn khóa học (KHÔNG phải roadmap hay tài liệu học). 
        Trả lời bằng HTML rõ ràng, chỉ sử dụng các khóa học từ dữ liệu dưới đây. 
        Chỉ giới thiệu các khóa học trong danh sách — không tự tạo mới.

        Trả lời ngắn gọn 3–4 câu, tập trung vào lợi ích và mô tả từng khóa học phù hợp nhất với yêu cầu.

        Trả về nội dung dưới dạng HTML (không phải markdown), dùng UTF-8, KHÔNG mã hóa HTML entities như &amp; hay &#234;.

        %s

        Trả lời người dùng theo ngôn ngữ họ hỏi. 
        Kết thúc bằng câu: "Bạn muốn xem thêm không?"
        """, courseContext);
    }

    public String callGroqWithPrompt(String systemPrompt, String userMessage) throws IOException {
        JsonArray messages = new JsonArray();

        JsonObject sys = new JsonObject();
        sys.addProperty("role", "system");
        sys.addProperty("content", systemPrompt);
        messages.add(sys);

        JsonObject user = new JsonObject();
        user.addProperty("role", "user");
        user.addProperty("content", userMessage);
        messages.add(user);

        return callGroq(messages);
    }

    private String callGroq(String role, String prompt) throws IOException {
        JsonArray messages = new JsonArray();
        JsonObject msg = new JsonObject();
        msg.addProperty("role", role);
        msg.addProperty("content", prompt);
        messages.add(msg);
        return callGroq(messages);
    }

    private String callGroq(JsonArray messages) throws IOException {
        JsonObject req = new JsonObject();
        req.addProperty("model", "llama3-8b-8192");
        req.add("messages", messages);
        req.addProperty("max_tokens", 300);
        req.addProperty("temperature", 0.2);

        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(req.toString().getBytes(StandardCharsets.UTF_8));
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        System.out.println("Raw Response: " + response.toString());

        JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
        return json.getAsJsonArray("choices").get(0)
                .getAsJsonObject().get("message")
                .getAsJsonObject().get("content").getAsString();
    }

    private String extractSQL(String raw) {
        // Nếu có markdown ```sql thì lấy đoạn bên trong
        if (raw.contains("```")) {
            raw = raw.replaceAll("(?s)```sql\\s*", "")
                    .replaceAll("(?s)```", "")
                    .trim();
        }

        // Tìm tất cả các đoạn SELECT có FROM (câu đầy đủ)
        Pattern pattern = Pattern.compile("(?i)(SELECT[\\s\\S]+?FROM[\\s\\S]+?)(?=SELECT|\\Z)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(raw);

        while (matcher.find()) {
            String candidate = matcher.group(1).trim();
            if (candidate.toUpperCase().contains("WHERE") || candidate.toUpperCase().contains("LIKE")) {
                return candidate; // Ưu tiên đoạn có điều kiện truy vấn
            }
        }

        // Nếu không có FROM đầy đủ, fallback: lấy câu SELECT đầu tiên
        Pattern fallback = Pattern.compile("(?i)(SELECT\\s+[\\s\\S]+?)\\s*;?\\s*(\\n|$)", Pattern.DOTALL);
        Matcher fallbackMatcher = fallback.matcher(raw);
        if (fallbackMatcher.find()) {
            return fallbackMatcher.group(1).trim();
        }

        throw new IllegalArgumentException("AI không trả về SQL hợp lệ:\n" + raw);
    }

}
