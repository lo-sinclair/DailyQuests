package org.elementcraft.dailyQuests.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class QuestProgressRepository{
    private final EntityManager em;

    public QuestProgressRepository(EntityManager em) {
        this.em = em;
    }

    public void save(QuestProgressEntity progress) {
        EntityTransaction tx = em.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            em.persist(progress);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public List<QuestProgressEntity> loadAll() {
        return em.createQuery("FROM QuestProgressEntity", QuestProgressEntity.class).getResultList();
    }

    public List<QuestProgressEntity> findByPlayerId(UUID playerId) {
        return em.createQuery("FROM QuestProgressEntity WHERE playerId = :playerId", QuestProgressEntity.class)
                .setParameter("playerId", playerId)
                .getResultList();
    }

    public void delete(QuestProgressEntity progress) {
        EntityTransaction tx = em.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            em.remove(progress);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
