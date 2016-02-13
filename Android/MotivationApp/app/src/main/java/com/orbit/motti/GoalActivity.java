package com.orbit.motti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;


/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class GoalActivity extends AppCompatActivity {

    private static final String GOAL_TAG = "GOAL_TAG";
    Goal goal;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgoal);
        goal = getIntent().getExtras().getParcelable(GOAL_TAG);
        mRecyclerView = (RecyclerView) findViewById(R.id.sub_goals_recycler_view);
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this, 1, false));
    }

    public static Intent getIntent(Context context, Goal goal) {
        Intent i = new Intent(context, GoalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(GOAL_TAG, goal);
        i.putExtras(bundle);
        return i;
    }
}
