package persistence;

import model.User;
import model.Collection;
import model.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter {
    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(Paths.get("./data"));
    }

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("testUser");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Expected exception thrown
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("testUser");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUser.json");
            user = reader.read();
            assertEquals("testUser", user.getUsername());
            assertTrue(user.getCollections().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have beenn thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("testUser");
            Collection collection = new Collection("testCollection");
            collection.addItem(new Item("item1", "desc1"));
            collection.addItem(new Item("item2", "desc2"));
            user.addCollection(collection);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("testUser", user.getUsername());
            List<Collection> collections = user.getCollections();
            assertEquals(1, collections.size());
            collection = collections.get(0);
            assertEquals("testCollection", collection.getCollectionName());
            List<Item> items = collection.getItems();
            assertEquals(2, items.size());
            assertEquals("item1", items.get(0).getItemName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
