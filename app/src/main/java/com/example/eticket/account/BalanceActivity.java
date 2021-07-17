package com.example.eticket.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eticket.R;
import com.example.eticket.data_model.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BalanceActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences profilePreferences;
    String username;
    TextView tv_balance,tv_balalnce_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        tv_balance = findViewById(R.id.tv_balance);
        tv_balalnce_low = findViewById(R.id.tv_low_alert);

        getUserName();
        getAcccountBalance();


    }

    public void getAcccountBalance() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("member").child(username).child("balance");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String accountBalance = snapshot.getValue().toString();
                DisplayData( accountBalance);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void DisplayData(String balance){
        tv_balance.setText(balance);
        if(Integer.parseInt(balance)<100){
            tv_balalnce_low.setVisibility(View.VISIBLE);
        }


    }

    public void getUserName(){
        profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }
}