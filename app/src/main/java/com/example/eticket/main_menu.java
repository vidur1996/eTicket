package com.example.eticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_menu extends AppCompatActivity {
Button ticket_btn;
String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ticket_btn = findViewById(R.id.ticket_btn);

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



    }
}
