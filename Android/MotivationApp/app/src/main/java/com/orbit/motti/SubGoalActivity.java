package com.orbit.motti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class SubGoalActivity extends AppCompatActivity {

    private static final String GOAL_TAG = "GOAL_TAG";
    Goal goal;
    private RecyclerView mRecyclerView;
    private TextView goalTitleTextView;
    private TextView goalPeriodTextView;

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
        mRecyclerView.setAdapter(new SubGoalAdapter(goal.getSubGoals()));
    }

    public static Intent getIntent(Context context, Goal goal) {
        Intent i = new Intent(context, SubGoalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(GOAL_TAG, goal);
        i.putExtras(bundle);
        return i;
    }
}
