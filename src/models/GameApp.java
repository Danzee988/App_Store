package models;

import utils.Utilities;

public class GameApp extends App {

    private boolean isMultiplayer = false;

    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer){
        super(developer,appName,appSize,appVersion,appCost);
        setMultiplayer(isMultiplayer);
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {

        isMultiplayer = multiplayer;
    }



    public Boolean isRecommendedApp(){
        if (isMultiplayer == true && calculateRating() >= 4.0 && getAppCost() >= 2.99) {
            return true;
        }else
            return false;
    }

    public String appSummary(){

        return super.appSummary() + ", Multiplayer " + isMultiplayer ;
    }

    public String toString(){

        return super.toString() + ", Multiplayer: " + isMultiplayer + "\n";
    }

    @Override
    public String displayCondensed() {
        return null;
    }
}