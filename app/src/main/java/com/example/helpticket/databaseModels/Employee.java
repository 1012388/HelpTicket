/*Objective:This class is for the employees of the company, they will request tickets to HelpDesk
* This application will get the tickets and so I need an class to store the data to those employees*/
package com.example.helpticket.databaseModels;

//Iniciar instância da classe que tem relação com, fazer isso com o resto

public class Employee {
    //fields
    private int idEmployee;


    private String name;
    private String email;



    //constructor


    public Employee() {
    }

    public Employee(int idEmployee, String name, String email) {
        this.idEmployee = idEmployee;

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
