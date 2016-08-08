package com.example.uddishverma.pg_app_beta;

/**
 * Created by Naman on 07-08-2016.
 */
public class PgDetails
{
    private int imageId;
    String pgName;
    String location;
    Boolean wifi;
    Boolean breakfast;

    public PgDetails(int imageId, String pgName, String location, Boolean wifi, Boolean breakfast) {
        this.imageId = imageId;
        this.pgName = pgName;
        this.location = location;
        this.wifi = wifi;
        this.breakfast = breakfast;
    }


    public int getImageId()
    {
        return imageId;
    }

    public String getPgName()
    {
        return pgName;
    }

    public String getLocation()
    {
        return location;
    }

    public Boolean getWifi()
    {
        return wifi;
    }

    public Boolean getBreakfast()
    {
        return breakfast;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }
}
