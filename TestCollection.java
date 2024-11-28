package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCollection {

    private Collection legoCollection;

    @BeforeEach
    void runBefore() {
        legoCollection = new Collection("Lego Collection");
    }

    @Test
    void testConstructor() {
        assertEquals("Lego Collection", legoCollection.getCollectionName());
    }

    @Test
    void testEditCollection() {
        Item lego = new Item("Speed Champion", "");
        Item lego1 = new Item("Technic", "");
        legoCollection.addItem(lego);
        legoCollection.addItem(lego1);
        assertEquals(2, legoCollection.getItems().size());
        assertTrue(legoCollection.getItems().contains(lego));
        assertTrue(legoCollection.getItems().contains(lego1));
        legoCollection.removeItem(lego);
        assertEquals(1, legoCollection.getItems().size());
        assertFalse(legoCollection.getItems().contains(lego));
    }

    @Test
    void getItemByName() {
        Item lego = new Item("Speed Champion", "");
        Item lego1 = new Item("Technic", "");
        legoCollection.addItem(lego);
        legoCollection.addItem(lego1);
        assertEquals(lego1, legoCollection.getItemByName("Technic"));
        assertEquals(null, legoCollection.getItemByName("Techieeeee"));
    }

    @Test
    void testToJson() {
        Item lego = new Item("Speed Champion", "");
        legoCollection.addItem(lego);
        JSONObject json = legoCollection.toJson();
        assertEquals("Lego Collection", json.getString("name"));
        JSONArray items = json.getJSONArray("items");
        assertEquals(1, items.length());

    }

}

