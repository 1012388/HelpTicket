package com.example.helpticket.ui.login;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpticket.R;
import com.example.helpticket.mainModel.MenuActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String PACKAGE_NAME = "com.example.helpticket.ui.login";
    public static final String LINK = "https://helpticket.page.link/";
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
    Button ForgotButton;


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
        ForgotButton = (Button) findViewById(R.id.ForgotButton);


        // Initialize Firebase Auth

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {//Significa que há um user ligado, Se está na login activity então é porque quer se desligar

            //todo:Testar isto, já deve dar bem
            welcome.setText("Hi, " + currentUser.getDisplayName());
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            LogOutButton.setVisibility(View.VISIBLE);
            LogOutUser();
        } else {//Não há ninguém logado
            welcome.setText("Hello, please Register or Log in");
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);
            LogOutButton.setVisibility(View.INVISIBLE);
        }


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

        ForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecoverPass(editTextEmail.getText().toString());
            }
        });

    }

    public void LogOutUser() {
        firebaseAuth.signOut();
        Toast.makeText(LoginActivity.this, "You are leaving the app", Toast.LENGTH_SHORT).show();
        //finish();
    }
    //todo:when the app is killed the user needs to log out

    public void LogInUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextEmail.getText().toString().trim();

        //Verifications
        boolean valid = true;
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            editTextEmail.setError("Haven't put any email");
            editTextPassword.setError("Haven't put any password");
            valid = false;
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

            editTextEmail.setError(null);
            editTextPassword.setError(null);
    }



    public void RegisterUser() {
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
                            } else {
                                Toast.makeText(LoginActivity.this, "The registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //Create an user then send a validation email
    public void EmailVerification() {
        //get current_user from FirebaseAuth
        FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();
        ActionCodeSettings.Builder actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(LINK).setAndroidPackageName(PACKAGE_NAME, false, null);

        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Sucesso e volta à app
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    updateUI(null);
                }
            }
        });
    }


    //Manage users
    //Reauthenticate
    public void reauthenticate(String email, String password) {
        // [START reauthenticate]
        //Set the old user to be the new user
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });
        // [END reauthenticate]
    }

    //Send an email to provide another password

    public void RecoverPass(String email) {
        firebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }


    //Delete
    public void deleteUser() {
        FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();

        currentUser.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "The current user was deleted sucessefully");
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

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            //PUT USERNAME IN THE TET VIEW
            editTextEmail.setVisibility(View.VISIBLE);
            editTextPassword.setVisibility(View.VISIBLE);

            LogInButton.setVisibility(View.VISIBLE);
            RegisterButton.setVisibility(View.INVISIBLE);
            LogOutButton.setVisibility(View.VISIBLE);

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
