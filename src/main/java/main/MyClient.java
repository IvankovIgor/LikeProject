package main;

import services.LikeService;

public class MyClient implements Runnable {
    private LikeService likeService;
    private String playerId;

    public MyClient(LikeService likeService, String playerId) {
        this.likeService = likeService;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            likeService.like(playerId);
        }
    }
}
