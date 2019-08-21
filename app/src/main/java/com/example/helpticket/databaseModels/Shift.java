package com.example.helpticket.databaseModels;

import java.util.Date;

public class Shift {
    private int idShift;
    private Date start_shift;
    private Date end_shift;
    private String name;

    //construtor


    public Shift() {
    }

    public Shift(int idShift, Date start_shift, Date end_shift, String name) {
        this.idShift = idShift;
        this.start_shift = start_shift;
        this.end_shift = end_shift;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
