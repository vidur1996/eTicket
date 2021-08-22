package com.example.eticket.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.example.eticket.MainMenuActivity;
import com.example.eticket.R;

public class ticket_sucess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sucess);

        new CountDownTimer(5000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent i1 = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(i1);
                finish();
            }

        }.start();
    }
}
