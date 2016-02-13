package com.orbit.motti.Records;

import android.database.Cursor;

import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

/**
 * Created by Vader on 13/02/2016.
 */
public class Motivation extends Record {
    private String description;
    public String getDescription(){return this.description;}

    private boolean isPositive;
    public boolean getIsPositive() {return isPositive;}

    private String image;
    public String getImage() {return image;}

    @Override
    public String getTableName() {
        return "Motivation";
    }

    @Override
    protected void createFromDatabase(Cursor data) {
        int descriptionIndex = data.getColumnIndex("description");
        this.description = data.getString(descriptionIndex);

        int imageIndex = data.getColumnIndex("image");
        this.image = data.getString(imageIndex);
//todo is it int ??
        int isPositiveIndex = data.getColumnIndex("is_positive");
        this.isPositive = data.getInt(isPositiveIndex) == 0;
    }

    @Override
    protected void copyFrom(Record data) {
        Motivation copy = (Motivation)data;
        this.description = copy.description;
        this.image = copy.image;
        this.isPositive = copy.isPositive;
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "description") {
            @Override
            public Object getValue() {
                return ((Motivation)this.getRecord()).getDescription();
            }

            @Override
            public void setValue(Object value) {
                ((Motivation)this.getRecord()).setDescription((String) value);
            }
        };
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
