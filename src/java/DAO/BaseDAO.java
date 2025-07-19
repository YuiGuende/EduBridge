/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;

/**
 *
 * @author DELL
 */
public abstract class BaseDAO {

    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EduBridgePU");

    public abstract EntityManager getEntityManager();
}
