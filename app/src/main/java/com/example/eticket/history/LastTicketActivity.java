package com.example.eticket.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.eticket.R;
import com.example.eticket.data_model.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LastTicketActivity extends AppCompatActivity {
    TextView tv_last_to,tv_last_from,tv_last_bus,tv_last_price,tv_last_ticket_no;
    Button btn_history;
    DatabaseReference databaseReference;
    SharedPreferences profilePreferences;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_ticket);

        tv_last_to = findViewById(R.id.tv_last_to);
        tv_last_from = findViewById(R.id.tv_last_from );
        tv_last_bus = findViewById(R.id.tv_last_bus);
        tv_last_price = findViewById(R.id.tv_last_price );
        tv_last_ticket_no = findViewById(R.id.tv_last_ticket_no);
        btn_history =  findViewById(R.id.btn_last_past_tickets);
        getUserName();

        getLastTicket();
    }


    public void getLastTicket() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("tickets").child(username).child("currentTicket");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                Log.e("****",snapshot.toString());
               Ticket ticket = snapshot.getValue(Ticket.class);
                    DisplayData(ticket);

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void DisplayData(Ticket ticket){
        tv_last_to.setText(       "Trip start   : "+ticket.getTicketTo());
        tv_last_from.setText(     "Trip From    : "+ticket.getTicketFrom());
        tv_last_bus.setText(      "Trip bus     : "+ticket.getBusName());
        tv_last_price.setText(    "Ticket Price : "+ticket.getTicketValue());
        tv_last_ticket_no.setText("Ticket No    : "+ticket.getTicketNo());
    }

    public void getUserName(){
        profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }
}