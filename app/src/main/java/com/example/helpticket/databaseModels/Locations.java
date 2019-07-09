/*Objective:This class will store the different locations of the factory, for example
* NAV1
* NAV2
* NAV3
* NAV4
* NAV5
* */


package com.example.helpticket.databaseModels;

public class Locations {

    //fields
    private int idLocations;
    private String name;

    //constructor


    public Locations() {
    }

    public Locations(int idLocations, String name) {
        this.idLocations = idLocations;
        this.name = name;
    }

    //gets and sets

    public int getIdLocations() {
        return idLocations;
    }

    public void setIdLocations(int idLocations) {
        this.idLocations = idLocations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
