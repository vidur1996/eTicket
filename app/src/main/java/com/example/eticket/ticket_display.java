package com.example.eticket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ticket_display extends AppCompatActivity {
TextView to_txt,from_txt,price_txt,distance_txt;
    ImageView qrimage;
    String uname,town_to,town_from,price,data,distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_display);

        price_txt  = findViewById(R.id.distance_txt);
        to_txt = findViewById(R.id.to_txt);
        from_txt = findViewById(R.id.from_txt);
        distance_txt = findViewById(R.id.distance_txt);
        qrimage = findViewById(R.id.qrPlaceHolder);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname = extras.getString("uname");
            town_to = extras.getString("town_to");
            town_from = extras.getString("town_from" );
            price= extras.getString("price" );
            distance =  extras.getString("distance" );
            price_txt.setText(   "price    :- "+ price);
            to_txt.setText(      "To       :- "+town_to);
            from_txt.setText(    "From     :- "+ town_from);
            distance_txt.setText("distance :- "+distance+" km");

        }

        data = uname+"*"+town_to+"*"+town_from+"*"+price;

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







    }
}
