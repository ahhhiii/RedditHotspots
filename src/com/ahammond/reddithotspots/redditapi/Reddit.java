package com.ahammond.reddithotspots.redditapi;

import com.ahammond.reddithotspots.Core;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;

public class Reddit {

    private Core core;
    public Reddit(Core core) {
        this.core=core;
    }

    HashSet<Long> duplicationCheck;
    public ArrayList<RedditPost> getTopPosts(int count, String subreddit) throws IOException {
        duplicationCheck = new HashSet<>();
        ArrayList<RedditPost> list = getTopPosts(count, subreddit, "month");
        list.addAll(getTopPosts(count, subreddit, "year"));
        list.addAll(getTopPosts(count, subreddit, "all"));
        return list;
    }

    public ArrayList<RedditPost> getTopPosts(int count, String subreddit, String timeRange) throws IOException {
        System.out.println("Executing query on " + subreddit + " (count = " + count + ")");

        URL url = new URL("https://www.reddit.com/r/" + subreddit + "/top/.json?t=" + timeRange + "&limit=" + count);
        URLConnection request = url.openConnection();
        request.setRequestProperty("Content-Type", "application/json; utf-8");
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        JSONObject json = null;

        try {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = request.getInputStream().read()) != -1) {
                sb.append((char) cp);
            }

            String jsonText = sb.toString();
            json = new JSONObject(jsonText);
        } catch (Exception e) {
            core.display.search.setText("ERROR");
            e.printStackTrace();
        }

        if (json == null || json.keySet().size() == 0) {
            core.display.alert("Error. Invalid subreddit?");
            core.display.search.setEnabled(true);
            return null;
        }

        return toRedditPosts(json);
    }

    private ArrayList<RedditPost> toRedditPosts(JSONObject json) {
        JSONArray data = json.getJSONObject("data").getJSONArray("children");
        ArrayList<RedditPost> posts = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i).getJSONObject("data");

            RedditPost post = new RedditPost();
            try {
                post.title = obj.getString("title");
                post.subreddit = obj.getString("subreddit");
                post.thumbnailURL = obj.getString("thumbnail");
                post.imageURL = obj.getString("url_overridden_by_dest");
                post.postlink = "https://www.reddit.com" + obj.getString("permalink");
                post.creation = LocalDateTime.ofInstant(Instant.ofEpochSecond(obj.getLong("created")), ZoneId.systemDefault());
                post.score = obj.getInt("score");
            } catch (JSONException ignore) {}

            if (post.creation == null || duplicationCheck.contains(post.creation.toEpochSecond(ZoneOffset.UTC))) {
                continue;
            }

            duplicationCheck.add(post.creation.toEpochSecond(ZoneOffset.UTC));
            posts.add(post);
        }

        System.out.println("Collected " + data.length() + " posts.");
        return posts;
    }

}
