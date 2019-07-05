package com.example.helpticket.mainModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpticket.R;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RequestAdapter> ticketDataList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View TicketView = inflater.inflate(R.layout.activity_solved_ticket, parent, false);
        ViewHolder viewHolder = new ViewHolder(TicketView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestAdapter requestAdapter = ticketDataList.get(position);
        Button b = holder.ticket;
        b.setText("Ticket " + position);
    }

    @Override
    public int getItemCount() {
        return ticketDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button ticket;


        public ViewHolder(View itemView) {
            super(itemView);

            ticket = (Button) itemView.findViewById(R.id.btnNewticket);

        }
    }

    public RequestAdapter(List<RequestAdapter> ticketDataList) {
        this.ticketDataList = ticketDataList;
    }


}
