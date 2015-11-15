import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import exceptions.AlreadyFollowUserException;
import exceptions.UserAlreadyExistsException;
import models.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Server {
    final static String DB_NAME = "yafi";
    MongoClient client;
    final Morphia morphia;
    final Datastore datastore;

    public void register(User user) throws UserAlreadyExistsException {
        try {
            datastore.save(user);
        } catch (DuplicateKeyException e){
            throw new UserAlreadyExistsException(user.getUsername());
        }
    }

    public void friend(String current,String target){
        //NO-OP
    }

    public void follow(String follower,String followed) throws AlreadyFollowUserException {
        Follower obj = new Follower();
        obj.setUsername(followed);
        obj.setFollower(follower);
        obj.setSince(new Date());
        try {
            datastore.save(obj);
        } catch (DuplicateKeyException e){
            throw new AlreadyFollowUserException(followed);
        }

    }

    public void tweet (String username, String body) {
        Tweet tweet = new Tweet();
        tweet.setUsername(username);
        tweet.setBody(body);
        tweet.setTime(System.currentTimeMillis());

        Userline userline = new Userline();
        userline.setUsername(username);
        userline.setTweet(tweet);

        datastore.save(userline);

        List<Follower> followers = getFollowers(username);

        for(Follower follower: followers){
            Timeline timeline = new Timeline();
            timeline.setUsername(follower.getFollower());
            timeline.setTweet(tweet);
            datastore.save(timeline);
        }

    }

    public List<Follower> getFollowers(String username)  {
        return datastore.createQuery(Follower.class).field("username").equal(username).asList();

    }

    public List<Userline> getUserline(String username)  {
        return datastore.createQuery(Userline.class)
                .field("username").equal(username)
                .order("-tweet.time").asList();
    }

    public List<Timeline> getTimeline(String username) {
        return datastore.createQuery(Timeline.class)
                .field("username").equal(username)
                .order("-tweet.time").asList();
    }

    public boolean login(String username, String password){
        User user = datastore.createQuery(User.class)
                .field("username").equal(username)
                .field("password").equal(password)
                .get();
        return (user != null);
    }

    public User getUser(String username,String password){
        return datastore.createQuery(User.class)
                .field("username").equal(username)
                .field("password").equal(password)
                .get();
    }

    public Server(){
//        client = new MongoClient(
//                Arrays.asList(
//                        new ServerAddress("167.205.35.19"),
//                        new ServerAddress("167.205.35.20"),
//                        new ServerAddress("167.205.35.21"),
//                        new ServerAddress("167.205.35.22")
//        ));
        client = new MongoClient("127.0.0.1");
        morphia = new Morphia();
        morphia.map(Follower.class);
        morphia.map(Timeline.class);
        morphia.map(Tweet.class);
        morphia.map(User.class);
        morphia.map(Userline.class);
        datastore = morphia.createDatastore(client,DB_NAME);
        datastore.ensureIndexes();

    }
}
