/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.course;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.course.CourseStatus;
import model.course.Tag;
import model.course.TagType;

/**
 *
 * @author DELL
 */
public class TagDAOImpl extends GenericDAO<Tag> implements ITagDAO {

    public TagDAOImpl(Class<Tag> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Tag> findTagsByType(TagType type) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT DISTINCT t FROM Tag t JOIN t.courses c WHERE t.type = :type AND c.status = :status";
            TypedQuery<Tag> query = em.createQuery(jpql, Tag.class);
            query.setParameter("type", type);
            query.setParameter("status", CourseStatus.PUBLIC);
            return query.getResultList();
        }
    }

    @Override
    public Tag findTagByNameAndType(String name, TagType type) {
        try (EntityManager em = getEntityManager()) {
            String jpql = "SELECT t FROM Tag t WHERE t.name = :name AND t.type = :type";
            TypedQuery<Tag> query = em.createQuery(jpql, Tag.class);
            query.setParameter("name", name);
            query.setParameter("type", type);
            List<Tag> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        }
    }

}
