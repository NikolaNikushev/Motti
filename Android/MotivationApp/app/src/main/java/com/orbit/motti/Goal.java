package com.orbit.motti;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class Goal implements Parcelable {

    private final String goalTitle;
    private final List<SubGoal> subGoals;
    private String goalDescription;
    private int reminderDaysSpan;
    private int goalProgress;
    private int goalPeriod;

    public Goal(String goalTitle, String goalDescription, int reminderDaysSpan, int goalPeriod) {
        this.goalTitle = goalTitle;
        this.goalDescription = goalDescription;
        this.subGoals = new ArrayList<>();
        this.reminderDaysSpan = reminderDaysSpan;
        this.goalPeriod = goalPeriod;
        goalProgress = 0;
    }

    protected Goal(Parcel in) {
        reminderDaysSpan = in.readInt();
        goalProgress = in.readInt();
        goalPeriod = in.readInt();
        goalTitle = in.readString();
        goalDescription = in.readString();
        subGoals = new ArrayList<>();
        in.readList(subGoals, SubGoal.class.getClassLoader());
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

    public String getGoalProgress() {
        int nrOfTaskFinished = 0;
        for (SubGoal sb : subGoals) {
            if (sb.isFinished()) {
                nrOfTaskFinished++;
            }
        }
        return String.format("%d/%d tasks finished", nrOfTaskFinished, subGoals.size());
    }

    public void addSubGoal(SubGoal subGoal) {

        if (this.subGoals.contains(subGoal)) {
            return;
        }
        subGoals.add(subGoal);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reminderDaysSpan);
        dest.writeInt(goalProgress);
        dest.writeInt(goalPeriod);
        dest.writeString(goalTitle);
        dest.writeString(goalDescription);
        dest.writeList(subGoals);
    }
}
