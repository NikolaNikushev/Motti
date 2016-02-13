package com.orbit.motti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orbit.motti.Records.Profile;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Goal> goals;
    private TextView firstTimeTextView;
    private GoalSwipeAdapter goalSwipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        firstTimeTextView = (TextView) findViewById(R.id.first_time_text_view);
        goals = new ArrayList<>();
        fillGoals();
        updateUI();
        mRecyclerView = (RecyclerView) findViewById(R.id.goals_recycler_view);
        goalSwipeAdapter = new GoalSwipeAdapter(goals, this);
        mRecyclerView.setAdapter(goalSwipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));


        FloatingActionButton addGoalFAB = (FloatingActionButton) findViewById(R.id.fab);
        addGoalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Database database = new Database();
        database.connectToDatabase(this);
        //Initialize the database, setting Database.Instance to the first created;
        Profile p = new Profile("test");

        try {
            p.loadFromDatabase();
        }catch (InvalidClassException ex){
            //unable to load data from the database ( 0 records )
        }
        catch (Exception ex){
            //invalid columns
        }
    }

    private void updateUI() {

        if (goals.size() == 0) {
            firstTimeTextView.setVisibility(View.VISIBLE);
        } else {
            firstTimeTextView.setVisibility(View.GONE);
        }
    }

    private void showDialog() {
        createDialog().show();
    }

    private AlertDialog createDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.goal_dialog, null);
        final EditText goalTitleText = (EditText) dialogView.findViewById(R.id.goal_title_edit_text);
        final EditText goalDescriptionText = (EditText) dialogView.findViewById(R.id.goal_description_edit_text);
        final EditText goalRemindText = (EditText) dialogView.findViewById(R.id.goal_remind_edit_text);
        final EditText goalPeriodText = (EditText) dialogView.findViewById(R.id.goal_period_edit_text);

        final AlertDialog goalDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Enter goal title")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        goalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = goalDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = goalDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goalDialog.dismiss();
                        updateUI();
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (goalTitleText.getText().length() == 0) {
                            Toast.makeText(
                                    dialogView.getContext(),
                                    "Title can't be empty!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Goal goal = new Goal(
                                    goalTitleText.getText().toString(),
                                    goalDescriptionText.getText().toString(),
                                    Integer.valueOf(goalRemindText.getText().toString()),
                                    Integer.valueOf(goalPeriodText.getText().toString()));
                            goals.add(goal);
                            goalDialog.dismiss();
                            updateUI();
                            goalSwipeAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        return goalDialog;
    }

    private void fillGoals() {
    }
}
