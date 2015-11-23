# MongoDB Tweets

Simple Twitter Application with MongoDB

### Members

- 13512014 Muhammad Yafi
- 13512066 Calvin Sadewa

### Prerequisites

1. Install Java JDK 1.8
2. Install Gradle, add it to your PATH environment variables.
3. Ensure gradle can works by typing `gradle` in your command prompt.

### How To Build

1. Type `gradle client`
2. Run at build/libs: `java -jar client-1.0.jar`


### Available Commands

`register [username] [password]` : register the username and password to database

`login [username] [password]` : login with the given username and password

`follow [username]` : current user will follow [username]

`userline [username]` : get userline of [username]

`timeline [username]` : get timeline of [username]

`tweet [body]` : do tweet

### Implementasi query

Basis data menggunakan library Morphia mapper dengan representasi model ada pada package `models.*`. Morphia
digunakan untuk memudahkan mapping antara Mongo Document dan Java Object.

Indeks ditempatkan pada masing-masing kelas model dengan annotation. Contoh: kelas User adalah
representasi dari collection "users" dengan indeks `username` yang unique.

Implementasi query server ada di kelas `Server` dengan perintah antara lain:

`datastore`: instance dari database (sebagai pengganti MongoDatabase)


- `register(User user)`
    - Dengan menginsert data ke collection `users` melalui fungsi `datastore.save(User)`. Jika username sudah ada dalam basis data maka method ini akan mengeluarkan exception.

- `follow(String follower,String followed)`
    - Menginsert data ke collection `followers` melalui fungsi `datastore.save(Follower)` Jika follower sudah mengikuti pengguna maka method ini akan mengeluarkan exception.

- `tweet (String username, String body)`
    - Menginsert data ke collection `timelines` dari followernya `username` dan collection `userlines`.

- `getUserline(String username)`
    - Melakukan query ke collection `userline` dengan perintah
    `datastore.createQuery(Userline.class).field("username").equal(username).asList()`

- `getTimeline(String username)` 
    - Melakukan query ke collection `timeline` dengan perintah
    `datastore.createQuery(Timeline.class).field("username").equal(username).asList();`
    
- `login(String username, String password)`
    - Melakukan query ke collection `users` dengan perintah
    ```
    datastore.createQuery(User.class)
                     .field("username").equal(username)
                     .field("password").equal(password)
                     .get();
    ```
    
    Lalu dicek apakah hasilnya null atau tidak.