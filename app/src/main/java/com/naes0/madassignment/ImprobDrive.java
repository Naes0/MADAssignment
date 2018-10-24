package com.naes0.madassignment;


import android.content.Context;
import android.content.Intent;

public class ImprobDrive extends Equipment
{
    public ImprobDrive(String desc, int value, double mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }

    //calls the gamedata regenerate method
    @Override
    public void use(Context c)
    {
        GameData data = GameData.get(c);
        data.regenerate();
        c.startActivity(new Intent(c, NavigationActivity.class));
        //regenerate map and items, player keeps items.
    }

    @Override
    public String getStringType()
    {
        return "ImprobDrive";
    }
}
