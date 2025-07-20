package DAO.user;

import DAO.GenericDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import model.user.Instructor;
import model.user.User;
import model.user.UserStatus;

public class InstructorDAOImpl extends GenericDAO<Instructor> implements IInstructorDAO {

    private final IUserDAO userDAO = new UserDAOImpl(User.class);

    public InstructorDAOImpl(Class<Instructor> entityClass) {
        super(entityClass);
    }

//    @Override
//    public User findById(Long id) {
//        User user = super.findById(id);
//        return user.filter(u -> "INSTRUCTOR".equals(u.getRole()));
//    }
    @Override
    public Instructor save(Instructor instructor) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            // Ensure the associated User is managed or persisted first
            User user = instructor.getUser();
            if (user != null) {
                if (user.getId() == null) { // New user
                    em.persist(user);
                    em.flush(); // Ensure ID is generated before setting it to instructor
                } else { // Existing user
                    user = em.merge(user);
                }
                instructor.setUser(user); // Set the managed user back to instructor
                instructor.setId(user.getId()); // Set instructor ID to user ID due to @MapsId
            }

            if (instructor.getId() == null || em.find(Instructor.class, instructor.getId()) == null) {
                em.persist(instructor);
            } else {
                instructor = em.merge(instructor);
            }

            em.getTransaction().commit();
            return instructor;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error saving instructor: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Instructor> findInstructorsWithFilters(String name, String email, String specialization, int offset, int limit) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT i FROM Instructor i JOIN i.user u WHERE 1=1");

            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                jpql.append(" AND i.specialization LIKE :specialization");
            }

            jpql.append(" ORDER BY u.createdAt DESC");

            TypedQuery<Instructor> query = em.createQuery(jpql.toString(), Instructor.class);

            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                query.setParameter("specialization", "%" + specialization + "%");
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
    public int countInstructorsWithFilters(String name, String email, String specialization) {
        try (EntityManager em = getEntityManager()) {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE 1=1");

            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                jpql.append(" AND i.specialization LIKE :specialization");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            if (specialization != null && !specialization.trim().isEmpty()) {
                query.setParameter("specialization", "%" + specialization + "%");
            }

            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int countActiveInstructors() {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE u.role = 'INSTRUCTOR'";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Instructor> findBySpecialization(String specialization) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT i FROM Instructor i WHERE i.specialization = :specialization";
            TypedQuery<Instructor> query = em.createQuery(jpql, Instructor.class);
            query.setParameter("specialization", specialization);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Instructor> findInstructorsWithPagination(int page, int size) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Instructor> query = em.createQuery(
                    "SELECT i FROM Instructor i JOIN FETCH i.user u WHERE u.role = 'INSTRUCTOR' ORDER BY u.createdAt DESC", Instructor.class);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countInstructors() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE u.role = 'INSTRUCTOR'", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Instructor findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT i FROM Instructor i LEFT JOIN FETCH i.coursesCreated WHERE i.id = :id", Instructor.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Instructor> findAllInstructors(UserStatus status) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Instructor> query = em.createQuery(
                    "SELECT i FROM Instructor i JOIN FETCH i.user u WHERE u.status = :status", Instructor.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void updateInstructorStatus(Long id, UserStatus status) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Instructor instructor = em.find(Instructor.class, id);
            if (instructor != null && instructor.getUser() != null) {
                userDAO.updateStatus(instructor.getUser().getId(), status); // Cập nhật trạng thái User
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Instructor> findInstructorsWithPagination(int page, int size, UserStatus status) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Instructor> query;

            if (status == UserStatus.ALL) {
                // Truy vấn lấy tất cả instructors, không lọc theo status
                query = em.createQuery(
                        "SELECT i FROM Instructor i JOIN FETCH i.user u ORDER BY i.id DESC",
                        Instructor.class
                );
            } else {
                // Truy vấn có lọc theo status
                query = em.createQuery(
                        "SELECT i FROM Instructor i JOIN FETCH i.user u WHERE u.status = :status ORDER BY i.id DESC",
                        Instructor.class
                );
                query.setParameter("status", status);
            }

            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countInstructors(UserStatus status) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE u.status = :status", Long.class);
            query.setParameter("status", status);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Instructor> findInstructorsWithFilters(String name, String email, int offset, int limit, UserStatus status) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT i FROM Instructor i JOIN i.user u WHERE u.status = :status");

            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }

            jpql.append(" ORDER BY u.createdAt DESC");

            TypedQuery<Instructor> query = em.createQuery(jpql.toString(), Instructor.class);

            query.setParameter("status", status);
            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }

            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public int countInstructorsWithFilters(String name, String email, UserStatus status) {
        EntityManager em = getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(i) FROM Instructor i JOIN i.user u WHERE u.status = :status");

            if (name != null && !name.trim().isEmpty()) {
                jpql.append(" AND u.fullname LIKE :name");
            }
            if (email != null && !email.trim().isEmpty()) {
                jpql.append(" AND u.email LIKE :email");
            }

            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

            query.setParameter("status", status);
            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }

            return query.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }
}
