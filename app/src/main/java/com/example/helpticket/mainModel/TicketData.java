package com.example.helpticket.mainModel;

import java.util.ArrayList;

public class TicketData {

    private String button_Description;
    private static int button_id;

    public TicketData(String button_description, int button_id) {
        button_Description = button_description;
        this.button_id = button_id;
    }

    public int getButton_id() {
        return button_id;
    }

    public void setButton_id(int button_id) {
        this.button_id = button_id;
    }


    public static ArrayList<TicketData> createTicketList(int numTickets) {
        ArrayList<TicketData> tickets = new ArrayList<TicketData>();

        for (int i = 1; i <= numTickets; i++)
            tickets.add(new TicketData("Tickets " + button_id + i, numTickets));

        return tickets;
    }
}
