package com.example.helpticket.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpticket.R;
import com.example.helpticket.mainModel.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login";


    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private FirebaseAuth mAtuth;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private ProgressBar progressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        progressBar      = findViewById(R.id.progressBar);

        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);
        findViewById(R.id.signOutButton).setOnClickListener(this);
        findViewById(R.id.signedInButtons).setOnClickListener(this);





        FirebaseApp.initializeApp(this);
        mAtuth = FirebaseAuth.getInstance();

    }

    private void showMainMenu(String username,String password) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("Username",username);
        intent.putExtra("Password", password);

        startActivity(intent);
    }


    private void signIn(String email,String password){
        Log.d(TAG,"signIn :" + email);
        if(!validateForm()){
         return;
        }

        showProgressDialog();
        mAtuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //If sucess then update the ui and open the new Activity
                            Log.d(TAG,"signInWithEmai:sucess");
                            FirebaseUser user = mAtuth.getCurrentUser();
                            updateUI(user);
                            showMainMenu(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                        }else{//else errors
                            Log.w(TAG,"signInWithEmai:failure",task.getException());
                            Toast.makeText(LoginActivity.this,"Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Authentication failed.",Toast.LENGTH_SHORT).show();

                        }
                       hideProgressDialog();
                    }
                });

    }



    //Default FirebaseApp is not initialized in this process
    //Make sure to call FirebaseApp.initializeApp(Context) first.

    private void sendEmailVerification(){
        FirebaseUser user = mAtuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this
                                    ,"Verification email sent to " + usernameEditText.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e(TAG,"sendEmailVerification",task.getException());
                            Toast.makeText(LoginActivity.this
                                    ,"Verification email sent to " + passwordEditText.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    public boolean validateForm() {
        boolean valid = true;
        String email = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(email)){
            usernameEditText.setError("Required.");
            valid = false;
        }else
        {
            usernameEditText.setError(null);
        }

        String password = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(password )){
            passwordEditText.setError("Required.");
            valid = false;
        }else
        {
            passwordEditText.setError(null);
        }


        return valid;
    }

    public void updateUI(FirebaseUser user){
        hideProgressDialog();
        if(user != null){
            mStatusTextView.setText("Email user : "+user.getDisplayName() +
                    user.getEmail()+ user.isEmailVerified());
            mDetailTextView.setText("Firebase status:" +  user.getUid());

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);

            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText("Sign Out");
            mDetailTextView.setText(null);

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            Toast.makeText(LoginActivity.this, "Criar conta", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.emailSignInButton) {
            signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            Toast.makeText(LoginActivity.this, "Entrar...", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.signOutButton) {//Botão para signOut
            signOut();

        }
    }

    @Override
    public void onStart() {

        super.onStart();

        mAtuth = FirebaseAuth.getInstance();

        new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAtuth.getCurrentUser();
                if(currentUser != null){
                    //está ligado
                    Log.d(TAG,"onAuthStateChanged:sign_in"+currentUser);
                    updateUI(currentUser);

                }else{
                    Log.d(TAG,"onAuthStateChanged:sign_out");
                    updateUI(null);

                }
            }
        };
    }

    public void createAccount(String email,String password){
        Log.d(TAG,"createAccount:"+email);
        if(!validateForm()) return;

        showProgressDialog();

        mAtuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG,"createUserWithEmail:sucess");
                        FirebaseUser currentUser = mAtuth.getCurrentUser();
                        updateUI(currentUser);
                    }else
                    {
                        Log.w(TAG,"createUserWithEmail:failure");
                        Toast.makeText(LoginActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    hideProgressDialog();
                }

            });
    }

    public void singIn(String email,String password){
        Log.d(TAG,"signIn:"+email);
        if(!validateForm()) return;

        showProgressDialog();

        mAtuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"createUserWithEmail:sucess");
                            FirebaseUser currentUser = mAtuth.getCurrentUser();
                            updateUI(currentUser);

                        }else{

                            Log.w(TAG,"createUserWithEmail:failure");
                            Toast.makeText(LoginActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if(!task.isSuccessful()) mStatusTextView.setText("Authentication Failed");

                        hideProgressDialog();
                    }
                });
    }

    public void signOut(){
        mAtuth.signOut();
        updateUI(null);
        finish();
    }

    public void hideProgressDialog(){
        progressBar = new ProgressBar(LoginActivity.this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setVisibility(View.GONE); //TO Hide ProgressBar
    }

    private void showProgressDialog(){
        progressBar = new ProgressBar(LoginActivity.this,null,android.R.attr.progressBarStyleLarge);

        progressBar.setVisibility(View.VISIBLE); //To show ProgressBar

    }

}
