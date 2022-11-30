package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.RatingUtility;
import utils.ScannerInput;
import utils.Utilities;

public class Driver {


    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 ----------------------------App Store----------------------------
                |  1) Developer - Management MENU                                |
                |  2) App - Management MENU                                      |
                |  3) Reports MENU                                               |
                |----------------------------------------------------------------|
                |  4) Search                                                     |
                |  5) Sort                                                       |
                |----------------------------------------------------------------|
                |  6) Recommended Apps                                           |
                |  7) Random App of the Day                                      |
                |  8) Simulate The Rating of Apps                                |
                |  9) Display Cheapest App                                       |
                |  10) Display Average App Cost                                  |
                |  12) List products that are more expensive than a given price  |
                |----------------------------------------------------------------|
                |  20) Save all                                                  |
                |  21) Load all                                                  |
                |----------------------------------------------------------------|
                |  0) Exit                                                       |
                 -----------------------------------------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppStoreMenu();
                case 3 -> runReportsMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> appStoreAPI.sortAppsByNameAscending();
                case 6 -> appStoreAPI.listAllRecommendedApps();
                case 7 -> appStoreAPI.randomApp();
                case 8 -> simulateRatings();
                case 9 -> appStoreAPI.cheapestApp();
                case 10 -> appStoreAPI.averageAppCost();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    private int appStoreMenu() {
        System.out.println("""
                 -------App Store Menu--------
                |   1) Add an app            |
                |   2) List all apps         |
                |   3) Update an app         |
                |   4) Delete an app         |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppStoreMenu() {
        int option = appStoreMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> runAppsToList();
                case 3 -> updateApp();
                case 4 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appStoreMenu();
        }
    }

    private void addApp() {

        boolean isAdded = false;

        int option = ScannerInput.validNextInt("""
                -----------------------------
                |   1) Add  Education App   |
                |   2) Add Game App         |
                |   3) Add Productivity Ap  |
                -----------------------------
                ==>> """);

        switch (option) {
            case 1 -> {
                String developerName = ScannerInput.validNextLine("Which developer is the app from: " + developerAPI.listDevelopers());
                if (developerAPI.isValidDeveloper(developerName)) {
                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                    double appSize = ScannerInput.validNextInt("Please enter the app size: ");
                    double appVersion = ScannerInput.validNextInt("Please enter the app version: ");
                    double appCost = ScannerInput.validNextInt("Please enter the app cost: ");
                    int appLevel = ScannerInput.validNextInt("Please enter the app level: ");
                    isAdded = appStoreAPI.addApp(new EducationApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, appLevel));
                    if (isAdded) {
                        System.out.println("App added successfully");
                        System.out.println(appStoreAPI.getApps());
                    } else {
                        System.out.println("No apps added");
                    }
                } else {
                    System.out.println("Invalid developer entered");
                }
            }

            case 2 -> {
                String developerName = ScannerInput.validNextLine("Which developer is the app from: " + developerAPI.listDevelopers());
                if (developerAPI.isValidDeveloper(developerName)) {
                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                    double appSize = ScannerInput.validNextInt("Please enter the app size: ");
                    double appVersion = ScannerInput.validNextInt("Please enter the app version: ");
                    double appCost = ScannerInput.validNextInt("Please enter the app cost: ");

                    //Ask the user to type in either a Y or an N. This is then converted to either a True or a False
                    char isAppMultiplayer = ScannerInput.validNextChar("Is this app multiplayer - Y/N :  ");
                    boolean isMultiplayerGame = Utilities.YNtoBoolean(isAppMultiplayer);

                    isAdded = appStoreAPI.addApp(new GameApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, isMultiplayerGame));
                    if (isAdded) {
                        System.out.println("App added successfully");
                        System.out.println(appStoreAPI.getApps());
                    } else {
                        System.out.println("No apps added");
                    }
                } else {
                    System.out.println("Invalid developer entered");
                }
            }
            case 3 -> {
                String developerName = ScannerInput.validNextLine("Which developer is the app from: " + developerAPI.listDevelopers());
                if (developerAPI.isValidDeveloper(developerName)) {
                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                    double appSize = ScannerInput.validNextInt("Please enter the app size: ");
                    double appVersion = ScannerInput.validNextInt("Please enter the app version: ");
                    double appCost = ScannerInput.validNextInt("Please enter the app cost: ");
                    String appTask = ScannerInput.validNextLine("Please enter the task of this app: ");
                    isAdded = appStoreAPI.addApp(new ProductivityApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, appTask));
                    if (isAdded) {
                        System.out.println("App added successfully");
                        System.out.println(appStoreAPI.getApps());
                    } else {
                        System.out.println("No apps added");
                    }
                } else {
                    System.out.println("Invalid developer entered");
                }
            }
            default -> System.out.println("Invalid option entered: " + option);
        }

        if (isAdded) {
            System.out.println("App Added Successfully");
        } else {
            System.out.println("No App Added");
        }
    }

    private int appsToList() {
        System.out.println("""
                -----------App Store Menu----------
                |   1) List all apps              |
                |   2) List Education apps        |
                |   3) List Game apps             |
                |   4) List Productivity apps     |
                |   0) RETURN to main menu        |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppsToList() {
        int option = appsToList();
        while (option != 0) {
            switch (option) {
                case 1 -> showApps();
                case 2 -> showEducationApps();
                case 3 -> showGameApps();
                case 4 -> showProductivityApps();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appsToList();
        }
    }

    private void updateApp() {

        if (appStoreAPI.numberOfApps() > 0) {
            boolean isUpdated = false;

            int option = ScannerInput.validNextInt("""
                    ---------------------------------
                    |   1) Update Education App     |
                    |   2) Update Game App          |
                    |   3) Update Productivity App  |
                    ---------------------------------
                    ==>> """);

            switch (option) {
                case 1 -> {
                    //ask the user to enter the index of the object to update, and assuming it's valid and is a MessagePost,
                    //gather the new data from the user and update the selected object.
                    showEducationApps();
                    if (appStoreAPI.numberOfEducationApps() > 0) {
                        int educationIndex = ScannerInput.validNextInt("Enter the index of the Education app to update ==> ");
                        if (appStoreAPI.isValidEducationAppIndex(educationIndex)) {
                            String developerName = ScannerInput.validNextLine("Which developer is the app from: " + developerAPI.listDevelopers());
                            if (developerAPI.isValidDeveloper(developerName)) {
                                String appName = ScannerInput.validNextLine("Please enter the app name: ");
                                double appSize = ScannerInput.validNextInt("Please enter the app size: ");
                                double appVersion = ScannerInput.validNextInt("Please enter the app version: ");
                                double appCost = ScannerInput.validNextInt("Please enter the app cost: ");
                                int appLevel = ScannerInput.validNextInt("Please enter the app level: ");
                                isUpdated = appStoreAPI.addApp(new EducationApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, appLevel));
                            }
                        }
                    }
                }

                case 2 -> {
                    //ask the user to enter the index of the object to update, and assuming it's valid and is a GameApp,
                    //gather the new data from the user and update the selected object.
                    showGameApps();
                    if (appStoreAPI.numberOfGameApps() > 0) {
                        int gameIndex = ScannerInput.validNextInt("Enter the index of the Game to update ==> ");
                        if (appStoreAPI.isValidGameAppIndex(gameIndex)) {
                            String developerName = ScannerInput.validNextLine("Which developer is the app from: " + developerAPI.listDevelopers());
                            if (developerAPI.isValidDeveloper(developerName)) {
                                String appName = ScannerInput.validNextLine("Please enter the app name: ");
                                double appSize = ScannerInput.validNextInt("Please enter the app size: ");
                                double appVersion = ScannerInput.validNextInt("Please enter the app version: ");
                                double appCost = ScannerInput.validNextInt("Please enter the app cost: ");
                                char isAppMultiplayer = ScannerInput.validNextChar("Is this game multiplayer - Y/N :  ");
                                boolean isMultiplayerGame = Utilities.YNtoBoolean(isAppMultiplayer);

                                isUpdated = appStoreAPI.addApp(new GameApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, isMultiplayerGame));
                            }
                        }
                    }
                }
                    case 3 -> {
                        //ask the user to enter the index of the object to update, and assuming it's valid and is a ProductivityApp ,
                        //gather the new data from the user and update the selected object.
                        showProductivityApps();
                        if (appStoreAPI.numberOfProductivityApps() > 0) {
                            int productivityIndex = ScannerInput.validNextInt("Enter the index of the productivity app to update ==> ");
                            if (appStoreAPI.isValidProductivityAppIndex(productivityIndex)) {
                                String developerName = ScannerInput.validNextLine("Which developer is the app from: " + developerAPI.listDevelopers());
                                if (developerAPI.isValidDeveloper(developerName)) {
                                    String appName = ScannerInput.validNextLine("Please enter the app name: ");
                                    double appSize = ScannerInput.validNextInt("Please enter the app size: ");
                                    double appVersion = ScannerInput.validNextInt("Please enter the app version: ");
                                    double appCost = ScannerInput.validNextInt("Please enter the app cost: ");
                                    String appTask = ScannerInput.validNextLine("Please enter the task of this app - Work, Education, Holiday, Games: ");
                                    isUpdated = appStoreAPI.addApp(new ProductivityApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, appTask));
                                }
                            }
                        }
                    }
                    default -> System.out.println("Invalid option entered: " + option);
                }

            if (isUpdated) {
                System.out.println("App Updated Successfully");
            } else {
                System.out.println("No App Updated");
            }
        }
        else{
            System.out.println("No apps added yet");
        }
    }




    private void showApps(){
        System.out.println("List of All Apps:");
        System.out.println(appStoreAPI.listAllApps());
    }

    private void showDevelopers(){
        System.out.println("List of All Develipers:");
        System.out.println(developerAPI.listDevelopers());
    }

    private void showEducationApps(){
        System.out.println("List of Education Apps:");
        System.out.println(appStoreAPI.listAllEducationApps());
    }

    private void showGameApps(){
        System.out.println("List of Game Apps:");
        System.out.println(appStoreAPI.listAllGameApps());
    }

    private void showProductivityApps(){
        System.out.println("List of Productivity Apps:");
        System.out.println(appStoreAPI.listAllProductivityApps());
    }

    private void deleteApp(){
        showApps();
        if (appStoreAPI.numberOfApps() > 0){
            //only ask the user to choose the app to delete if apps exist
            System.out.println(appStoreAPI.listAllApps());
            int indexToDelete = ScannerInput.validNextInt("Enter the index of the app to delete ==> ");
            //pass the index of the app to appStoreAI for deleting and check for success.
            App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);
            if (appToDelete != null){
                System.out.println("Delete Successful! Deleted app: " + appToDelete);
            }
            else{
                System.out.println("Delete NOT Successful");
            }
        }
    }

    private int reportsMenu() {
        System.out.println("""
                 -------------Reports Menu--------------
                |   1) App Overview                    |
                |   2) Developer Overview              |
                |   2) List the Summary of all apps    |
                |   3) List All Education Apps         |
                |   4) List All Game Apps              |
                |   5) List All Productivity Apps      |
                |   0) RETURN to main menu             |
                 ---------------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> showApps();
                case 2 -> showDevelopers();
                case 3 -> appStoreAPI.listSummaryOfAllApps();
                case 4 -> appStoreAPI.listAllEducationApps();
                case 5 -> appStoreAPI.listAllGameApps();
                case 6 -> appStoreAPI.listAllProductivityApps();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }




//*********************************************************************************************************************************
    //--------------------------------------------------
    // TODO UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
             case 1 -> showAppsByName();
             case 2 -> showAppsByDeveloperName();
             case 3 -> showAppsForAGivenRating();
             default -> System.out.println("Invalid option");
        }
    }

    private void showAppsByName(){
        String appName = ScannerInput.validNextLine("Enter the name of the app: ");
        System.out.println(appStoreAPI.listAllAppsByName(appName));
    }

    private void showAppsByDeveloperName(){
        String developerName = ScannerInput.validNextLine("Enter the name of the developer: ");
        System.out.println(appStoreAPI.listAllAppsByName(developerName));
    }

    private void showAppsForAGivenRating(){
        String appRating = ScannerInput.validNextLine("Enter the rating to look for: ");
        System.out.println(appStoreAPI.listAllAppsByName(appRating));
    }



    //--------------------------------------------------
    // TODO UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc).
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            RatingUtility.generateRandomRating();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        try {
            System.out.println("Saving to file: " + appStoreAPI.fileName());
            System.out.println("Saving to file: " + developerAPI.fileName());
            appStoreAPI.save();
            developerAPI.save();
        } catch (Exception e){
            System.out.println("Error writing to file: " + e);
            System.out.println("Error writing to file: " + e);
        }
    }

    private void loadAllData() {
        try {
            System.out.println("Loading from file: " + appStoreAPI.fileName());
            System.out.println("Loading from file: " + developerAPI.fileName());
            appStoreAPI.load();
            developerAPI.load();
        } catch (Exception e){
            System.out.println("Error reading from file: " + e);
            System.out.println("Error reading from file: " + e);
        }
    }

}