package db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import services.DatabaseService;

public class PlayerDAOImpl implements PlayerDAO {
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
            }
        }
    }
}
