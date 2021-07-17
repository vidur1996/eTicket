package com.example.eticket.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChangePasswordActivity extends AppCompatActivity {
    Button btn_updatePassword;
    EditText et_currentPassword, et_newPassword, et_confirmPassword;
    String username, currentPassword;
    DatabaseReference reff, password_reffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        btn_updatePassword = (Button) findViewById(R.id.btn_update_password);
        et_currentPassword = (EditText) findViewById(R.id.et_password_current);
        et_newPassword = (EditText) findViewById(R.id.et_password_new);
        et_confirmPassword = (EditText) findViewById(R.id.et_password_confirm);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }
        getCurrentPassword();
        btn_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });



    }

    public void getCurrentPassword() {
        reff = FirebaseDatabase.getInstance().getReference().child("member").child(username);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                currentPassword = snapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    public Boolean verifyPassword() {

        if (et_confirmPassword.getText().toString().equals(et_newPassword.getText().toString())) {
            return true;

        } else {
            Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Boolean checkEmptyPassword() {
        return (!et_currentPassword.getText().toString().equals("") ||
                !et_confirmPassword.getText().toString().equals("") ||
                !et_newPassword.getText().toString().equals(""));
    }

    public void updatePassword() {
        if (checkEmptyPassword()) {
            if (currentPassword.equals(et_currentPassword.getText().toString())) {
                if (verifyPassword()) {
                    savePassword(et_newPassword.getText().toString());
                }

            } else {
                Toast.makeText(this, "current password is wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Password missing", Toast.LENGTH_SHORT).show();
        }
    }

    public void savePassword(String newPassword) {
        password_reffer = FirebaseDatabase.getInstance().getReference().child("member").child(username);
        password_reffer.child("password").setValue(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    showAlert();
                }
            }
        });
    }

    public void moveToProfile() {
        finish();
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("Password changed Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveToProfile();

                    }
                }).show();

    }
}