package com.example.eticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button signup_btn,login_btn,rem_btn;
    TextView user_txt,pass_txt;
    String username;
    String pass;
    String lock;
    SharedPreferences sharedpreferences;

    String pass_db;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signup_btn = findViewById(R.id.sign_btn);
        login_btn  =findViewById(R.id.login_btn);
        user_txt = findViewById(R.id.username_txt);
        pass_txt = findViewById(R.id.password_txt);
        rem_btn  =findViewById(R.id.rem_btn);
        SharedPreferences sh2 = getApplicationContext().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String uname = sh2.getString("user","");
        String p2 = sh2.getString("pass","");
        final String del = sh2.getString("del","");

        if(uname!="" )
        {
            user_txt.setText(uname);
            pass_txt.setText(p2);
        }


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),signup.class);
                startActivity(it);
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 username = user_txt.getText().toString().trim();
                 pass = pass_txt.getText().toString().trim();

                if(username=="" || username.equals(""))
                {
                    Toast.makeText(MainActivity.this,"enter username",Toast.LENGTH_SHORT).show();
                }
                else if(pass=="" ||pass.equals(""))
                {
                    Toast.makeText(MainActivity.this,"enter password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    reff = FirebaseDatabase.getInstance().getReference().child("member").child(username);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pass_db = dataSnapshot.child("password").getValue().toString();
                            lock =dataSnapshot.child("lock").getValue().toString();
                            if(pass.equals(pass_db) || pass==pass_db)
                            {



                                pass_txt.setText("");
                                user_txt.setText("");
                               if(del.equals("true"))
                                {
                                    Intent star = new Intent(getApplicationContext(),saveuser.class);
                                    star.putExtra("uname",username);
                                    star.putExtra("password",pass);
                                    startActivity(star);
                                }
                                else
                                {

                                        reff.child("lock").setValue("1");
                                        saveProfile(username);
                                        Intent star2 = new Intent(getApplicationContext(), MainMenuActivity.class);
                                        Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                                        star2.putExtra("uname",username);
                                        startActivity(star2);
                                        username = "";
                                        pass="";
                                        MainActivity.this.finish();



                               }


                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                                pass_txt.setText("");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this,"wrong username",Toast.LENGTH_SHORT).show();
                            pass_txt.setText("");
                            user_txt.setText("");
                        }
                    });



                }



            }
        });




    }

    public void saveProfile(String username) {
        final String MyPREFERENCES = "PROFILE";
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("USERNAME", username);
        editor.commit();
    }

}
