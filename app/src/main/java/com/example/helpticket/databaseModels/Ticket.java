/*Objective:This class is to store the information coming from HelpDesk,it will be sent to the technicians as a
* (notification?) and they also can see it in app*/

package com.example.helpticket.databaseModels;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ticket {

    //fields
    private int idTicket;
    private Equipment idEquipment;
    private Date requested_date;
    private Employee idEmployee;
    private String description;
    private boolean state;//serve para o técnico ter a opção 0 não;1 sim

    //constructor
    public Ticket() {
    }

    public Ticket(int idEquipment, Date requested_date, int idEmployee, String description, Boolean state){

    }

    public Ticket(Equipment idEquipment, Date requested_data, Employee idEmployee,String description) {

        this.idEquipment = idEquipment;
        this.requested_date = requested_data;
        this.idEmployee = idEmployee;
        this.description = description;
        this.state = false;
    }

    //@Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idTicket", idTicket);
        result.put("idEquipment", idEquipment);
        result.put("idEmployee", idEmployee);
        result.put("requested_date", requested_date);
        result.put("description", description);
        result.put("state", state);

        return result;
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

    public Date getRequested_date() {
        return requested_date;
    }

    public void setRequested_date(Date requested_date) {
        this.requested_date = requested_date;
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


    public Boolean getState() {
        return state;
    }


    public void setState(Boolean state) {
        this.state = state;
    }







}


