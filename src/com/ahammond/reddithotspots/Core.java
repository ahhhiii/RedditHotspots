package com.ahammond.reddithotspots;

import com.ahammond.reddithotspots.display.Display;
import com.ahammond.reddithotspots.redditapi.Reddit;
import com.ahammond.reddithotspots.redditapi.RedditPost;

import java.io.IOException;

public class Core {

    public Display display = new Display(this);
    public Reddit reddit = new Reddit(this);

    public void query(String subreddit) {
        alertRunning();
        RedditPost[] posts = getPosts(subreddit);

        if (posts == null) {
            return;
        }

        alertTabling();
    }

    private RedditPost[] getPosts(String subreddit) {
        RedditPost[] posts;
        try {
            posts = reddit.getTopPosts(100, subreddit);
        } catch (IOException e) {
            display.search.setText("Failed.");
            display.search.setEnabled(true);
            e.printStackTrace();
            return null;
        }

        return posts;
    }

    private void alertRunning() {
        display.search.setEnabled(false);
        display.alert("Querying reddit...");
    }

    private void alertTabling() {
        display.alert("Tabling posts...");
    }

}
