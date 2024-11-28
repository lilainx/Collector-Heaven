package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

    private User user;
    private Collection legoCollection;
    private Collection pokemonCollection;
    
    @BeforeEach
    void runBefore() {
        user = new User("Elaine");
        legoCollection = new Collection("Lego Collection");
        pokemonCollection = new Collection("Pokemon Collection");
    }

    @Test
    void testConstructor() {
        assertEquals("Elaine", user.getUsername()); 
    }

    @Test
    void testSetUsername() {
        user.setUsername("li.lainx");
        assertEquals("li.lainx", user.getUsername());
    }

    @Test
    void testAddCollection() {
        Collection legoCollection = new Collection("Lego Collection");
        user.addCollection(legoCollection);
        assertEquals(1, user.getCollections().size());
        assertTrue(user.getCollections().contains(legoCollection));
    }

    @Test
    void testRemoveCollection() {
        user.addCollection(legoCollection);
        user.addCollection(pokemonCollection);
        assertEquals(2, user.getCollections().size());
        user.removeCollection(legoCollection);
        assertEquals(1, user.getCollections().size());
        user.removeCollection(pokemonCollection);
        assertEquals(0, user.getCollections().size());
    }

    @Test
    void testGetCollectionByName() {
        user.addCollection(legoCollection);
        assertEquals(legoCollection, user.getCollectionByName("Lego Collection"));
        assertEquals(null, user.getCollectionByName("Just Lego boohoo"));
    }

    @Test
    void testToJson() {
        user.addCollection(legoCollection);
        JSONObject json = user.toJson();
        assertEquals("Elaine", json.getString("username"));
        JSONArray collections =  json.getJSONArray("collections");
        assertEquals(1, collections.length());
    }
}
