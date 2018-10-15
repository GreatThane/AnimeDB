package org.thane.AnimeDB.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WatchListEntry {

    private Integer status;
    private Integer score;
    private String tags;
    private Byte is_rewatching;
    private Integer num_watched_episodes;
    private String anime_title;
    private Integer anime_num_episodes;
    private Integer anime_airing_status;
    private Integer anime_id;
    private Boolean has_episode_video;
    private Boolean has_promotion_video;
    private Boolean has_video;
    private String video_url;
    private String anime_url;
    private String anime_image_path;
    private Boolean is_added_to_list;
    private String anime_media_type_string;
    private String anime_mpaa_rating_string;
    private String anime_start_date_string;
    private String anime_end_date_string;
    private String storage_string;
    private String priority_string;

    public WatchListEntry(String name, int score) {
        this.anime_title = name;
        this.score = score;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public String getName() {
        return anime_title;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WatchListEntry
                && anime_title.equalsIgnoreCase(
                ((WatchListEntry) o).anime_title);
    }

    public Integer getId() {
        return anime_id;
    }

    public String getType() {
        return anime_media_type_string;
    }
}

