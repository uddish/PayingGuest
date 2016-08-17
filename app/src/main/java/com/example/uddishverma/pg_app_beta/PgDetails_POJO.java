package com.example.uddishverma.pg_app_beta;

import java.util.ArrayList;

/**
 * Created by UddishVerma on 09/08/16.
 */
public class PgDetails_POJO {

    //TODO add support for imageID

    public static class PgDetails   {
//        private int imageId;
        String id;
        String pgName;
        String ownerName;
         double contactNo;
        String email;
        double rent;
        double depositAmount;
        String extraFeatures;
        Boolean wifi;
        Boolean breakfast;
        Boolean parking;
        String addressOne;
        String addressTwo;
        String city;
        String state;
        double pinCode;
        Boolean ac;
        Boolean lunch;
        Boolean dinner;
        Boolean roWater;
        Boolean security;
        Boolean tv;
        Boolean hotWater;
        Boolean fridge;
        String preference;
        String genderPreference;

    // add metro boolean
//        public PgDetails(int imageId, String pgName, String location,
//                         String ownerName, double contactNo, String email,
//                         double rent, double depositAmount, String extraFeatures, Boolean wifi,
//                         Boolean breakfast, Boolean parking) {
//            this.imageId = imageId;
//            this.pgName = pgName;
//            this.location = location;
//            this.ownerName = ownerName;
//            this.contactNo = contactNo;
//            this.email = email;
//            this.rent = rent;
//            this.depositAmount = depositAmount;
//            this.extraFeatures = extraFeatures;
//            this.wifi = wifi;
//            this.breakfast = breakfast;
//            this.parking = parking;
//        }

        //This constructor doesn't contain image id
        public PgDetails(String id, String pgName, String ownerName, double contactNo, String email,
                         double rent, double depositAmount, String extraFeatures, Boolean wifi,
                         Boolean breakfast, Boolean parking, Boolean ac, Boolean lunch ,Boolean dinner,
                         Boolean roWater, Boolean security, Boolean tv, Boolean hotWater,Boolean fridge,
                         String addressOne, String addressTwo, String city,
                         String state, double pinCode, String preference, String genderPreference) {

            this.id = id;
            this.pgName = pgName;
            this.ownerName = ownerName;
            this.contactNo = contactNo;
            this.email = email;
            this.rent = rent;
            this.depositAmount = depositAmount;
            this.extraFeatures = extraFeatures;
            this.wifi = wifi;
            this.breakfast = breakfast;
            this.parking = parking;
            this.ac = ac;
            this.lunch = lunch;
            this.dinner = dinner;
            this.roWater = roWater;
            this.security = security;
            this.tv = tv;
            this.hotWater = hotWater;
            this.fridge = fridge;
            this.addressOne = addressOne;
            this.addressTwo = addressTwo;
            this.city = city;
            this.state = state;
            this.pinCode = pinCode;
            this.preference = preference;
            this.genderPreference = genderPreference;
        }

        public PgDetails()  {
        }

//        public int getImageId() {
//            return imageId;
//        }

        public String getPgName() {
            return pgName;
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

        public String getAddressOne() {
            return addressOne;
        }

        public String getAddressTwo() {
            return addressTwo;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public double getPinCode() {
            return pinCode;
        }

        public String getId() {
            return id;
        }

        public Boolean getAc() {
            return ac;
        }

        public Boolean getLunch() {
            return lunch;
        }

        public Boolean getDinner() {
            return dinner;
        }

        public Boolean getRoWater() {
            return roWater;
        }

        public Boolean getSecurity() {
            return security;
        }

        public Boolean getTv() {
            return tv;
        }

        public Boolean getHotWater() {
            return hotWater;
        }

        public Boolean getFridge() {
            return fridge;
        }

        public String getPreference() {
            return preference;
        }

        public String getGenderPreference() {
            return genderPreference;
        }


    }

}
