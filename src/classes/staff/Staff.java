package classes.staff;

import java.io.Serializable;
import java.util.ArrayList;

public class Staff implements Serializable{
    private String name;
    private ArrayList<String> penTypes = new ArrayList<>() ;
    private Integer staffID;

    public Staff (String name, ArrayList<String> responsibleFor) {
        setName(name);
        for (String pen : responsibleFor) {
            setPenTypes(pen);
        }
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setPenTypes(String penType) { this.penTypes.add(penType); }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    //GETTERS

    public String getName() {
        return name;
    }

    public ArrayList<String> getPenTypes() { return penTypes; }

    public Integer getStaffID() {
        return staffID;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) { return false; }
        Staff a = (Staff) obj;
        return a.getStaffID().equals(this.getStaffID()) &&
                a.getName().equals(this.getName()) &&
                a.getPenTypes().equals(this.getPenTypes());
    }
}
