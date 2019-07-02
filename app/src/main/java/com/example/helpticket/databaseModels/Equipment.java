/*Objective:This class will store the different equipments that the technicians are called to */
package com.example.helpticket.databaseModels;

public class Equipment {
    //fields
    private int idEquipment;
    private String name;

    //constructor
    public Equipment(int idEquipment, String name) {
        this.setIdEquipment(idEquipment);
        this.setName(name);
    }


    //gets e sets
    public int getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
