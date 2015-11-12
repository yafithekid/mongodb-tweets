package models;

import models.Tweet;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Userline {
    private ObjectId _id;
    private String username;
    private Tweet tweet;
    private long time;




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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
