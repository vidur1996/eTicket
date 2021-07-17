package com.example.eticket.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {
    SharedPreferences profilePreferences;
    Button btn_changePassword;
    Button btn_editProfile;
    TextView tv_name, tv_email, tv_phone, tv_username;
    String username;
    String name, email, phone;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_changePassword = (Button) findViewById(R.id.btn_profile_change_password);
        btn_editProfile = (Button) findViewById(R.id.btn_profile_edit);
        tv_name = (TextView) findViewById(R.id.tv_profile_name);
        tv_username = (TextView) findViewById(R.id.tv_profile_username);
        tv_email = (TextView) findViewById(R.id.tv_profile_email);
        tv_phone = (TextView) findViewById(R.id.tv_profile_phone);
        getUserName();
        tv_username.setText(username);
        reff = FirebaseDatabase.getInstance().getReference().child("member").child(username);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                name = snapshot.child("name").getValue().toString();
                phone = snapshot.child("num").getValue().toString();
                email = snapshot.child("email").getValue().toString();
                tv_email.setText(email);
                tv_name.setText(name);
                tv_phone.setText(phone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), EditProfileActivity.class);
                in1.putExtra("username", username);
                in1.putExtra("name", name);
                in1.putExtra("phone", phone);
                in1.putExtra("email", email);
                startActivity(in1);
            }
        });

        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                in1.putExtra("username", username);
                startActivity(in1);

            }
        });
    }

    public void getUserName(){
        profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }
}