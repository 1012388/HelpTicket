package com.example.helpticket.mainModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Ticket;
import com.example.helpticket.databaseModels.Ticket_Technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpticket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {

    private Ticket ticket;
    private Ticket_Technician ticket_technician;
    private static final String TAG = "DetailsActivity";
    private Equipment equipment;
    private int idEquipFromTicket;
    private int idLocationsFromEquip;
    private String LocationsName;
    private int idTechnicianFromTicket;
    private String technicianName;
    private int idEmpFromTicket;
    private String EmpName;
    private Calendar instantOpen;
    private Calendar instantDura;
    private Calendar instantFini;
    private ArrayAdapter<String> adapter;
    private List<String> ticketState;
    private boolean clickable;
    private FirebaseUser currentFirebaseUser;
    private String user_id_will_be;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textViewNumTicket = (TextView) findViewById(R.id.textViewNumTicket);
        TextView textViewLoc = (TextView) findViewById(R.id.textViewLoc);
        TextView textViewEquip = (TextView) findViewById(R.id.textViewEquip);
        EditText editTextDesc1 = (EditText) findViewById(R.id.editTextDesc1);
        Spinner spinnerDetailsState = (Spinner) findViewById(R.id.spinnerDetailsState);
        TextView textViewTech = (TextView) findViewById(R.id.textViewTech);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);

        Intent intent = getIntent();

        int idTicket = intent.getIntExtra("IDTicket", 0);

        FirebaseDatabase instance = FirebaseDatabase.getInstance();

        DatabaseReference ref = instance.getReference("Ticket_Technician");
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ticket_technician = dataSnapshot.getValue(Ticket_Technician.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DetailsActivity.this, "Canceled " + databaseError.toException(), Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "Failed to read " + databaseError.toException());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseReference ticketRef = instance.getReference("Ticket");
        try {
            ticketRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ticket = dataSnapshot.getValue(Ticket.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DetailsActivity.this, "Canceled " + databaseError.toException(), Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "Failed to read " + databaseError.toException());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseReference equipmentRef = instance.getReference("Equipment");
        try {
            equipmentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    equipment = dataSnapshot.getValue(Equipment.class);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        textViewNumTicket.setText("Ticket Number: " + ticket.getIdTicket());

        //Queries to get the Locations's Name of the current ticket
        Query reference;
        try {
            reference = FirebaseDatabase.getInstance().getReference("Ticket").orderByChild("idEquipment").equalTo(equipment.getIdEquipment());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    idEquipFromTicket = (int) dataSnapshot.child("idEquipment").getValue();
                    idEmpFromTicket = (int) dataSnapshot.child("idEmployee").getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            reference.keepSynced(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            reference = FirebaseDatabase.getInstance().getReference("Equipment").orderByChild("idEquipment").equalTo(idEquipFromTicket);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    idLocationsFromEquip = (int) dataSnapshot.child("idLocations").getValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            reference.keepSynced(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            reference = FirebaseDatabase.getInstance().getReference("Locations").orderByChild("idLocations").equalTo(idLocationsFromEquip);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    LocationsName = (String) dataSnapshot.child("name").getValue();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        textViewLoc.setText("Location: " + LocationsName);
        textViewLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:OPEN MAP
            }
        });

        try {
            reference = FirebaseDatabase.getInstance().getReference("Ticket")
                    .orderByChild("idTicket").equalTo(ticket.getIdTicket());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ticketState = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ticket = snapshot.getValue(Ticket.class);
                        String ticketStateFromSnapshot = ticket.getState().toString();
                        ticketState.add(ticketStateFromSnapshot);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(DetailsActivity.this, android.R.layout.simple_spinner_item, ticketState);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDetailsState.setAdapter(adapter);
        spinnerDetailsState.setClickable(true);
        currentFirebaseUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        user_id_will_be = currentFirebaseUser.getUid();
        DatabaseReference updateData = FirebaseDatabase.getInstance()
                .getReference("Ticket")
                .child(user_id_will_be);
        String newState = spinnerDetailsState.getSelectedItem().toString();

        try {//Update the ticket witht the new state
            updateData.child("State").setValue(newState);
        } catch (Exception e) {
            e.printStackTrace();
        }

        editTextDesc1.setClickable(false);
        editTextDesc1.setText("Description :" + ticket.getDescription());
        currentFirebaseUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        user_id_will_be = currentFirebaseUser.getUid();

        try {
            DatabaseReference updatedData = FirebaseDatabase.getInstance()
                    .getReference("Ticket")
                    .child(user_id_will_be);

            editTextDesc1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    editTextDesc1.setClickable(true);
                    String newDesc = editTextDesc1.getText().toString();
                    updatedData.child("description").setValue(newDesc);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Queries to get the Technician's Name that attended the current ticket
        reference = FirebaseDatabase.getInstance().getReference("Ticket_Technician").orderByChild("idTicket").equalTo(ticket.getIdTicket());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idTechnicianFromTicket = (int) dataSnapshot.child("idTechnician").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        reference = (Query) FirebaseDatabase.getInstance().getReference("Technician")
                .orderByChild("idTechnician").equalTo(idTechnicianFromTicket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                technicianName = (String) dataSnapshot.child("name").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        textViewTech.setText("Technician Name: " + technicianName);
        reference = FirebaseDatabase.getInstance().getReference("Employee")
                .orderByChild("idEmployee").equalTo(idEmpFromTicket);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EmpName = (String) dataSnapshot.child("name").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        textViewEquip.setText("Employee's Name: " + EmpName);
        textViewDate.setText("Openned at: " + ticket.getRequested_date());
        //requested_date(09/07/2019 11:15) + duracao(00:15) = finished_date(09/07/2019 11:30)
        instantOpen.setTime(ticket.getRequested_date());
        instantDura.setTime(ticket_technician.getDuration());
        instantFini.add(instantOpen.get(Calendar.DATE), instantDura.get(Calendar.DATE));

        textViewDate.setText("Closed at: " + instantFini.toString());

        //Keeps updating the ticket with info
        ticketRef.keepSynced(true);
        equipmentRef.keepSynced(true);
    }
}
