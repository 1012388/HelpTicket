package com.example.helpticket.mainModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpticket.R;
import com.example.helpticket.databaseModels.Equipment;
import com.example.helpticket.databaseModels.Ticket;
import com.example.helpticket.databaseModels.Ticket_Technician;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SolvedTicketActivity extends AppCompatActivity {
    private static final String TAG = "SolvedTicketActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase;
    private Ticket ticket;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Ticket_Technician ticket_technician;

    private FirebaseRecyclerAdapter<TicketData, EntryViewHolder> firebaseRecyclerAdapter;
    private List<EntryViewHolder> ticketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_ticket);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<TicketData> options = new FirebaseRecyclerOptions.Builder<TicketData>()
                .setQuery(mDatabase, TicketData.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TicketData, EntryViewHolder>(options) {
            @NonNull
            @Override
            public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View SolvedTicketview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_solved_ticket, parent, false);
                return new EntryViewHolder(SolvedTicketview);
            }

            @Override
            protected void onBindViewHolder(@NonNull EntryViewHolder entryViewHolder, int i, @NonNull TicketData ticketData) {
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

    public void getSolvedTickets() {
        //TODO:USE THIS QUERY TO FEED TICKET DATA
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

        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Ticket");

            //Select idTicket from Ticket,Ticket_Techinician where Ticket_Techinician.idTicket = Ticket.idTicket and state is true;
            Query queryState =
                    (Query) reference.orderByChild("idTicket").equalTo((ticket_technician.getIdTicket().toString()))
                            .orderByChild("state").equalTo(true);

            queryState.addValueEventListener(new ValueEventListener() {
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
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button ticket;

        public EntryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title){
            ticket = (Button) mView.findViewById(R.id.btnNewticket);
            ticket.setText(title);
        }

        public void setContent(String content){
            ticket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDetailsTIcket();
                }
            });
        }
    }

    public void showDetailsTIcket(){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("IDTicket",ticket.getIdTicket());
        startActivity(intent);
    }
}