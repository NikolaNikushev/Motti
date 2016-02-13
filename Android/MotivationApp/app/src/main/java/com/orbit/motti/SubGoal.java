package com.orbit.motti;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class SubGoal {

    private final String subGoalTitle;
    private final String subGoalDescription;
    private boolean isFinished;
    private final int subGoalTimePeriod;

    public SubGoal(String goalTitle, String goalDescription, int subGoalTimePeriod) {
        this.subGoalTitle = goalTitle;
        this.subGoalDescription = goalDescription;
        this.isFinished = false;
        this.subGoalTimePeriod = subGoalTimePeriod;
    }


    public String getSubGoalTitle() {
        return subGoalTitle;
    }

    public String getSubGoalDescription() {
        return subGoalDescription;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public int getSubGoalTimePeriod() {
        return subGoalTimePeriod;
    }
}
