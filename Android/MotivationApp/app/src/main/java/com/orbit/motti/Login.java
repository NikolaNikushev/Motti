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


        //// TODO: 13/02/2016  temporary fix

        InitDBData(true);


        Button next = (Button) findViewById(R.id.but_login);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), GoalsActivity.class);
                startActivity(i);

                Profile p = new Profile(((EditText) findViewById(R.id.login_edit_text)).getText().toString());
                GoalsActivity.p = p;
                try {
                    p.loadFromDatabase();
                    Intent in = new Intent(getApplicationContext(), Spinner.class);


                    startActivity(in);
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

    private void executeSQL(String sql, Database database) {

        try {
            database.executeSQL(sql);

        } catch (Exception ex) {
            Log.i("probably have", "temporary fix");
        }
    }

    private void InitDBData(Boolean clear) {
        if (clear) {
            // make drop of tables and create again.
            // TODO: 13/02/2016


            Database database = new Database();
            database.connectToDatabase(this);

            //addiction types
            executeSQL("Insert into addiction_type  values ('smoking')", database);
            executeSQL("Insert into addiction_type  values ('drinking')", database);
            executeSQL("Insert into addiction_type  values ('debt')", database);
            executeSQL("Insert into addiction_type  values ('drugs')", database);


            //motivations
            executeSQL("Insert into motivation  values ('Im not telling you it is going to be easy but im telling you it is worth it.'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('It always seems impossible, until it is done!'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Do not give up, the beginning is always the hardest!'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('It’s easy to quit, it takes faith to go through!'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('The struggle you are in today is the strength you need tomorrow!'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Money does not have healing properties but it makes a nice salve!'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('If you owe some money get a plan and pay them!'),'none', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Make this the year to tackle your problem once and for all!'),'none', true, 'null'", database);


            //did you know
            executeSQL("Insert into motivation  values ('Alcohol consumption by college students is linked to at least 1,400 student deaths and 500,000 unintentional injuries each year.'),'drinking-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Alcohol does not relieve depression - it makes it worse.'),'drinking-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Excessive drinking can decrease the amount of testosterone in a man's body and cause impotence.'),'drinking-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('A daily glass of wine will add 10 pounds per year.'),'drinking-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('0.6% of people in the world are kleptomaniacs.'),'thief-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Women are more likely to have kleptomania than men.'),'thief-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Debts can destroy marriages and relationships.'),'debt-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('For households that have credit card debt, the average amount of credit card debt is an astounding $15,799.'),'debt-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Smoking depletes the body of vitamin C by 30%.'),'smoking-know', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Smoking damages the gut.'),'smoking-know', true, 'null'", database);

            //personal questions
            executeSQL("Insert into motivation  values ('Do you feel responsible for your own action?'),'question', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Do you think you can break your old patterns?'),'question', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Can you do without social media for 2 days?'),'question', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Do you need professional help to solve your problem?'),'question', true, 'null'", database);
            //Plus personal for goal


            //personal action

            executeSQL("Insert into motivation  values ('Say 3 times facing the mirror \"I can do this\"!'),'action', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Today I’m not going to spent money!'),'action', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Today I will do good for someone else.'),'action', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Say 3 times facing a mirror \"I like myself\"!'),'action', true, 'null'", database);
            executeSQL("Insert into motivation  values ('Today I am going to solve a problem.'),'action', true, 'null'", database);

            //users
            executeSQL("Insert into profile  values ('nikola', 3, 1)",database);
            executeSQL("Insert into profile  values ('ruud', 3, 1)", database);
            executeSQL("Insert into profile  values ('preslav', 3, 3)", database);
            executeSQL("Insert into profile  values ('ilia', 3, 1)", database);
            executeSQL("Insert into profile  values ('francisco',  3, 5)", database);


        } else {
            return;
        }
    }
}
