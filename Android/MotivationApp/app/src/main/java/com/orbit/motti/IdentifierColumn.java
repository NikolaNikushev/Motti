package com.orbit.motti;

import java.util.Objects;

/**
 * Created by Vader on 13/02/2016.
 */
public abstract class IdentifierColumn {

    private String name;
    private  Record record;
    public  Record getRecord(){return  this.record;}

    public  IdentifierColumn(Record record, String name) {
        this.record = record;
        this.name = name;
    }

    public abstract Object getValue();
    public abstract void setValue(Object value);

    public String getName(){return this.name;}

    public String forQuery() {
        Object value = this.getValue();
        if(value instanceof String)
            value = "'" + value.toString().replace("'","''") + "'";
        return this.name + " = " + value.toString();
    }


}
