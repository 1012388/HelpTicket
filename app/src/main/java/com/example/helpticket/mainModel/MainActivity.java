package com.example.helpticket.mainModel;

import android.app.Activity;
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
}
