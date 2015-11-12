package models;

import org.bson.types.ObjectId;

import java.util.Date;

public class Follower {
    private ObjectId _id;
    private String username;
    private String follower;
    private Date since;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }
}
