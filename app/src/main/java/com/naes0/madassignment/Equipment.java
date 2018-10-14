package com.naes0.madassignment;


public class Equipment extends Item
{
    private double mass;
    private boolean usable;

    public Equipment(String desc, int value, int mass)
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
    public void use()
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
