package org.elementcraft.dailyQuests.db;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static EntityManagerFactory emf;

    static {
        init();
    }

    private static void init() {
        if (emf != null && emf.isOpen()) {
            return;
        }

        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(HibernateUtil.class.getClassLoader());

            System.out.println("[Hibernate] Initializing EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("quest_persistence_unit");
            System.out.println("[Hibernate] EntityManagerFactory initialized successfully.");

        } catch (Throwable ex) {
            System.err.println("[Hibernate] Initial EntityManagerFactory creation failed: " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }


    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            init();
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("[Hibernate] EntityManagerFactory shut down.");
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
