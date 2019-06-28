package com.example.helpticket.data.model;

public class Employee {

    private int idEmployee;
    private int idRequest;
    private int idEquipment_Employee;
    private String name;
    private String email;


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
