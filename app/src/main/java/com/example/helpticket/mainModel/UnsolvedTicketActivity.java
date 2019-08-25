package com.example.helpticket.mainModel;
import android.content.Intent;
import android.os.Bundle;

import com.example.helpticket.databaseModels.Ticket;
import com.example.helpticket.databaseModels.Ticket_Technician;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.helpticket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UnsolvedTicketActivity extends AppCompatActivity {
    private static final String TAG = "UnsolvedTicketActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase;
    private com.example.helpticket.databaseModels.Ticket ticket;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Ticket_Technician ticket_technician;
    private FirebaseRecyclerAdapter<Ticket, EntryViewHolder> firebaseRecyclerAdapter;
    private List<SolvedTicketActivity.EntryViewHolder> ticketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsolved_ticket);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //TODO:MAKE THE QUERY INTERACTIVE,MEANING LET THE USER SHEARCH IN THE UNSOLVED TICKETS

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Ticket> options = new FirebaseRecyclerOptions.Builder<Ticket>()
                .setQuery(getUnsolvedTickets(), Ticket.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Ticket, UnsolvedTicketActivity.EntryViewHolder>(options) {
            @NonNull
            @Override
            public UnsolvedTicketActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View UnsolvedTicketview = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.activity_unsolved_ticket, parent, false);
                return new UnsolvedTicketActivity.EntryViewHolder(UnsolvedTicketview);
            }

            @Override
            protected void onBindViewHolder(@NonNull EntryViewHolder entryViewHolder, int i, @NonNull Ticket ticket) {
                entryViewHolder.setTitle("Ticket " + i);

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

    private Query getUnsolvedTickets() {
        DatabaseReference reference;
        //Reference for Ticket node
        reference = FirebaseDatabase.getInstance().getReference().child("Ticket");
        reference.keepSynced(true);
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        //Reference for Technician_Ticket node
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Ticket_Technician");
            Query ticket_technicianID = reference.orderByChild("idTechnician").equalTo(currentUserId).limitToFirst(1);
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
        Query queryUnsolvedTickets = null;

        try {
            //Select idTicket from Ticket,Ticket_Techinician where Ticket_Techinician.idTicket = Ticket.idTicket and state is true;
            queryUnsolvedTickets = (Query)
                    reference.orderByChild("idTicket").equalTo((ticket_technician.getIdTicket().toString()))
                            .orderByChild("state").equalTo(false);
            queryUnsolvedTickets.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getValue(com.example.helpticket.databaseModels.Ticket.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryUnsolvedTickets;
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
}
