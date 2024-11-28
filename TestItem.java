package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestItem {

    private Item lego;

    @BeforeEach
    void runBefore() {
        lego = new Item("Lego Aston Martin", "");
    }

    @Test
    void testConstructor() {
        assertEquals("Lego Aston Martin", lego.getItemName());
        assertEquals("", lego.getItemDescription());

    }

    @Test
    void testEditDescription() {
        lego.editDescription("Retired Lego product");
        assertEquals("Retired Lego product", lego.getItemDescription());
    }

    @Test
    void testEditItemName() {
        lego.editItemName("007 Aston Martin DB5");
        assertEquals("007 Aston Martin DB5", lego.getItemName());
    }

    @Test
    void testToJson() {
        JSONObject json = lego.toJson();
        assertEquals("Lego Aston Martin", json.getString("name"));
        assertEquals("", json.getString("description"));
    }


}
