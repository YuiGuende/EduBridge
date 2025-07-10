package ws;


import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

// endpoint URL: ws://.../notification
@ServerEndpoint(value = "/notification")
public class NotificationSocket {

    // D�ng ?? l?u c�c session ?ang m? theo userId
    private static ConcurrentHashMap<Long, Session> userSessions = new ConcurrentHashMap<>();

    private Long userId; // m?i session g?n v?i 1 user

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // T?m th?i: userId truy?n qua query string
        System.out.println("open socket");//tôi không thấy dòng này chạy
        String query = session.getQueryString(); // ?userId=999
        if (query != null && query.startsWith("userId=")) {
            try {
                userId = Long.valueOf(query.split("=")[1]);
                userSessions.put(userId, session);
                System.out.println("User " + userId + " connected to WebSocket.");
            } catch (NumberFormatException ignored) {}
        }
    }

    @OnClose
    public void onClose(Session session) {
        userSessions.remove(userId);
        System.out.println("User " + userId + " disconnected from WebSocket.");
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        // Kh�ng c?n x? l� g� trong v� d? n�y
    }

    // H�m public ?? Servlet kh�c g?i
    public static void sendNotificationToUser(Long userId, String message) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User " + userId + " kh�ng online ? ch? l?u notification.");
        }
    }
}
