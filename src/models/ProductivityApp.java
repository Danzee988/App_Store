package models;

import utils.TaskUtility;

public class ProductivityApp extends App {

    private String task = "";

    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost, String task){
        super(developer,appName,appSize,appVersion,appCost);
        setTask(task);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        if (TaskUtility.isValidTask(task)) {
            this.task = task;
        }
    }

    public Boolean isRecommendedApp(){
        if (getAppCost() >= 1.99 && calculateRating() > 3.0 && TaskUtility.isValidTask(task)){
            return true;
        }
        else
            return false;
    }

    public String appSummary(){
        return super.appSummary() + ", Task " + task ;
    }

    public String toString(){
        return super.toString() + ", Task: " + task + "\n";
    }


    @Override
    public String displayCondensed() {
        return null;
    }
}
