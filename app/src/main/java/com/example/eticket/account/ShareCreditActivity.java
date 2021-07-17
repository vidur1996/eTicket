package com.example.eticket.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eticket.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShareCreditActivity extends AppCompatActivity {
     TextView tv_share_balance;
      EditText et_share_username;
    Button btn_share_credit;
    String username,myBalance;
    int shareAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_credit);
        tv_share_balance = findViewById(R.id.tv_share_balance);
        et_share_username = findViewById(R.id.et_share_username);
        btn_share_credit = findViewById(R.id.btn_share_credit);
        getUserName();
        getAcccountBalance();

        btn_share_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_share_username.getText().toString()!=""){
                    shareAmount = Integer.parseInt(et_share_username.getText().toString());
                }

            }
        });



    }
    public void getUserName(){
       SharedPreferences profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }

    public void getAcccountBalance() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("member").child(username).child("balance");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myBalance = snapshot.getValue().toString();
                tv_share_balance.setText("Rs. "+myBalance);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("Edit successFul")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                }).show();

    }
}