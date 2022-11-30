package utils;

import java.util.ArrayList;

public class TaskUtility {

    // create a ArrayList String type

    private static ArrayList<String> taskUtility = new ArrayList<String>()
    {
        {
            add("Home");
            add("Work");
            add("Education");
            add("Holiday");
            add("Games");
        }
    };
    public static boolean isValidTask(String strToCheck)
    {
        for (int i = 0; i < taskUtility.size(); i++)
        {
            if (taskUtility.get(i).equalsIgnoreCase(strToCheck))
            {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getTaskUtility() {
        return taskUtility;
    }

}

