/*Objective:This class will be for storing the information about the Technicians, more importantly
* their username and password so they can acess the app and later for their end shift report*/
package com.example.helpticket.databaseModels;

public class Technician {
    //fields
    private int idTechnician;
    private String name;
    private String email;
    private String username;
    private String password;

    //constructor

    public Technician() {
    }

    public Technician(int idTechnician, String email, String name, String username, String password) {
        this.idTechnician = idTechnician;
        this.email = email;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    //gets and sets
    public int getIdTechnician() {
        return idTechnician;
    }

    public void setIdTechnician(int idTechnician) {
        this.idTechnician = idTechnician;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
