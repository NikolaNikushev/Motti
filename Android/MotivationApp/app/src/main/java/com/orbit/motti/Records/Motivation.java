package com.orbit.motti.Records;

import android.database.Cursor;

import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

/**
 * Created by Vader on 13/02/2016.
 */
public class Motivation extends Record {
    private int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    private String description;
    public String getDescription(){return this.description;}

    private boolean isPositive;
    public boolean getIsPositive() {return isPositive;}

    private String image;
    public String getImage() {return image;}

    private String type;
    public String getType(){return type;}

    private String addiction;
    public String getAddiction(){return addiction;}

    @Override
    public String getTableName() {
        return "Motivation";
    }

    @Override
    public void loadFromCursor(ExtendedCursor data) {
        this.setID(data.getInt("id"));
        this.description = data.getString("description");
        this.image = data.getString("image");
//todo is it int ??
        this.isPositive = data.getBoolean("is_positive");
        this.type = data.getString("motivation_type");
        this.addiction = data.getString("addiction");
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "id") {
            @Override
            public Object getValue() {
                return ((Motivation)this.getRecord()).getID();
            }

            @Override
            public void setValue(Object value) {
                ((Motivation)this.getRecord()).setID((int) value);
            }
        };
    }
}
