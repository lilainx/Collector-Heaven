package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.Event;
import model.EventLog;
import model.User;

    // Represents a writer that writes JSON representation of userprofile to file
    // Adapted from Json sterilization demo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        try {
            writer = new PrintWriter(new File(destination));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unable to open file: " + destination);
        }
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of userprofile to file
    public void write(User u) {
        JSONObject json = u.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved to file."));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

