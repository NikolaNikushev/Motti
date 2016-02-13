package com.orbit.motti;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.orbit.motti.Records.Profile;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity {
    private int lastDeletedPosition = -1;
    private Goal lastDeletedGoal;
    private RecyclerView mRecyclerView;
    private List<Goal> goals;
    private TextView firstTimeTextView;
    private GoalAdapter goalSwipeAdapter;
    public static Profile p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        firstTimeTextView = (TextView) findViewById(R.id.first_time_text_view);
        goals = new ArrayList<>();
        fillGoals();
        updateUI();
        mRecyclerView = (RecyclerView) findViewById(R.id.goals_recycler_view);
        goalSwipeAdapter = new GoalAdapter(goals, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(goalSwipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    lastDeletedPosition = position;
                                    lastDeletedGoal = goalSwipeAdapter.getGoal(position);
                                    goalSwipeAdapter.removeItem(position);
                                    goalSwipeAdapter.notifyItemRemoved(position);
                                }
                                goalSwipeAdapter.notifyDataSetChanged();
                                Snackbar.make(recyclerView, "Goal deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (lastDeletedGoal != null && lastDeletedPosition != -1) {
                                                    goalSwipeAdapter.addItem(lastDeletedGoal, lastDeletedPosition);
                                                    lastDeletedGoal = null;
                                                    lastDeletedPosition = -1;
                                                }
                                            }
                                        }).show();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    lastDeletedPosition = position;
                                    lastDeletedGoal = goalSwipeAdapter.getGoal(position);
                                    goalSwipeAdapter.removeItem(position);
                                    goalSwipeAdapter.notifyItemRemoved(position);
                                }
                                goalSwipeAdapter.notifyDataSetChanged();
                                Snackbar.make(recyclerView, "Goal deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (lastDeletedGoal != null && lastDeletedPosition != -1) {
                                                    goalSwipeAdapter.addItem(lastDeletedGoal, lastDeletedPosition);
                                                    lastDeletedGoal = null;
                                                    lastDeletedPosition = -1;
                                                }
                                            }
                                        }).show();
                            }
                        });

        mRecyclerView.addOnItemTouchListener(swipeTouchListener);

        FloatingActionButton addGoalFAB = (FloatingActionButton) findViewById(R.id.fab);
        addGoalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        //  Database database = new Database();
        //  database.connectToDatabase(this);
        //Initialize the database, setting Database.Instance to the first created;
        Profile p = new Profile("test");

        try {
            p.loadFromDatabase();
        } catch (InvalidClassException ex) {
            //unable to load data from the database ( 0 records )
        } catch (Exception ex) {
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
                                    getFillerText(),
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

    private String getFillerText() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae " +
                "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
                "lectus, feugiat eget ullamcorper vitae, ornare et sem. Fusce dapibus ipsum" +
                " sed laoreet suscipit.";
    }

    private void fillGoals() {
        for (int i = 0; i < 15; i++) {
            Goal g = new Goal("I want to quit smoking " + i, getFillerText(), 4, 5);
            for (int y = 0; y < 15; y++) {
                SubGoal sg = new SubGoal("I want to smoke only 1 cigarrete today", 4);
                sg.setIsFinished(y % 2 == 0);
                g.addSubGoal(sg);
            }
            goals.add(g);
        }
    }
}
