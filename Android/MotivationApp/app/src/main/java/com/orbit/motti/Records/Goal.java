package com.orbit.motti.Records;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.text.method.DateTimeKeyListener;

import com.orbit.motti.Database;
import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

import java.io.InvalidClassException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Preslav Gerchev on 13.2.2016 г..
 */
public class Goal extends Record implements Parcelable {

    private static  List<Goal> goalCache = new ArrayList<>();


    private String goalTitle;
    private List<SubGoal> subGoals=new ArrayList<>();
    private String goalDescription;

    private Date dateFrom=new Date();
    private Date dateTo=new Date();
    private Date dateFinished=new Date(12,20,2012);

    private int reminderDaysSpan;

    private String addiction;
    public String getAddiction(){return this.addiction;}

    public String getDateFromStr(){
        return new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).format(dateFrom);
    }

    public String getDateToStr(){
        return new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).format(dateTo);
    }

    public Date getDateFrom(){
        return dateFrom;
    }

    public Date getDateTo(){
        return dateTo;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public boolean isFinished(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFinished);
        int a = calendar.get(Calendar.YEAR);

        return dateFinished != null && a> 2015;}
    public int daysPeriod(){
        return (int)((dateTo.getTime()-dateFrom.getTime()) / DateUtils.DAY_IN_MILLIS);
    }
    public int progress(){
        int done = 0;
        for (int i = 0; i < subGoals.size();i++){
            if(subGoals.get(i).isFinished())
                done += 1;
        }

        return (int)(100 * ((float)done/subGoals.size()));
    }

    static int currentId = 0;
    private int ID = ++currentId;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Goal(){

    }
    public Goal(String goalTitle, String goalDescription, int reminderDaysSpan, int goalPeriodDays,String adddictType) {
        this.goalTitle = goalTitle;
        this.goalDescription = goalDescription;
        this.subGoals = new ArrayList<>();
        this.reminderDaysSpan = reminderDaysSpan;
        this.dateFrom = new Date();
        this.dateTo = new Date(dateFrom.getTime() + DateUtils.DAY_IN_MILLIS * goalPeriodDays);
        goalCache.add(this);
        this.addiction = adddictType;
    }

    protected Goal(Parcel in) {
        reminderDaysSpan = in.readInt();
        dateFrom = new Date(in.readLong());
        dateTo = new Date(in.readLong());
        dateFinished = new Date(in.readLong());
        goalTitle = in.readString();
        addiction = in.readString();
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
        return daysPeriod();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reminderDaysSpan);
        dest.writeLong(dateFrom.getTime());
        dest.writeLong(dateTo.getTime());
        dest.writeLong(dateFinished.getTime());
        dest.writeString(goalTitle);
        dest.writeString(addiction);

        dest.writeString(goalDescription);

        dest.writeList(subGoals);
    }

    @Override
    public String getTableName() {
        return "Goal";
    }

    @Override
    public void loadFromCursor(ExtendedCursor data) {
        this.setID(data.getInt("id"));
        reminderDaysSpan = data.getInt("reminder_frequency");
        goalDescription = data.getString("description");
        this.reminderDaysSpan = data.getInt("reminder_frequency");
        this.addiction = data.getString("addiction_type");
        this.goalTitle = data.getString("title");


        //todo check format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        try {
            this.dateFrom = format.parse(data.getString("date_from"));
        }catch (Exception ex){}
        try {
            this.dateTo = format.parse(data.getString("date_to"));
        }catch (Exception ex){}
        try {
            this.dateFinished = format.parse(data.getString("date_finished"));
        }catch (Exception ex){}

        SubGoal subGoal = new SubGoal(null,0);

        ExtendedCursor subGoalsCursor = Database.Instance.executeWithResult("Select * from " + subGoal.getTableName() + " where parent_goal = " + this.getID());

        while(subGoalsCursor.moveToNext())
        {
            subGoal = new SubGoal(null,0);
            subGoal.loadFromCursor(subGoalsCursor);
            this.addSubGoal(subGoal);
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

    public static Goal FindOrCreate(int id) {
        for (Goal i : goalCache) {
            if(i.ID == id && i.getIsCreated())
                return i;
        }
        Goal g = new Goal(null,null,0,0,"");
        g.setID(id);
        try {
            g.loadFromDatabase();
        }catch (InvalidClassException ex){}
        return g;
    }
}
