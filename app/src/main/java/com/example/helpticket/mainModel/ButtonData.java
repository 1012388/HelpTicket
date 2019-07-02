package com.example.helpticket.mainModel;

public class ButtonData {

    private String button_Description;
    private int button_id;

    public ButtonData(String button_description, int button_id) {
        button_Description = button_description;
        this.button_id = button_id;
    }

    public int getButton_id() {
        return button_id;
    }

    public void setButton_id(int button_id) {
        this.button_id = button_id;
    }

    public String getButton_Description() {
        return button_Description;
    }

    public void setButton_Description(String button_Description) {
        this.button_Description = button_Description;
    }
}
