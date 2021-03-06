package com.example.notalone_covid19;

import android.util.Log;

public class RiskGroupPerson {
    private String id;
    private String fullName;
    private String Address;
    private double longitude;
    private double latitude;
    private int helperID;
    private String phone;
    private String status;

    public RiskGroupPerson() {
    }

    public RiskGroupPerson(String id, String fullName, String address, double longitude, double latitude, int helperID, String phone, String status) {
        this.id = id;
        this.fullName = fullName;
        Address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.helperID = helperID;
        this.phone = phone;
        this.status = status;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getHelperID() {
        return helperID;
    }

    public void setHelperID(int helperID) {
        this.helperID = helperID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Double[] getAddressAsDoubleArray(){
//        String[]latLongString = Address.split(",");
//        try {
//            Double[] latlong = new Double[]{
//                    Double.valueOf(latLongString[0]),
//                    Double.valueOf(latLongString[1])
//            };
//            return latlong;
//        }catch(ArrayIndexOutOfBoundsException e){
//            Log.i("latitude/longitude", "Could not parse latitude/longitude \n" + e);
//            return null;
//        }
//    }

    @Override
    public String toString() {
        return "RiskGroupPerson{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Address='" + Address + '\'' +
                ", helperID=" + helperID +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || (o instanceof RiskGroupPerson)) return false;
        RiskGroupPerson r = (RiskGroupPerson)o;
        return r.getId().equals(id);
    }
}