package com.techelevator.model;

public class Location {

    public Location() {
    }

    private double lat;
    private double lng;

    public Location(int lat, int lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}