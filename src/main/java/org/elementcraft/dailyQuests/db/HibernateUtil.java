package org.elementcraft.dailyQuests.db;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static final EntityManagerFactory emf;

    static {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(HibernateUtil.class.getClassLoader());
            emf = Persistence.createEntityManagerFactory("quest_persistence_unit");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
        finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null) {
            emf.close();
        }
    }
}

/*
ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
try {
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

entityManagerFactory = Persistence.createEntityManagerFactory("quest_persistence_unit");
} finally {
        Thread.currentThread().setContextClassLoader(originalClassLoader); // Восстанавливаем!
}*/
