//package ws;
//
//import jakarta.websocket.*;
//import jakarta.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//import org.cloudinary.json.JSONObject;
//
//// endpoint URL: ws://.../admin-notifications
//@ServerEndpoint(value = "/admin-notifications")
//public class AdminNotificationSocket {
//
//    private static ConcurrentHashMap<String, Session> adminSessions = new ConcurrentHashMap<>();
//    private String adminId;
//
////    @OnOpen
////    public void onOpen(Session session, EndpointConfig config) {
////        System.out.println("Admin socket opened");
////        String query = session.getQueryString(); 
////        if (query != null && query.startsWith("adminId=")) {
////            try {
////                adminId = query.split("=")[1];
////                adminSessions.put(adminId, session);
////                System.out.println("Admin " + adminId + " connected to WebSocket.");
////            } catch (Exception ignored) {}
////        }
////    }
//    @OnOpen
//    public void onOpen(Session session, EndpointConfig config) {
//        System.out.println("Admin socket opened");
//        String query = session.getQueryString(); 
//        if (query != null && query.startsWith("adminId=")) {
//            try {
//                adminId = query.split("=")[1];
//                adminSessions.put(adminId, session);
//                System.out.println("Admin " + adminId + " connected to WebSocket.");//không in ra được dòng này
//            } catch (Exception ignored) {}
//        }
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        adminSessions.remove(adminId);
//        System.out.println("Admin " + adminId + " disconnected from WebSocket.");
//    }
//
//    @OnMessage
//    public void onMessage(String msg, Session session) {
////        try {
////            JSONObject json = new JSONObject(msg);
////            if ("auth".equals(json.getString("type"))) {
////                this.adminId = json.getString("adminId");
////                adminSessions.put(adminId, session);
////                System.out.println("Admin " + adminId + " authenticated and connected.");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        System.out.println("Message from admin " + adminId + ": " + msg);
//    }
//
//    public static void sendNotificationToAdmin(String adminId, String message) {
//        Session session = adminSessions.get(adminId);
//        if (session != null && session.isOpen()) {
//
//            try {
//                session.getBasicRemote().sendText(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Admin " + adminId + " kh�ng online.");
//        }
//    }
//
//    public static void broadcastToAllAdmins(String message) {
//        for (Session session : adminSessions.values()) {
//            if (session.isOpen()) {
//                try {
//                    session.getBasicRemote().sendText(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static int getOnlineAdminCount() {
//        return adminSessions.size();
//    }
//}
package ws;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.cloudinary.json.JSONObject;

@ServerEndpoint(value = "/admin-notifications")
public class AdminNotificationSocket {

    private static final ConcurrentHashMap<String, Session> adminSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("Admin socket opened");
        // Không lấy adminId từ query string vì bạn dùng JSON gửi sau
        // --> Chờ tới @OnMessage để xác thực
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        try {
            JSONObject json = new JSONObject(msg);

            if ("auth".equals(json.getString("type"))) {
                String adminId = json.getString("adminId");

                // Gán adminId vào session
                session.getUserProperties().put("adminId", adminId);
                adminSessions.put(adminId, session);

                System.out.println("Admin " + adminId + " authenticated and connected.");
            } else {
                // Nếu không phải auth thì in ra để debug
                String adminId = (String) session.getUserProperties().get("adminId");
                System.out.println("Message from admin " + adminId + ": " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        String adminId = (String) session.getUserProperties().get("adminId");
        if (adminId != null) {
            adminSessions.remove(adminId);
            System.out.println("Admin " + adminId + " disconnected from WebSocket.");
        } else {
            System.out.println("Disconnected session but adminId was null.");
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("WebSocket error: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    // Optional: phương thức để gửi thông báo tới admin cụ thể
    public static void sendNotificationToAdmin(String adminId, String message) {
        Session session = adminSessions.get(adminId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Admin " + adminId + " not connected.");
        }
    }

    public static void broadcastToAllAdmins(String message) {
        for (Session session : adminSessions.values()) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getOnlineAdminCount() {
        return adminSessions.size();
    }
}
