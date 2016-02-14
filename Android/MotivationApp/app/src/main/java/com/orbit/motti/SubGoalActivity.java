package com.orbit.motti;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orbit.motti.Records.Goal;
import com.orbit.motti.Records.SubGoal;


/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class SubGoalActivity extends AppCompatActivity {

    private static final String GOAL_TAG = "GOAL_TAG";
    Goal goal;
    private RecyclerView mRecyclerView;
    private TextView goalTitleTextView;
    private TextView goalPeriodTextView;
    private FloatingActionButton subGoalFab;
    private SubGoalAdapter subGoalAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgoal);

        goal = getIntent().getExtras().getParcelable(GOAL_TAG);
        goalTitleTextView = (TextView) findViewById(R.id.sub_goal_main_title_text_view);
        goalPeriodTextView = (TextView) findViewById(R.id.sub_goal_period_text_view);
        goalTitleTextView.setText(goal.getGoalTitle());
        goalPeriodTextView.setText("Days left: " + goal.getGoalPeriod());

        mRecyclerView = (RecyclerView) findViewById(R.id.sub_goals_recycler_view);
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this, 1, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        subGoalAdapter = new SubGoalAdapter(goal.getSubGoals());
        mRecyclerView.setAdapter(subGoalAdapter);

        subGoalFab = (FloatingActionButton) findViewById(R.id.sub_goal_fab);
        subGoalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    createDialog().show();
            }
        });

    }

    public static Intent getIntent(Context context, Goal goal) {
        Intent i = new Intent(context, SubGoalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(GOAL_TAG, goal);
        i.putExtras(bundle);
        return i;
    }


    private AlertDialog createDialog() {
        final View dialogView = getLayoutInflater().inflate(R.layout.add_sub_goal_dialog, null);
        final EditText subGoalTitleText = (EditText) dialogView.findViewById(R.id.sub_goal_title_edit_text);
        final EditText subGoalPeriodText = (EditText) dialogView.findViewById(R.id.sub_goal_period_edit_text);
        final AlertDialog subGoalDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Add new sub goal")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        subGoalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = subGoalDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = subGoalDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subGoalDialog.dismiss();

                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (subGoalTitleText.getText().length() == 0) {
                            Toast.makeText(
                                    dialogView.getContext(),
                                    "Title can't be empty!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            SubGoal sg = new SubGoal(subGoalTitleText.getText().toString(),
                                    Integer.valueOf(subGoalPeriodText.getText().toString()));
                            goal.addSubGoal(sg);
                            subGoalDialog.dismiss();
                            subGoalAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        return subGoalDialog;
    }
}
