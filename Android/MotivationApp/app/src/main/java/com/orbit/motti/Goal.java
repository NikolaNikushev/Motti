package com.orbit.motti;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.orbit.motti.Records.ExtendedCursor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class Goal extends Record implements Parcelable {

    private String goalTitle;
    private List<SubGoal> subGoals;
    private String goalDescription;
    private int reminderDaysSpan;
    private int goalProgress;
    private int goalPeriod;

    private String addiction;
    public String getAddiction(){return  this.addiction;}

    private int ID;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


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

    @Override
    public String getTableName() {
        return "Goal";
    }

    @Override
    public void loadFromCursor(ExtendedCursor data) {
        reminderDaysSpan = data.getInt("reminder_days");
        goalDescription = data.getString("description");
        this.addiction = data.getString("addiction");
        this.goalTitle = data.getString("title");
        //todo frequency
        /*
        Title VARCHAR(256),
	Description TEXT(2000000000),
	date_from DATE(2000000000),
	Date_to DATE(2000000000),
	reminder_frequency INTEGER,
	parent_goal INTEGER,
	ID INTEGER,
	date_finished DATE(2000000000),
	Addiction_type VARCHAR(256),
	profile VARCHAR(256),
         */

        //todo read subgoals
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "id") {
            @Override
            public Object getValue() {
                return ((Goal)this.getRecord()).getID();
            }

            @Override
            public void setValue(Object value) {
                ((Goal)this.getRecord()).setID((int)value);
            }
        };
    }
}
