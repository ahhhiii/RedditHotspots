package com.ahammond.reddithotspots.redditapi;

import java.time.LocalDateTime;

public class RedditPost {

    public String title, subreddit, thumbnailURL, imageURL;
    public LocalDateTime creation;
    public int score;

    @Override
    public String toString() {
        return "RedditPost{" +
                "title='" + title + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", creation=" + creation +
                ", score=" + score +
                '}';
    }
}
