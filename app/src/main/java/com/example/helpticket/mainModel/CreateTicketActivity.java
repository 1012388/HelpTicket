package com.example.helpticket.mainModel;

import android.os.Bundle;

import com.example.helpticket.databaseModels.Employee;
import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.helpticket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTicketActivity extends AppCompatActivity {

    private static final String TAG = "CreateTicketActivity";
    private Date currentTime;
    private String equipName;
    private Equipment equipment;
    private Employee employee;
    private DatabaseReference mDatabase;
    private Ticket ticket;
    private String ticketId;
    private String empName;
    private String desc;
    private FirebaseAuth firebaseAuth;
    private String emails;
    //public CreateTicketActivity() {
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        TextView textViewNumTicket = (TextView) findViewById(R.id.textViewNumTicket);
        Spinner spinnerLoc = (Spinner) findViewById(R.id.spinnerLoc);
        Spinner spinnerEquip = (Spinner) findViewById(R.id.spinnerEquip);
        EditText editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        Spinner spinnerEmp = (Spinner) findViewById(R.id.spinnerEmp);
        TextView textViewState1 = (TextView) findViewById(R.id.textViewState1);
        TextView textViewTech1 = (TextView) findViewById(R.id.textViewTech1);
        TextView textViewDate1 = (TextView) findViewById(R.id.textViewDate1);
        Button btnCreateTicket = (Button) findViewById(R.id.btnCreateTicket);


        currentTime = Calendar.getInstance().getTime();
        textViewDate1.setText(currentTime.toString());

        FirebaseDatabase instance = FirebaseDatabase.getInstance();


        //textViewNumTicket.setText("Ticket Number: "+idTicket+1);


        //Creating a path for Ticket
        instance.getReference("Ticket");

        //TODO: FIX THE LAYOUT MISTAKE
        //Get the Locations
        //select name from Locations
        //select
        Query queryLocs = instance.getReference("Locations").orderByChild("name");
        try {
            queryLocs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> locList = new ArrayList<String>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String locNameFromSnapshot = snapshot.child("name").getValue(String.class);

                        locList.add(locNameFromSnapshot);
                    }
                    Log.d(TAG, "Employee id" + employee.getIdEmployee());

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateTicketActivity.this, android.R.layout.simple_spinner_item, locList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerLoc.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the Equipments

        Query queryidEquip = instance.getReference("Equipment").orderByChild("idEquipment");
        try {
            queryidEquip.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    equipment = dataSnapshot.getValue(Equipment.class);


                    final List<String> equipName = new ArrayList<String>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String equipNameFromSnapshot = snapshot.child("name").getValue(String.class);

                        equipName.add(equipNameFromSnapshot);
                    }
                    Log.d(TAG, "Employee id" + employee.getIdEmployee());

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateTicketActivity.this, android.R.layout.simple_spinner_item, equipName);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerEquip.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Query queryidEmp = instance.getReference("Employee").orderByChild("IdEmployee");

            queryidEmp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    employee = dataSnapshot.getValue(Employee.class);
                    final List<String> empName = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String empNameFromSnapshot = snapshot.getValue(String.class);
                        empName.add(empNameFromSnapshot);
                    }
                    Log.d(TAG, "Employee id" + employee.getIdEmployee());

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateTicketActivity.this, android.R.layout.simple_spinner_item, empName);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerEmp.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        //State
        textViewState1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                i = i++;
                if (i % 2 == 0) {//State é False
                    ticket.setState(false);
                    textViewState1.setText("Not Solved");
                } else {
                    ticket.setState(true);
                    textViewState1.setText("Solved");
                }
            }
        });


        //Tech
        String CurrentTechnicianEmail = firebaseAuth.getCurrentUser().getEmail();
        //Query à bd; Select email from Technician;
        Query queryEmails = instance.getReference("Technician").orderByChild("email");

        try {
            queryEmails.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //employee = dataSnapshot.getValue(Employee.class);
                    final List<String> listEmails = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        emails = snapshot.getValue(String.class);
                        listEmails.add(emails);
                    }
                    if (listEmails.contains(CurrentTechnicianEmail)) {
                        textViewTech1.setText(firebaseAuth.getCurrentUser().getDisplayName());
                    }
                    Log.d(TAG, "Technician's Email " + employee.getIdEmployee());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnCreateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desc = editTextDesc.getText().toString();
                CreateTicket(currentTime, desc, ticket.getState(), equipment.getIdEquipment(), employee.getIdEmployee());
                Snackbar.make(view, "Criado com sucesso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void CreateTicket(Date requested_date, String description, Boolean state, int idEquipment, int idEmployee) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Creates an node and firebase generats and unique id
        ticketId = mDatabase.push().getKey();
        ticket = new Ticket(idEquipment,requested_date,idEmployee,description,state);

        mDatabase.child(ticketId).setValue(ticket);
        Map<String, Object> postValues = ticket.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticket/" + ticketId, postValues);
        mDatabase.updateChildren(childUpdates);
        //Assign the current user to the ticket
    }

}
