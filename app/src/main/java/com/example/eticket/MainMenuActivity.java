package com.example.eticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eticket.account.BalanceActivity;
import com.example.eticket.account.ShareCreditActivity;
import com.example.eticket.favorites.FavoriteRoutesActivity;
import com.example.eticket.history.LastTicketActivity;
import com.example.eticket.history.RecentTravelActivity;
import com.example.eticket.profile.ProfileActivity;
import com.example.eticket.ticket.ticket_gen;
import com.google.firebase.database.DatabaseReference;

public class MainMenuActivity extends AppCompatActivity {
    Button ticket_btn,logout,account_btn,favorite_btn,recent_btn,
            balance_btn,share_btn,last_ticket_btn;
    String uname;
    SharedPreferences sh;
    DatabaseReference reff1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ticket_btn = findViewById(R.id.ticket_btn);
        logout = findViewById(R.id.logout_btn);
        account_btn = findViewById(R.id.account_btn);
        favorite_btn= findViewById(R.id.favorite_btn);
        recent_btn= findViewById(R.id.recent_btn);
        balance_btn= findViewById(R.id.balance_btn);
        share_btn= findViewById(R.id.share_btn);
        last_ticket_btn= findViewById(R.id.lastticket_btn);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
             uname = extras.getString("uname");
        }
        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star = new Intent(getApplicationContext(), ticket_gen.class);
                star.putExtra("uname",uname);
                startActivity(star);
                //main_menu.this.finish();
            }
        });

        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(star1);
               // main_menu.this.finish();

            }
        });

        last_ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), LastTicketActivity.class);
                startActivity(star1);

            }
        });
        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), FavoriteRoutesActivity.class);
                startActivity(star1);
                //todo
            }
        });
        recent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), RecentTravelActivity.class);
                startActivity(star1);
                //todo
            }
        });
        balance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), BalanceActivity.class);
                startActivity(star1);

            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), ShareCreditActivity.class);
                startActivity(star1);
                //todo
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
           /*   reff1 = FirebaseDatabase.getInstance().getReference().child("member").child(uname);
                reff1.child("lock").setValue("0");
                sh = getSharedPreferences("user_details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor  = sh.edit();
                editor.clear();
                editor.commit();
                Intent test1 = new Intent(getApplicationContext(),MainActivity.class);
                Toast.makeText(main_menu.this, "LOGOUT SUCESSFUL", Toast.LENGTH_SHORT).show();
               startActivity(test1);
               main_menu.this.finish();
*/
            }
        });





    }
}
