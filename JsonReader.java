package persistence;

import model.User;
import model.Collection;
import model.Event;
import model.EventLog;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


import org.json.*;

// Represents a reader that reads user profile from JSON data stored in file
// Adapted from Json sterilization demo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads User from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Profile loaded."));
        return parseUserProfile(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

      // EFFECTS: parses user profile from JSON object and returns it
    public User parseUserProfile(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        User user = new User(username);
        addCollections(user, jsonObject.getJSONArray("collections"));
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses collections from JSON array and adds them to user
    private void addCollections(User user, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextCollection = (JSONObject) json;
            Collection collection = parseCollection(nextCollection);
            user.addCollection(collection);
        }
    }


    // EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Collection collection = new Collection(name);
        addItems(collection, jsonObject.getJSONArray("items"));
        return collection;
    }

    // MODIFIES: collection
    // EFFECTS: parses items from JSON array and adds them to collection
    private void addItems(Collection collection, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(collection, nextItem);
        }
    }

    // MODIFIES: collection
    // EFFECTS: parses item from JSON object and adds it to collection
    private void addItem(Collection collection, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        Item item = new Item(name, description);
        collection.addItem(item);
    }
}
