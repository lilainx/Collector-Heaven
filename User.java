package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

// Represents the user's collection profile, a list of collections
public class User implements Writable {

    private String username;
    private List<Collection> collections = new ArrayList<>();
    
    // EFFECTS: contructs a user profile with a default username
    //          and empty list of collections
    public User(String username) {
        this.username = username;
        this.collections = new ArrayList<>();
        
    }

    public List<Collection> getCollections() {
        return collections;
    }

    // MODIFIES: this
    // EFFECTS: adds a collection to the user's list of collections 
    public void addCollection(Collection collection) {
        collections.add(collection);
        EventLog.getInstance().logEvent(new Event(collection.getCollectionName() + " added to user profile."));
    }

    // REQUIRES: num of collections >= 1
    // MODIFIES: this
    // EFFECTS: removes specified collection from user profile
    public void removeCollection(Collection collection) {
        collections.remove(collection);
        EventLog.getInstance().logEvent(new Event(collection.getCollectionName() + " removed from user profile."));
    }

    // EFFECTS: if a collection with String name exists, 
    //          finds and returns that collection
    //          Otherwise, returns null
    public Collection getCollectionByName(String name) {
        for (Collection collection : collections) {
            if (collection.getCollectionName().equalsIgnoreCase(name)) {
                return collection;
            }
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        EventLog.getInstance().logEvent(new Event("Username changed to " + username));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("collections", collectionsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray collectionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Collection c : collections) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
