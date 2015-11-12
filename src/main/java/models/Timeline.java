package models;

import org.bson.types.ObjectId;

public class Timeline {
    private ObjectId _id;
    private String username;
    private Tweet tweet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

}
