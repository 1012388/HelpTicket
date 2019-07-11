package com.example.helpticket.databaseModels;

import java.util.Date;

public class Shift {
    private int idShift;
    private Date start_shift;
    private Date end_shift;

    //construtor


    public Shift() {
    }

    public Shift(int idShift, Date start_shift,Date end_shift) {
        this.idShift = idShift;
        this.start_shift = start_shift;
        this.end_shift = end_shift;
    }


    //gets and sets


    public int getIdShift() {
        return idShift;
    }

    public void setIdShift(int idShift) {
        this.idShift = idShift;
    }

    public Date getStart_shift() {
        return start_shift;
    }

    public void setStart_shift(Date start_shift) {
        this.start_shift = start_shift;
    }

    public Date getEnd_shift() {
        return end_shift;
    }

    public void setEnd_shift(Date end_shift) {
        this.end_shift = end_shift;
    }


}
