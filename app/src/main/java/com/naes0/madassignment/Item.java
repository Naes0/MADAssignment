package com.naes0.madassignment;

import android.content.Context;

public abstract class Item
{
    private String desc;
    private int value;
    private static int nextId = 0;

    public int getNextId()
    {
        return nextId;
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

    public abstract double getMassOrHealth();
    public abstract String getStringType();
    public abstract String getStringHealthMass();
    public abstract void use(Context c);
}