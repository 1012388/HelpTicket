package com.example.helpticket.mainModel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helpticket.R;
import com.example.helpticket.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        Button buttonLogIn = (Button) findViewById(R.id.buttonLogIn);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:ABRIR A PÁGINA DO LOGIN
                logIn();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:ABRIR A PÁGINA DO SINGUP
                // signUp();
            }
        });
    }

    private void logIn() {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
}
