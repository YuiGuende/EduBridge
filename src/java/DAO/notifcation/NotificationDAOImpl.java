package DAO.notifcation;


import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.notification.Notification;

public class NotificationDAOImpl extends GenericDAO<Notification> implements INotificationDAO {

    public NotificationDAOImpl() {
        super(Notification.class);
    }


    @Override
    public List<Notification> findByUserId(Long userId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT n FROM Notification n WHERE n.userId = :userId ORDER BY n.createdAt DESC";
            TypedQuery<Notification> query = em.createQuery(jpql, Notification.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Notification> findUnseenByUserId(Long userId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT n FROM Notification n WHERE n.userId = :userId AND n.seen = false ORDER BY n.createdAt DESC";
            TypedQuery<Notification> query = em.createQuery(jpql, Notification.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int countUnseenByUserId(Long userId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.seen = false";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("userId", userId);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void markAllAsSeenForUser(Long userId) {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();

            String jpql = "UPDATE Notification n SET n.seen = true WHERE n.userId = :userId AND n.seen = false";
            em.createQuery(jpql)
                    .setParameter("userId", userId)
                    .executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOldNotifications(int daysOld) {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -daysOld);
            Date cutoffDate = cal.getTime();

            String jpql = "DELETE FROM Notification n WHERE n.createdAt < :cutoffDate";
            em.createQuery(jpql)
                    .setParameter("cutoffDate", cutoffDate)
                    .executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

}
