package com.orbit.motti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class GoalSwipeAdapter extends SwipeAdapter {


    private final List<Goal> goalList;

    public GoalSwipeAdapter(List<Goal> goals) {
        this.goalList = goals;
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goal_holder, viewGroup, true);
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
        if (direction == SWIPE_RIGHT) {
            goalList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class GoalViewHolder extends RecyclerView.ViewHolder {

        private TextView goalTitleTextView;
        private ListView goalSubListView;
        private Goal goal;

        public GoalViewHolder(View itemView) {
            super(itemView);
            goalTitleTextView = (TextView) itemView.findViewById(R.id.goal_title_text_view);
            goalSubListView = (ListView) itemView.findViewById(R.id.goal_sub_list_view);
        }

        public void bindGoal(Goal pGoal) {
            this.goal = pGoal;
            goalTitleTextView.setText(goal.getGoalTitle());
        }
    }
}
