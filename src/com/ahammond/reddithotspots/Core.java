package com.ahammond.reddithotspots;

import com.ahammond.reddithotspots.display.Display;
import com.ahammond.reddithotspots.display.ElementManager;
import com.ahammond.reddithotspots.display.ElementPanel;
import com.ahammond.reddithotspots.redditapi.Reddit;
import com.ahammond.reddithotspots.redditapi.RedditPost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Core {

    public Display display = new Display(this);
    public Reddit reddit = new Reddit(this);

    public void query(String subreddit) {
        display.search.setEnabled(false);
        display.alert("Querying reddit...");
        ArrayList<RedditPost> posts = getPosts(subreddit);

        if (posts == null) {
            return;
        }

        // Adding data
        display.alert("Tabling posts...");
        ArrayList<ArrayList<ElementPanel>> table = tableData(posts);
        display.contentPanel.setElementsAndShow(table);
    }

    private ArrayList<ArrayList<ElementPanel>> tableData(ArrayList<RedditPost> posts) {
        ArrayList<ArrayList<ElementPanel>> map = new ArrayList<>(); // day, hour, element

        for (int i = 0; i < 7; i++) {
            map.add(new ArrayList<>());
            for (int n = 0; n < 24; n++) {
                map.get(i).add(new ElementPanel());
                map.get(i).get(n).day = i;
                map.get(i).get(n).hour = n;
            }
        }

        for (RedditPost post : posts) {
            int dayOfWeek = post.creation.getDayOfWeek().getValue() - 1;
            int hourOfDay = post.creation.getHour();
            map.get(dayOfWeek).get(hourOfDay).posts.add(post);

            if (map.get(dayOfWeek).get(hourOfDay).posts.size() > ElementManager.HIGHEST_ELEMENT_COUNT) {
                ElementManager.HIGHEST_ELEMENT_COUNT = map.get(dayOfWeek).get(hourOfDay).posts.size();
            }
        }

        System.out.println("All posts tabled.");
        return map;
    }

    private ArrayList<RedditPost> getPosts(String subreddit) {
        ArrayList<RedditPost> posts;
        try {
            posts = reddit.getTopPosts(1000, subreddit);
        } catch (IOException e) {
            display.search.setText("Failed.");
            display.search.setEnabled(true);
            e.printStackTrace();
            return null;
        }

        return posts;
    }

}
