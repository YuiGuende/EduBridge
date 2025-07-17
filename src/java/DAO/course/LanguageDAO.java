/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.course;

import DAO.GenericDAO;
import DAO.course.ILanguageDAO;
import model.course.Language;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class LanguageDAO extends GenericDAO<Language> implements ILanguageDAO {

    public LanguageDAO(Class<Language> entityClass) {
        super(entityClass);
    }

    // Create
//    public Language save(Language language) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            if (language.getId() == null) {
//                em.persist(language);
//            } else {
//                language = em.merge(language);
//            }
//            em.getTransaction().commit();
//            return language;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error saving language: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }

    // Read
//    @Override
//    public Optional<Language> findLanguageById(Long id) {
//        EntityManager em = getEntityManager();
//
//        Language language = em.find(Language.class, id);
//        return Optional.ofNullable(language);
//
//    }

//    public List<Language> findAll() {
//        EntityManager em = getEntityManager();
//        try {
//            System.out.println("Finding all languages...");
//            TypedQuery<Language> query = em.createQuery(
//                    "SELECT l FROM Language l ORDER BY l.name",
//                    Language.class);
//            List<Language> languages = query.getResultList();
//            System.out.println("Found " + languages.size() + " languages");
//            return languages;
//        } catch (Exception e) {
//            System.err.println("Error finding all languages: " + e.getMessage());
//            e.printStackTrace();
//            throw new RuntimeException("Error finding all languages: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
    @Override
    public Optional<Language> findByCode(String code) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Language> query = em.createQuery(
                    "SELECT l FROM Language l WHERE l.code = :code",
                    Language.class);
            query.setParameter("code", code);
            List<Language> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            System.err.println("Error finding language by code: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Language> findByName(String name) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Language> query = em.createQuery(
                    "SELECT l FROM Language l WHERE l.name = :name",
                    Language.class);
            query.setParameter("name", name);
            List<Language> results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        } catch (Exception e) {
            System.err.println("Error finding language by name: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Language> findByNameContaining(String name) {
        try (EntityManager em = getEntityManager();) {
            TypedQuery<Language> query = em.createQuery(
                    "SELECT l FROM Language l WHERE LOWER(l.name) LIKE LOWER(:name) ORDER BY l.name",
                    Language.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error finding languages by name containing: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list instead of null
        }
    }

//    // Update
//    public Language update(Language language) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Language updatedLanguage = em.merge(language);
//            em.getTransaction().commit();
//            return updatedLanguage;
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error updating language: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
    // Delete
//    public void delete(Language language) {
//        EntityTransaction tx = null;
//        try (EntityManager em = getEntityManager();) {
//            tx = em.getTransaction();
//            tx.begin();
//            Language managedLanguage = em.merge(language);
//            em.remove(managedLanguage);
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null && tx.isActive()) {
//                tx.rollback();
//            }
//            throw new RuntimeException("Error deleting language: " + e.getMessage(), e);
//        }
//    }

//    public void deleteById(Long id) {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            Language language = em.find(Language.class, id);
//            if (language != null) {
//                em.remove(language);
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Error deleting language by ID: " + e.getMessage(), e);
//        } finally {
//            em.close();
//        }
//    }
    // Utility methods
//    public long count() {
//        try (EntityManager em = getEntityManager();) {
//            TypedQuery<Long> query = em.createQuery(
//                    "SELECT COUNT(l) FROM Language l", Long.class);
//            return query.getSingleResult();
//        }
//    }

//    public boolean exists(Long id) {
//        return findLanguageById(id).isPresent();
//    }

    public boolean existsByCode(String code) {
        return findByCode(code).isPresent();
    }

    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }
    
}
