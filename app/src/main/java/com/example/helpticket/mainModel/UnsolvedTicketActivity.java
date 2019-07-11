package com.example.helpticket.mainModel;

import android.os.Bundle;

import com.example.helpticket.databaseModels.Ticket;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.helpticket.R;
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


    private FirebaseRecyclerAdapter<TicketData, UnsolvedTicketActivity.EntryViewHolder> firebaseRecyclerAdapter;
    private List<SolvedTicketActivity.EntryViewHolder> ticketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsolved_ticket);



            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Ticket");
            reference.keepSynced(true);

            Query queryState = reference.orderByChild("state");//Hopefully gets the tickets with the state true

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            getuUnsolvedTickets();
        }


        @Override
        protected void onStart() {
            super.onStart();

            FirebaseRecyclerOptions<TicketData> options = new FirebaseRecyclerOptions.Builder<TicketData>()
                    .setQuery(mDatabase, TicketData.class)
                    .build();

            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TicketData, UnsolvedTicketActivity.EntryViewHolder>(options) {
                @NonNull
                @Override
                public UnsolvedTicketActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View UnsolvedTicketview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_unsolved_ticket, parent, false);


                    return new UnsolvedTicketActivity.EntryViewHolder(UnsolvedTicketview);
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

        private void getuUnsolvedTickets() {
            FirebaseDatabase instance = FirebaseDatabase.getInstance();
            final DatabaseReference ticketPath = instance.getReference("Ticket");

            Query queryIdUnsolvedTicket = instance.getReference("Ticket").orderByChild("state").equalTo(0);

            queryIdUnsolvedTicket.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Ticket ticket = dataSnapshot.getValue(Ticket.class);
                    Log.d(TAG, "Unsolved Ticket Id is " + ticket.getIdTicket());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.w(TAG,"Failed to read " + databaseError.toException());
                }
            });


        }

        public static class EntryViewHolder extends RecyclerView.ViewHolder {
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
                        //TODO:Abrir o novo ticket
                        // Abrir details
                    }
                });
            }

        }
}
