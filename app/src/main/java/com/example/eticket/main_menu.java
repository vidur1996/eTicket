package com.example.eticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class main_menu extends AppCompatActivity {
Button ticket_btn,logout;
String uname;
    SharedPreferences sh;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ticket_btn = findViewById(R.id.ticket_btn);
        logout = findViewById(R.id.logout_btn);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
             uname = extras.getString("uname");
        }
        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star = new Intent(getApplicationContext(),ticket_gen.class);
                star.putExtra("uname",uname);
                startActivity(star);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("member").child(uname);
                reff.child("lock").setValue("0");
                sh = getSharedPreferences("user_details", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor  = sh.edit();
                editor.putString("user","");
                editor.putString("pass","");
                editor.putString("del","false");
                editor.commit();


                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });





    }
}
