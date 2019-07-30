package com.example.helpticket.ReportManager;

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
import com.example.helpticket.mainModel.DetailsActivity;
import com.example.helpticket.mainModel.SolvedTicketActivity;
import com.example.helpticket.mainModel.TicketData;
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
    private FirebaseRecyclerAdapter<TicketData, SolvedTicketActivity.EntryViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);


        //TODO: SET AN MENU TO SHEARCH THE REPORTS

        //TODO:CHECK IF IT IS POSSIBLE TO FORCE AN ACTIVITY TO OPEN AT CERTAIN TIME

        //get the ticket_technician.idTickets that are stated as solved then display them into a recyclerView
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<TicketData> options = new FirebaseRecyclerOptions.Builder<TicketData>()
                .setQuery(getReportList(), TicketData.class).build();

        new FirebaseRecyclerAdapter<TicketData, EntryViewHolder>(options) {
            @NonNull
            @Override
            public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View ReportView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_report, parent, false);
                return new EntryViewHolder(ReportView);
            }

            @Override
            protected void onBindViewHolder(@NonNull EntryViewHolder entryViewHolder, int i, @NonNull TicketData ticketData) {
                entryViewHolder.setTitle("Ticket id:" + i);
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

    private Query getReportList() {
        DatabaseReference reference;
        //Reference for Ticket node
        reference = FirebaseDatabase.getInstance().getReference().child("Ticket");
        reference.keepSynced(true);

        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        //Reference for Technician_Ticket node
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Ticket_Technician");

            //select idTechinician from Ticket_technician where requested_date = current_Date
            Query ticket_technicianID = reference.orderByChild("idTechnician").equalTo(currentUserId)
                    .orderByChild("requested_date").equalTo(currentDate.getTime().toString()).limitToFirst(1);

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


        reference = FirebaseDatabase.getInstance().getReference().child("Ticket");
        Query querySolvedTickets = null;

        //Select idTicket from Ticket,Ticket_Techinician where Ticket_Techinician.idTicket = Ticket.idTicket and state is true;
        try {
            querySolvedTickets =
                    (Query) reference.orderByChild("idTicket").equalTo((ticket_technician.getIdTicket().toString()))
                            .orderByChild("state").equalTo(true);

            querySolvedTickets.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getValue(Ticket.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return querySolvedTickets;
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

    public void sendReport() {
        //TODO: MAKE THE PYTHON TO SEND THE REPORTS THAT ARE CHOOSEN

    }

    public void showDetailsTIcket() {
        Intent intent = new Intent(this, DetailsActivity.class);
        //PARSE THE CURRENT USER??
        startActivity(intent);
    }
}


