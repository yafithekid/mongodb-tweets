package models;

import models.Tweet;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("userlines")
@Indexes(
        @Index(fields = {@Field("tweet.time")})
)
public class Userline {
    @Id
    private ObjectId id;
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
