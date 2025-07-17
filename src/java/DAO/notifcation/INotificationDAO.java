package DAO.notifcation;


import java.util.List;
import model.notification.Notification;

public interface INotificationDAO {
    Notification save(Notification notification);
    Notification findById(Long id);
    Notification update(Notification notification);
    void deleteById(Long id);
    List<Notification> findAll();
    
    // Notification specific methods
    List<Notification> findByUserId(Long userId);
    List<Notification> findUnseenByUserId(Long userId);
    int countUnseenByUserId(Long userId);
    void markAllAsSeenForUser(Long userId);
    void deleteOldNotifications(int daysOld);
}
