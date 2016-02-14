package com.orbit.motti.Records;

import android.os.Parcel;
import android.os.Parcelable;

import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class SubGoal extends Record implements Parcelable {

    private  String subGoalTitle;
    private int subGoalTimePeriod;
    private boolean isFinished;
    static int currentId = 0;
    private int ID = ++currentId;
    public int getID(){return ID;}
    public void setID(int value){this.ID = value;}

    public SubGoal(String goalTitle, int subGoalTimePeriod) {
        this.subGoalTimePeriod = subGoalTimePeriod;
        this.subGoalTitle = goalTitle;
        this.isFinished = false;
    }

    protected SubGoal(Parcel in) {
        subGoalTimePeriod = in.readInt();
        subGoalTitle = in.readString();
        isFinished = in.readByte() != 0;
    }

    public static final Creator<SubGoal> CREATOR = new Creator<SubGoal>() {
        @Override
        public SubGoal createFromParcel(Parcel in) {
            return new SubGoal(in);
        }

        @Override
        public SubGoal[] newArray(int size) {
            return new SubGoal[size];
        }
    };

    public String getSubGoalTitle() {
        return subGoalTitle;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subGoalTimePeriod);
        dest.writeString(subGoalTitle);
        dest.writeByte((byte) (isFinished ? 1 : 0));
    }

    @Override
    public String getTableName() {
        return "Sub_Goal";
    }

    @Override
    protected void loadFromCursor(ExtendedCursor data) {
        this.setID(data.getInt("id"));
        subGoalTimePeriod = data.getInt("time_period");
        subGoalTitle = data.getString("title");
        isFinished = data.getBoolean("is_finished");
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "id") {
            @Override
            public Object getValue() {
                return ((SubGoal)this.getRecord()).getID();
            }

            @Override
            public void setValue(Object value) {
                ((SubGoal)this.getRecord()).setID((int)value);
            }
        };
    }
}
