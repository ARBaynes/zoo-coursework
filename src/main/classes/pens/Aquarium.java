package main.classes.pens;

import main.interfaces.HighPen;
import main.interfaces.WaterPen;

import java.io.Serializable;

public class Aquarium extends Pen implements HighPen, WaterPen, Serializable{
    private Double height;
    private Double waterVolume;
    private String waterType;

    /*private Integer penID;
    private Double length;
    private Double width;
    private Double temperature;
    private ArrayList<Animal> containedAnimals = new ArrayList<>();
    private Staff staffResponsible;
    public  String penType;*/

    public Aquarium (Double length, Double width, Double temperature, Double height, Double waterVolume, String waterType) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setWaterVolume(waterVolume);
        setWaterType(waterType);
        setHeight(height);
        setPenType();
    }

    @Override
    public void setPenType() { penType = "aquarium"; }

    @Override
    public void setHeight(Double height) { this.height = height; }

    @Override
    public Double getHeight() { return height; }

    @Override
    public void setWaterVolume(Double waterVolume) { this.waterVolume = waterVolume; }

    @Override
    public Double getWaterVolume() { return waterVolume; }

    @Override
    public void setWaterType(String waterType) { this.waterType = waterType;    }

    @Override
    public String getWaterType() { return waterType; }
}
