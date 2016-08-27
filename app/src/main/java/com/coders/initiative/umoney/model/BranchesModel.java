package com.coders.initiative.umoney.model;

/**
 * Created by Kira on 8/27/2016.
 */
public class BranchesModel {

    /**
     * id": 1015,
     "name": "ABS-CBN",
     "address": "West Wing G/F ELJ Communication Center, ABS-CBN Broadcast Center Sgt. Esguerra St. South Triangle, Quezon City",
     "latitude": 14.639915,
     "longitude": 121.036476
     */

    /**
     * JSON FIELDS
     */
    public static final String JSON_ID = "id";
    public static final String JSON_NAME = "name";
    public static final String JSON_ADDRESS = "address";
    public static final String JSON_LATITUDE = "latitude";
    public static final String JSON_LONGTITUDE = "longitude";

    private String id;
    private String name;
    private String address;
    private String latitude;
    private String longtitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }
}
