package com.example.helpticket.mainModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helpticket.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends Activity {

   // private Databas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnNewTicket    = (Button) findViewById(R.id.btnNew);
        Button btnUpdateTicket = (Button) findViewById(R.id.btnUpdate);
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

        btnUpdateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Updating a Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showUpdateTicket();
            }
            //TODO:
        });
        btnDeleteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Deleting a Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showDeleteTicket();
            }
            //TODO:
        });
        btnSearchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Searching a new Ticket", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showSearchTicket();
            }

        });


    }

    public void showSearchTicket() {

    }

    public void showDeleteTicket() {
    }

    public void showUpdateTicket() {
    }

    public void showCreateNewTicket() {

        Intent intent = new Intent(this, CreateTicketActivity.class);


        // intent.putExtra();//Para passar valores de uma atividade para outra
         startActivity(intent);
    }

    //TODO:Criar as classes para os vários layouts
    //TODO:Para o spinner no create fazer o que está na página https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
    //TODO:Detalhes antes de for resolvido, detalhes depois de for resolvido
    //TODO:Relatório, tem de mostrar todos os pedidos que um técnico fez nesse dia. Poder ver sempre os relatórios antigos.
    //TODO:Create falta colocar spinner também os equipamentos que estão associados à pessoa.
}

