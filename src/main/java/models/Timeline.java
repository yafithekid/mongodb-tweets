package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("timelines")
@Indexes(
    @Index(fields = {@Field("tweet.time")})
)
public class Timeline {
    @Id
    private ObjectId id;
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
