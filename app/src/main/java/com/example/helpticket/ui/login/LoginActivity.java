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
        loginButton = findViewById(R.id.emailSignInButton);

        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance();
    }

    private void showMainMenu(Editable username, Editable password) {
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
                            Log.d(TAG,"signInWithEmai:sucess");
                            FirebaseUser user = mAtuth.getCurrentUser();
                            updateUI(user);
                        }else{
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

    private boolean validateForm() {
        boolean valid = true;
        String email = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(email)){
            usernameEditText.setError("Required.");
            valid = true;
        }else
        {
            usernameEditText.setError(null);
        }

        String password = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(password )){
            passwordEditText.setError("Required.");
            valid = true;
        }else
        {
            passwordEditText.setError(null);
        }


        return valid;
    }

    private void updateUI(FirebaseUser user){
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
        } else if (i == R.id.signedInButtons) {
            signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        } else if (i == R.id.signedInButtons) {//Botão para signOut
            signOut();
        }
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseUser currentUser = mAtuth.getCurrentUser();
        updateUI(currentUser);

    }
    private void createAccount(String email,String password){
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

    private void singIn(String email,String password){
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

    private void signOut(){
        mAtuth.signOut();
        updateUI(null);
    }

    private void hideProgressDialog(){
        progressBar = new ProgressBar(LoginActivity.this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setVisibility(View.GONE); //TO Hide ProgressBar
    }

    private void showProgressDialog(){
        progressBar = new ProgressBar(LoginActivity.this,null,android.R.attr.progressBarStyleLarge);

        progressBar.setVisibility(View.VISIBLE); //To show ProgressBar

    }

}
