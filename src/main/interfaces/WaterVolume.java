package main.interfaces;

public interface WaterVolume {

    void setWaterDepth (Double waterDepth);
    Double getWaterDepth ();

    void setWaterLength (Double waterLength);
    Double getWaterLength ();

    void setWaterWidth (Double waterWidth);
    Double getWaterWidth ();

    Double getWaterVolume ();
    Double getCurrentWaterVolume ();
}
