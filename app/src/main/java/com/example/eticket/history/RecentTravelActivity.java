package com.example.eticket.history;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket.R;
import com.example.eticket.data_model.Ticket;
import com.example.eticket.history.adapter.RecentTicketAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecentTravelActivity extends AppCompatActivity {
    ArrayList<Ticket> list = new ArrayList<Ticket>();
    DatabaseReference databaseReference;
    RecentTicketAdapter adapter;
    SharedPreferences profilePreferences;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_travel);
        getUserName();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("tickets").child(username).child("pastTickets");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Ticket value = snapshot.getValue(Ticket.class);

                    list.add(value);
                    adapter.notifyDataSetChanged();
                } else {


                }


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recent_travel);
        adapter = new RecentTicketAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void getUserName(){
        profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }


}