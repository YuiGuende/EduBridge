package service.notification;

import DAO.notifcation.INotificationDAO;
import DAO.notifcation.NotificationDAOImpl;
import model.notification.Notification;

public class NotificationServiceImpl implements INotificationService {

    private final INotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationDAO.save(notification);
    }
}
