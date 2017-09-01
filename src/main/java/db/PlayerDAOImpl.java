package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.DatabaseService;

import java.lang.invoke.MethodHandles;

public class PlayerDAOImpl implements PlayerDAO {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private DatabaseService databaseService;

    public PlayerDAOImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void save(Player player) {
        try (Session session = databaseService.getSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(player);
                tx.commit();
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
}
