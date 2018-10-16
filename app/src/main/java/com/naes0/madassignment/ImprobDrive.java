package com.naes0.madassignment;


import android.content.Context;

public class ImprobDrive extends Equipment
{
    public ImprobDrive(String desc, int value, double mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }

    @Override
    public void use(Context c)
    {
        GameData data = GameData.get();
        data.regenerate();
        //regenerate map and items, player keeps items.
    }
}
