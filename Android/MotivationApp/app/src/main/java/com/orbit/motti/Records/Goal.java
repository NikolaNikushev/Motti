package com.orbit.motti.Records;

import android.os.Parcel;
import android.os.Parcelable;

import com.orbit.motti.Database;
import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;
import com.orbit.motti.SubGoal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class Goal extends Record implements Parcelable {
    private String goalTitle;
    private List<SubGoal> subGoals;
    private String goalDescription;

    private Date dateFrom;
    private Date dateTo;
    private Date dateFinished;

    private int reminderDaysSpan;
    private int goalProgress;
    private int goalPeriod;

    private String addiction;
    public String getAddiction(){return this.addiction;}

    private Profile profile;
    public Profile getProfile(){return profile;}
    public Profile getOwner(){return  getProfile();}

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
        this.profile = Profile.FindOrCreate(data.getString("profile"));

        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        try {
            this.dateFrom = format.parse(data.getString("date_from"));
        }catch (Exception ex){}
        try {
            this.dateTo = format.parse(data.getString("date_to"));
        }catch (Exception ex){}
        try {
            this.dateFinished = format.parse(data.getString("date_finished"));
        }catch (Exception ex){}
        //todo frequency // reminder_frequency
        //todo parent_goal

        ExtendedCursor subGoalsCursor = Database.Instance.executeWithResult("Select * from " + this.getTableName() + " where parent_goal = " + this.getID());

        while(subGoalsCursor.moveToNext())
        {
            Goal subGoal = new Goal(null);
            subGoal.loadFromCursor(subGoalsCursor);
            //todo SubGoal
            //this.addSubGoal(subGoal);
        }
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
