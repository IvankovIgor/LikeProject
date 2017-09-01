package db;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @Column(name = "playerId", unique = true)
    private String playerId;

    @Column(name = "likes")
    private long likes;

    public Player(String playerId) {
        this.playerId = playerId;
        this.likes = 0;
    }

    String getPlayerId() {
        return playerId;
    }

    void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    long getLikes() {
        return likes;
    }

    void setLikes(long likes) {
        this.likes = likes;
    }
}
