package com.example.helpticket.databaseModels;

import java.util.Date;

public class Shift_Technician {
    private int idShift_Technician;
    private Shift idShift;
    private Technician idTechnician;
    private Date startShift;//hora_inicio do turno
    private Date endShift;//hora_fim do turno

    //construtor

    public Shift_Technician(int idShift_Technician, Shift idShift, Technician idTechnician, Date startShift, Date endShift) {
        this.idShift_Technician = idShift_Technician;
        this.idShift = idShift;
        this.idTechnician = idTechnician;
        this.startShift = startShift;
        this.endShift = endShift;
    }


    //gets and sets


    public int getIdShift_Technician() {
        return idShift_Technician;
    }

    public void setIdShift_Technician(int idShift_Technician) {
        this.idShift_Technician = idShift_Technician;
    }

    public Shift getIdShift() {
        return idShift;
    }

    public void setIdShift(Shift idShift) {
        this.idShift = idShift;
    }

    public Technician getIdTechnician() {
        return idTechnician;
    }

    public void setIdTechnician(Technician idTechnician) {
        this.idTechnician = idTechnician;
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
