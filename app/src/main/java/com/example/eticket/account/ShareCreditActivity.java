package com.example.eticket.account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.eticket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class ShareCreditActivity extends AppCompatActivity {
    TextView tv_share_balance;
    EditText et_share_username,et_share_amount;
    Button btn_share_credit;
    String username,myBalance,friendBalance;
    Boolean validUser = false, successUser = false,successFriend = false;
    int shareAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_credit);
        tv_share_balance = findViewById(R.id.tv_share_balance);
        et_share_username = findViewById(R.id.et_share_username);
        btn_share_credit = findViewById(R.id.btn_share_credit);
        et_share_amount = findViewById(R.id.et_share_amount);
        getUserName();
        getAcccountBalance();

        btn_share_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatedata()){
                        validateUser(et_share_username.getText().toString());
                }

            }
        });

    }
    public void getUserName(){
        SharedPreferences profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }

    public void showAlert(String title,String message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();

    }
    public Boolean validatedata(){
        if (et_share_username.getText().toString() != "") {
            if (et_share_amount.getText().toString() != "") {
                    return true;

            } else {
                showAlert("Error", "please enter the amount");
                return false;
            }
        } else {
            showAlert("Error", "please enter the username");
            return false ;
        }
    }

    public void getAcccountBalance() {
        DatabaseReference databaseReference
                = FirebaseDatabase.getInstance().getReference().child("member").child(username).child("balance");
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


    public void showConfirmation(String title,String message) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            if (checkCredit()){
                                shareCredit(et_share_username.getText().toString());
                            }

                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
        }).show();

    }

    public void   validateUser(final String friend){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("member").child(friend);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){


                    friendBalance = snapshot.child("balance").getValue().toString();
                    showConfirmation("Confirmation", "are you sure you want to share "+et_share_amount.getText().toString());
                    databaseReference.removeEventListener(this);
                }
                else{
                    showAlert("Error","User not found, Please check the username");
                    databaseReference.removeEventListener(this);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public Boolean checkCredit() {
        shareAmount = Integer.parseInt(et_share_amount.getText().toString());
        if (Integer.parseInt(myBalance) > (shareAmount + 100)) {
            return true;

        } else {
            showAlert("Balance Insufficient", "Balance should be greater than Rs.100 after Transfer");
            return false;
        }

    }
    public void shareCredit(String friend){
        int totalFriendBalance = Integer.parseInt(friendBalance) +shareAmount;
        DatabaseReference databaseReferenceFriend = FirebaseDatabase.getInstance().getReference().child("member").child(friend);
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error,  @NotNull DatabaseReference ref) {
                  updateUser();

            }
        };


        databaseReferenceFriend.child("balance").setValue(totalFriendBalance,completionListener);

    }

    public void updateUser(){

            DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("member").child(username);
            databaseReferenceUser.child("balance").setValue(Integer.parseInt(myBalance)-shareAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    alert();
                }
            });
    }

    public void alert() {

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Success")
                    .setMessage("credit amount " + shareAmount + " Successful")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            et_share_amount.setText("");
                            et_share_username.setText("");
                           // getAcccountBalance();

                        }}).show();


    }

}