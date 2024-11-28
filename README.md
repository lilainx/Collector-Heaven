# About Collections
## What is Collections
Collections is a platform for (lego, card, figurine, etc.) collectors to *share* their personal collections, *connect* with other collectors that share their interests, and *discover* new interests through browsing new collections curated by other users. Each user will have a *personal profile* with self-uploaded photos of pieces/items in their personal collection. Users can have more than one collection on their profile and are able to **pin** their favourite collections/items/profiles of their own or of other users. Double tap to like and save someone's collection...that collection will then be added to your profile.

For the first version of this project, users will be able to set up their personal profile and added their collections. 

## Who will use Collections?
- Collectors of all shapes, types, and genres of collector items.
   - looking to expand their collections/interests
   - connect with other likeminded collectors
- Browsers looking for cool collections.

## Why is this project of interest to you?
Throughout my life, I've started collections of various things! From pokemon cards and fantasy fiction books in elementary school to lego sets in the beginning of 2023. These interests form a significant part of my character, and I am filled with excitement and passion when sharing the progress of my collections with the people around me. While renowned platforms such as Pinterest, Instagram, and FaceBook are home to well-established and vast communities that exist for similar purposes, there lacks a platform that is dedicated to this purpose (for once keep fashion separate). 

# User Stories
- As a user I want to be able to create a collection and add it to a list of collections (user profile)
- As a user I want to be able to view my list of collections on my user profile
- As a user I want to be able to create an item and add it to an existing collection
- As a user I want to be able to select a collection and view the list of items in the collection
- As a user I want to be able to select an item and add a description
- As a user, as I select the quit option from the main menu, I want to be reminded to save my user profile, and have the option to do so or not
- As a user, when I start the application, I want to be given the option to load my user profile from file

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by selecting a collection, and item in the collection, and clicking the "add description" button to add a description to the selected item (double click item to view description)
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by selecting an item in an existing collection and clicking the "delete item" (follow the prompts!)
- You can add items to collections by selecting a collection and clicking the "add item" button and name the item
- You can locate my visual component by creating a **new** collection, and a temporary pikachu image will let you know that your collection was successfully added
- You can save the state of my application by 
1. exiting the application as you will be prompted to save your user profile or...
2. clicking the "save profile" button if you want to save while you're still in the application
- You can reload the state of my application by running the application, and selecting "No" when prompted to load a saved profile
- You can load your previously saved state by selecting "Yes" when you initially run the application

# Phase 4: Task 2
Wed Aug 07 13:41:13 PDT 2024
moew was added to lego collection.
Wed Aug 07 13:41:13 PDT 2024
lego added to user profile.
Wed Aug 07 13:41:13 PDT 2024
piplup was added to pokemon collection.
Wed Aug 07 13:41:13 PDT 2024
pikachu was added to pokemon collection.
Wed Aug 07 13:41:13 PDT 2024
pokemon added to user profile.
Wed Aug 07 13:41:13 PDT 2024
fruit added to user profile.
Wed Aug 07 13:41:13 PDT 2024
pigs added to user profile.


# Phase 4: Task 3
By introducing a controller layer, the application logic would be decoupled from the UI and the model. This would make it easier to manage and test each component independently. The controller would handle user inputs and coordinate between the model and the view.

In addition, breaking down the CollectorHeavenGUI class into smaller classes with specific responsibilities would make the codebase cleaner and easier to manage.

Moreover, implementing custom exceptions, consistent error handling, and a uniform error-handling strategy across the application would enhance the robustness of the application. This would ensure that errors are handled in a consistent manner, making the application more reliable and easier to debug.