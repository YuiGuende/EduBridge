package service.notification;

import DAO.notifcation.INotificationDAO;
import DAO.notifcation.NotificationDAOImpl;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.Date;
import model.notification.Notification;
import model.notification.NotificationType;
import service.user.IUserService;
import service.user.UserServiceImpl;
import ws.AdminNotificationSocket;
import ws.NotificationSocket;

public class NotificationServiceImpl implements INotificationService {

    private INotificationDAO notificationDAO;
    private Gson gson;
     private IUserService userService = new UserServiceImpl();

    public NotificationServiceImpl() {
        this.notificationDAO = new NotificationDAOImpl();
        this.gson = new Gson();
    }

    @Override
    public void sendNotificationToInstructor(Long instructorId, String message, String actionUrl) {
        // Tạo notification object để gửi qua WebSocket
        NotificationMessage notification = new NotificationMessage();
        notification.setType("COURSE_STATUS_UPDATE");
        notification.setTitle("Course Status Update");
        notification.setMessage(message);
        notification.setActionUrl(actionUrl);
        notification.setTimestamp(new Date());

        // Gửi qua WebSocket sử dụng class NotificationSocket của bạn
        String jsonMessage = gson.toJson(notification);
        NotificationSocket.sendNotificationToUser(instructorId, jsonMessage);

        // Lưu vào database để user offline có thể xem sau
        
        saveNotificationToDatabase(instructorId, message, actionUrl, NotificationType.INSTRUCTOR_NOTIFICATION);
    }

    @Override
    public void sendNotificationToUser(Long userId, String message, String actionUrl) {
        NotificationMessage notification = new NotificationMessage();
        notification.setType("USER_NOTIFICATION");
        notification.setTitle("System Notification");
        notification.setMessage(message);
        notification.setActionUrl(actionUrl);
        notification.setTimestamp(new Date());

        String jsonMessage = gson.toJson(notification);
        NotificationSocket.sendNotificationToUser(userId, jsonMessage);

        saveNotificationToDatabase(userId, message, actionUrl, NotificationType.USER_NOTIFICATION);
    }

    @Override
    public void sendNotificationToAdmins(String message, String type) {
        AdminNotificationMessage notification = new AdminNotificationMessage();
        notification.setType(type);
        notification.setMessage(message);
        notification.setTimestamp(new Date());

        String jsonMessage = gson.toJson(notification);
        AdminNotificationSocket.broadcastToAllAdmins(jsonMessage);
    }

    @Override
    public void broadcastNotification(String message) {
        // Gửi cho tất cả admin
        sendNotificationToAdmins(message, "BROADCAST");

        // Nếu cần gửi cho tất cả user, bạn cần mở rộng NotificationSocket
        // để hỗ trợ broadcast hoặc lưu danh sách user IDs
    }

    // Lưu notification vào database
    private void saveNotificationToDatabase(Long userId, String message, String actionUrl, NotificationType type) {
        try {
            
            Notification notification = new Notification();
            notification.setReceiver(userService.findById(userId));
            notification.setMessage(message);
            notification.setTargetUrl(actionUrl);
            notification.setType(type);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setIsRead(false);

            notificationDAO.save(notification);
//            System.out.println("Notification saved to database for user: " + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Các method bổ sung để quản lý notification
    public void markNotificationAsSeen(Long notificationId) {
        try {
            Notification notification = notificationDAO.findById(notificationId);
            if (notification != null) {
                notification.setIsRead(true);
                notificationDAO.update(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAllNotificationsAsSeen(Long userId) {
        try {
            notificationDAO.markAllAsSeenForUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Notification saveNotification(Notification notification) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Inner classes cho JSON message structure
    private static class NotificationMessage {

        private String type;
        private String title;
        private String message;
        private String actionUrl;
        private Date timestamp;

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
    }

    private static class AdminNotificationMessage {

        private String type;
        private String message;
        private Date timestamp;

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
    }
}
