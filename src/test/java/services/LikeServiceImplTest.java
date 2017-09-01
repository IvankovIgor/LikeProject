package services;

import db.Player;
import db.PlayerDAO;
import db.PlayerDAOImpl;
import main.MyClient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LikeServiceImplTest {
    private DatabaseService databaseService;
    private LikeService likeService;
    private PlayerDAO playerDAO;

    @Before
    public void setUp() {
        databaseService = new DatabaseServiceImpl();
        likeService = new LikeServiceImpl(databaseService);
        playerDAO = new PlayerDAOImpl(databaseService);
    }

    @Test
    public void testLikeService() throws Exception {
        playerDAO.save(new Player("alesha"));
        playerDAO.save(new Player("petya"));
        likeService.like("alesha");
        likeService.like("alesha");
        likeService.like("petya");
        likeService.like("alesha");
        likeService.like("pasha");
        likeService.getLikes("dasha");
        assertEquals(3, likeService.getLikes("alesha"));
        assertEquals(1, likeService.getLikes("petya"));
    }

    @Test
    public void multiTest() throws Exception {
        playerDAO.save(new Player("al"));
        Thread thread1 = new Thread(new MyClient(likeService, "al"));
        LikeService l2 = new LikeServiceImpl(databaseService);
        Thread thread2 = new Thread(new MyClient(l2, "al"));
        Thread thread3 = new Thread(new MyClient(l2, "al"));
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        assertEquals(3000, likeService.getLikes("al"));
    }
}