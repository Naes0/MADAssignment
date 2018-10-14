package com.naes0.madassignment;

import android.content.Context;

public class Food extends Item
{
    private double health;

    public Food(String desc, int value, int health)
    {
        this.health = health;
        super.setDesc(desc);
        super.setValue(value);
    }

    @Override
    public double getMassOrHealth()
    {
        return health;
    }

    @Override
    public String getStringType()
    {
        return "Food";
    }

    @Override
    public String getStringHealthMass()
    {
        return "Health: ";
    }

    @Override
    public void use(Context c)
    {

    }
}
