/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.course;

import DAO.course.ILanguageDAO;
import model.course.Language;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class LanguageDAO implements ILanguageDAO {

    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("coursePU");
        } catch (Exception e) {
            System.err.println("Error creating EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Create
    public Language save(Language language) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (language.getId() == null) {
                em.persist(language);
            } else {
                language = em.merge(language);
            }
            em.getTransaction().commit();
            return language;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error saving language: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // Read
    @Override
    public Optional<Language> findById(Long id) {
        EntityManager em = getEntityManager();

        Language language = em.find(Language.class, id);
        return Optional.ofNullable(language);

    }

    @Override
    public List<Language> findAll() {
        EntityManager em = getEntityManager();
        try {
            System.out.println("Finding all languages...");
            TypedQuery<Language> query = em.createQuery(
                    "SELECT l FROM Language l ORDER BY l.name",
                    Language.class);
            List<Language> languages = query.getResultList();
            System.out.println("Found " + languages.size() + " languages");
            return languages;
        } catch (Exception e) {
            System.err.println("Error finding all languages: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error finding all languages: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Language> findByCode(String code) {
        EntityManager em = getEntityManager();
        try {
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
        } finally {
            em.close();
        }
    }

    public Optional<Language> findByName(String name) {
        EntityManager em = getEntityManager();
        try {
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
        } finally {
            em.close();
        }
    }

    public List<Language> findByNameContaining(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Language> query = em.createQuery(
                    "SELECT l FROM Language l WHERE LOWER(l.name) LIKE LOWER(:name) ORDER BY l.name",
                    Language.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error finding languages by name containing: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list instead of null
        } finally {
            em.close();
        }
    }

    // Update
    public Language update(Language language) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Language updatedLanguage = em.merge(language);
            em.getTransaction().commit();
            return updatedLanguage;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error updating language: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // Delete
    public void delete(Language language) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Language managedLanguage = em.merge(language);
            em.remove(managedLanguage);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting language: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void deleteById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Language language = em.find(Language.class, id);
            if (language != null) {
                em.remove(language);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting language by ID: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // Utility methods
    public long count() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(l) FROM Language l", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public boolean exists(Long id) {
        return findById(id).isPresent();
    }

    public boolean existsByCode(String code) {
        return findByCode(code).isPresent();
    }

    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    // Cleanup method
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
