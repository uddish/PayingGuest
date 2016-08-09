package com.example.uddishverma.pg_app_beta;

import java.util.ArrayList;

/**
 * Created by UddishVerma on 09/08/16.
 */
public class PgDetails_POJO {

    public static class PgDetails   {
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

        public int getImageId() {
            return imageId;
        }

        public String getPgName() {
            return pgName;
        }

        public String getLocation() {
            return location;
        }

        public Boolean getWifi() {
            return wifi;
        }

        public Boolean getBreakfast() {
            return breakfast;
        }

        public Boolean getParking() {
            return parking;
        }

        public Boolean getMetro() {
            return metro;
        }
    }

    public static ArrayList<PgDetails> getPGDetails()   {
        ArrayList<PgDetails> details = new ArrayList<>(10);
        for (int i = 0; i < 4;i++) {
            details.add(new PgDetails(R.drawable.pg1, "NAME 1", "DWARKA", true, true, true, false));
            details.add(new PgDetails(R.drawable.pg2, "NAME 2", "NOIDA", false, true, false, false));
            details.add(new PgDetails(R.drawable.pg3, "NAME 3", "GURGAON", true, true, true, false));
            details.add(new PgDetails(R.drawable.pg4, "NAME 4", "DWARKA", false, true, true, true));
        }
        return details;
    }

}
