package com.example.eticket.history.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket.R;
import com.example.eticket.data_model.Ticket;

import java.util.ArrayList;


public class RecentTicketAdapter extends RecyclerView.Adapter<RecentTicketAdapter.ViewHolder> {
        private final ArrayList<Ticket> userset;
        public RecentTicketAdapter(ArrayList<Ticket> arrayList) {
            userset = arrayList;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.item_recent_ticket, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            final Ticket myListData = userset.get(position);
            holder.ticket_no.setText(myListData.getTicketNo());
            holder.ticket_from.setText(myListData.getTicketFrom());
            holder.ticket_to.setText(myListData.getTicketTo());
            holder.ticket_user.setText(myListData.getBusName());
            holder.ticket_value.setText("Rs." + myListData.getPrice());
            holder.ticket_Time.setText(myListData.getTicketTime());
        }

        @Override
        public int getItemCount() {
            return userset.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView ticket_no;
            TextView ticket_to;
            TextView ticket_from;
            TextView ticket_user;
            TextView ticket_value;
            TextView ticket_Time;

            public ViewHolder(View itemView) {
                super(itemView);

                ticket_no = (TextView) itemView.findViewById(R.id.tv_ticket_id);
                ticket_to = (TextView) itemView.findViewById(R.id.tv_ticket_to);
                ticket_from = (TextView) itemView.findViewById(R.id.tv_ticket_from);
                ticket_user = (TextView) itemView.findViewById(R.id.tv_passenger_id);
                ticket_value = (TextView) itemView.findViewById(R.id.tv_ticket_value);
                ticket_Time = (TextView) itemView.findViewById(R.id.tv_ticket_time);
            }
        }

    }
