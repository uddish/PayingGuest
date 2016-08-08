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
    Boolean parking;
    Boolean metro;

    public PgDetails(int imageId, String pgName, String location, Boolean wifi, Boolean breakfast, Boolean parking, Boolean metro) {
        this.imageId = imageId;
        this.pgName = pgName;
        this.location = location;
        this.wifi = wifi;
        this.breakfast = breakfast;
        this.parking = parking;
        this.metro = metro;

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

    public Boolean getMetro() {
        return metro;
    }

    public Boolean getParking() {
        return parking;
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

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public void setMetro(Boolean metro) {
        this.metro = metro;
    }
}
