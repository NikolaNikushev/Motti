package com.orbit.motti;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orbit.motti.Records.Goal;
import com.orbit.motti.Records.Profile;
import com.orbit.motti.Records.SubGoal;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Goal> goals;
    private GoalAdapter goalSwipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Database database = new Database(this);
        database.connectToDatabase();
        try {
            Profile p = GoalsActivity.p;
            GoalsActivity.p.loadFromDatabase();
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }
        goals = new ArrayList<>();
        List<Goal> golz = GoalsActivity.p.goals;
        for (int i = 0; i < golz.size();i++){

            Calendar calendar = Calendar.getInstance();
            calendar.setTime( golz.get(i).getDateFinished());
            int a = calendar.get(Calendar.YEAR);
            if (a > 2015)
            goals.add(golz.get(i));
        }
       // goals = golz;

        mRecyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        goalSwipeAdapter = new GoalAdapter(goals, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(goalSwipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                Intent i = new Intent(this, Spinner.class);
                startActivity(i);
                return true;
            case R.id.goals:
                i = new Intent(this, GoalsActivity.class);
                startActivity(i);

                return true;
            case R.id.help:
                final Context context = this;
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Emergency help!")
                        .setMessage("With this action you are confirming that you wish to receive assistance from a professional. You need to specify a way of contact and a trained expert will contact you as soon as possiblle.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogs, int which) {
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.custom_popup);
                                dialog.setTitle("Contact form");

                                // set the custom dialog components - text, image and button
                                TextView text = (TextView) dialog.findViewById(R.id.text);
                                text.setText("Please specify by what method you would like to be contacted. \n(Optional)Specify details for our expert.");
                                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                                image.setImageResource(R.mipmap.fruit_power);

                                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                                // if button is clicked, close the custom dialog
                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(context, "Form submitted succsfully. Our expert will contact you as soon as possible.", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });
                                Button dialogClose = (Button) dialog.findViewById(R.id.dialogButtonDismiss);
                                // if button is clicked, close the custom dialog
                                dialogClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();

                                dialogs.dismiss();


                            }
                        })

                        .setIcon(R.drawable.ic_priority_high_black_24dp)
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_history, menu);
        return true;
    }
}
