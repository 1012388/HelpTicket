package com.example.helpticket.mainModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.helpticket.databaseModels.Ticket;
import com.example.helpticket.databaseModels.Ticket_Technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpticket.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private Calendar currentDate;
    private Ticket_Technician ticket_technician;
    private List<String> ticketList;
    private Ticket ticket;
    private Query mDatabase;
    private FirebaseRecyclerAdapter<TicketData, UnsolvedTicketActivity.EntryViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //TODO:CHECK IF IT IS POSSIBLE TO FORCE AN ACTIVITY TO OPEN AT CERTAIN TIME
        //  getReportList();
        //get the ticket_technician.idTickets that are stated as solved then display them into a recyclerView
    }

    //TODO:FINISH THIS ACTIVITY
    //TODO:MAKE THE QUERY INTERACTIVE,MEANING LET THE USER SHEARCH IN THE UNSOLVED TICKETS
/*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<TicketData> options = new FirebaseRecyclerOptions.Builder<TicketData>()
                .setQuery(mDatabase, TicketData.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TicketData, UnsolvedTicketActivity.EntryViewHolder>(options) {
            @NonNull
            @Override
            public ReportActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View ReportTicketview = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.activity_unsolved_ticket, parent, false);
                return new ReportActivity.EntryViewHolder(ReportTicketview);
            }

            @Override
            protected void onBindViewHolder(@NonNull UnsolvedTicketActivity.EntryViewHolder entryViewHolder, int i, @NonNull TicketData ticketData) {
                entryViewHolder.setTitle("Ticket " + i);
                entryViewHolder.setContent(ticketData.getContent());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    private void getReportList() {
        DatabaseReference reference;
        //Reference for Ticket node
        reference = FirebaseDatabase.getInstance().getReference().child("Ticket");
        reference.keepSynced(true);


        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Ticket_Technician");

            //select idTechnician from ticket_techenician where idTechinician = currentUserFromFirebase and requested_date = currentDate
            Query ticket_technicianID = reference.orderByChild("idTechnician").equalTo(currentUserId)
                    .orderByChild("requested_date").equalTo(currentDate.getTime().toString());

            ticket_technicianID.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ticket_technician = dataSnapshot.getValue(Ticket_Technician.class);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Ticket");
            Query idTicket = reference.orderByChild("idTicket").equalTo(ticket_technician.getIdTicket().toString());

            idTicket.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ticketList = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ticket = snapshot.getValue(Ticket.class);
                        String ticketStateFromSnapshot = ticket.getState().toString();
                        ticketList.add(ticketStateFromSnapshot);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button ticket;

        public EntryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            ticket = (Button) mView.findViewById(R.id.btnNewticket);
            ticket.setText(title);
        }

        public void setContent(String content) {
            ticket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDetailsTIcket();
                }
            });
        }
    }

    public void showDetailsTIcket() {
        Intent intent = new Intent(this, DetailsActivity.class);
        //intent.putExtra("IDTicket",ticket.getIdTicket());
        startActivity(intent);
    }

    */
}


