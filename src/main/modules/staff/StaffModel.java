package main.modules.staff;

import main.classes.staff.Staff;

import java.io.File;
import java.util.ArrayList;

public class StaffModel {
    protected static String filePath = "data/staff_data/";
    protected static ArrayList<Staff> allStaff = new ArrayList<>();

    //SETTERS

    public static void setAllStaff() {
    }


    //GETTERS

    public static ArrayList<Staff> getAllStaff() {
        return allStaff;
    }


    //PUBLIC DATA MANIPULATION

    public static void addStaff (Staff staff) {

    }

    public static void editStaff (Staff toFind, Staff toReplaceWith) {

    }

    public static void removeStaff (Staff toFind) {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Staff staff) {

    }

    private static Staff deserialize (File toRead) {
        return null;
    }

    //GENERAL PURPOSE

}
