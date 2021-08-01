package com.example.eticket.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eticket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class ShareCreditActivity extends AppCompatActivity {
     TextView tv_share_balance;
      EditText et_share_username,et_share_amount;
    Button btn_share_credit;
    String username,myBalance,friendBalance;
    Boolean successUser = false,successFriend = false;
    int shareAmount;
       ;
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
                if(et_share_username.getText().toString()!=""){
                    if(et_share_amount.getText().toString()!=""){
                        validateUser(et_share_username.getText().toString().trim());
                    }else {
                        showAlert("Error","plesse enter the amount");
                    }

                }else{
                    showAlert("Error","plesse enter the username");
                }

            }
        });



    }
    public void getUserName(){
       SharedPreferences profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
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

    public int getFriendBalance(String friend) {
        DatabaseReference databaseReferenceAmount = FirebaseDatabase.getInstance().getReference().child("member").child(friend).child("balance");

        databaseReferenceAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                friendBalance = snapshot.getValue().toString();
                Log.e("*****",snapshot.getValue().toString());
                tv_share_balance.setText("Rs. "+friendBalance);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
            return Integer.parseInt(friendBalance);


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

    public void  validateUser(final String friend){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("member").child(friend);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    checkCredit(friend);
                }
                else{
                    showAlert("Error","User not found, Please check the username");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkCredit(String friend){
        shareAmount = Integer.parseInt(et_share_amount.getText().toString());
        if(Integer.parseInt(myBalance)>(shareAmount+100) )
        {
            shareCredit(friend);
        }else {
            showAlert("Balance Insufficient","Balance should be greater than Rs.100 after Transfer");
        }

    }
    public void shareCredit(String friend){
       int totalFriendBalance =  getFriendBalance(friend)+shareAmount;
        DatabaseReference databaseReferenceFriend = FirebaseDatabase.getInstance().getReference().child("member").child(friend);
                DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                        updateUser();
                        successFriend = true;
                    }
                };


        databaseReferenceFriend.child("balance").setValue(totalFriendBalance,completionListener);

    }

    public void updateUser(){
        if(successFriend) {
            DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child("member").child(username);
            databaseReferenceUser.child("balance").setValue(Integer.parseInt(myBalance)-shareAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    successUser = true;
                    alert();

                }
            });
        }
    }

    public void alert() {
        if(successUser) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Success")
                    .setMessage("credit amount " + shareAmount + " Successful")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            et_share_amount.setText("");
                            et_share_username.setText("");
                            getAcccountBalance();

                        }
                    }).show();
        }

    }

}