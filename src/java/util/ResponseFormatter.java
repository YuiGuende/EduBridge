///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package util;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//
///**
// *
// * @author LEGION
// */
//public class ResponseFormatter {
//
//    private final Gson gson = new Gson();
//
//    public String parseAndFormat(String groqResponse, String formatType) {
//        try {
//            JsonObject response = gson.fromJson(groqResponse, JsonObject.class);
//            String content = response.getAsJsonArray("choices")
//                    .get(0).getAsJsonObject()
//                    .getAsJsonObject("message")
//                    .get("content").getAsString();
//
//            return switch (formatType) {
//                case "clean" ->
//                    cleanResponse(content);
//                case "html" ->
//                    toHtml(content);
//                case "json" ->
//                    toJsonFormat(content);
//                default ->
//                    content;
//            };
//        } catch (Exception e) {
//            return "Lỗi parse response: " + e.getMessage();
//        }
//    }
//
//    private String cleanResponse(String content) {
//        return content
//                .replaceAll("\\*\\*(.*?)\\*\\*", "$1") // Remove bold markdown
//                .replaceAll("\\*(.*?)\\*", "$1") // Remove italic markdown
//                .replaceAll("```[\\s\\S]*?```", "") // Remove code blocks
//                .trim();
//    }
//
//    private String toHtml(String content) {
//        return content
//                .replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>")
//                .replaceAll("\\*(.*?)\\*", "<em>$1</em>")
//                .replaceAll("\\n", "<br>")
//                .replaceAll("```([\\s\\S]*?)```", "<pre><code>$1</code></pre>");
//    }
//
//    private String toJsonFormat(String content) {
//        JsonObject result = new JsonObject();
//        result.addProperty("response", content);
//        result.addProperty("timestamp", System.currentTimeMillis());
//        return gson.toJson(result);
//    }
//}
package util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class ResponseFormatter {

    private final Gson gson = new Gson();

    public String safeParseContent(String groqResponse) {
        try {
            System.out.println("Parsing response: " + groqResponse);

            JsonObject response = gson.fromJson(groqResponse, JsonObject.class);

            if (response == null) {
                return "Lỗi: Response null";
            }

            // Kiểm tra error từ API
            if (response.has("error")) {
                JsonObject error = response.getAsJsonObject("error");
                String errorMsg = error.has("message") ? error.get("message").getAsString() : "Unknown error";
                return "API Error: " + errorMsg;
            }

            // Kiểm tra choices array
            if (!response.has("choices")) {
                return "Lỗi: Không có 'choices' trong response. Response keys: " + response.keySet();
            }

            JsonArray choices = response.getAsJsonArray("choices");
            if (choices == null || choices.size() == 0) {
                return "Lỗi: Choices array rỗng";
            }

            JsonObject firstChoice = choices.get(0).getAsJsonObject();
            if (!firstChoice.has("message")) {
                return "Lỗi: Choice không có 'message'. Choice keys: " + firstChoice.keySet();
            }

            JsonObject message = firstChoice.getAsJsonObject("message");
            if (!message.has("content")) {
                return "Lỗi: Message không có 'content'. Message keys: " + message.keySet();
            }

            return cleanResponse(message.get("content").getAsString());

        } catch (Exception e) {
            // Fallback: Thử parse bằng regex
            return parseWithRegex(groqResponse, e);
        }
    }

    private String parseWithRegex(String groqResponse, Exception originalError) {
        try {
            // Tìm content bằng regex
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                    "\"content\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"",
                    java.util.regex.Pattern.DOTALL
            );
            java.util.regex.Matcher matcher = pattern.matcher(groqResponse);

            if (matcher.find()) {
                String content = matcher.group(1);
                // Unescape JSON string
                content = content.replace("\\\"", "\"")
                        .replace("\\n", "\n")
                        .replace("\\r", "\r")
                        .replace("\\t", "\t")
                        .replace("\\\\", "\\");
                return cleanResponse(content);
            }

            return "Parse failed. Original error: " + originalError.getMessage()
                    + "\nResponse: " + groqResponse.substring(0, Math.min(200, groqResponse.length()));

        } catch (Exception e) {
            return "Complete parse failure: " + e.getMessage();
        }
    }

    public String parseAndFormat(String groqResponse, String formatType) {
        String content = safeParseContent(groqResponse);

        if (content.startsWith("Lỗi:") || content.startsWith("API Error:") || content.startsWith("Parse")) {
            return content; // Trả về error message
        }

        return switch (formatType) {
            case "clean" ->
                cleanResponse(content);
            case "html" ->
                toHtml(content);
            case "json" ->
                toJsonFormat(content);
            default ->
                content;
        };
    }

    private String cleanResponse(String content) {
        if (content == null) {
            return "";
        }

        return content
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1") // Remove bold markdown
                .replaceAll("\\*(.*?)\\*", "$1") // Remove italic markdown
                .replaceAll("```[\\s\\S]*?```", "") // Remove code blocks
                .replaceAll("#{1,6}\\s*", "") // Remove headers
                .trim();
    }

    private String toHtml(String content) {
        return content
                .replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>")
                .replaceAll("\\*(.*?)\\*", "<em>$1</em>")
                .replaceAll("\\n", "<br>")
                .replaceAll("```([\\s\\S]*?)```", "<pre><code>$1</code></pre>");
    }

    private String toJsonFormat(String content) {
        JsonObject result = new JsonObject();
        result.addProperty("response", content);
        result.addProperty("timestamp", System.currentTimeMillis());
        return gson.toJson(result);
    }
}
