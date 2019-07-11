package com.example.helpticket.mainModel;

import java.util.ArrayList;

public class TicketData {

    private String button_Description,content;
    private  int button_id;

    public TicketData(String button_description,String content , int button_id) {
        button_Description = button_description;
        this.button_id = button_id;
        this.content =content;
    }

    public int getButton_id() {
         return button_id;
    }

    public void setButton_id(int button_id)  {
        this.button_id = button_id;
    }

    public String getButton_Description() {
        return button_Description;
    }

    public void setButton_Description(String button_Description) {
        this.button_Description = button_Description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
