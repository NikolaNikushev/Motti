package com.orbit.motti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orbit.motti.Records.Profile;

import java.io.InvalidClassException;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Database database = new Database();
        database.connectToDatabase(this);

        //// TODO: 13/02/2016  temporary fix
        try {
            database.executeSQL("Insert into profile (username) values ('nikola')");

        } catch (Exception ex) {
            Log.i("probably have", "temporary fix");
        }

        try {
            database.executeSQL("Insert into profile (username) values ('ruud')");

        } catch (Exception ex) {
            Log.i("probably have", "temporary fix");
        }

        try {
            database.executeSQL("Insert into profile (username) values ('preslav')");

        } catch (Exception ex) {
            Log.i("probably have", "temporary fix");
        }

        try {
            database.executeSQL("Insert into profile (username) values ('ilia')");

        } catch (Exception ex) {
            Log.i("probably have", "temporary fix");
        }

        try {
            database.executeSQL("Insert into profile (username) values ('francisco')");

        } catch (Exception ex) {
            Log.i("probably have", "temporary fix");
        }

        Button next = (Button) findViewById(R.id.but_login);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile p = new Profile(((EditText) findViewById(R.id.login_edit_text)).getText().toString());
                Home.p = p;
                try {
                    p.loadFromDatabase();
                    Intent i = new Intent(getApplicationContext(), Spinner.class);


                    startActivity(i);
                } catch (InvalidClassException ex) {
                    //unable to load data from the database ( 0 records )
                    Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

                } catch (Exception ex) {
                    //invalid columns
                    Log.i("ex", ex.toString());
                }

            }
        });

    }
}
