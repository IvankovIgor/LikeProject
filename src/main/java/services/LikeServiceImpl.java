package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.lang.invoke.MethodHandles;

class LikeServiceImpl implements LikeService {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private DatabaseService databaseService;

    LikeServiceImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void like(String playerId) {
        try (Session session = databaseService.getSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("UPDATE Player p SET p.likes = p.likes + 1 WHERE p.playerId = :playerId");
                query.setParameter("playerId", playerId);
                int success = query.executeUpdate();
                if (success > 0)
                    tx.commit();
                else
                    throw new Exception();
            }
            catch (Exception e) {
                if (tx != null)
                    tx.rollback();
                logger.warn("like inside fail");
                throw e;
            }
        } catch (Exception e) {
            logger.warn("like outside fail");
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
                logger.warn("getLikes inside fail");
                throw e;
            }
        } catch (Exception e) {
            logger.warn("getLikes outside fail");
        }
        return likes;
    }
}
