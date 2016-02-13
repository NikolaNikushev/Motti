package com.orbit.motti.Records;

import android.database.Cursor;

import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

/**
 * Created by Vader on 13/02/2016.
 */
public class AgeGroup extends Record {
    private String name;
    public String getName(){return this.name;}

    public AgeGroup(String name){
        this.name = name;
    }

    @Override
    public String getTableName() {
        return "Age_Group";
    }

    @Override
    protected void createFromDatabase(Cursor data) {
        this.name = data.getString(0);
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "name") {
            @Override
            public Object getValue() {
                return ((AgeGroup)this.getRecord()).getName();
            }
            @Override
            public void setValue(Object value) {
                ((AgeGroup) this.getRecord()).setName((String) value);
            }
        };
    }

    public void setName(String name) {
        this.name = name;
    }
}
