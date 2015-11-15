package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity("followers")
@Indexes(
    @Index(fields = {@Field("username"),@Field("follower")}, options = @IndexOptions(unique=true))
)
public class Follower {
    @Id
    private ObjectId id;
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
