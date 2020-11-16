package com.example.eticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ticket_display extends AppCompatActivity {
    TextView to_txt,from_txt,price_txt1,distance_txt1,user_txt1;
    ImageView qrimage;
    String uname1,town_to1,town_from1,price1,data,balance,distance;
    DatabaseReference reffer1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_display);

        price_txt1  = findViewById(R.id.price_txt1);
        to_txt = findViewById(R.id.to_txt1);
        from_txt = findViewById(R.id.from_txt1);
        distance_txt1 = findViewById(R.id.distance_txt1);
        user_txt1 = findViewById(R.id.user_txt1);
        qrimage = findViewById(R.id.qrPlaceHolder);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname1 = extras.getString("uname");
            town_to1 = extras.getString("town_to");
            town_from1= extras.getString("town_from" );
            price1= extras.getString("price" );
            distance =  extras.getString("distance" );
            user_txt1.setText(uname1);
            balance = extras.getString("balance" );
            price_txt1.setText(   "price    :- "+ price1);
            to_txt.setText(       "To       :- "+town_to1);
            from_txt.setText(     "From     :- "+ town_from1);
            distance_txt1.setText("distance :- "+distance+" km");

        }

        data = uname1+"*"+town_to1+"*"+town_from1+"*"+price1;

        QRGEncoder qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,500);
        try
        {
                        /*Bitmap qrimg;
                        qrimg = qrgEncoder.encodeAsBitmap();
                        qrimage.setImageBitmap(qrimg);*/

            Bitmap bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            qrimage.setImageBitmap(bitmap);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        reffer1 = FirebaseDatabase.getInstance().getReference().child("member").child(uname1).child("balance");

        reffer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bal1 = dataSnapshot.getValue().toString();



                if(! bal1.equals(balance))
                {
                    Intent i1 = new Intent(getApplicationContext(),ticket_sucess.class);
                    startActivity(i1);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
