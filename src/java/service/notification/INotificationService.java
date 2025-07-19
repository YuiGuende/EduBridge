package service.notification;

import model.notification.Notification;

public interface INotificationService {

    Notification saveNotification(Notification notification);

    void sendNotificationToInstructor(Long instructorId, String message, String actionUrl);

    void sendNotificationToUser(Long userId, String message, String actionUrl);

    void sendNotificationToAdmins(String message, String type);

    void broadcastNotification(String message);
}
