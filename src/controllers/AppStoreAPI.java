package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.ISerializer;
import utils.Utilities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI implements ISerializer {

    private List<App> apps;

    private Random randomApp = new Random();
    public AppStoreAPI(){
        apps = new LinkedList<App>();
    }

    //Getters
    public List<App> getApps() {
        return apps;
    }


    //Setters
    public void setApps(List<App> apps) {
        this.apps = apps;
    }


    //Helper Methods
    public boolean isValidIndex(int index){

        return (index >= 0) && (index < apps.size());
    }

    public boolean isValidAppName(String nameToCheck){
        int i;
        String name = nameToCheck.toLowerCase();
        for (i = 0; i < apps.size();i++){
            if (apps.get(i).equals(name)){
              return true;
            }
        }
        return false;
    }

    public boolean isValidEducationAppIndex(int index) {
        if (isValidIndex(index)) {
            return (apps.get(index)) instanceof EducationApp;
        }
        return false;
    }

    public boolean isValidGameAppIndex(int index) {
        if (isValidIndex(index)) {
            return (apps.get(index)) instanceof GameApp;
        }
        return false;
    }

    public boolean isValidProductivityAppIndex(int index) {
        if (isValidIndex(index)) {
            return (apps.get(index)) instanceof ProductivityApp;
        }
        return false;
    }

    //CRUD Methods
    public boolean addApp(App app){
        return apps.add(app);
    }

    public App deleteAppByIndex(int indexToDelete){
        if (isValidIndex(indexToDelete)){
            return apps.remove(indexToDelete);
        }
        return null;
    }

    public App getAppByIndex(int searchIndex){
        if (isValidIndex(searchIndex)){
            return apps.get(searchIndex);
        }
        return null;
    }

    public String getAppByName(String searchName){
        String matchingName = "";
        for (App app : apps){
            if (app.getAppName().toLowerCase().contains(searchName.toLowerCase())){
                matchingName += app ;
            }
        }
        if (matchingName.equals("")){
            return "No apps with that name";
        }
        else {
            return matchingName;
        }
    }

    public boolean updateEducationApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, int level){
        //Looks for the object bt the index
        App foundEdApp = getAppByIndex(indexToUpdate);

        //if the object exists, update it with the parameters passed
        if ((foundEdApp != null) && (foundEdApp instanceof EducationApp)){
            foundEdApp.setAppCost(appCost);
            foundEdApp.setAppName(appName);
            foundEdApp.setAppSize(appSize);
            foundEdApp.setAppVersion(appVersion);
            foundEdApp.setDeveloper(developer);
            ((EducationApp) foundEdApp).setLevel(level);
            return true;
        }

        //if the object was not found return false
        return false;
    }

    //Updating Methods
    public boolean updateGameApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer){
        //Looks for the object bt the index
        App foundGameApp = getAppByIndex(indexToUpdate);

        //if the object exists, update it with the parameters passed
        if ((foundGameApp != null) && (foundGameApp instanceof GameApp)){
            foundGameApp.setAppCost(appCost);
            foundGameApp.setAppName(appName);
            foundGameApp.setAppSize(appSize);
            foundGameApp.setAppVersion(appVersion);
            foundGameApp.setDeveloper(developer);
            ((GameApp) foundGameApp).setMultiplayer(isMultiplayer);
            return true;
        }

        //if the object was not found return false
        return false;
    }

    public boolean updateProdApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, String task){
        //Looks for the object bt the index
        App foundProdApp = getAppByIndex(indexToUpdate);

        //if the object exists, update it with the parameters passed
        if ((foundProdApp != null) && (foundProdApp instanceof ProductivityApp)){
            foundProdApp.setAppCost(appCost);
            foundProdApp.setAppName(appName);
            foundProdApp.setAppSize(appSize);
            foundProdApp.setAppVersion(appVersion);
            foundProdApp.setDeveloper(developer);
            ((ProductivityApp) foundProdApp).setTask(task);
            return true;
        }

        //if the object was not found return false
        return false;
    }


    //Reporting Methods
    public String listAllApps(){
        if (apps.isEmpty()){
            return "No apps";
        }else {
            String listOfApps = "";
            for (int i = 0; i < apps.size(); i++){
                listOfApps += i + ": " + apps.get(i);
            }
            return listOfApps;
        }

    }

    public String listSummaryOfAllApps(){
        if (apps.isEmpty()){
            return "No apps added";
        }else {
            String summaryOfAllApps = "";
            for (App app : apps){
                //int i = 0;
                summaryOfAllApps += apps.indexOf(app) + ": "+ app.appSummary() +"\n";
                //i++;
            }
            return summaryOfAllApps;
        }
    }

    public String listAllGameApps(){
       String listOfAllGames = "";
       for (App app : apps){
           if (app instanceof GameApp){
               listOfAllGames += apps.indexOf(app) + ": " + app.toString() + "\n";
           }
       }
       if (listOfAllGames.isEmpty()){
           return "No Game apps added";
       } else
           return listOfAllGames;
    }

    public String listAllEducationApps(){
        String listOfAllEducationApps = "";
        for (App app : apps){
            if (app instanceof EducationApp){
                listOfAllEducationApps += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (listOfAllEducationApps.isEmpty()){
            return "No Education apps added";
        } else
            return listOfAllEducationApps;
    }

    public String listAllProductivityApps(){
        String listOfAllProductivityApps = "";
        for (App app : apps){
            if (app instanceof ProductivityApp){
                listOfAllProductivityApps += apps.indexOf(app) + ": " + app.toString() + "\n";
            }
        }
        if (listOfAllProductivityApps.isEmpty()){
            return "No Productivity apps added";
        } else
            return listOfAllProductivityApps;
    }

    public String listAllAppsByName(String appName){
        if (apps.isEmpty()){
            return "No Apps Added";
        }
        String listByName = "";
        for (App app : apps) {
            if (app.getAppName().equals(appName)) {
                listByName += app;
            }
        }
        if (isValidAppName(appName)){
            return "No apps for name" + appName + "exist";
        }
        return listByName;
    }

    public String listAllAppsAboveOrEqualAGivenStarRating(int rating){
        if (apps.isEmpty()){
            return "No Apps Added";
        }
        String listOfAppsForRating = "";
        for (App app : apps) {
            if (app.calculateRating() >= rating) {
                listOfAppsForRating += app;
            }
        }
        if (listOfAppsForRating.isEmpty()){
            return "No apps have a rating of" + rating + " or above";
        }
        return listOfAppsForRating;
    }

    public String listAllRecommendedApps() {
        String listOfRecommendedGameApps = "";
        String listOfRecommendedEducationApps = "";
        String listOfRecommendedProductivityApps = "";
        for (App app : apps) {
            if (app instanceof GameApp) {
                if (app.isRecommendedApp()) {
                    listOfRecommendedGameApps += app.toString();
                }
            }
            if (app instanceof EducationApp) {
                if (app.isRecommendedApp()) {
                    listOfRecommendedEducationApps += app.toString();
                }
            }
            if (app instanceof ProductivityApp) {
                if (app.isRecommendedApp()) {
                    listOfRecommendedProductivityApps += app.toString();
                }
            }
            //return "Recommended Apps" + "\n" + "Game Apps: " + listOfRecommendedGameApps + "\n" + "Education Apps: " + listOfRecommendedEducationApps + "\n" + "Productivity Apps" + listOfRecommendedProductivityApps + "\n";
        }
        String listOfRecommendedApps = "Recommended Apps" + "\n" + "Game Apps: " + listOfRecommendedGameApps + "\n" + "Education Apps: " + listOfRecommendedEducationApps + "\n" + "Productivity Apps: " + listOfRecommendedProductivityApps + "\n";
        if (listOfRecommendedGameApps.isEmpty() && listOfRecommendedEducationApps.isEmpty() && listOfRecommendedProductivityApps.isEmpty()) {
            return "No recommended apps";
        } else
            System.out.println(listOfRecommendedApps);
        return listOfRecommendedApps;
    }

    public String listAllAppsByChosenDeveloper(String developerName){
        if (apps.isEmpty()){
            return "No Apps Added";
        }
        String listByDeveloper = "";
        for (App app : apps) {
            if (app.getDeveloper().getDeveloperName() == developerName) {
                listByDeveloper += app;
            }
        }
        if (listByDeveloper.isEmpty()){
            return "No apps for developer" + developerName + "exist";
        }
        return listByDeveloper;
    }

    public App cheapestApp(){
        if (!apps.isEmpty()){
            App cheapestApp = apps.get(0);
            for (App app : apps){
                if (app.getAppCost() < cheapestApp.getAppCost())
                    cheapestApp = app;
            }
            return cheapestApp;
        }else {
            return null;
        }
    }

    public double averageAppCost(){
        if (!apps.isEmpty()){
            double totalCost =0;
            for (App app : apps){
                totalCost += app.getAppCost();
            }
            return Utilities.toTwoDecimalPlaces(totalCost / apps.size());
        }
        else
            return -1;
    }

    //Random App
    public App randomApp() {
        if (apps.isEmpty()) {
            return null;
        } else {
            int random = randomApp.nextInt(apps.size());
            return apps.get(random);
        }
    }


    public int numberOfAppsByChosenDeveloper(String checkDeveloper){
        int amountOfApps = 0;
        for (App app : apps){
            if (app.getDeveloper().getDeveloperName() == checkDeveloper){
                amountOfApps++;
            }
        }
        return amountOfApps;
    }

    public int numberOfApps(){

        return apps.size();
    }

    public int numberOfEducationApps(){
        int number = 0;
        for (App app : apps){
            if (app instanceof EducationApp){
                number++;
            }
        }
        return number;
    }

    public int numberOfGameApps(){
        int number = 0;
        for (App app : apps){
            if (app instanceof GameApp){
                number++;
            }
        }
        return number;
    }

    public int numberOfProductivityApps(){
        int number = 0;
        for (App app : apps){
            if (app instanceof ProductivityApp){
                number++;
            }
        }
        return number;
    }

    //Sorting Methods
    public void sortAppsByNameAscending(){
        for (int i = apps.size() - 1; i >= 0; i--){
            int highestIndex = 0;
            for (int j = 0; j <= i; j++){
                if (apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0){
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    private void swapApps(List<App> amount, int i, int j){
        App smaller = apps.get(i);
        App bigger = apps.get(j);

        apps.set(i,bigger);
        apps.set(j,smaller);
    }


    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }

}