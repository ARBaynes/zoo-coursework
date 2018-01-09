package main.classes.staff;

import java.io.Serializable;

public class Staff implements Serializable{
    private String name;
    private String preferredPenType = "";
    private Integer staffID;

    public Staff (String name) {
        setName(name);
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setPreferredPenType(String preferredPenType) {
        this.preferredPenType = preferredPenType;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    //GETTERS

    public String getName() {
        return name;
    }

    public String getPreferredPenType() {
        return preferredPenType;
    }

    public Integer getStaffID() {
        return staffID;
    }
}
