package utils;

import java.util.ArrayList;

public class DriveUtilities {

    // create a ArrayList String type
    private static ArrayList<String> appTypes = new ArrayList<String>()
    {
        {
            add("Education");
            add("Game");
            add("Productivity");

        }
    };

    public static boolean isValidAppType(String strToCheck)
    {
        for (int i = 0; i < appTypes.size(); i++)
        {
            if (appTypes.get(i).equalsIgnoreCase(strToCheck))
            {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getCategories()
    {

        return appTypes;
    }

}
