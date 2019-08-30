package com.example.helpticket.mainModel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpticket.R;
import com.example.helpticket.databaseModels.Ticket;
import com.example.helpticket.databaseModels.Ticket_Technician;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class SolvedTicketActivity extends AppCompatActivity {
    //This activity is to show the Solved Tickets of the current logged in user
    private static final String TAG = "SolvedTicketActivity";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase;
    private com.example.helpticket.databaseModels.Ticket ticket;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Ticket_Technician ticket_technician;
    private Calendar currentDate;
    private FirebaseRecyclerAdapter<Ticket, EntryViewHolder> firebaseRecyclerAdapter;
    private List<EntryViewHolder> ticketList;
    private String parameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_ticket);
        Toolbar toolbarSolved = (Toolbar) findViewById(R.id.toolbarSolved);

        toolbarSolved.inflateMenu(R.menu.main_menu);
        setContentView(toolbarSolved);

        toolbarSolved.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_idTicket) {

                    inputShearchParameters();
                }

                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSolved);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String param = "idTicket";
        FirebaseRecyclerOptions<Ticket> options = new FirebaseRecyclerOptions.Builder<Ticket>()
                .setQuery(getReportList(param), Ticket.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Ticket, SolvedTicketActivity.EntryViewHolder>(options) {
            @NonNull
            @Override
            public SolvedTicketActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View ReportView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list, parent, false);
                return new SolvedTicketActivity.EntryViewHolder(ReportView);
            }

            @Override
            protected void onBindViewHolder(SolvedTicketActivity.EntryViewHolder entryViewHolder, int position, Ticket ticket) {
                entryViewHolder.setTitle("Ticket nº: " + ticket.getIdTicket());

                entryViewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SolvedTicketActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private String inputShearchParameters() {
        //TODO:TESTAR A VER SE DÁ
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Shearch");
        builder.setMessage("Please input your shearch paramaters");
        EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    parameter = input.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(getApplicationContext(), "You have canceled the input", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return parameter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //when the recycler View is first loaded the view is loaded with the default parameter,

        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    private Query getReportList(String param) {


        DatabaseReference reference;
        //Reference for TicketHolder node
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        //Reference for Technician_Ticket node
        try {
            reference = FirebaseDatabase.getInstance().getReference().child("Ticket_Technician");
            reference.keepSynced(true);

            //Query : select idTechinician from Ticket_technician where requested_date = current_Date
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
        querySolvedTickets = (Query) reference.orderByChild(param).equalTo((ticket_technician.getIdTicket().toString()))
                .orderByChild("state").equalTo(true);

        reference.keepSynced(true);

        return querySolvedTickets;
    }


    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public Button ticket;

        public EntryViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.listRoot);

            ticket = itemView.findViewById(R.id.btnTicket);

            ticket.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              // showDetailsTIcket();
                                          }
                                      }
            );

        }

        public void setTitle(String title) {
            ticket = (Button) itemView.findViewById(R.id.btnNewticket);
            ticket.setText(title);
        }


    }

    public void showDetailsTIcket(){
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("IDTicket",ticket.getIdTicket());
        startActivity(intent);
    }

}