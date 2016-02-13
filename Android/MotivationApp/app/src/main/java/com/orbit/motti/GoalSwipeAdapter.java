package com.orbit.motti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class GoalSwipeAdapter extends SwipeAdapter {


    private final List<Goal> goalList;
    private final Context context;

    public GoalSwipeAdapter(List<Goal> goals,Context context) {
        this.goalList = goals;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goal_content_holder, viewGroup, true);
        return new GoalViewHolder(v);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GoalViewHolder goalViewHolder = (GoalViewHolder) viewHolder;
        goalViewHolder.bindGoal(goalList.get(position));
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int i) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(android.R.color.background_dark)
                .setLeftUndoable(true)
                .setLeftUndoDescription("Goal archived")
                .setDescriptionTextColorResource(android.R.color.white)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.RESTRICTED_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(int position, int direction) {
        Toast.makeText(context, "HIIDADS", Toast.LENGTH_SHORT).show();

        if (direction == SWIPE_RIGHT) {
            goalList.remove(position);
            notifyItemRemoved(position);
        }
    }


    public class GoalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView goalTitleTextView;
        private Goal goal;
        private LinearLayout contentWrapper;
        private TextView goalDescriptionTextView;
        private TextView goalPeriodLeftTextView;

        public GoalViewHolder(View itemView) {
            super(itemView);
            contentWrapper=(LinearLayout)itemView.findViewById(R.id.content_wrapper);
            contentWrapper.setOnClickListener(this);
            goalTitleTextView = (TextView) itemView.findViewById(R.id.goal_title_text_view);
            goalDescriptionTextView = (TextView) itemView.findViewById(R.id.goal_description_text_view);
            goalPeriodLeftTextView = (TextView) itemView.findViewById(R.id.goal_period_text_view);
        }

        public void bindGoal(Goal pGoal) {
            this.goal = pGoal;
            goalTitleTextView.setText(goal.getGoalTitle());
            goalDescriptionTextView.setText(goal.getGoalDescription());
            goalPeriodLeftTextView.setText("Days left: " + goal.getGoalPeriod());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Toast.makeText(context, "HIIDADS"+ pos, Toast.LENGTH_SHORT).show();
        }
    }
}
