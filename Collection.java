package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;

// Represents a list of items of the same collection
public class Collection implements Writable {

    private String name;
    private List<Item> items;
    
    // EFFECTS: constructs an empty list of items, with given name
    public Collection(String collectionName) {
        name = collectionName;
        this.items = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECT: adds an item to the collection
    public void addItem(Item item) {
        items.add(item);
        EventLog.getInstance().logEvent(new Event(item.getItemName() + " was added to " + name + " collection."));
    }

    // REQUIRES: num of items >= 1
    // MODIFIES: this
    // EFFECT: removes an item from the collection
    public void removeItem(Item item) {
        items.remove(item);
        EventLog.getInstance().logEvent(new Event(item.getItemName() + " was removed from " +  name +  " collection."));
    }

    public String getCollectionName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    // EFFECTS: if a item with String name exists, 
    //          finds and returns that item
    //          Otherwise, returns null
    public Item getItemByName(String name) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: returns things in this collection as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item t : items) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
