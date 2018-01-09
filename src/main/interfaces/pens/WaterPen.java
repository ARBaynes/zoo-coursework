package main.interfaces.pens;

public interface WaterPen {

    //Water Volume
    void setWaterVolume(Integer waterVolume);
    Integer getWaterVolume();

    //Water Type
    String getWaterType();
    void setWaterType(String waterType);

}
