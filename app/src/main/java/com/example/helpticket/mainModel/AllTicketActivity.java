package com.example.helpticket.mainModel;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpticket.R;

public class AllTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ticket);
    }

    public void showUnsolvedTickets(View view) {

        //Cena do intent
    }

    public void showSolvedTickets(View view) {

        //Cena do intent para a view dos
    }
}
