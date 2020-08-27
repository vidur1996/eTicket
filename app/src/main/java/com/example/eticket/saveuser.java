package com.example.eticket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class saveuser extends Activity {
Button yes_btn,no_btn;
    String username;
    String password;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveuser);


        yes_btn = findViewById(R.id.yes_btn);
        no_btn = findViewById(R.id.no_btn);
        sh = getSharedPreferences("user_details", Context.MODE_PRIVATE);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            username = extras.getString("uname");
            password = extras.getString("pass");
        }




        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor  = sh.edit();
                editor.putString("user",username);
                editor.putString("pass",password);
                editor.commit();
                Intent star = new Intent(getApplicationContext(),main_menu.class);
                star.putExtra("uname",username);
                startActivity(star);
            }
        });


        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star = new Intent(getApplicationContext(),main_menu.class);
                star.putExtra("uname",username);
                startActivity(star);
            }
        });
    }
}
