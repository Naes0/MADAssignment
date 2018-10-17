package com.naes0.madassignment;


import android.content.Context;

public class Equipment extends Item
{
    public final int id;
    private double mass;
    private boolean usable;
    private int nextId = 0;

    public Equipment(int id, String desc, int value, double mass)
    {
        this.id = id;
        this.usable = false;
        this.mass = mass;
        super.setDesc(desc);
        super.setValue(value);
        nextId = id + 1;
    }

    public Equipment(String desc, int value, double mass)
    {
        this(super.getNextId(), desc, value, mass);
        nextId++;
    }

    @Override
    public double getMassOrHealth()
    {
        return mass;
    }

    @Override
    public String getStringType()
    {
        return "Equipment";
    }

    @Override
    public String getStringHealthMass()
    {
        return "Mass: ";
    }

    @Override
    public void use(Context c)
    {
    }

    public boolean isUsable()
    {
        return usable;
    }

    public void setUsable(Boolean boo)
    {
        usable = boo;
    }
}
