package com.naes0.madassignment;

import android.content.Context;

public abstract class Item
{
    private String desc;
    private int value;
    private int postion;

    public void setPosition(int position)
    {
        this.postion = position;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }

    public int getValue()
    {
        return value;
    }

    //returns mass or health dependent on type.
    public abstract double getMassOrHealth();
    //returns the type in string form.
    public abstract String getStringType();
    //returns mass or health in string form;
    public abstract String getStringHealthMass();
    //use method to easily call one method for all the usable items
    // with each use method in the usable items having a different
    // implementation.
    public abstract void use(Context c);
}