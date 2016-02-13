package com.orbit.motti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class Goal {

    private final String goalTitle;
    private final List<SubGoal> subGoals;
    private String goalDescription;
    private int reminderDaysSpan;
    private int goalProgress;
    private int goalPeriod;

    public Goal(String goalTitle, String goalDescription, int reminderDaysSpan,int goalPeriod) {
        this.goalTitle = goalTitle;
        this.goalDescription = goalDescription;
        this.subGoals = new ArrayList<>();
        this.reminderDaysSpan = reminderDaysSpan;
        this.goalPeriod = goalPeriod;
        goalProgress = 0;
    }


    public List<SubGoal> getSubGoals() {
        return subGoals;
    }

    public String getGoalTitle() {
        return goalTitle;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public int getReminderDaysSpan() {
        return reminderDaysSpan;
    }

    public int getGoalPeriod() {
        return goalPeriod;
    }
}
