package DAO.report;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.notification.Report;
import model.notification.ReportTarget;
import model.user.User;

import java.util.List;
import model.notification.ReportType;

public class ReportDAOImpl extends GenericDAO<Report> implements IReportDAO {

    public ReportDAOImpl() {
        super(Report.class);
    }

    @Override
    public List<Report> findByReporter(User user) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Report> query = em.createQuery(
                    "SELECT r FROM Report r WHERE r.reporter = :user ORDER BY r.createdAt DESC",
                    Report.class
            );
            query.setParameter("user", user);
            return query.getResultList();
        }
    }

    @Override
    public List<Report> findByTarget(ReportTarget target) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Report> query = em.createQuery(
                    "SELECT r FROM Report r WHERE r.target = :target ORDER BY r.createdAt DESC",
                    Report.class
            );
            query.setParameter("target", target);
            return query.getResultList();
        }
    }

    @Override
    public long countByTarget(ReportTarget target) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(
                    "SELECT COUNT(r) FROM Report r WHERE r.target = :target",
                    Long.class
            ).setParameter("target", target)
                    .getSingleResult();
        }
    }

    @Override
    public List<Report> findBySeen(boolean seen) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT r FROM Report r WHERE r.seen = :seen", Report.class)
                    .setParameter("seen", seen)
                    .getResultList();
        }
    }

    @Override
    public List<Report> findByType(ReportType type) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT r FROM Report r WHERE r.type = :type", Report.class)
                    .setParameter("type", type)
                    .getResultList();
        }
    }

    @Override
    public void markAsSeen(Long reportId) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Report report = em.find(Report.class, reportId);
            if (report != null) {
                report.setSeen(true);
                em.merge(report);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to mark report as seen", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Report> findReportsWithFilters(String type, String status, String targetType, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT r FROM Report r WHERE 1=1");

            if (type != null && !type.trim().isEmpty()) {
                jpql.append(" AND r.type = :type");
            }
            if (status != null && !status.trim().isEmpty()) {
                jpql.append(" AND r.status = :status");
            }
            if (targetType != null && !targetType.trim().isEmpty()) {
                jpql.append(" AND r.targetType = :targetType");
            }

            jpql.append(" ORDER BY r.createdAt DESC");

            TypedQuery<Report> query = em.createQuery(jpql.toString(), Report.class);

            if (type != null && !type.trim().isEmpty()) {
                query.setParameter("type", type);
            }
            if (status != null && !status.trim().isEmpty()) {
                query.setParameter("status", status);
            }
            if (targetType != null && !targetType.trim().isEmpty()) {
                query.setParameter("targetType", targetType);
            }

            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int countReportsWithFilters(String type, String status, String targetType) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(r) FROM Report r WHERE 1=1");

            if (type != null && !type.trim().isEmpty()) {
                jpql.append(" AND r.type = :type");
            }
            if (status != null && !status.trim().isEmpty()) {
                jpql.append(" AND r.status = :status");
            }
            if (targetType != null && !targetType.trim().isEmpty()) {
                jpql.append(" AND r.targetType = :targetType");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

            if (type != null && !type.trim().isEmpty()) {
                query.setParameter("type", type);
            }
            if (status != null && !status.trim().isEmpty()) {
                query.setParameter("status", status);
            }
            if (targetType != null && !targetType.trim().isEmpty()) {
                query.setParameter("targetType", targetType);
            }

            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Report> findRecentReports(int limit) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM Report r ORDER BY r.createdAt DESC";
            TypedQuery<Report> query = em.createQuery(jpql, Report.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int countPendingReports() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(r) FROM Report r WHERE r.status = 'PENDING'";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Report> findByStatus(String status) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT r FROM Report r WHERE r.status = :status ORDER BY r.createdAt DESC";
            TypedQuery<Report> query = em.createQuery(jpql, Report.class);
            query.setParameter("status", status);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
