package classes.main;

import java.io.Serializable;

public class Weather implements Serializable {
    private static Integer ID;
    private static String main;
    private static String description;
    private static String temp;

    public static void init (Integer ID, String main, String description, String temp) {
        setID(ID);
        setMain(main);
        setDescription(description);
        setTemp(temp);
    }

    private static void setTemp(String temp) {
        Weather.temp = temp;
    }

    public static String getTemp() {
        return temp;
    }

    private static void setID(Integer ID) {
        Weather.ID = ID;
    }

    public static Integer getID() {
        return ID;
    }

    private static void setMain(String main) {
        Weather.main = main;
    }

    public static String getMain() {
        return main;
    }

    private static void setDescription(String description) {
        Weather.description = description;
    }

    public static String getDescription() {
        return description;
    }

}
