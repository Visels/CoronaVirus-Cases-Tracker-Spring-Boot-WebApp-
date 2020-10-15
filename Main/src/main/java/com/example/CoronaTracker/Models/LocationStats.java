package com.example.CoronaTracker.Models;

public class LocationStats {

    private String state;
    private String Country;
    private String latestCases;
    private int delta;

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLatestCases() {
        return latestCases;
    }

    public void setLatestCases(String latestCases) {
        this.latestCases = latestCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", Country='" + Country + '\'' +
                ", latestCases='" + latestCases + '\'' +
                '}';
    }
}
