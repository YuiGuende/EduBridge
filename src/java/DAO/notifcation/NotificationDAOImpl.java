package DAO.notifcation;


import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import model.notification.Notification;

public class NotificationDAOImpl extends GenericDAO<Notification> implements INotificationDAO {

    public NotificationDAOImpl() {
        super(Notification.class);
    }

}
