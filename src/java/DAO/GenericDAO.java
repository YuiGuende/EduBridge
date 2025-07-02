/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author DELL
 * @param <T>
 */
public class GenericDAO<T> extends BaseDAO {

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<T> findAll() {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        }
    }

    public T findById(Long id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(entityClass, id);
        }
    }

    public T save(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager();) {
            tx = em.getTransaction();
            tx.begin();
            if (entityClass.getMethod("getId").invoke(entity) == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error saving " + entityClass.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    public void insert(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error inserting " + entityClass.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    public T update(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            T managedEntity = em.merge(entity);
            tx.commit();
            return managedEntity;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error updating " + entityClass.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    public void deleteById(int id) throws Exception {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager()) {
            tx = em.getTransaction();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                tx.begin();
                em.remove(em.merge(entity));
                tx.commit();
            } else {
                throw new IllegalArgumentException("Not found");
            }
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    public void delete(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = getEntityManager()) {
            tx = em.getTransaction();
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error deleting " + entityClass.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    public long count(T entity) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult();
        }
    }

    public Optional<T> findByIdReturnOptional(Long id) {
        try (EntityManager em = getEntityManager();) {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        }
    }

    public boolean exists(Long id) {
        return findByIdReturnOptional(id).isPresent();
    }
}
