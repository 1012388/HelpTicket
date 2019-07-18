package com.example.helpticket.mainModel;

import android.content.Intent;
import android.os.Bundle;

import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Ticket;
import com.example.helpticket.databaseModels.Ticket_Technician;
import com.example.helpticket.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpticket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;

public class DetailsActivity extends AppCompatActivity {

    private Ticket ticket;
    private Ticket_Technician ticket_technician;
    private static final String TAG = "DetailsActivity";
    private Equipment equipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textViewNumTicket = (TextView) findViewById(R.id.textViewNumTicket);
        TextView textViewLoc = (TextView) findViewById(R.id.textViewLoc);
        TextView textViewEquip = (TextView) findViewById(R.id.textViewEquip);
        TextView textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        TextView textViewState = (TextView) findViewById(R.id.textViewState);
        TextView textViewTech = (TextView) findViewById(R.id.textViewTech);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);



        Intent intent = getIntent();
        //TODO:Usar o idTicket que veio da atividade para comparar com o que vem do firebase
        int idTicket = intent.getIntExtra("IDTicket",0);


        FirebaseDatabase instance = FirebaseDatabase.getInstance();

        DatabaseReference ref = instance.getReference("Ticket_Technician");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { ticket_technician = dataSnapshot.getValue(Ticket_Technician.class); }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailsActivity.this,"Canceled "+databaseError.toException(),Toast.LENGTH_SHORT).show();
                Log.w(TAG,"Failed to read " + databaseError.toException());
            }
        });

        DatabaseReference ticketRef = instance.getReference("Ticket");

        ticketRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ticket = dataSnapshot.getValue(Ticket.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailsActivity.this,"Canceled "+databaseError.toException(),Toast.LENGTH_SHORT).show();
                Log.w(TAG,"Failed to read " + databaseError.toException());
            }
        });

        DatabaseReference equipmentRef = instance.getReference("Equipment");

        equipmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                equipment = dataSnapshot.getValue(Equipment.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        textViewNumTicket.setText("Ticket Number: "+ ticket.getIdTicket());
        //textViewLoc.setText("Location: "+ getLocByEmp(ticket.getIdEquipment()));//TODO:GET Locations's name
        textViewState.setText("State :"+ ticket.getState());
        textViewDesc.setText("Description :"+ ticket.getDescription());
        textViewTech.setText("Technician Name: "+ ticket_technician.getIdTechnician());//TODO:GET Technician'S NAME
        textViewEquip.setText("Employee's Name: "+ticket.getIdEmployee());//TODO:GET EMPLOYEE'S NAME
        textViewDate.setText("Requested Date: "+ ticket.getRequested_date());





    }



}
