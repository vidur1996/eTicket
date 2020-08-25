package com.example.eticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                pass1_txt.setText("");
                pass2_txt.setText("");
                name_txt.setText("");
                user_txt.setText("");
                mail_txt.setText("");
                phone_txt.setText("");
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
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("member");

                DatabaseReference.CompletionListener complete = new DatabaseReference.CompletionListener() {
                    @Override

                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError != null)
                            {
                                Toast.makeText(signup.this, "register failed", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(signup.this, "registration succesful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                if(name.equals("") || email.equals("") || phone.equals("")) {
                    if (name.equals(""))
                    {
                        makeText(signup.this, "enter the name", Toast.LENGTH_SHORT).show();
                    }
                    else if (email.equals(""))
                    {
                        makeText(signup.this, "enter the email address", Toast.LENGTH_SHORT).show();
                    }
                    else if(phone.equals(""))
                    {
                        makeText(signup.this, "enter the phone number", Toast.LENGTH_SHORT).show();
                    }


                }
                else if( user.equals("")  )
                {
                    makeText(signup.this, "enter username", Toast.LENGTH_SHORT).show();
                }



                else if(pass1.equals("") ||pass2.equals("") )
                {
                    makeText(signup.this, "enter passsword", Toast.LENGTH_SHORT).show();
                }


                else  if( ! pass1.equals(pass2)) {
                    makeText(signup.this, "both passsword does not match", Toast.LENGTH_SHORT).show();
                }







                else
                {

                    mem.setName(name);
                    mem.setEmail(email);
                    mem.setNum(phone);
                    mem.setUsername(user);
                    mem.setPassword(pass1);
                    reff.child(user).setValue(mem ,complete);


                }
            }
        });



    }
}
