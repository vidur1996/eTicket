package com.example.eticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ticket_gen extends AppCompatActivity {
    TextView user_txt;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_gen);

        user_txt  =findViewById(R.id.user_txt);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname = extras.getString("uname");
        }

        user_txt.setText(uname);



    }
}
