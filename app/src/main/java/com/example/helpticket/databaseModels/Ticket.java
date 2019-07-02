/*Objective:This class is to store the information coming from HelpDesk,it will be sent to the technicians as a
* (notification?) and they also can see it in app*/

package com.example.helpticket.databaseModels;

import java.util.Date;

public class Ticket {

    //fields
    private int idTicket;
    private Date data;
    private int idEmployee;
    private String desccription;

    //constructor
    public Ticket(int idTicket, Date data, int idEmployee, String desccription) {
        this.idTicket = idTicket;
        this.data = data;
        this.idEmployee = idEmployee;
        this.desccription = desccription;
    }

    //gets and sets
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getDesccription() {
        return desccription;
    }

    public void setDesccription(String desccription) {
        this.desccription = desccription;
    }
}
