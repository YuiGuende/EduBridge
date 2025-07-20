package DAO.report;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.notification.Report;
import model.notification.ReportTargetType;
import model.notification.ReportType;
import model.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportDAOImpl extends GenericDAO<Report> implements IReportDAO {

    public ReportDAOImpl() {
        super(Report.class);
    }

    @Override
    public Report save(Report report) {
        return super.save(report);
    }

    @Override
    public Report update(Report report) {
        return super.update(report);
    }

    @Override
    public void delete(Report report) {
        super.delete(report);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public List<Report> findAll() {
        return super.findAll();
    }

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public List<Report> findByReporter(User user) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Report> query = em.createQuery(
                    "SELECT r FROM Report r WHERE r.user = :user ORDER BY r.createdAt DESC",
                    Report.class
            );
            query.setParameter("user", user);
            return query.getResultList();
        }
    }

    @Override
    public List<Report> findByReportedItem(Long reportedItemId, ReportTargetType reportedItemType) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Report> query = em.createQuery(
                    "SELECT r FROM Report r WHERE r.reportedItemId = :reportedItemId AND r.reportedItemType = :reportedItemType ORDER BY r.createdAt DESC",
                    Report.class
            );
            query.setParameter("reportedItemId", reportedItemId);
            query.setParameter("reportedItemType", reportedItemType);
            return query.getResultList();
        }
    }

    @Override
    public long countByReportedItem(Long reportedItemId, ReportTargetType reportedItemType) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(
                    "SELECT COUNT(r) FROM Report r WHERE r.reportedItemId = :reportedItemId AND r.reportedItemType = :reportedItemType",
                    Long.class
            ).setParameter("reportedItemId", reportedItemId)
             .setParameter("reportedItemType", reportedItemType)
             .getSingleResult();
        }
    }

    @Override
    public List<Report> findBySeen(boolean seen) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT r FROM Report r WHERE r.seen = :seen ORDER BY r.createdAt DESC", Report.class)
                    .setParameter("seen", seen)
                    .getResultList();
        }
    }

    @Override
    public List<Report> findByType(ReportType type) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT r FROM Report r WHERE r.type = :type ORDER BY r.createdAt DESC", Report.class)
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
    public List<Report> findReportsWithFilters(ReportType type, Report.ReportStatus status, ReportTargetType targetType, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Report> cq = cb.createQuery(Report.class);
            Root<Report> report = cq.from(Report.class);
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(report.get("type"), type));
            }
            if (status != null) {
                predicates.add(cb.equal(report.get("status"), status));
            }
            if (targetType != null) {
                predicates.add(cb.equal(report.get("reportedItemType"), targetType));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.desc(report.get("createdAt")));

            TypedQuery<Report> query = em.createQuery(cq);
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public int countReportsWithFilters(ReportType type, Report.ReportStatus status, ReportTargetType targetType) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Report> report = cq.from(Report.class);
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(report.get("type"), type));
            }
            if (status != null) {
                predicates.add(cb.equal(report.get("status"), status));
            }
            if (targetType != null) {
                predicates.add(cb.equal(report.get("reportedItemType"), targetType));
            }

            cq.select(cb.count(report));
            cq.where(predicates.toArray(new Predicate[0]));

            return em.createQuery(cq).getSingleResult().intValue();
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
            String jpql = "SELECT COUNT(r) FROM Report r WHERE r.status = :status";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("status", Report.ReportStatus.PENDING);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Report> findByStatus(Report.ReportStatus status) {
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