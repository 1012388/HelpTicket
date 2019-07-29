package com.example.helpticket.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpticket.R;
import com.example.helpticket.mainModel.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.microsoft.identity.client.*;
import com.microsoft.identity.client.exception.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseUser firebaseUser;

    private MenuActivity menuActivity;
    String displayName;
    String uid;
    private String mCustomToken;
    private TokenBroadcastReceiver mTokenReceiver;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Button click listeners
        //button.setOnClickListener(this);

        mTokenReceiver = new TokenBroadcastReceiver() {
            @Override
            public void onNewToken(String token) {
                Log.d(TAG, "onNewToken:" + token);
                setCustomToken(token);
            }
        };

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();


        FileInputStream serviceAccount = null;
        FirebaseOptions options = null;
        try {
            serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://helpticket-e00ce.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        updateUI(currentUser);
    }

    public void onResume() {

        super.onResume();
        //registerReceiver(mToksignInWithCustomToken(mCustomToken)enReceiver,TokenBroadcastReceiver.getFilter())
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mTokenReceiver);
    }

    public void signIn() {
        firebaseAuth.
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCustomToken:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != currentUser) {
            //PUT USERNAME IN THE TET VIEW

        } else {
            //ERROR
        }
    }

    private void setCustomToken(String token) {
        mCustomToken = token;

        String status;
        if (mCustomToken != null) {
            status = "Token:" + mCustomToken;
        } else {
            status = "Token: null";
        }

        //Enable/disable the sign-in button and show the token
    }

    public void onClick(View v) {
        int i = v.getId();
        //if(i == R.id.buttonSignIn){
        //    startSignIn();
        // }

    }


    public void signOut() {
        firebaseAuth.getInstance().signOut();
    }


    private void showMenu() {
        Intent intent = new Intent(this, MenuActivity.class);

        intent.putExtra("Firebase User id", uid);
        intent.putExtra("Firebase User Name", displayName);

        startActivity(intent);
    }



}
