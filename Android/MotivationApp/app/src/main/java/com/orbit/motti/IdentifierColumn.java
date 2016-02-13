package com.orbit.motti;

/**
 * Created by Vader on 13/02/2016.
 */
public abstract class IdentifierColumn {

    private String name;
    private  Record record;
    public  Record getRecord(){return  this.record;};

    public  IdentifierColumn(Record record, String name) {
        this.record = record;
        this.name = name;
    }

    public  abstract Object getValue();

    public String getName(){return this.name;}
}
