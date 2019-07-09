package com.example.helpticket.databaseModels;

import java.util.Date;

public class Shift {
    private int idShift;
    private int duration;
    private Date startShift;//hora_inicio do turno
    private Date endShift;//hora_fim do turno


    //construtor

    public Shift(){

    }
    public Shift(int idShift, int duration) {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartShift() {
        return startShift;
    }

    public void setStartShift(Date startShift) {
        this.startShift = startShift;
    }

    public Date getEndShift() {
        return endShift;
    }

    public void setEndShift(Date endShift) {
        this.endShift = endShift;
    }


}
