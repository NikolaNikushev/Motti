package com.orbit.motti;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
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

        InitDBData(true);


        Button next = (Button) findViewById(R.id.but_login);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Profile p = new Profile(((EditText) findViewById(R.id.login_edit_text)).getText().toString());

                try {
                    p.loadFromDatabase();
                    Intent in = new Intent(getApplicationContext(), GoalsActivity.class);
                    GoalsActivity.p = p;

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


            Database database = new Database(this);
            try {
                database.dropTables();
            }catch(SQLiteException ex){

            }
            database.connectToDatabase();

            //addiction types

            executeSQL("Insert into addiction_type (name)  values ('smoking')", database);
            executeSQL("Insert into addiction_type (name)  values ('drinking')", database);
            executeSQL("Insert into addiction_type (name)  values ('debt')", database);
            executeSQL("Insert into addiction_type (name)  values ('drugs')", database);

            executeSQL("Insert into motivation_type (name)  values ('none')", database);
            executeSQL("Insert into motivation_type (name)  values ('question')", database);
            executeSQL("Insert into motivation_type (name)  values ('good-to-know')", database);
            executeSQL("Insert into motivation_type (name)  values ('action')", database);

            //motivations

            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Im not telling you it is going to be easy but im telling you it is worth it.','none', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('It always seems impossible, until it is done!','none', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Do not give up, the beginning is always the hardest!','none', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('It’s easy to quit, it takes faith to go through!','none', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('The struggle you are in today is the strength you need tomorrow!','none', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Money does not have healing properties but it makes a nice salve!','none', 1, 'null', 'debt')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('If you owe some money get a plan and pay them!','none', 1, 'null', 'debt')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Make this the year to tackle your problem once and for all!','none', 1, 'null')", database);


            //did you know
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Alcohol consumption by college students is linked to at least 1,400 student deaths and 500,000 unintentional injuries each year.','good-to-know', 1, 'null', 'drinking')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Alcohol does not relieve depression - it makes it worse.','good-to-know', 1, 'null', 'drinking')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Excessive drinking can decrease the amount of testosterone in a man]s body and cause impotence.','good-to-know', 1, 'null', 'drinking')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('A daily glass of wine will add 10 pounds per year.','good-to-know', 1, 'null', 'drugs')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('0.6% of people in the world are kleptomaniacs.','good-to-know', 1, 'null', 'drinking')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Women are more likely to have kleptomania than men.','good-to-know', 1, 'null', 'debt')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Debts can destroy marriages and relationships.','good-to-know', 1, 'null', 'debt')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('For households that have credit card debt, the average amount of credit card debt is an astounding $15,799.','good-to-know', 1, 'null', 'debt')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Smoking depletes the body of vitamin C by 30%.','good-to-know', 1, 'null', 'smoking')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image, addiction) values ('Smoking damages the gut.','good-to-know', 1, 'null', 'smoking')", database);

            //personal questions
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Do you feel responsible for your own action?','question', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Do you think you can break your old patterns?','question', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Can you do without social media for 2 days?','question', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Do you need professional help to solve your problem?','question', 1, 'null')", database);
            //Plus personal for goal


            //personal action

            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Say 3 times facing the mirror \"I can do this\"!','action', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Today I’m not going to spent money!','action', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Today I will do good for someone else.','action', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Say 3 times facing a mirror \"I like myself\"!','action', 1, 'null')", database);
            executeSQL("Insert into motivation (description,motivation_type, is_positive, image) values ('Today I am going to solve a problem.','action', 1, 'null')", database);

            //users
            executeSQL("Insert into profile  values ('nikola', 1)",database);
            executeSQL("Insert into profile  values ('ruud', 1)", database);
            executeSQL("Insert into profile  values ('preslav', 3)", database);
            executeSQL("Insert into profile  values ('ilia', 1)", database);
            executeSQL("Insert into profile  values ('francisco', 5)", database);

            database.insert("goal",
                    "id, title, date_finished, description, date_from, date_to, reminder_frequency, Addiction_type, profile",
                    "10, 'Stop smoking 5 packs','2016-02-10', 'I would love to stop smoking so much', '2016-02-02', '2016-02-08', 2, 'smoking', 'ruud'");

            database.insert("goal",
                    "id, title, description, date_from, date_to, reminder_frequency, Addiction_type, profile",
                    "20, 'Spend less', 'My birthday is coming up', '2016-03-02', '2016-05-08', 2, 'debt', 'ruud'");

            database.insert("sub_goal",
                    "id, title, parent_goal, is_finished",
                    "100, 'Save on electronics', 20, 1"
                    );
            database.insert("sub_goal",
                    "id, title, parent_goal, is_finished",
                    "101, 'Cut on junk food', 20, 0"
            );

        } else {
            return;
        }
    }
}
