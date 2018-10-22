package com.naes0.madassignment;


import android.content.Context;

public class Equipment extends Item
{
    private double mass;
    private boolean usable;

    public Equipment(String desc, int value, double mass)
    {
        this.usable = false;
        this.mass = mass;
        super.setDesc(desc);
        super.setValue(value);
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

    // used to determine whether the equipment
    public boolean isUsable()
    {
        return usable;
    }

    // set an item to be usable or unusable.
    public void setUsable(Boolean boo)
    {
        usable = boo;
    }
}
