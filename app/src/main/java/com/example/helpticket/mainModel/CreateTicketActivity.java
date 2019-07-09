package com.example.helpticket.mainModel;

import android.os.Bundle;

import com.example.helpticket.databaseModels.Employee;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class CreateTicketActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Ticket ticket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id);
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

        final Date currentTime = Calendar.getInstance().getTime();

        textViewDate1.setText(currentTime.toString());
        //textViewEmp1.setText();


        btnCreateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //idEquip = ;//Buscar das caixas de texto
               // idEmp=;//Buscar das caixas de texto
                //CreateTicket(currentTime, "Troca de HDD para SSD", false,,);


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public void CreateTicket(Date requested_date, String description, Boolean state, Equipment idEquipment, Employee idEmployee) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ticket = new Ticket(1, idEquipment, requested_date, idEmployee, description, state);

        //mDatabase.child("users").child(userId).setValue(useR);


        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        // [END write_message]

    }
/*
    public void basicReadWrite() {


        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
// [END read_message]

    }*/
}
