package com.example.helpticket.mainModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helpticket.R;
import com.example.helpticket.ReportManager.ReportActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class MenuActivity extends Activity {
    OAuthProvider.Builder provider;
    private FirebaseAuth firebaseAuth;
    private String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnNewTicket = (Button) findViewById(R.id.btnNew);
        Button btnReports = (Button) findViewById(R.id.btnReports);
        Button btnDeleteTicket = (Button) findViewById(R.id.btnDelete);
        Button btnSearchTicket = (Button) findViewById(R.id.btnSearch);

        btnNewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating a new Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showCreateNewTicket();
            }
        });

        btnDeleteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Deleting a Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showDeleteTicket();
            }
        });

        btnSearchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching a  Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showSearchTicket();
            }
        });

        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching a  Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showReports();
            }
        });
    }

    private void showReports() {
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    public void showSearchTicket() {
        Intent intent = new Intent(this, AllTicketActivity.class);
        startActivity(intent);
    }

    public void showDeleteTicket() {
    }

    public void showCreateNewTicket() {
        Intent intent = new Intent(this, CreateTicketActivity.class);
        startActivity(intent);
    }
}

