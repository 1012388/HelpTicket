package com.example.helpticket.mainModel;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.example.helpticket.R;

public class AllTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ticket);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        Button buttonSolved     = (Button) findViewById(R.id.buttonSolved);
        Button buttonNotSolved  = (Button) findViewById(R.id.buttonNotSolved);
    }

    public void showUnsolvedTickets(View view) {
        Intent intent = new Intent(this, UnsolvedTicketActivity.class);
        startActivity(intent);
    }

    public void showSolvedTickets(View view) {
        Intent intent = new Intent(this, SolvedTicketActivity.class);
        startActivity(intent);
    }
}
