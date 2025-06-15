/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author LEGION
 */
public class JpaConfig {
    public static EntityManager getEntityManager(){
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("coursePU");
         return emf.createEntityManager();
    }
}
