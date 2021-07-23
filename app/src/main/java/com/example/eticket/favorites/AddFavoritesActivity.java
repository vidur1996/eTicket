package com.example.eticket.favorites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eticket.R;
import com.example.eticket.history.adapter.RecentTicketAdapter;
import com.example.eticket.ticket.ticket_gen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class AddFavoritesActivity extends AppCompatActivity {
    Spinner favo_from,favo_to;
    Button add_favo;
    String to,from,id;

    DatabaseReference databaseReference;
    SharedPreferences profilePreferences;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);
        favo_from = findViewById(R.id.spin_add_favo_from);
        favo_to = findViewById(R.id.spin_add_favo_to);
        add_favo = findViewById(R.id.btn_add_favo);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            id = extras.getString("id");
        }
        setSpinner();

        favo_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            { from = favo_from.getSelectedItem().toString().trim(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        favo_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            { to = favo_to.getSelectedItem().toString().trim(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        add_favo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validSelected()){
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("favorites");
                }

            }
        });
    }

    public void setSpinner(){
        final ArrayAdapter<String> myadapter  = new ArrayAdapter<String>(AddFavoritesActivity.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tr_batti));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favo_from.setAdapter(myadapter);

        final ArrayAdapter<String> myadapter1  = new ArrayAdapter<String>(AddFavoritesActivity.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tr_batti));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favo_to.setAdapter(myadapter1);
    }

    public Boolean validSelected(){
        if(to==null || from==null){
            Toast.makeText(this,"please select start and end destination",Toast.LENGTH_SHORT).show();
            return false;
        }else if(to==from){
            Toast.makeText(this,"start and end destination",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public void getUserName(){
        profilePreferences = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);
        username = profilePreferences.getString("USERNAME", "");
    }

    public void saveData(final String id) {
        databaseReference.child(id).child("id").setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    databaseReference.child(id).child("to").setValue(to);
                    databaseReference.child(id).child("from").setValue(from).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                showAlert();
                            }
                        }
                    });
                }
            }
        });
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("New Route Created Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddFavoritesActivity.this.finish();

                    }
                }).show();

}