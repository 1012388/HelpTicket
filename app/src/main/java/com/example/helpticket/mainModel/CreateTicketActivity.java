package com.example.helpticket.mainModel;

import android.os.Bundle;

import com.example.helpticket.databaseModels.Employee;
import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Locations;
import com.example.helpticket.databaseModels.Shift_Technician;
import com.example.helpticket.databaseModels.Technician;
import com.example.helpticket.databaseModels.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.helpticket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateTicketActivity extends AppCompatActivity {

    private static final String TAG = "CreateTicketActivity";
    private DatabaseReference mDatabase;
    private Ticket ticket;
    private Equipment equipment;
    private Technician technician;

    private Employee employee;
    String ticketId;
    Date currentTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id);
        setSupportActionBar(toolbar);

        TextView textViewTech = (TextView) findViewById(R.id.textViewTech);
        TextView textViewTech1 = (TextView) findViewById(R.id.textViewTech1);
        TextView textViewDate1 = (TextView) findViewById(R.id.textViewDate1);
        TextView textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        final EditText editTextDesc = (EditText) findViewById(R.id.editTextDesc);
        TextView textViewEmp = (TextView) findViewById(R.id.textViewEmp);
        final Spinner spinnerEmp = (Spinner) findViewById(R.id.spinnerEmp);
        TextView textViewState = (TextView) findViewById(R.id.textViewState);
        TextView textViewState1 = (TextView) findViewById(R.id.textViewState1);
        TextView textViewNumTicket = (TextView) findViewById(R.id.textViewNumTicket);
        TextView textViewLoc = (TextView) findViewById(R.id.textViewLoc);
        TextView textViewEquip = (TextView) findViewById(R.id.textViewEquip);
        final Spinner spinnerEquip = (Spinner) findViewById(R.id.spinnerEquip);

        final Spinner spinnerLoc = (Spinner) findViewById(R.id.spinnerLoc);
        final Button btnCreateTicket = (Button) findViewById(R.id.btnCreateTicket);



        final String equipName = spinnerEquip.getSelectedItem().toString();
        final String empName = spinnerEmp.getSelectedItem().toString();
        String  locName = spinnerLoc.getSelectedItem().toString();

        currentTime = Calendar.getInstance().getTime();


        textViewDate1.setText(currentTime.toString());
        //textViewEmp1.setText();
        FirebaseDatabase instance = FirebaseDatabase.getInstance();

        //Creating a path for Ticket
        DatabaseReference ticket = instance.getReference("Ticket");

        Query queryidEquip = instance.getReference("Equipment").orderByChild("idEquipment").equalTo(equipName).limitToFirst(1);

        queryidEquip.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 equipment = dataSnapshot.getValue(Equipment.class);
                Log.d(TAG, "Equipment id is " + equipment.getIdEquipment());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        Query queryidEmp = instance.getReference("Employee").orderByChild("IdEmployee").equalTo(empName).limitToFirst(1);

        queryidEmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 employee = dataSnapshot.getValue(Employee.class);
                Log.d(TAG, "Employee id" + employee.getIdEmployee());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnCreateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:FINISH THIS ONE(Finished? Check)
                String desc = editTextDesc.getText().toString();


                if(TextUtils.isEmpty(ticketId)) {
                    CreateTicket(currentTime,desc, false, equipment.getIdEquipment(),employee.getIdEmployee());
                    Snackbar.make(view, "Criado com sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
               }else{
                    Snackbar.make(view, "Criado sem sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
    }
}
