package models;

import utils.Utilities;

import java.util.ArrayList;

public abstract class App {

    private Developer developer;
    private String appName = "no app name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private ArrayList<Rating> ratings = new ArrayList<Rating>();

    public App(Developer developer, String appName, double appSize, double appVersion, double appCost){

        this.developer = developer;

        this.appName = appName;

        if (Utilities.validRangeExclIncl(appSize, 0, 1000)){
            this.appSize = appSize;
        }

        if (Utilities.greaterThanOrEqualTo(appVersion,1.0)){
            this.appVersion = appVersion;
        }

        if (Utilities.greaterThanOrEqualTo(appCost, 0.0)){
            this.appCost = appCost;
        }

    }

    //Getter Methods
    public Developer getDeveloper() {
        return developer;
    }

    public String getAppName() {
        return appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public double getAppVersion() {
        return appVersion;
    }

    public double getAppCost() {
        return appCost;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    //Setter Methods
    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppSize(double appSize) {
        if (Utilities.validRangeExclIncl(appSize, 0, 1000)) {
            this.appSize = appSize;
        }
    }

    public void setAppVersion(double appVersion) {
        if (Utilities.greaterThanOrEqualTo(appVersion,1.0)) {
            this.appVersion = appVersion;
        }
    }

    public void setAppCost(double appCost) {
        if (Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
            this.appCost = appCost;
        }
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    //Methods
    public String appSummary(){
        return appName + " (V" + appVersion + ") by " + developer + ", €" + appCost + ", Rating: " + calculateRating();
    }

    public boolean addRating(Rating rating){
        return ratings.add(rating);
    }

    public String listRatings(){
        if (ratings.isEmpty()){
            return "No ratings added";
        }else {
            String listOfRatings = "";
            for (int i = 0; i < ratings.size(); i++){
                listOfRatings += " " + i + ": " + ratings.get(i);
            }
            return listOfRatings;
        }
    }

    public double calculateRating(){
        if (!ratings.isEmpty()){
            double totalRating = 0;
            for (Rating rating : ratings){
                totalRating += rating.getNumberOfStars();
            }
            return Utilities.toTwoDecimalPlaces(totalRating / ratings.size());
        } else {
            return -1;
        }
    }

    public Boolean isRecommendedApp(){

        return true;
    }

    public String toString(){
        return appName + "(Version " + appVersion + ", " + developer + ", " +appSize + "MB, " + "Cost: " + appCost + ", Ratings (" + calculateRating() + ") " + listRatings();
    }

    public abstract String displayCondensed();
}
