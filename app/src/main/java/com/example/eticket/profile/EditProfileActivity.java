package com.example.eticket.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eticket.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {
    String username;
    String name, email, phone;
    Button btn_saveProfile;
    TextView mail_error;
    EditText et_name, et_email, et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        btn_saveProfile = (Button) findViewById(R.id.btn_edit_save_profile);
        et_name = (EditText) findViewById(R.id.et_edit_name);
        et_email = (EditText) findViewById(R.id.et_edit_email);
        et_phone = (EditText) findViewById(R.id.et_edit_phone);
        mail_error = (TextView) findViewById(R.id.tv_edit_email_error);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            name = extras.getString("name");
            email = extras.getString("email");
            phone = extras.getString("phone");
            setdata();
        }
        emailVaildator();
        btn_saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validdata()) {
                    if (savedata()) {
                        showAlert();
                    }
                }
            }
        });


    }

    public void setdata() {
        et_name.setText(name);
        et_email.setText(email);
        et_phone.setText(phone);
    }

    public Boolean validdata() {
        if (et_name.getText().toString().equals("")) {
            Toast.makeText(this, "name required", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_phone.getText().toString().equals("")) {
            Toast.makeText(this, "phone number required", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_email.getText().toString().equals("")) {
            Toast.makeText(this, "email required", Toast.LENGTH_LONG).show();
            return false;
        } else  if(!et_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();
            return false;
        }

        else {
            return true;
        }

    }

    public Boolean savedata() {
        String new_email = et_email.getText().toString().trim();
        String new_name = et_name.getText().toString().trim();
        String new_phone = et_phone.getText().toString().trim();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("member").child(username);
        final boolean[] check = {true};
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                check[0] = error == null;
            }
        };

        reff.child("email").setValue(new_email, completionListener);
        reff.child("name").setValue(new_name, completionListener);
        reff.child("num").setValue(new_phone, completionListener);

        return check[0];
    }


    public void emailVaildator() {
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    mail_error.setText("");
                } else {

                    mail_error.setText("invalid email");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("Edit successFul")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                }).show();

    }


}