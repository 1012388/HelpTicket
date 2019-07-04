package com.example.helpticket.databaseModels;

import java.util.Date;

public class Shift {
    private int idShift;
    private Date duration;

    //construtor

    public Shift(int idShift, Date duration) {
        this.idShift = idShift;
        this.duration = duration;
    }


    //gets and sets


    public int getIdShift() {
        return idShift;
    }

    public void setIdShift(int idShift) {
        this.idShift = idShift;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
