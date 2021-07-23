package com.example.eticket.favorites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket.R;
import com.example.eticket.favorites.adapter.FavouriteRouteAdapter;
import com.example.eticket.favorites.adapter.Routes;
import com.example.eticket.profile.ProfileActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteRoutesActivity extends AppCompatActivity {
    ImageView img_add_new;
    String id;
    String username;
    DatabaseReference databaseReference;
    SharedPreferences profilePreferences;
    FavouriteRouteAdapter adapter;
    ArrayList<Routes> list = new ArrayList<Routes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_routes);
        img_add_new = findViewById(R.id.img_add_new);

        //getUserName();

        //SetRecycler();






        img_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount()==0){
                    id = "1";
                }else {
                    int currentID = adapter.getMax();
                    id = (currentID+1) + "";

                }

                Intent star1 = new Intent(getApplicationContext(), AddFavoritesActivity.class);
                star1.putExtra("id",id);
                startActivity(star1);
            }
        });

    }

    public void getUserName() {
        profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }
    public void SetRecycler(){
        list.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("favorites").child(username);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Routes value = snapshot.getValue(Routes.class);

                    list.add(value);
                    adapter.notifyDataSetChanged();
                } else {


                }


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_favorite_route);
        adapter = new FavouriteRouteAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserName();
        SetRecycler();
    }
}