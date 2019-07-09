package com.example.helpticket.databaseModels;

import java.util.Date;

public class Ticket_Technician {
    private Ticket_Technician idTicket_technician;
    private Ticket idTicket;
    private Technician idTechnician;
    private Date Ticket_Start_Hour;
    private Date Ticket_Finish_Hour;


    //construtor


    public Ticket_Technician() {
    }

    public Ticket_Technician(Ticket_Technician idTicket_technician, Ticket idTicket, Technician idTechnician, Date ticket_Start_Hour, Date ticket_Finish_Hour) {
        this.idTicket_technician = idTicket_technician;
        this.idTicket = idTicket;
        this.idTechnician = idTechnician;
        Ticket_Start_Hour = ticket_Start_Hour;
        Ticket_Finish_Hour = ticket_Finish_Hour;
    }


    //gets and sets


    public Ticket_Technician getIdTicket_technician() {
        return idTicket_technician;
    }

    public void setIdTicket_technician(Ticket_Technician idTicket_technician) {
        this.idTicket_technician = idTicket_technician;
    }

    public Ticket getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Ticket idTicket) {
        this.idTicket = idTicket;
    }

    public Technician getIdTechnician() {
        return idTechnician;
    }

    public void setIdTechnician(Technician idTechnician) {
        this.idTechnician = idTechnician;
    }

    public Date getTicket_Start_Hour() {
        return Ticket_Start_Hour;
    }

    public void setTicket_Start_Hour(Date ticket_Start_Hour) {
        Ticket_Start_Hour = ticket_Start_Hour;
    }

    public Date getTicket_Finish_Hour() {
        return Ticket_Finish_Hour;
    }

    public void setTicket_Finish_Hour(Date ticket_Finish_Hour) {
        Ticket_Finish_Hour = ticket_Finish_Hour;
    }
}
