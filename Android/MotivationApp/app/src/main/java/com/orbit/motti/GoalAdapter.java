package com.orbit.motti;

import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {


    private final List<Goal> goalList;
    private final Context context;

    public GoalAdapter(List<Goal> goals, Context context) {
        this.goalList = goals;
        this.context = context;
    }

    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_content_holder, parent, false);
        return new GoalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {
        holder.bindGoal(goalList.get(position));
    }

    public void removeItem(int position) {
        goalList.remove(position);
    }

    public Goal getGoal(int position) {
        return goalList.get(position);
    }

    public void addItem(Goal g, int position) {
        goalList.add(position, g);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public class GoalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView goalTitleTextView;
        private Goal goal;
        private CardView contentWrapper;
        private ProgressBar goalProgressBar;
        private TextView goalDescriptionTextView;
        private TextView goalPeriodLeftTextView;

        public GoalViewHolder(View itemView) {
            super(itemView);
            contentWrapper = (CardView) itemView.findViewById(R.id.content_wrapper);
            contentWrapper.setOnClickListener(this);
            contentWrapper.setBackgroundColor(Color.rgb(249,249,249));
            goalProgressBar=(ProgressBar)itemView.findViewById(R.id.goal_progress_bar);
            goalTitleTextView = (TextView) itemView.findViewById(R.id.goal_title_text_view);
            goalDescriptionTextView = (TextView) itemView.findViewById(R.id.goal_description_text_view);
            goalPeriodLeftTextView = (TextView) itemView.findViewById(R.id.goal_period_text_view);
        }

        public void bindGoal(Goal pGoal) {
            this.goal = pGoal;
            goalTitleTextView.setText(goal.getGoalTitle());
            goalDescriptionTextView.setText(goal.getGoalDescription());
            goalPeriodLeftTextView.setText("Days left: " + goal.getGoalPeriod());

            if (goal.getGoalProgress() == 100) {
                PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                goalProgressBar.getIndeterminateDrawable().setColorFilter(colorFilter);
            } else {
                PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY   );
                goalProgressBar.getIndeterminateDrawable().setColorFilter(colorFilter);
            }
            goalProgressBar.setProgress(goal.getGoalProgress());
        }

        @Override
        public void onClick(View v) {
            Goal g = getGoal(getAdapterPosition());
            Intent intent = GoalActivity.getIntent(context, g);
            context.startActivity(intent);
        }
    }


}
