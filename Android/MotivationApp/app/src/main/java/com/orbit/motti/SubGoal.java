package com.orbit.motti;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class SubGoal implements Parcelable {

    private  String subGoalTitle;
    private int subGoalTimePeriod;
    private boolean isFinished;

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
}
