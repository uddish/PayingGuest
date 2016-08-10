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
        String ownerName;
        double contactNo;
        String email;
        double rent;
        double depositAmount;
        String extraFeatures;
        Boolean wifi;
        Boolean breakfast;
        Boolean parking;
    // add metro boolean
        public PgDetails(int imageId, String pgName, String location,
                         String ownerName, double contactNo, String email,
                         double rent, double depositAmount, String extraFeatures, Boolean wifi,
                         Boolean breakfast, Boolean parking) {
            this.imageId = imageId;
            this.pgName = pgName;
            this.location = location;
            this.ownerName = ownerName;
            this.contactNo = contactNo;
            this.email = email;
            this.rent = rent;
            this.depositAmount = depositAmount;
            this.extraFeatures = extraFeatures;
            this.wifi = wifi;
            this.breakfast = breakfast;
            this.parking = parking;
        }
        //This constructor doesn't contain image id
        public PgDetails(String pgName, String location,
                         String ownerName, double contactNo, String email,
                         double rent, double depositAmount, String extraFeatures, Boolean wifi,
                         Boolean breakfast, Boolean parking) {

            this.pgName = pgName;
            this.location = location;
            this.ownerName = ownerName;
            this.contactNo = contactNo;
            this.email = email;
            this.rent = rent;
            this.depositAmount = depositAmount;
            this.extraFeatures = extraFeatures;
            this.wifi = wifi;
            this.breakfast = breakfast;
            this.parking = parking;
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

        public String getOwnerName() {
            return ownerName;
        }

        public double getContactNo() {
            return contactNo;
        }

        public String getEmail() {
            return email;
        }

        public double getRent() {
            return rent;
        }

        public double getDepositAmount() {
            return depositAmount;
        }

        public String getExtraFeatures() {
            return extraFeatures;
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

    }

    public static ArrayList<PgDetails> getPGDetails()   {
        ArrayList<PgDetails> details = new ArrayList<>(10);
        for (int i = 0; i < 4;i++) {
            details.add(new PgDetails(R.drawable.pg1, "NAME 1", "DWARKA", "UDDISH", 1234, "xyz@gmail"
                    , 100, 100, "none", true, true, true));

            details.add(new PgDetails(R.drawable.pg2, "NAME 2", "NOIDA", "NAMAN", 1234, "xyz@gmail"
                    , 100, 100, "none", true, true, true));

            details.add(new PgDetails(R.drawable.pg3, "NAME 3", "GURGAON", "UDDISH", 1234, "xyz@gmail"
                    , 100, 100, "none", true, true, true));

            details.add(new PgDetails(R.drawable.pg4, "NAME 4", "DWARKA", "NAMAN", 1234, "xyz@gmail"
                    , 100, 100, "none", true, true, true));
        }
        return details;
    }

}
