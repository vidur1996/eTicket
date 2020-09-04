package com.example.eticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ticket_gen extends AppCompatActivity {
    Spinner from_spin,to_spin;
    TextView user_txt;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_gen);

        user_txt  =findViewById(R.id.user_txt);
        from_spin=findViewById(R.id.from_spin);
        to_spin = findViewById(R.id.to_spin);


        ArrayAdapter<String> myadapter  = new ArrayAdapter<String>(ticket_gen.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tr_batti));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from_spin.setAdapter(myadapter);





        ArrayAdapter<String> myadapter1  = new ArrayAdapter<String>(ticket_gen.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tr_batti));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        to_spin.setAdapter(myadapter1);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname = extras.getString("uname");
        }

        user_txt.setText(uname);



    }
}
