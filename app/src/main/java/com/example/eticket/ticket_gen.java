package com.example.eticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ticket_gen extends AppCompatActivity {
    Spinner from_spin,to_spin;
    TextView user_txt,distance_txt , price_txt;
    String uname ;
    String town_from ="";
    String town_to ="";
    int distance, rate,price;
    DatabaseReference reff,reff2;
    Intent star;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_gen);

        user_txt  =findViewById(R.id.user_txt);
        from_spin=findViewById(R.id.from_spin);
        to_spin = findViewById(R.id.to_spin);
        distance_txt=findViewById(R.id.distance_txt);
        price_txt = findViewById(R.id.price_txt);
        Button get_ticket_btn = findViewById(R.id.gen_ticket_btn);
        Button back_btn1 = findViewById(R.id.back_btn1);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname = extras.getString("uname");
        }

        user_txt.setText(uname);


        star = new Intent(getApplicationContext(),ticket_display.class);
        star.putExtra("uname",uname);



        reff2 = FirebaseDatabase.getInstance().getReference();
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                price=  Integer.parseInt(dataSnapshot.child("rate").getValue().toString());
                //   price_txt.setText(dataSnapshot.child("rate").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        final ArrayAdapter<String> myadapter  = new ArrayAdapter<String>(ticket_gen.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tr_batti));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from_spin.setAdapter(myadapter);





        final ArrayAdapter<String> myadapter1  = new ArrayAdapter<String>(ticket_gen.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tr_batti));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        to_spin.setAdapter(myadapter1);





        from_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                town_from = from_spin.getSelectedItem().toString().trim();
                // datagather();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        to_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                town_to = to_spin.getSelectedItem().toString().trim();
                if(!town_from.equals("") && !town_to.equals(""))
                {
                    reff = FirebaseDatabase.getInstance().getReference().child("distance").child(town_from);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            distance=  Integer.parseInt(dataSnapshot.child(town_to).getValue().toString());
                            distance_txt.setText(dataSnapshot.child(town_to).getValue().toString());
                            price = price*distance;
                            price_txt.setText(Integer.toString(price));

                            star.putExtra("town_to",town_to );
                            star.putExtra("town_from",town_from );
                            star.putExtra("price",Integer.toString(price) );
                            star.putExtra("distance",Integer.toString(distance) );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(ticket_gen.this, "select destination", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        get_ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(star);
            }
        });


        back_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent =  new Intent(getApplicationContext(),member.class);
                startActivity(backintent);
                ticket_gen.this.finish();

            }
        });










    }


}
