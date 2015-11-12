import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import models.*;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Server {
    final static String DB_NAME = "yafi";
    MongoClient client;
    MongoDatabase database;
    ObjectMapper objectMapper = new ObjectMapper();

    public void register(User user) throws IOException {
        String json = objectMapper.writeValueAsString(user);
        getUserCollection().insertOne(Document.parse(json));
    }

    public void friend(String current,String target){
        //NO-OP
    }

    public void follow(String follower,String followed) throws IOException {
        Follower obj = new Follower();
        obj.setUsername(followed);
        obj.setFollower(follower);
        obj.setSince(new Date());
        String json = objectMapper.writeValueAsString(obj);
        getFollowerCollection().insertOne(Document.parse(json));
    }

    public void tweet (String username, String body) throws IOException {
        Tweet tweet = new Tweet();
        tweet.setUsername(username);
        tweet.setBody(body);
        tweet.setTime(System.currentTimeMillis());

        Userline userline = new Userline();
        userline.setUsername(username);
        userline.setTweet(tweet);

        String objUserline = objectMapper.writeValueAsString(userline);
        getUserlineCollection().insertOne(Document.parse(objUserline));

        List<Follower> followers = getFollowers(username);

        for(Follower follower: followers){
            Timeline timeline = new Timeline();
            timeline.setUsername(follower.getFollower());
            timeline.setTweet(tweet);

            String objTimeline =  objectMapper.writeValueAsString(timeline);
            getTimelineCollection().insertOne(Document.parse(objTimeline));
        };

    }

    public List<Follower> getFollowers(String username) throws IOException {
        BasicDBObject dbObject = new BasicDBObject()
                .append("username",username);
        MongoCursor<Document> iterator = getFollowerCollection().find(dbObject).iterator();

        List<Follower> retval = new ArrayList<>();
        while (iterator.hasNext()){
            retval.add(objectMapper.readValue(iterator.next().toJson(),Follower.class));
        }
        return retval;
    }

    public List<Userline> getUserline(String username) throws IOException {
        BasicDBObject dbObject = new BasicDBObject()
                .append("username",username);
        BasicDBObject dbSort = new BasicDBObject()
                .append("tweet.time",-1);
        MongoCursor<Document> iterator = getUserlineCollection().find(dbObject).sort(dbSort).iterator();

        List<Userline> userline = new ArrayList<>();
        while(iterator.hasNext()){
            userline.add(objectMapper.readValue(iterator.next().toJson(),Userline.class));
        }
        return userline;
    }

    public List<Timeline> getTimeline(String username) throws IOException {
        BasicDBObject dbObject = new BasicDBObject()
                .append("username",username);
        BasicDBObject dbSort = new BasicDBObject()
                .append("tweet.time",-1);
        MongoCursor<Document> iterator = getTimelineCollection().find(dbObject).sort(dbSort).iterator();

        List<Timeline> timelines = new ArrayList<>();
        while(iterator.hasNext()){
            timelines.add(objectMapper.readValue(iterator.next().toJson(), Timeline.class));
        }
        return timelines;
    }

    public boolean login(User user){
        BasicDBObject dbObject = new BasicDBObject()
                .append("username",user.getUsername())
                .append("password",user.getPassword());
        Document first = getUserCollection().find(dbObject).first();
        if (first == null){
            return false;
        } else {
            return true;
        }
    }

    public MongoCollection<Document> getTweetCollection(){
        return this.database.getCollection("tweets");
    }

    public MongoCollection<Document> getUserCollection(){
        return this.database.getCollection("users");
    }

    public MongoCollection<Document> getUserlineCollection(){
        return this.database.getCollection("userline");
    }

    public MongoCollection<Document> getTimelineCollection(){
        return this.database.getCollection("timeline");
    }

    public MongoCollection<Document> getFollowerCollection(){
        return this.database.getCollection("followers");
    }

    public Server(){
        System.setProperty("java.net.preferIPv4Stack" , "true");
        client = new MongoClient(
                Arrays.asList(
                        new ServerAddress("127.0.0.1")
//                        new ServerAddress("167.205.35.20"),
//                        new ServerAddress("167.205.35.21"),
//                        new ServerAddress("167.205.35.22")
        ));
        database = client.getDatabase(DB_NAME);
        getUserCollection().createIndex(new BasicDBObject().append("username",1),new IndexOptions().unique(true));
        getFollowerCollection().createIndex(
                new BasicDBObject()
                        .append("username",1).append("follower",1),
                new IndexOptions().unique(true));
        getUserlineCollection().createIndex(
                new BasicDBObject().append("tweet.time",-1)
        );
        getTimelineCollection().createIndex(
                new BasicDBObject().append("tweet.time",-1)
        );
    }
}
