package controllers;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {
    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");
    private Developer developerKoolGames = new Developer("Kool Games", "www.koolgames.com");
    private Developer developerApple = new Developer("Apple", "www.apple.com");
    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private AppStoreAPI appStore = new AppStoreAPI();
    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0,  1);

        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0,
                1.99, 10);

        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5,  2.99,  11);

        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00,  0);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        prodAppBelowBoundary = new ProductivityApp(developerApple, "NoteKeeper", 1, 1.0, 0.0,"Work");

        prodAppOnBoundary = new ProductivityApp(developerMicrosoft, "Outlook", 1000, 2.0, 1.99, "Education");

        prodAppAboveBoundary = new ProductivityApp(developerApple, "Pages", 1001, 3.5, 2.99, "Vacation");

        prodAppInvalidData = new ProductivityApp(developerMicrosoft, "", -1, 0, -1.00, "");


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        gameAppBelowBoundary = new GameApp(developerEAGames, "Tetris", 1, 1.0, 0.0,  false);

        gameAppOnBoundary = new GameApp(developerKoolGames, "CookOff", 1000, 2.0, 1.99,  true);

        gameAppAboveBoundary = new GameApp(developerEAGames, "Empires", 1001, 3.5,  2.99, false);

        gameAppInvalidData = new GameApp(developerKoolGames, "", -1, 0,  -1.00,  true);

        //not included - edAppOnBoundary, edAppInvalidData, prodAppBelowBoundary, gameAppBelowBoundary, gameAppInvalidData.
        appStore.addApp(edAppBelowBoundary);
        appStore.addApp(edAppAboveBoundary);
        appStore.addApp(prodAppOnBoundary);
        appStore.addApp(prodAppBelowBoundary);
        appStore.addApp(gameAppOnBoundary);
        appStore.addApp(gameAppAboveBoundary);
        appStore.addApp(prodAppInvalidData);
        //7 Apps

    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerApple = developerEAGames = developerKoolGames = developerLego = developerMicrosoft = null;
        appStore = emptyAppStore = null;
    }

    @Nested
    class GettersAndSetters {
        @Test
        void gettingAppListReturnsList(){
            List<App> testApps = new ArrayList<>();
            testApps.add(edAppBelowBoundary);
            testApps.add(edAppAboveBoundary);
            testApps.add(prodAppOnBoundary);
            testApps.add(prodAppBelowBoundary);
            testApps.add(gameAppOnBoundary);
            testApps.add(gameAppAboveBoundary);
            testApps.add(prodAppInvalidData);
            assertEquals(testApps,appStore.getApps());
            assertEquals(new ArrayList<App>(), emptyAppStore.getApps());
        }

        @Test
        void settingAppListReplacesList(){
            List<App> testApps = new ArrayList<>();
            testApps.add(edAppBelowBoundary);
            testApps.add(edAppAboveBoundary);
            testApps.add(prodAppOnBoundary);

            assertEquals(7 , appStore.numberOfApps());
            appStore.setApps(testApps);
            assertEquals(testApps, appStore.getApps());
            assertEquals(3, appStore.numberOfApps());

            emptyAppStore.setApps(new ArrayList<App>());
            assertEquals(0, emptyAppStore.numberOfApps());
        }

    }

    @Nested
    class CRUDMethods {

        @Test
        void addingAnAppAddsToList(){
            assertEquals(7 , appStore.numberOfApps());

            //Testing EducationApp object
            assertTrue(appStore.addApp(edAppAboveBoundary));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(appStore.numberOfApps() - 1));

            //Testing GameApp object
            assertTrue(appStore.addApp(gameAppBelowBoundary));
            assertEquals(gameAppBelowBoundary, appStore.getAppByIndex(appStore.numberOfApps() - 1));

            //Testing ProductivityApp object
            assertTrue(appStore.addApp(prodAppOnBoundary));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(appStore.numberOfApps() - 1));

            //Testing empty List
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.addApp(prodAppOnBoundary));
            assertEquals(prodAppOnBoundary, emptyAppStore.getAppByIndex(emptyAppStore.numberOfApps() - 1));
        }

        @Test
        void updatingAnAppThatDoesNotExistReturnsFalse() {
            //Testing List with apps
            assertFalse(appStore.updateEducationApp(7,developerSphero,"Learning",2.0,3.0,5.99,4));
            assertFalse(appStore.updateGameApp(7,developerSphero,"Learning",2.0,3.0,5.99,true));
            assertFalse(appStore.updateProdApp(7,developerSphero,"Learning",2.0,3.0,5.99,"Work"));

            //Testing empty List
            assertFalse(emptyAppStore.updateEducationApp( 0,developerSphero,"Learning",2.0,3.0,5.99,4));
        }

        @Test
        void updatingEducationAppThatExistsReturnsTrueAndUpdates(){
            EducationApp foundApp = (EducationApp) appStore.getAppByIndex(0);
            assertEquals(edAppBelowBoundary, foundApp);

            //update messagePost, index 1 exists and check the contents
            assertTrue(appStore.updateEducationApp(1, developerSphero,"Learning",2.0,3.0,5.99,4));
            EducationApp updatedApp = (EducationApp) appStore.getAppByIndex(1);
            assertEquals(developerSphero, updatedApp.getDeveloper());
            assertEquals("Learning", updatedApp.getAppName());
            assertEquals(2.0, updatedApp.getAppSize());
            assertEquals(3.0, updatedApp.getAppVersion());
            assertEquals(5.99, updatedApp.getAppCost());
            assertEquals(4, updatedApp.getLevel());
        }
        @Test
        void updatingGameAppThatExistsReturnsTrueAndUpdates(){
            GameApp foundApp = (GameApp) appStore.getAppByIndex(4);
            assertEquals(gameAppOnBoundary, foundApp);

            //update messagePost, index 1 exists and check the contents
            assertTrue(appStore.updateGameApp(4, developerSphero,"Learning",2.0,3.0,5.99,false));
            GameApp updatedApp = (GameApp) appStore.getAppByIndex(4);
            assertEquals(developerSphero, updatedApp.getDeveloper());
            assertEquals("Learning", updatedApp.getAppName());
            assertEquals(2.0, updatedApp.getAppSize());
            assertEquals(3.0, updatedApp.getAppVersion());
            assertEquals(5.99, updatedApp.getAppCost());
            assertFalse(updatedApp.isMultiplayer());
        }

        @Test
        void updatingProductivityAppThatExistsReturnsTrueAndUpdates(){
            ProductivityApp foundApp = (ProductivityApp) appStore.getAppByIndex(2);
            assertEquals(prodAppOnBoundary, foundApp);

            //update messagePost, index 1 exists and check the contents
            assertTrue(appStore.updateProdApp(2, developerSphero,"Learning",2.0,3.0,5.99,"Education"));
            ProductivityApp updatedApp = (ProductivityApp) appStore.getAppByIndex(2);
            assertEquals(developerSphero, updatedApp.getDeveloper());
            assertEquals("Learning", updatedApp.getAppName());
            assertEquals(2.0, updatedApp.getAppSize());
            assertEquals(3.0, updatedApp.getAppVersion());
            assertEquals(5.99, updatedApp.getAppCost());
            assertEquals("Education", updatedApp.getTask());
        }

        @Test
        void deletingAnAppThatDoesNotExistReturnsNull(){
            assertNull(emptyAppStore.deleteAppByIndex(0));
            assertNull(appStore.deleteAppByIndex(-1));
            assertNull(appStore.deleteAppByIndex(appStore.numberOfApps()));
        }

        @Test
        void deletingAnAppThatExistsDeletesAndReturnsDeletedObject(){
            //deleting aa app at the start of the list
            assertEquals(7, appStore.numberOfApps());
            assertEquals(edAppBelowBoundary, appStore.deleteAppByIndex(0));
            assertEquals(6, appStore.numberOfApps());

            //deleting a post at the end of the arraylist
            assertEquals(6, appStore.numberOfApps());
            assertEquals(prodAppInvalidData, appStore.deleteAppByIndex(5));
            assertEquals(5, appStore.numberOfApps());
        }

    }

    @Nested
    class ReportingMethods {


        @Test
        void listAllAppsReturnsNoAppsStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllApps();
            System.out.println(apps);
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(7, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }

        @Test
        void listSummaryOfAllAppsReturnsSummaryOfAllAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listSummaryOfAllApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));


        }

        @Test
        void listSummaryOfAllAppsReturnsStringWhenNoAppsAdded() {
            assertEquals(0, emptyAppStore.numberOfApps());

            String apps = emptyAppStore.listSummaryOfAllApps();
            System.out.println(apps);
        }



        @Test
        void listAllGameAppsReturnsListOfGameAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllGameApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("CookOff"));
            assertTrue(apps.contains("EA Games"));
            assertTrue(apps.contains("MazeRunner"));
        }

        @Test
        void listAllGameAppsReturnsStringWhenNoAppsAdded() {
            assertEquals(0, emptyAppStore.numberOfApps());

            String apps = emptyAppStore.listAllGameApps();
            System.out.println(apps);
        }

        @Test
        void listAllEducationAppsReturnsListOfEducationAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllEducationApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Lego"));
            assertTrue(apps.contains("Level: 3"));
        }

        @Test
        void listAllEducationAppsReturnsStringWhenNoAppsAdded() {
            assertEquals(0, emptyAppStore.numberOfApps());

            String apps = emptyAppStore.listAllEducationApps();
            System.out.println(apps);
        }

        @Test
        void listAllProductivityAppsReturnsListOfProductivityAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllProductivityApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("Task: Education"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("Apple"));
        }

        @Test
        void listAllProductivityAppsReturnsStringWhenNoAppsAdded() {
            assertEquals(0, emptyAppStore.numberOfApps());

            String apps = emptyAppStore.listAllProductivityApps();
            System.out.println(apps);
        }


        @Test
        void listAllAppsByNameReturnsListOfAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllAppsByName("WeDo");
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("Level: 1"));
            assertTrue(apps.contains("Lego"));
            assertTrue(apps.contains("3.5"));

        }

        @Test
        void listAllAppsByNameReturnsStringWhenTheyDontExist() {
            assertEquals(7, appStore.numberOfApps());

            String apps = appStore.listAllAppsByName("bob");
            System.out.println(apps);
        }

        @Test
        void listAllAppsByNameReturnsStringWhenNoAppsAdded() {
            assertEquals(0, emptyAppStore.numberOfApps());

            String apps = emptyAppStore.listAllAppsByName("WeDo");
            System.out.println(apps);
        }

        @Test
        void listAllAppsAboveOrEqualAGivenStarRatingReturnsListOfAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllAppsAboveOrEqualAGivenStarRating(4);
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("MazeRunner"));
            assertTrue(apps.contains("John Soap"));
            assertTrue(apps.contains("Nice Game"));
        }

        @Test
        void listAllAppsAboveOrEqualAGivenStarRatingReturnsStringWhenNoAppsAdded() {
            assertEquals(0, emptyAppStore.numberOfApps());

            String apps = emptyAppStore.listAllAppsAboveOrEqualAGivenStarRating(4);
            System.out.println(apps);
        }

        @Test
        void listAllAppsByChosenDeveloperReturnsListOfAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(3, appStore.numberOfAppsByChosenDeveloper("Lego"));

            String apps = appStore.listAllAppsByChosenDeveloper("Lego");
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("Lego"));
        }

        @Test
        void averageAppCostCalculatesCorrectly() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(1.42, appStore.averageAppCost(), 0.01);

            assertEquals(0, emptyAppStore.numberOfApps());
            assertEquals(-1, emptyAppStore.averageAppCost(), 0.01);

            emptyAppStore.addApp(gameAppOnBoundary);
            assertEquals(1, emptyAppStore.numberOfApps());
            assertEquals(1.99, emptyAppStore.averageAppCost(), 0.01);
        }

        @Test
        void cheapestAppCalculatedCorrectly() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(edAppBelowBoundary, appStore.cheapestApp());
            edAppBelowBoundary.setAppCost(999.99);
            assertEquals(prodAppBelowBoundary, appStore.cheapestApp());
            prodAppBelowBoundary.setAppCost(360);
            assertEquals(prodAppInvalidData, appStore.cheapestApp());

            assertEquals(0, emptyAppStore.numberOfApps());
            assertNull(emptyAppStore.cheapestApp());
        }

    }

    @Nested
    class SearchingMethods {

        @Test
        void getAppByIndexReturnsAppWhenIndexIsValid() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(0));
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(6));
        }


        @Test
        void getAppByIndexReturnsNullWhenIndexIsInValid() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertNull(emptyAppStore.getAppByIndex(0));

            assertEquals(7, appStore.numberOfApps());
            assertNull(appStore.getAppByIndex(-1));
            assertNull(appStore.getAppByIndex(7));
        }

        @Test
        void getAppByNameReturnsAppWhenNameIsValid() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.getAppByName("NoteKeeper");
            System.out.println(apps);
            //checks for the three objects in the string
            assertTrue(apps.contains("Apple"));
            assertTrue(apps.contains("Ratings (-1.0)"));
            assertTrue(apps.contains("Task: Work"));
        }

        @Test
        void getAppByNameReturnsStringWhenNameIsNotValid() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.getAppByName("Bob");
            System.out.println(apps);
        }

        @Test
        void randomAppReturnsARandomAppWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());
            //String random = String.valueOf(appStore.getRandomApp());
            String apps = String.valueOf(appStore.randomApp());
            System.out.println(apps);
        }

        @Test
        void randomAppReturnsARandomNullWhenNoApps() {
            assertEquals(7, appStore.numberOfApps());
            //String random = String.valueOf(appStore.getRandomApp());
            String apps = String.valueOf(appStore.randomApp());
            System.out.println(apps);
        }


    }

    @Nested
    class SortingMethods {

        @Test
        void sortByNameAscendingReOrdersList() {
            assertEquals(7, appStore.numberOfApps());
            //checks the order of the objects in the list
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(0));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(2));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(5));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(3));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(1));
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(6));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(4));

            appStore.sortAppsByNameAscending();
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(0));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(3));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(5));
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(6));
        }

        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0,emptyAppStore.numberOfApps());
            emptyAppStore.sortAppsByNameAscending();
        }

    }

    @Nested
    class CountingMethods{

        @Test
        void numberOfEducationAppsCalculatedCorrectly() {
            assertEquals(2, appStore.numberOfEducationApps());
            assertEquals(0, emptyAppStore.numberOfEducationApps());
        }

        @Test
        void numberOfGameAppsCalculatedCorrectly() {
            assertEquals(2, appStore.numberOfGameApps());
            assertEquals(0, emptyAppStore.numberOfGameApps());
        }

        @Test
        void numberOfProductivityAppsCalculatedCorrectly() {
            assertEquals(3, appStore.numberOfProductivityApps());
            assertEquals(0, emptyAppStore.numberOfProductivityApps());
        }
    }

    /* appStore.addApp(edAppBelowBoundary);
        appStore.addApp(edAppAboveBoundary);
        appStore.addApp(prodAppOnBoundary);
        appStore.addApp(prodAppBelowBoundary);
        appStore.addApp(gameAppOnBoundary);
        appStore.addApp(gameAppAboveBoundary);
        appStore.addApp(prodAppInvalidData);*/
    //--------------------------------------------
    // Helper Methods
    //--------------------------------------------
    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerEAGames, "MazeRunner", 1,
                1.0, 2.99, true);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(developerApple, "Evernote", 1,
                1.0, 1.99, "Work");

        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

    @Test
    void isValidEducationIndexReturnsTrueForValidIndex() {
        assertTrue(appStore.isValidIndex(0));
        assertTrue(appStore.isValidIndex(4));
    }

    @Test
    void isValidGameIndexReturnsTrueForValidIndex() {
        assertTrue(appStore.isValidIndex(2));
        assertTrue(appStore.isValidIndex(3));
    }

    @Test
    void isValidProductivityIndexReturnsTrueForValidIndex() {
        assertTrue(appStore.isValidIndex(1));
        assertTrue(appStore.isValidIndex(7));
    }

    @Test
    void isValidEducationIndexReturnsFalseForInValidIndex() {
        assertFalse(emptyAppStore.isValidEducationAppIndex(0));
        assertFalse(emptyAppStore.isValidEducationAppIndex(1));

        assertFalse(appStore.isValidEducationAppIndex(-1));
        assertFalse(appStore.isValidEducationAppIndex(3));
    }

    @Test
    void isValidGameIndexReturnsFalseForInValidIndex() {
        assertFalse(emptyAppStore.isValidGameAppIndex(0));
        assertFalse(emptyAppStore.isValidGameAppIndex(1));

        assertFalse(appStore.isValidGameAppIndex(-1));
        assertFalse(appStore.isValidGameAppIndex(3));
    }

    @Test
    void isValidProductivityIndexReturnsFalseForInValidIndex() {
        assertFalse(emptyAppStore.isValidProductivityAppIndex(0));
        assertFalse(emptyAppStore.isValidProductivityAppIndex(1));

        assertFalse(appStore.isValidProductivityAppIndex(-1));
        assertFalse(appStore.isValidProductivityAppIndex(1));
    }



}
