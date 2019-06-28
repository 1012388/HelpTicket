/*Objective:This class is for the employees of the company, they will request tickets to HelpDesk
* This application will get the tickets and so I need an class to store the data to those employees*/
package com.example.helpticket.data.model;

public class Employee {
    //fields
    private int idEmployee;
    private int idRequest;
    private int idEquipment_Employee;
    private String name;
    private String email;

    //constructor
    public Employee(int idEmployee, int idRequest, int idEquipment_Employee, String name, String email) {
        this.idEmployee = idEmployee;
        this.idRequest = idRequest;
        this.idEquipment_Employee = idEquipment_Employee;
        this.name = name;
        this.email = email;
    }

    //gets and sets
    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdEquipment_Employee() {
        return idEquipment_Employee;
    }

    public void setIdEquipment_Employee(int idEquipment_Employee) {
        this.idEquipment_Employee = idEquipment_Employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
