package com.example.eticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eticket.data_model.member;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.makeText;

public class signup extends AppCompatActivity {
    Button submit_btn , clear_btn , back_btn;
    TextView name_txt, user_txt, pass1_txt,pass2_txt, mail_txt ,phone_txt  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        submit_btn = (Button)   findViewById(R.id.submit_btn);
        clear_btn  = (Button)   findViewById(R.id.clear_btn );
        back_btn   = (Button)   findViewById(R.id.back__btn );
        name_txt   = (TextView) findViewById(R.id.name_txt  );
        user_txt   = (TextView) findViewById(R.id.user_txt  );
        pass1_txt  = (TextView) findViewById(R.id.pass1_txt );
        pass2_txt  = (TextView) findViewById(R.id.pass2_txt );
        mail_txt   = (TextView) findViewById(R.id.mail_txt  );
        phone_txt  = (TextView) findViewById(R.id.phone_txt );

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear_data();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(it);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String pass1 = pass1_txt.getText().toString().trim();
                String pass2 = pass2_txt.getText().toString().trim();
                String name = name_txt.getText().toString().trim();
                String user = user_txt.getText().toString().trim();
                String email= mail_txt.getText().toString().trim();
                String phone = phone_txt.getText().toString().trim();
                member mem = new member();
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("temp_member");

                DatabaseReference.CompletionListener complete = new DatabaseReference.CompletionListener() {
                    @Override

                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError != null)
                            {
                                Toast.makeText(signup.this, "register failed", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                showAlert();
                                //Toast.makeText(signup.this, "registration succesful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                if(name.equals("") || email.equals("") ||
                        phone.equals("") || user.equals("")||
                        pass1.equals("") ||pass2.equals("") ) {
                    if (name.equals("")) {
                        showMsg( "enter the name");
                    } else if (email.equals("")) {
                        showMsg("enter the email address");
                    } else if (phone.equals("")) {
                        showMsg("enter the phone number");
                    } else if (user.equals("")) {
                        showMsg("enter username");
                    } else if (pass1.equals("") || pass2.equals("")) {
                        showMsg("enter passsword");
                    }
                }
                else  if( ! pass1.equals(pass2)) {
                    showMsg("both passsword does not match");
                }
                else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showMsg( "invalid email");
                }

                else
                {

                    mem.setName(name);
                    mem.setEmail(email);
                    mem.setNum(phone);
                    mem.setUsername(user);
                    mem.setPassword(pass1);
                    mem.setLock("0");
                    reff.child(user).setValue(mem ,complete);
                    clear_data();

                }
            }
        });




    }

    public void showAlert() {

        new MaterialAlertDialogBuilder(this)
                .setTitle("Registration successFul")
                .setMessage("You will receive a the status from an admin ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent it = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(it);
                        finish();

                    }
                }).show();

    }

    public void showMsg(String msg) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    void clear_data()
    {
        pass1_txt.setText("");
        pass2_txt.setText("");
        name_txt.setText("");
        user_txt.setText("");
        mail_txt.setText("");
        phone_txt.setText("");
    }
}
