package com.example.eticket.favorites;

import androidx.appcompat.app.AppCompatActivity;
import com.example.eticket.R;
import com.example.eticket.profile.ProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FavoriteRoutesActivity extends AppCompatActivity {
ImageView img_add_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_routes);

        img_add_new = findViewById(R.id.img_add_new);

        img_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent star1 = new Intent(getApplicationContext(), AddFavoritesActivity.class);
                startActivity(star1);
            }
        });

    }
}