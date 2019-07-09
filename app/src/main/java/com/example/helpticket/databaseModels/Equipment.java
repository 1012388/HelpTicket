/*Objective:This class will store the different equipments that the technicians are called to */
package com.example.helpticket.databaseModels;

public class Equipment {
    //fields
    private int idEquipment;
    private Employee idEmployee;
    private Locations idLocations;
    private String name;

    //constructor


    public Equipment() {
    }

    public Equipment(int idEquipment, Employee idEmployee, Locations idLocations, String name) {
        this.idEquipment = idEquipment;
        this.idEmployee = idEmployee;
        this.idLocations = idLocations;
        this.name = name;
    }

    //gets e sets
    public int getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }

    public Employee getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employee idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Locations getIdLocations() {
        return idLocations;
    }

    public void setIdLocations(Locations idLocations) {
        this.idLocations = idLocations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
