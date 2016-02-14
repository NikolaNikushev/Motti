package com.orbit.motti;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.orbit.motti.Records.SubGoal;

import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class SubGoalAdapter extends RecyclerView.Adapter<SubGoalAdapter.SubGoalViewHolder> {

    List<SubGoal> subGoals;

    public SubGoalAdapter(List<SubGoal> subGoals) {
        this.subGoals = subGoals;
    }

    @Override
    public SubGoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_goal_content_holder, parent, false);
        return new SubGoalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubGoalViewHolder holder, int position) {
        holder.bindSubGoal(subGoals.get(position));
    }

    @Override
    public int getItemCount() {
        return subGoals.size();
    }

    public class SubGoalViewHolder extends RecyclerView.ViewHolder {

        private TextView subGoalTitle;
        private CheckBox subGoalFinishedCheckBox;

        public SubGoalViewHolder(View itemView) {
            super(itemView);
            subGoalTitle = (TextView) itemView.findViewById(R.id.sub_goal_title_text_view);
            subGoalFinishedCheckBox = (CheckBox) itemView.findViewById(R.id.sub_goal_finished_cb);
        }

        public void bindSubGoal(SubGoal subGoal) {
            subGoalTitle.setText(subGoal.getSubGoalTitle());
            subGoalFinishedCheckBox.setChecked(subGoal.isFinished());
        }
    }
}
