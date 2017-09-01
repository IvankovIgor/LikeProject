package services;

import db.Player;
import db.PlayerDAO;
import db.PlayerDAOImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LikeServiceImplTest {
    private LikeService likeService;
    private PlayerDAO playerDAO;

    @Before
    public void setUp() {
        DatabaseService databaseService = new DatabaseServiceImpl();
        likeService = new LikeServiceImpl(databaseService);
        playerDAO = new PlayerDAOImpl(databaseService);
    }

    @Test
    public void like() throws Exception {
    }

    @Test
    public void getLikes() throws Exception {
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

}