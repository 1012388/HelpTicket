package com.example.helpticket.databaseModels;

import java.util.Date;

public class Ticket_Technician {
    private Ticket_Technician idTicket_technician;
    private Ticket idTicket;
    private Technician idTechnician;
    private Date duration;



    //construtor
    public Ticket_Technician() {
    }

    public Ticket_Technician(Ticket_Technician idTicket_technician, Ticket idTicket, Technician idTechnician, Date duration) {
        this.idTicket_technician = idTicket_technician;
        this.idTicket = idTicket;
        this.idTechnician = idTechnician;
        this.duration=duration;

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

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
