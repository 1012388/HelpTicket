package com.example.helpticket.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpticket.R;
import com.example.helpticket.mainModel.MenuActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private MenuActivity menuActivity;
    String displayName;
    String uid;
    EditText editTextEmail;
    EditText editTextPassword;
    Button LogInButton;
    Button RegisterButton;
    TextView welcome;
    Button LogOutButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        LogInButton = (Button) findViewById(R.id.LogInButton);
        RegisterButton = (Button) findViewById(R.id.RegisterButton);
        welcome = (TextView) findViewById(R.id.welcome);
        LogOutButton = (Button) findViewById(R.id.LogOutButton);


        // Initialize Firebase Auth

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {//Significa que há um user ligado, Se está na login activity então é porque quer se desligar
            welcome.setText("Hi, " + currentUser.getDisplayName());
            LogOutButton.setVisibility(View.VISIBLE);
        } else {//Não há ninguém logado
            welcome.setText("Hello, please Register or Log in");
            editTextEmail.setVisibility(View.INVISIBLE);
            editTextPassword.setVisibility(View.INVISIBLE);
            LogOutButton.setVisibility(View.INVISIBLE);
        }

        //TODO: DELETE USER,RECOVER

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI(null);
                RegisterUser();
            }
        });

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogInUser();
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOutUser();
            }
        });
    }

    private void LogOutUser() {
        firebaseAuth.signOut();
        Toast.makeText(LoginActivity.this, "You are leaving the app", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void LogInUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextEmail.getText().toString().trim();

        //Verifications
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Haven't put any email");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Haven't put any password");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        Task<AuthResult> authResultTask = firebaseAuth.signInWithEmailAndPassword(email, password);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {
                        welcome.setText("Welcome: " + firebaseAuth.getCurrentUser().getDisplayName());
                        //porque raio fecho? Ver o que faz
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "The login was failed", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void RegisterUser() {
        //Register User
        //Get email and password
        String email = editTextEmail.getText().toString().trim();
        String password = editTextEmail.getText().toString().trim();

        //Verifications
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Haven't put any email");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Haven't put any password");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(),
                    "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "The registration was succeseful", Toast.LENGTH_SHORT).show();
                                //porque raio fecho? Ver o que faz

                                finish();
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                                updateUI(null);
                            } else {
                                Toast.makeText(LoginActivity.this, "The registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();

        //I am suposed to start the listeners here

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            //PUT USERNAME IN THE TET VIEW
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);

            LogInButton.setVisibility(View.VISIBLE);
            RegisterButton.setVisibility(View.INVISIBLE);
            LogOutButton.setVisibility(View.GONE);

        } else {//Se o user não esta ligado significa que está a tentar ligá-lo

            welcome.setText("Trying to Register");
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);

            LogInButton.setVisibility(View.INVISIBLE);
            RegisterButton.setVisibility(View.VISIBLE);
            LogOutButton.setVisibility(View.INVISIBLE);
        }
    }
}
