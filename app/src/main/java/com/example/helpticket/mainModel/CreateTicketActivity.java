package com.example.helpticket.mainModel;

import android.os.Bundle;

import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Shift_Technician;
import com.example.helpticket.databaseModels.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.helpticket.R;

import java.util.Calendar;
import java.util.Date;

public class CreateTicketActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        setSupportActionBar(toolbar);

        TextView textViewTech = (TextView) findViewById(R.id.textViewTech);
        TextView textViewTech1 = (TextView) findViewById(R.id.textViewTech1);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        TextView textViewDate1 = (TextView) findViewById(R.id.textViewDate1);
        TextView textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        final TextView textViewDesc1 = (TextView) findViewById(R.id.textViewDesc1);
        TextView textViewEmp = (TextView) findViewById(R.id.textViewEmp);
        final TextView textViewEmp1 = (TextView) findViewById(R.id.textViewEmp1);
        TextView textViewState = (TextView) findViewById(R.id.textViewState);
        TextView textViewState1 = (TextView) findViewById(R.id.textViewState1);
        TextView textViewNumTicket = (TextView) findViewById(R.id.textViewNumTicket);
        TextView textViewLoc = (TextView) findViewById(R.id.textViewLoc);
        TextView textViewEquip = (TextView) findViewById(R.id.textViewEquip);
        TextView textViewEquip1 = (TextView) findViewById(R.id.textViewEquip1);

        Spinner spinnerLoc = (Spinner) findViewById(R.id.spinnerLoc);
        final Button btnCreateTicket = (Button) findViewById(R.id.btnCreateTicket);

        Date currentTime = Calendar.getInstance().getTime();

        textViewDate1.setText(currentTime.toString());
        //textViewEmp1.setText();


        btnCreateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*idTicket++;

                ticket.setIdTicket(idTicket);
                ticket.setIdEmployee(textViewEmp1.getText().toString());
                ticket.setDescription(textViewDesc1.getText().toString());
*/

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public void CreateTicket(){
       // textViewTech1.s
    }

}
