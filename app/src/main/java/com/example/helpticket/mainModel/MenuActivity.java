package com.example.helpticket.mainModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.helpticket.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
/*
        provider = OAuthProvider.newBuilder("microsoft.com");

        //Force re-consent
        //provider.addCustomParamater("prompt","consent");

        //Target specific email with login hint
        provider.addCustomParameter("login_hint", "user@fistadd.onmicrofoft.com");

        //    provider.addCustomParamater("tenant","TENTANT_ID");

        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    currentUserName = authResult.getUser().getDisplayName();
                                    ((TextView) findViewById(R.id.textViewTech)).setText(currentUserName);

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.

            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth
                        .startActivityForSignInWithProvider( this, provider.build())
                       .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().

                                        currentUserName = authResult.getUser().getDisplayName();
                                        ((TextView) findViewById(R.id.textViewTech)).setText(currentUserName);
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                    }
                                });


            } else {
                // The user is already signed-in.
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                firebaseUser
                        .startActivityForLinkWithProvider(/* activity=  this, provider.build())
                   /*     .addOnSuccessListener(
                                new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // Microsoft credential is linked to the current user.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                        currentUserName = authResult.getUser().getDisplayName();
                                        ((TextView) findViewById(R.id.textViewTech)).setText(currentUserName);

                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                    }
                                });


            }

        }*/
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


    //TODO:Relatório, tem de mostrar todos os pedidos que um técnico fez nesse dia. Poder ver sempre os relatórios antigos.
}

