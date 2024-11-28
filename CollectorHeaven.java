package ui;

import model.Collection;
import model.Item;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

// represents the console ui for CollectorHeaven application
public class CollectorHeaven {
    private static final String JSON_STORE = "./data/user.json";
    private User user;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs a new CollectorHeaven application
    public CollectorHeaven() {
        user = new User("default_user");
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadProfile();
    }

    
    // MODIFIES: this
    // EFFECTS: runs the main menu loop
    public void start() {
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newLine
            handleMainMenuChoice(choice);
            exit = (choice == 5);
        }
        System.out.println("Goodbye, Collector!");
        System.out.println("See you soon!");
        
    }

    // EFFECTS: handles the main menu choices
    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                viewUserProfile();
                break;
            case 2:
                createCollection();
                break;
            case 3:
                deleteCollection();
                break;
            case 4: 
                changeUsername();
                break;
            case 5:
                promptSaveBeforeExit();
                break;
            default:
                System.out.println("Invalid choice. Please try again!");
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts the user to load their profile from a file or create a new one
    private void loadProfile() {
        System.out.print("Do you want to load your user profile from file? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("yes")) {
            try {
                user = jsonReader.read();
                System.out.println("Loaded user profile from " + JSON_STORE);

            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
                user = new User("default_user");
            }
        } else {
            user = new User("default_user");
        }
    }

    // EFFECTS: prompts the user to save their profile before exiting
    //          returns true to exit the application
    private boolean promptSaveBeforeExit() {
        System.out.print("Do you want to save your user profile before exiting? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("yes")) {
            try {
                jsonWriter.open();
                jsonWriter.write(user);
                jsonWriter.close();
                System.out.println("Saved user profile to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        return true;
    }

    // EEFECTS: displays the main menu
    public static void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. View User's Collection Profile");
        System.out.println("2. Create Collection");
        System.out.println("3. Delete Collection");
        System.out.println("4. Change Username");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // MODIFIES: this
    // EFFECTS: displays the user's collection profile and allows managing a specific collection
    private void viewUserProfile() {
        List<Collection> collections = user.getCollections();
        System.out.println("Welcome to your Collections Profile '" + user.getUsername() + "'.");
        if (collections.isEmpty()) {
            System.out.println("Looks like you don't have any collections yet. Time to make a new one!");
            return;
        }

        System.out.println("My collections:");
        for (Collection collection : collections) {
            System.out.println("- " + collection.getCollectionName());
        }

        System.out.print("Enter the name of the collection to view its items or manage: ");
        String collectionName = scanner.nextLine();
        manageCollection(collectionName);
    }

    // REQUIRES: collectionName is a valid collection name
    // MODIFIES: this
    // EFFECTS: manages the specified collection
    private void manageCollection(String collectionName) {
        Collection collection = user.getCollectionByName(collectionName);

        if (collection != null) {
            manageCollectionLoop(collection);

        } else {
            System.out.println("Collection not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles the collection management loop
    private void manageCollectionLoop(Collection collection) {
        boolean back = false;
        while (!back) {
            displayCollectionMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            handleManageCollection(collection, choice);
            back = (choice == 5);
        }
    }

    // EFFECTS: handles manage collection choices
    private void handleManageCollection(Collection collection, int choice) {
        switch (choice) {
            case 1:
                viewCollectionItems(collection);
                break;
            case 2:
                createItem(collection);
                break;
            case 3:
                editItemDescription(collection);
                break;
            case 4:
                deleteItem(collection);
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice. Please try again!");
        }
    }
    
    // EFFECTS: displays the collection menu
    public static void displayCollectionMenu() {
        System.out.println("Collection Menu:");
        System.out.println("1. View Items");
        System.out.println("2. Create Item");
        System.out.println("3. Edit Item Description");
        System.out.println("4. Delete Item");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose an option: ");
    }

    // REQUIRES: collection is not null
    // EFFECTS: displays the items in the specified collection
    private void viewCollectionItems(Collection collection) {
        List<Item> items = collection.getItems();
        if (items.isEmpty()) {
            System.out.println("This collection is empty. Try adding a new item.");
        } else {
            System.out.println("Items in collection '" + collection.getCollectionName() + "':");
            for (Item item : items) {
                System.out.println("- " + item.getItemName() + "  " + item.getItemDescription());
            }
        }
    }


    // REQUIRES: collection is not null
    // MODIFIES: collection
    // EFFECTS: creates a new item and adds it to the specified collection
    private void createItem(Collection collection) {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        String description = scanner.nextLine();
        Item item = new Item(itemName, description);
        collection.addItem(item);
        System.out.println("New item created and added to the '" + collection.getCollectionName() + "' collection.");

    }

    // REQUIRES: collection is not null
    // MODIFIES: collection
    // EFFECTS: edits the description of an item in the specified collection
    private void editItemDescription(Collection collection) {
        System.out.print("Enter the name of the item to edit its description: ");
        String itemName = scanner.nextLine();
        Item item = collection.getItemByName(itemName);

        if (item != null) {
            System.out.print("Enter new description: ");
            String description = scanner.nextLine();
            item.editDescription(description);
            System.out.println("Description updated for item '" + item.getItemName() + "'.");

        } else {
            System.out.println("Item not found.");
        }
    }

    // REQUIRES: collection is not null
    // MODIFIES: collection
    // EFFECTS: deletes an item from the specified collection
    private void deleteItem(Collection collection) {
        System.out.print("Enter the name of the item to delete: ");
        String itemName = scanner.nextLine();
        Item item = collection.getItemByName(itemName);

        if (item != null) {
            collection.removeItem(item);
            System.out.println("Item '" + item.getItemName() + "' deleted from the collection.");
        } else {
            System.out.println("Item not found.");
        }
    }
    // collection menu ends here


    // MODIFIES: user
    // EFFECTS: creates a new collection and adds it to the user's profile
    private void createCollection() {
        System.out.print("Give your collection a name: ");
        String name = scanner.nextLine();
        Collection collection = new Collection(name);
        user.addCollection(collection);
        System.out.println("You created a new collection and added it to your profile!");
    }


    // MODIFIES: user
    // EFFECTS: deletes an exitsing collection from the user's profile
    private void deleteCollection() {
        System.out.print("Enter the name of the collection to delete: ");
        String collectionName = scanner.nextLine();
        Collection collection = user.getCollectionByName(collectionName);

        if (collection != null) {
            user.removeCollection(collection);
            System.out.println("Collection '" + collection.getCollectionName() + "' deleted.");

        } else {
            System.out.println("Collection not found.");
        }
    }

    // MODIFIES: user
    // EFFECTS: changes the username of the user
    private void changeUsername() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        user.setUsername(newUsername);
        System.out.println("Username changed to '" + user.getUsername() + "'.");
    }



   
}
