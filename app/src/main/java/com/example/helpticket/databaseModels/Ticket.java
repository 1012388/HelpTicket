/*Objective:This class is to store the information coming from HelpDesk,it will be sent to the technicians as a
* (notification?) and they also can see it in app*/

package com.example.helpticket.databaseModels;

import java.util.Date;

public class Ticket {

    //fields
    private int idTicket;
    private Equipment idEquipment;
    private Date requested_data;
    private Date finished_data;
    private Employee idEmployee;
    private String description;
    //urgente
    private boolean pendent;//serve para o técnico ter a opção 0 não;1 sim

    //constructor


    public Ticket(){

    }

    public Ticket(int idTicket, Equipment idEquipment, Date requested_data, Date finished_data, Employee idEmployee, String description) {
        this.idTicket = idTicket;
        this.idEquipment = idEquipment;
        this.requested_data = requested_data;
        this.finished_data = finished_data;
        this.idEmployee = idEmployee;
        this.description = description;
        this.pendent = false;
    }

    public Ticket(int idTicket, Equipment idEquipment,Date requested_data,Date finished_data,Employee idEmployee, String description,boolean pendent) {
        this.idTicket = idTicket;
        this.idEquipment = idEquipment;
        this.requested_data = requested_data;
        this.finished_data = finished_data;
        this.idEmployee = idEmployee;
        this.description = description;
        this.pendent = pendent;
    }

    //gets and sets

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Equipment getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(Equipment idEquipment) {
        this.idEquipment = idEquipment;
    }

    public Date getRequested_data() {
        return requested_data;
    }

    public void setRequested_data(Date requested_data) {
        this.requested_data = requested_data;
    }

    public Date getFinished_data() {
        return finished_data;
    }

    public void setFinished_data(Date finished_data) {
        this.finished_data = finished_data;
    }

    public Employee getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employee idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
