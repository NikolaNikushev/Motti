package com.orbit.motti.Records;

import com.orbit.motti.Database;
import com.orbit.motti.Goal;
import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vader on 13/02/2016.
 */
public class Profile extends Record {
    private String username = null;

    public List<String> addictions = new ArrayList<>();

    public String getUsername() {
        return this.username;
    }

    private int credits = -1;

    public int getCredits() {
        return this.credits;
    }

    public ArrayList<Goal> goals = new ArrayList<Goal>();

    public Profile(String name) {
        this.username = name;
    }

    @Override
    public String getTableName() {
        return "Profile";
    }

    @Override
    public void loadFromCursor(ExtendedCursor data) {
        //todo check if case sensitive
        this.username = data.getString("username");

        this.credits = data.getInt("credits");

        this.goals.clear();
        Goal g = new Goal(null,null,0,0);
        ExtendedCursor records = Database.Instance.executeWithResult("Select * from " + g.getTableName() + " where profile = '" + this.username +"'");

        while (records.moveToNext()) {
            g = new Goal(null,null,0,0);
            g.loadFromCursor(records);
            this.goals.add(g);
            if(!this.addictions.contains(g.getAddiction()))
                this.addictions.add(g.getAddiction());
        }
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "username") {
            @Override
            public Object getValue() {
                return ((Profile) this.getRecord()).getUsername();
            }

            @Override
            public void setValue(Object value) {
                ((Profile) this.getRecord()).setUsername((String) value);
            }
        };
    }

    public void setUsername(String username) {
        this.username = username;
    }
}