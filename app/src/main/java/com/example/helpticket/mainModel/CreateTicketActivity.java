package com.example.helpticket.mainModel;

import android.os.Bundle;

import com.example.helpticket.databaseModels.Employee;
import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Locations;
import com.example.helpticket.databaseModels.Ticket;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpticket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
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

    public static final String SOLVED = "Solved";
    private static final String TAG = "CreateTicketActivity";
    public static final String NOT_SOLVED = "Not Solved";
    private Date currentTime;
    private String equipName;
    private Equipment equipment;
    private Employee employee;
    private DatabaseReference mDatabase;
    private Ticket ticket;
    private Locations locations;
    private String ticketId;
    private String empName;
    private String desc;
    private FirebaseAuth firebaseAuth;
    private String emails;
    private int i = 0;


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


        //textViewNumTicket.setText("TicketHolder Number: "+idTicket+1);


        //Creating a path for TicketHolder

        //Get the Locations
        //select name from Locations
        //select

        locations = new Locations();

        Query queryLocs = instance.getReference().child("Locations").orderByChild("name");
        try {
            queryLocs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> locList = new ArrayList<String>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String locNameFromSnapshot = snapshot.child("name").getValue(String.class);

                        locList.add(locNameFromSnapshot);
                    }


                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateTicketActivity.this, android.R.layout.simple_spinner_item, locList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerLoc.setAdapter(arrayAdapter);
                    spinnerLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            locations.setIdLocations(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the Equipments
        equipment = new Equipment();
        Query queryidEquip = instance.getReference().child("Equipment").orderByChild("idEquipment");
        try {
            queryidEquip.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> equipName = new ArrayList<String>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String equipNameFromSnapshot = snapshot.child("name").getValue(String.class);

                        equipName.add(equipNameFromSnapshot);
                    }


                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateTicketActivity.this, android.R.layout.simple_spinner_item, equipName);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerEquip.setAdapter(arrayAdapter);
                    spinnerEquip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            equipment.setIdEquipment(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        employee = new Employee();

        try {
            Query queryidEmp = instance.getReference().child("Employee").orderByChild("IdEmployee").limitToFirst(1);

            queryidEmp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //employee = dataSnapshot.getValue(Employee.class);
                    final List<String> empName = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String empNameFromSnapshot = snapshot.child("name").getValue(String.class);
                        empName.add(empNameFromSnapshot);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateTicketActivity.this, android.R.layout.simple_spinner_item, empName);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerEmp.setAdapter(arrayAdapter);
                    spinnerEmp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            employee.setIdEmployee(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
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
        ticket = new Ticket();

        textViewState1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i = i++;
                if (i % 2 == 0) {//State é False
                    ticket.setState(true);
                    textViewState1.setText(NOT_SOLVED);

                } else {
                    ticket.setState(false);
                    textViewState1.setText(SOLVED);
                }

            }
        });


        //Tech

        firebaseAuth = FirebaseAuth.getInstance();
        // Task<GetTokenResult> accessToken = firebaseAuth.getAccessToken(true);
        //check if the user is authenticated
        //if(accessToken.isSuccessful()) {


        //Query à bd; Select email from Technician;
        Query queryName = instance.getReference().child("Technician").orderByChild("name").limitToFirst(1);
        String CurrentTechnicianName = firebaseAuth.getCurrentUser().getDisplayName();
        //try {

        queryName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String techNameFromSnapshot = dataSnapshot.child("name").getValue(String.class);
                if (CurrentTechnicianName != techNameFromSnapshot) {
                    Toast.makeText(CreateTicketActivity.this, "Failed to read the query,but there you go a default name", Toast.LENGTH_LONG).show();
                    textViewTech1.setText("Bruno");
                }
                //textViewTech1.setText(techNameFromSnapshot);
                textViewTech1.setText(CurrentTechnicianName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // } catch (Exception e) {
        //.printStackTrace();
        //}
        //}

        //Description
        desc = editTextDesc.getText().toString();
        if (TextUtils.isEmpty(desc)) {
            editTextDesc.setError("Don't leave this field empty");
        }
        ticket.setDescription(desc);
        btnCreateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTicket(currentTime, desc, ticket.getState(), equipment.getIdEquipment(), employee.getIdEmployee());
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
        if (ticketId == mDatabase.getKey()) {
            Toast.makeText(CreateTicketActivity.this, "Criado com sucesso", Toast.LENGTH_SHORT).show();
        }
        //Assign the current user to the ticket
    }

}
