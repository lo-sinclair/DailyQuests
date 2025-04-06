package org.elementcraft.dailyQuests.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.net.URL;

public class HibernateUtil {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            URL url = HibernateUtil.class.getClassLoader().getResource("META-INF/persistence.xml");
            System.out.println("Persistence.xml path: " + (url != null ? url.toString() : "NOT FOUND"));
            entityManagerFactory = Persistence.createEntityManagerFactory("quest_persistence_unit");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
