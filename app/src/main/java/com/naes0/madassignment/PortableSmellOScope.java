package com.naes0.madassignment;

import android.content.Context;
import android.content.Intent;

public class PortableSmellOScope extends Equipment
{
    public PortableSmellOScope(String desc, int value, double mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }

    @Override
    public void use(Context c)
    {
        c.startActivity(new Intent(c, SOSActivity.class));
        //start new activity of item of up to 2 squares away;
    }

    @Override
    public String getStringType()
    {
        return "PortableSmellOScope";
    }
}
