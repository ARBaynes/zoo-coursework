package models.staff;

import classes.staff.Staff;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class StaffModel {
    private static String filePath = "data/staff_data/";
    private static ArrayList<Staff> allStaff = new ArrayList<>();

    //SETTERS

    public static void setAllStaff() {
        allStaff.clear();
        File folder = new File(filePath);
        ArrayList<Staff> staff = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            staff.add(deserialize(fileEntry));
        }
        allStaff.addAll(staff);
    }


    //GETTERS

    public static ArrayList<Staff> getAllStaff() {
        if (allStaff.isEmpty()) {
            setAllStaff();
        }
        return allStaff;
    }

    public static ArrayList<Integer> getAllStaffIDs() {
        if (allStaff.isEmpty()) { setAllStaff(); }
        ArrayList<Integer> staffIDHashMap = new ArrayList<>();
        for (Staff staff : allStaff) {
            staffIDHashMap.add(staff.getStaffID());
        }
        return staffIDHashMap;
    }

    public static Staff getStaffByID (Integer ID) {
        for ( Staff staff : allStaff ) {
            if (staff.getStaffID().equals(ID)) {
                return staff;
            }
        }
        return null;
    }

    public static ArrayList<Staff> getAllStaffBy (String penType) {
        ArrayList<Staff> staffByPens = new ArrayList<>();
        for ( Staff staff : allStaff ) {
            for (String staffPenType : staff.getPenTypes()) {
                if (staffPenType.equals(penType)) {
                    staffByPens.add(staff);
                }
            }
        }
        return staffByPens;
    }

    public static ArrayList<Integer> extractStaffIDs (ArrayList<Staff> staffToCheck) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for ( Staff staff : staffToCheck ) {
            integerArrayList.add(staff.getStaffID());
        }
        return integerArrayList;
    }


    //PUBLIC DATA MANIPULATION

    public static void addStaff (Staff staff) {
        serialize(staff);
        setAllStaff();
    }

    public static void editStaff (Staff toEdit) {
        addStaff(toEdit);
    }

    public static void removeStaff (Staff toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getStaffID() + ".staff");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllStaff();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Staff staff) {
        if (staff.getStaffID() == null) { staff.setStaffID(createID(filePath)); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath + staff.getStaffID() +".staff");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(staff);
            out.close();
            fileOut.close();
            System.out.println("Serialized staff data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Staff deserialize (File toRead) {
        Staff staff=null ;
        try
        {
            FileInputStream fileInputStream = new FileInputStream(toRead);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            staff = (Staff) objectInputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return staff;
    }

    //GENERAL PURPOSE

    private static Integer createID (String filePath ) {
        Integer ID = 0;
        File folder = new File(filePath);
        if (folder.exists()) {
            ID = folder.listFiles().length + 1;
        }
        return ID;
    }

}
