package com.example.helpticket.ReportManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private Calendar currentDate;
    private Ticket_Technician ticket_technician;
    private List<String> ticketList;
    private com.example.helpticket.databaseModels.Ticket ticket;
    private Query mDatabase;
    private FirebaseRecyclerAdapter<Ticket, SolvedTicketActivity.EntryViewHolder> firebaseRecyclerAdapter;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMenu);
        toolbar.inflateMenu(R.menu.main_menu);
        setContentView(toolbar);

        //get the ticket_technician.idTickets that are stated as solved then display them into a recyclerView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_idTicket:
                showInputDialog(id);
                return true;
            case R.id.action_idEquipment:
                showInputDialog(id);
                return true;
            case R.id.action_RequestedDate:
                showInputDialog(id);
                return true;
            case R.id.action_idEmployee:
                showInputDialog(id);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void showInputDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.input_dialog, null);
        builder.setView(view);


        final EditText editText = (EditText) view.findViewById(R.id.editTextInput);
        switch (id) {
            case R.id.action_idTicket:
                title = "Shearch by Ticket id:";

            case R.id.action_idEquipment:
                title = "Shearch by Equipment:";

            case R.id.action_RequestedDate:
                title = "Shearch by Date:";

            case R.id.action_idEmployee:
                title = "Shearch by Employee:";

            default:
                title = "Shearch by Ticket id:";
        }

        builder.setTitle(title);
        builder.setMessage("Enter shearch parameter below");
        builder.setPositiveButton("Shearch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                shearchReport(editText.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //go back ? how to, probably nothing ?
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    /*private void FirebaseSearch() {
        FirebaseRecyclerOptions<Ticket> options = new FirebaseRecyclerOptions.Builder<Ticket>()
                .setQuery(getReportList(), Ticket.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Ticket, SolvedTicketActivity.EntryViewHolder>(options) {
            @NonNull
            @Override
            public SolvedTicketActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View ReportView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_report, parent, false);
                return new SolvedTicketActivity.EntryViewHolder(ReportView);
            }
            @Override
            protected void onBindViewHolder(@NonNull SolvedTicketActivity.EntryViewHolder entryViewHolder, int i, @NonNull Ticket ticket) {
                entryViewHolder.setTitle("TicketHolder id:" + i);
                //entryViewHolder.setContent(ticket.getContent());
            }
        };
        recyclerView.setAdapter(this.firebaseRecyclerAdapter);
        this.firebaseRecyclerAdapter.startListening();
    }*/

    private String shearchReport(String shearchParam) {
        //Procura linear
        String reportName;

        Query reportList = getReportList(shearchParam);

        reportList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                com.example.helpticket.databaseModels.Ticket ticket = dataSnapshot.getValue(com.example.helpticket.databaseModels.Ticket.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReportActivity.this, "The read operation has failed : " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });


        if (String.valueOf(ticket.getIdTicket()) == shearchParam) {
            reportName = shearchParam;
            //only populate the reports that have the shearch Param
        } else {
            reportName = null;
        }

        return reportName;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //when the recycler View is first loaded the view is loaded with the default parameter,
        String param = "idTicket";
        FirebaseRecyclerOptions<Ticket> options = new FirebaseRecyclerOptions.Builder<Ticket>()
                .setQuery(getReportList(param), Ticket.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Ticket, SolvedTicketActivity.EntryViewHolder>(options) {
            @NonNull
            @Override
            public SolvedTicketActivity.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View ReportView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_report, parent, false);
                return new SolvedTicketActivity.EntryViewHolder(ReportView);
            }
            @Override
            protected void onBindViewHolder(@NonNull SolvedTicketActivity.EntryViewHolder entryViewHolder, int i, @NonNull Ticket ticket) {
                entryViewHolder.setTitle("Ticket id:" + i);
                entryViewHolder.setIsRecyclable(true);
                //entryViewHolder.setContent(ticket.getContent());
            }
        };
        recyclerView.setAdapter(this.firebaseRecyclerAdapter);
        this.firebaseRecyclerAdapter.startListening();

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

    public void showDetailsTIcket() {
        Intent intent = new Intent(this, DetailsActivity.class);
        //PARSE THE CURRENT USER??
        startActivity(intent);
    }
}


