package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an item 
public class Item implements Writable {

    private String name;
    private String description;

    // REQUIRES: itemName is a non-zero length
    // EFFECTS: name of item is set to itemName;
    //          description of item is set to description (can be empty)
    public Item(String itemName, String description) {
        this.name = itemName;
        this.description = "";
    }

    // MODIFIES: this
    // EFFECTS: updates description to given input
    public void editDescription(String description) {
        this.description = description;
        EventLog.getInstance().logEvent(new Event("Description of " + name + " changed to " + description));
    }

    // MODIFIES: this
    // EFFECTS: updates name of item
    public void editItemName(String itemName) {
        this.name = itemName;
        EventLog.getInstance().logEvent(new Event("Item name edited to " + itemName));
    }

    public String getItemName() {
        return name;

    }

    public String getItemDescription() {
        return description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        return json;
    }

}


    




