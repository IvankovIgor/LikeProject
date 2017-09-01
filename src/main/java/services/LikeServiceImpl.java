package services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

class LikeServiceImpl implements LikeService {
    private DatabaseService databaseService;

    LikeServiceImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void like(String playerId) {
        try (Session session = databaseService.getSession()) {
            try {
                long likes = getLikes(playerId);
                updateLikes(session, playerId, likes + 1);
            } catch (NoResultException e) {
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public long getLikes(String playerId) {
        long likes = 0;
        try (Session session = databaseService.getSession()) {
            try {
                Query<Long> query = session.createQuery("SELECT p.likes FROM db.Player p WHERE p.playerId = :playerId", Long.class);
                query.setParameter("playerId", playerId);
                likes = query.getSingleResult();
            } catch (NoResultException e) {
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return likes;
    }

    private void updateLikes(Session session, String playerId, long likes) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("UPDATE Player p SET p.likes = :likes WHERE p.playerId = :playerId");
            query.setParameter("playerId", playerId);
            query.setParameter("likes", likes);
            query.executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null)
                tx.rollback();
        }
    }
}
