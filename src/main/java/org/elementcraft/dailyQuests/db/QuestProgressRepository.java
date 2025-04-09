package org.elementcraft.dailyQuests.db;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public void delete(UUID playerId, String questId) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            QuestProgressId id = new QuestProgressId(playerId, questId);
            QuestProgressEntity entity = em.find(QuestProgressEntity.class, id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
