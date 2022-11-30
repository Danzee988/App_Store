package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductivityAppTest {

    private ProductivityApp pdAppBelowBoundary, pdAppOnBoundary, pdAppOutOfBoundary, pdAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");


    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), task(Home,Work,Education,Holiday,Games).
        pdAppBelowBoundary = new ProductivityApp(developerLego, "WeDo", 1, 1.0, 0, "Work");
        pdAppOnBoundary = new ProductivityApp(developerLego, "Spike", 1000, 2.0, 1.99, "Education");
        pdAppOutOfBoundary = new ProductivityApp(developerLego, "EV3", 1001, 3.5, 2.99, "Assignment");
        pdAppInvalidData = new ProductivityApp(developerLego, "", -1, 0, -1.00,"");

    }

    @AfterEach
    void tearDown() {
        pdAppBelowBoundary = pdAppOnBoundary = pdAppOutOfBoundary = pdAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerLego, pdAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, pdAppOnBoundary.getDeveloper());
            assertEquals(developerLego, pdAppOutOfBoundary.getDeveloper());
            assertEquals(developerLego, pdAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", pdAppBelowBoundary.getAppName());
            assertEquals("Spike", pdAppOnBoundary.getAppName());
            assertEquals("EV3", pdAppOutOfBoundary.getAppName());
            assertEquals("", pdAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, pdAppBelowBoundary.getAppSize());
            assertEquals(1000, pdAppOnBoundary.getAppSize());
            assertEquals(0, pdAppOutOfBoundary.getAppSize());
            assertEquals(0, pdAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, pdAppBelowBoundary.getAppVersion());
            assertEquals(2.0, pdAppOnBoundary.getAppVersion());
            assertEquals(3.5, pdAppOutOfBoundary.getAppVersion());
            assertEquals(1.0, pdAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, pdAppBelowBoundary.getAppCost());
            assertEquals(1.99, pdAppOnBoundary.getAppCost());
            assertEquals(2.99, pdAppOutOfBoundary.getAppCost());
            assertEquals(0, pdAppInvalidData.getAppCost());
        }

        @Test
        void getTask() {
            assertEquals("Work", pdAppBelowBoundary.getTask());
            assertEquals("Education", pdAppOnBoundary.getTask());
            assertEquals("", pdAppOutOfBoundary.getTask());
            assertEquals("", pdAppInvalidData.getTask());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, pdAppBelowBoundary.getDeveloper());
            pdAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, pdAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", pdAppBelowBoundary.getAppName());
            pdAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", pdAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, pdAppBelowBoundary.getAppSize());

            pdAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, pdAppBelowBoundary.getAppSize()); //update

            pdAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, pdAppBelowBoundary.getAppSize()); //no update

            pdAppBelowBoundary.setAppSize(2);
            assertEquals(2, pdAppBelowBoundary.getAppSize()); //update

            pdAppBelowBoundary.setAppSize(0);
            assertEquals(2, pdAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, pdAppBelowBoundary.getAppVersion());

            pdAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, pdAppBelowBoundary.getAppVersion()); //update

            pdAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, pdAppBelowBoundary.getAppVersion()); //no update

            pdAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, pdAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, pdAppBelowBoundary.getAppCost());

            pdAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, pdAppBelowBoundary.getAppCost()); //update

            pdAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, pdAppBelowBoundary.getAppCost()); //no update

            pdAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, pdAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setTask() {
            //Validation: level(1-10)
            assertEquals("Work", pdAppBelowBoundary.getTask());

            pdAppBelowBoundary.setTask("Education");
            assertEquals("Education", pdAppBelowBoundary.getTask()); //update

            pdAppBelowBoundary.setTask("Bob");
            assertEquals("Education", pdAppBelowBoundary.getTask()); //no update

            pdAppBelowBoundary.setTask("Work");
            assertEquals("Work", pdAppBelowBoundary.getTask()); //update
        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void toStringReturnsCorrectString() {
            ProductivityApp pdApp = setupProductivityAppWithRating(4, 5);
            String stringContents = pdApp.toString();

            assertTrue(stringContents.contains(pdApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + pdApp.getAppVersion()));
            assertTrue(stringContents.contains(pdApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(pdApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + pdApp.getAppCost()));
            assertTrue(stringContents.contains("Task: " + pdApp.getTask()));
            assertTrue(stringContents.contains("Ratings (" + pdApp.calculateRating()));

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
            ProductivityApp pdApp = setupProductivityAppWithRating(4, 5);

            //now setting appCost to 0.99 so app should not be recommended now
            pdApp.setAppCost(0.99);
            assertFalse(pdApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan3AndAHalf() {
            //setting all conditions to true with ratings of 3 and 3 (i.e. 3.0)
            ProductivityApp pdApp = setupProductivityAppWithRating(3, 3);
            //verifying recommended app returns false (rating not high enough
            assertFalse(pdApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            ProductivityApp pdApp = new ProductivityApp(developerLego, "WeDo", 1,
                    1.0, 1.00,  "Games");
            //verifying recommended app returns true
            assertFalse(pdApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            //setting all conditions to true with ratings of 3 and 4 (i.e. 3.5)
            ProductivityApp pdApp = setupProductivityAppWithRating(4, 5);

            //verifying recommended app returns true

            System.out.println(pdApp.isRecommendedApp());
            assertTrue(pdApp.isRecommendedApp());
        }

    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        ProductivityApp pdApp = new ProductivityApp(developerLego, "WeDo", 1,
                1.0, 1.99,  "Games");
        pdApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        pdApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended productivity app]
        assertEquals(2, pdApp.getRatings().size());  //two ratings are added
        assertEquals(1.99, pdApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), pdApp.calculateRating(), 0.01);
        assertEquals("Games", pdApp.getTask());

        return pdApp;
    }
}
