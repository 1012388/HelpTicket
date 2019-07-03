package com.example.helpticket.mainModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpticket.R;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_ticket);

        ButtonData[] buttonData = new ButtonData[]{
                new ButtonData("Request", android.R.drawable.btn_default)
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RequestAdapter requestAdapter = new RequestAdapter(buttonData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(requestAdapter);
    }

    public void showMainActivity() {
        //TODO:criar uma classe para mostrar o menu
        //Intent intent = new Intent(this, showMainActivity.class);


        // intent.putExtra();//Para passar valores de uma atividade para outra
        // startActivity(intent);
    }

    //TODO:Criar as classes para os vários layouts
    //TODO:Para o spinner no create fazer o que está na página https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
    //TODO:Detalhes antes de for resolvido, detalhes depois de for resolvido
    //TODO:Relatório, tem de mostrar todos os pedidos que um técnico fez nesse dia. Poder ver sempre os relatórios antigos.
    //TODO:Create falta colocar spinner também os equipamentos que estão associados à pessoa.
}
