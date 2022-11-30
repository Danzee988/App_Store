package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameAppTest {

    private GameApp gAppBelowBoundary, gAppOnBoundary, gAppAboveBoundary, gAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    

    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), isMultiplayer(True/False).
        gAppBelowBoundary = new GameApp(developerLego, "WeDo", 1, 1.0, 0, false);
        gAppOnBoundary = new GameApp(developerLego, "Spike", 1000, 2.0, 1.99, true);
        gAppAboveBoundary = new GameApp(developerLego, "EV3", 1001, 3.5, 2.99, false);
        gAppInvalidData = new GameApp(developerLego, "", -1, 0, -1.00,false);
    }

    @AfterEach
    void tearDown() {
        gAppBelowBoundary = gAppOnBoundary = gAppAboveBoundary = gAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerLego, gAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, gAppOnBoundary.getDeveloper());
            assertEquals(developerLego, gAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, gAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", gAppBelowBoundary.getAppName());
            assertEquals("Spike", gAppOnBoundary.getAppName());
            assertEquals("EV3", gAppAboveBoundary.getAppName());
            assertEquals("", gAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, gAppBelowBoundary.getAppSize());
            assertEquals(1000, gAppOnBoundary.getAppSize());
            assertEquals(0, gAppAboveBoundary.getAppSize());
            assertEquals(0, gAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, gAppBelowBoundary.getAppVersion());
            assertEquals(2.0, gAppOnBoundary.getAppVersion());
            assertEquals(3.5, gAppAboveBoundary.getAppVersion());
            assertEquals(1.0, gAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, gAppBelowBoundary.getAppCost());
            assertEquals(1.99, gAppOnBoundary.getAppCost());
            assertEquals(2.99, gAppAboveBoundary.getAppCost());
            assertEquals(0, gAppInvalidData.getAppCost());
        }

        @Test
        void isMultiplayer() {
            assertEquals(false, gAppBelowBoundary.isMultiplayer());
            assertEquals(true, gAppOnBoundary.isMultiplayer());
            assertEquals(false, gAppAboveBoundary.isMultiplayer());
            assertEquals(false, gAppInvalidData.isMultiplayer());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, gAppBelowBoundary.getDeveloper());
            gAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, gAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", gAppBelowBoundary.getAppName());
            gAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", gAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, gAppBelowBoundary.getAppSize());

            gAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, gAppBelowBoundary.getAppSize()); //update

            gAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, gAppBelowBoundary.getAppSize()); //no update

            gAppBelowBoundary.setAppSize(2);
            assertEquals(2, gAppBelowBoundary.getAppSize()); //update

            gAppBelowBoundary.setAppSize(0);
            assertEquals(2, gAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, gAppBelowBoundary.getAppVersion());

            gAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, gAppBelowBoundary.getAppVersion()); //update

            gAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, gAppBelowBoundary.getAppVersion()); //no update

            gAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, gAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, gAppBelowBoundary.getAppCost());

            gAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, gAppBelowBoundary.getAppCost()); //update

            gAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, gAppBelowBoundary.getAppCost()); //no update

            gAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, gAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setMultiplayer() {
            //Validation: Multiplayer(True/False)
            assertEquals(false, gAppBelowBoundary.isMultiplayer());

            gAppBelowBoundary.setMultiplayer(true);
            assertEquals(true, gAppBelowBoundary.isMultiplayer()); //update

            gAppBelowBoundary.setMultiplayer(false);
            assertEquals(false, gAppBelowBoundary.isMultiplayer()); //no update

            gAppBelowBoundary.setMultiplayer(true);
            assertEquals(true, gAppBelowBoundary.isMultiplayer()); //update
        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            GameApp gApp = setupGameAppWithRating(3, 4);
            String stringContents = gApp.appSummary();

            assertTrue(stringContents.contains(", Multiplayer " + gApp.isMultiplayer()));
            assertTrue(stringContents.contains(gApp.getAppName() + "(V" + gApp.getAppVersion()));
            assertTrue(stringContents.contains(gApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + gApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + gApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            GameApp gApp = setupGameAppWithRating(3, 4);
            String stringContents = gApp.toString();

            assertTrue(stringContents.contains(gApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + gApp.getAppVersion()));
            assertTrue(stringContents.contains(gApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(gApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + gApp.getAppCost()));
            assertTrue(stringContents.contains("Multiplayer: " + gApp.isMultiplayer()));
            assertTrue(stringContents.contains("Ratings (" + gApp.calculateRating()));

            //contains list of ratings too
            assertTrue(stringContents.contains("John Doe"));
            assertTrue(stringContents.contains("Very Good"));
            assertTrue(stringContents.contains("Jane Doe"));
            assertTrue(stringContents.contains("Excellent"));
        }

    }

    @Nested
    class RecommendedApp {

        @Test
        void appIsNotRecommendedWhenInAppCostIs99c() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            GameApp gApp = setupGameAppWithRating(3, 4);

            //now setting appCost to 0.99 so app should not be recommended now
            gApp.setAppCost(0.99);
            assertFalse(gApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan4() {
            //setting all conditions to true with ratings of 3 and 3 (i.e. 3.0)
            GameApp gApp = setupGameAppWithRating(3, 3);
            //verifying recommended app returns false (rating not high enough
            assertFalse(gApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            GameApp gApp = new GameApp(developerLego, "WeDo", 1,
                    1.0, 4.00,  true);
            //verifying recommended app returns true
            assertFalse(gApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 4 and 5
            GameApp gApp = setupGameAppWithRating(4, 5);

            //verifying recommended app returns true
            assertTrue(gApp.isRecommendedApp());
        }

    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        GameApp gApp = new GameApp(developerLego, "WeDo", 1,
                1.0, 4.00,  true);
        gApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        gApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, gApp.getRatings().size());  //two ratings are added
        assertEquals(4.0, gApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), gApp.calculateRating(), 0.01);
        assertEquals(true, gApp.isMultiplayer());

        return gApp;
    }
}
