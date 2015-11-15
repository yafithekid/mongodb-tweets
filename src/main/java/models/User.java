package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("users")
@Indexes(
    @Index(fields = {@Field("username")},options = @IndexOptions(unique=true))
)
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
