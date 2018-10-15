package org.thane.AnimeDB.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_anime")
public class UserAnime {

    private int score;
    private Instant lastUpdated;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private int userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "anime_id")
    private int animeId;

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getAnimeId() {
        return animeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
