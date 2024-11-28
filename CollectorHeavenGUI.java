package ui;

import model.Collection;
import model.EventLog;
import model.Event;
import model.Item;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the graphical user interface for the Collector Heaven application.
// Allows users to manage collections and items, save/load user profiles, and interact with the UI.
public class CollectorHeavenGUI extends JFrame {
    private static final String JSON_STORE = "./data/user.json";
    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private DefaultListModel<String> collectionListModel;
    private DefaultListModel<String> itemListModel;
    private JList<String> collectionList;
    private JList<String> itemList;

    private ImagePanel imagePanel;

    // Constructs a CollectorHeavenGUI instance and initializes the components.
    public CollectorHeavenGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initComponents();
        loadProfile();
        setupWindowListener();
    }

    // Requires: The frame is initialized with default size and layout.
    // Modifies: Adds components to the frame.
    // Effects: Sets up the layout and adds action listeners to buttons.
    private void initComponents() {
        setTitle("Collector Heaven");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addTopPanel();
        addCenterPanel();
        addImagePanel();
    }

    // Requires: The frame is initialized with a BorderLayout.
    // Modifies: Adds the top panel and buttons to the frame.
    // Effects: Creates the top panel with buttons and adds action listeners.
    private void addTopPanel() {
        JPanel topPanel = new JPanel();
        JButton resetButton = new JButton("Reset Profile");
        JButton saveButton = new JButton("Save Profile");
        JButton createCollectionButton = new JButton("Create Collection");
        JButton createItemButton = new JButton("Create Item");
        JButton addDescriptionButton = new JButton("Add Description");
        JButton deleteItemButton = new JButton("Delete Item");

        topPanel.add(resetButton);
        topPanel.add(saveButton);
        topPanel.add(createCollectionButton);
        topPanel.add(createItemButton);
        topPanel.add(deleteItemButton);
        topPanel.add(addDescriptionButton);

        add(topPanel, BorderLayout.NORTH);

        resetButton.addActionListener(new LoadButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        createCollectionButton.addActionListener(new CreateCollectionButtonListener());
        createItemButton.addActionListener(new CreateItemButtonListener());
        deleteItemButton.addActionListener(new DeleteItemButtonListener());
        addDescriptionButton.addActionListener(new AddDescriptionButtonListener());
    }

    // Requires: The frame is initialized with a BorderLayout.
    // Modifies: Adds the center panel and list components to the frame.
    // Effects: Creates the center panel with collection and item lists and adds a selection listener.
    private void addCenterPanel() {
        collectionListModel = new DefaultListModel<>();
        collectionList = new JList<>(collectionListModel);
        JScrollPane collectionScrollPane = new JScrollPane(collectionList); // Ensure this is JScrollPane

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        JScrollPane itemScrollPane = new JScrollPane(itemList); // Ensure this is JScrollPane

        collectionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateItemList();
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(collectionScrollPane);
        centerPanel.add(itemScrollPane);

        add(centerPanel, BorderLayout.CENTER);
    }

    // Requires: The frame is initialized with a BorderLayout.
    // Modifies: Adds the image panel to the frame.
    // Effects: Creates and adds the image panel to the bottom of the frame.
    private void addImagePanel() {
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.SOUTH);
    }

    // Requires: The user confirms whether they want to load their profile.
    // Modifies: The user profile and collection list.
    // Effects: Loads the profile from the file if confirmed, or initializes a default user.
    private void loadProfile() {
        String message = "Do you want to load your user profile from file?";
        String title = "Load Profile";
        int choice = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                user = jsonReader.read();
                updateCollectionList();
                JOptionPane.showMessageDialog(this, "Loaded user profile from " + JSON_STORE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
                user = new User("default_user");
            }
        } else {
            user = new User("default_user");
        }
    }

    // Requires: The user's collections are up-to-date.
    // Modifies: The collection list model.
    // Effects: Clears and repopulates the collection list with the user's collections.
    private void updateCollectionList() {
        collectionListModel.clear();
        for (Collection collection : user.getCollections()) {
            collectionListModel.addElement(collection.getCollectionName());
        }
    }

    // Requires: A collection is selected in the collection list.
    // Modifies: The item list model.
    // Effects: Clears and repopulates the item list with items from the selected collection.
    private void updateItemList() {
        itemListModel.clear();
        populateItemList();
        addItemListMouseListener();
    }

    // Requires: A collection is selected in the collection list.
    // Modifies: The item list model.
    // Effects: Populates the item list with items from the selected collection.
    private void populateItemList() {
        String selectedCollectionName = collectionList.getSelectedValue();
        if (selectedCollectionName != null) {
            Collection collection = user.getCollectionByName(selectedCollectionName);
            if (collection != null) {
                for (Item item : collection.getItems()) {
                    itemListModel.addElement(item.getItemName());
                }
            }
        }
    }

    // Modifies: Adds a mouse listener to the item list.
    // Effects: Adds a mouse listener to display the description of an item when double-clicked.
    private void addItemListMouseListener() {
        itemList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-click detected
                    showItemDescription();
                }
            }
        });
    }

    // Effects: Shows a dialog with the description of the selected item.
    private void showItemDescription() {
        String selectedCollectionName = collectionList.getSelectedValue();
        String selectedItemName = itemList.getSelectedValue();
        if (selectedCollectionName != null && selectedItemName != null) {
            Collection collection = user.getCollectionByName(selectedCollectionName);
            if (collection != null) {
                Item item = collection.getItemByName(selectedItemName);
                if (item != null) {
                    String description = item.getItemDescription();
                    JOptionPane.showMessageDialog(CollectorHeavenGUI.this,
                            description.isEmpty() ? "No description available." : description,
                            "Item Description", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    // Listens for the "Reset Profile" button click and reloads the profile.
    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadProfile();
        }
    }

    // Listens for the "Save Profile" button click and saves the current profile to a file.
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(user);
                jsonWriter.close();
                JOptionPane.showMessageDialog(CollectorHeavenGUI.this, "Saved user profile to " + JSON_STORE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(CollectorHeavenGUI.this, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Listens for the "Create Collection" button click and creates a new collection.
    private class CreateCollectionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String collectionName = JOptionPane.showInputDialog("Enter Collection Name:");
            if (collectionName != null && !collectionName.trim().isEmpty()) {
                Collection collection = new Collection(collectionName.trim());
                user.addCollection(collection);
                updateCollectionList();

                // Display the image temporarily
                imagePanel.showImage();
            }
        }
    }

    // Listens for the "Create Item" button click and creates a new item in the selected collection.
    private class CreateItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCollectionName = collectionList.getSelectedValue();
            if (selectedCollectionName != null) {
                String itemName = JOptionPane.showInputDialog("Enter Item Name:");
                if (itemName != null && !itemName.trim().isEmpty()) {
                    Collection collection = user.getCollectionByName(selectedCollectionName);
                    if (collection != null) {
                        Item item = new Item(itemName.trim(), "");
                        collection.addItem(item);
                        updateItemList();
                    }
                }
            }
        }
    }

    // Listens for the "Delete Item" button click and deletes the selected item from the selected collection.
    private class DeleteItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCollectionName = collectionList.getSelectedValue();
            String selectedItemName = itemList.getSelectedValue();
            if (selectedCollectionName != null && selectedItemName != null) {
                Collection collection = user.getCollectionByName(selectedCollectionName);
                if (collection != null) {
                    Item item = collection.getItemByName(selectedItemName);
                    if (item != null) {
                        int confirm = JOptionPane.showConfirmDialog(CollectorHeavenGUI.this,
                                "Are you sure you want to delete this item?", 
                                "Confirm Deletion", 
                                JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            collection.removeItem(item); 
                            updateItemList();
                        }
                    } 
                } 
            } else {
                JOptionPane.showMessageDialog(CollectorHeavenGUI.this,
                        "Please select both a collection and an item to delete.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Listens for the "Add Description" button click and adds a description to the selected item.
    private class AddDescriptionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCollectionName = collectionList.getSelectedValue();
            if (selectedCollectionName != null) {
                Collection collection = user.getCollectionByName(selectedCollectionName);
                if (collection != null) {
                    String selectedItemName = itemList.getSelectedValue();
                    if (selectedItemName != null) {
                        Item item = collection.getItemByName(selectedItemName);
                        if (item != null) {
                            String description = JOptionPane.showInputDialog("Enter Description:");
                            if (description != null) {
                                item.editDescription(description.trim());
                            }
                        }
                    }
                }
            }
        }
    }

    // Requires: The user chooses to save their profile.
    // Modifies: The user profile saved to file.
    // Effects: Saves the profile to file if the user confirms.
    private void promptSaveBeforeExit() {
        String message = "Do you want to save your user profile before exiting?";
        String title = "Save Profile";
        int choice = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(user);
                jsonWriter.close();
                JOptionPane.showMessageDialog(this, "Saved user profile to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
            }
        }
        printLog();
    }

    // Effects: Prints all logged events from the EventLog to the console.
    private void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
    }

    // Requires: The application window is closing.
    // Modifies: Triggers a save prompt.
    // Effects: Prompts the user to save before exiting the application.
    void setupWindowListener() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                promptSaveBeforeExit();
                System.exit(0);
            }
        });
    }
}
