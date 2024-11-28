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

public class TestJsonReader {
    @BeforeEach
    void setup() throws IOException {
        Files.createDirectories(Paths.get("./data"));
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // Expected exception was thrown
            assertTrue(e instanceof IOException);
        }
    }

    @Test
    void testReaderEmptyUser() throws IOException {
        String jsonString = "{\"username\":\"testUser\",\"collections\":[]}";
        Files.write(Paths.get("./data/testReaderEmptyUser.json"), jsonString.getBytes());

        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("testUser", user.getUsername());
            assertTrue(user.getCollections().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() throws IOException {
        String jsonString = "{"
                + "\"username\":\"testUser\",\"collections\":[{"
                + "\"name\":\"testCollection\",\"items\":["
                + "{\"name\":\"item1\",\"description\":\"desc1\"},"
                + "{\"name\":\"item2\",\"description\":\"desc2\"}"
                + "]}]}";
        Files.write(Paths.get("./data/testReaderGeneralUser.json"), jsonString.getBytes());

        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            assertEquals("testUser", user.getUsername());
            List<Collection> collections = user.getCollections();
            assertEquals(1, collections.size());
            Collection collection = collections.get(0);
            assertEquals("testCollection", collection.getCollectionName());
            List<Item> items = collection.getItems();
            assertEquals(2, items.size());
            assertEquals("item1", items.get(0).getItemName());
            assertEquals("item2", items.get(1).getItemName());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
