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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Goal> goals;
    private GoalSwipeAdapter goalSwipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goals = new ArrayList<>();
        fillGoals();

        mRecyclerView = (RecyclerView) findViewById(R.id.goals_recycler_view);
        goalSwipeAdapter = new GoalSwipeAdapter(goals);
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
    }

    private void showDialog() {
        createDialog().show();
    }

    private AlertDialog createDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.goal_dialog, null);
        final EditText goalText = (EditText) dialogView.findViewById(R.id.goal_title_edit_text);

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
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (goalText.getText().length() == 0) {
                            Toast.makeText(
                                    dialogView.getContext(),
                                    "Title can't be empty!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            goals.add(new Goal(goalText.getText().toString(),"",2));
                            goalDialog.dismiss();
                            goalSwipeAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        return goalDialog;
    }

    private void fillGoals() {
        for (int i = 0; i < 15; i++) {
            Goal g = new Goal("you can do it!","",2);
            goals.add(g);
        }
    }
}
