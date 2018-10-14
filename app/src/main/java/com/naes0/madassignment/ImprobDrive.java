package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class ImprobDrive extends Equipment implements Parcelable
{
    public ImprobDrive(String desc, int value, int mass)
    {
        super(desc, value, mass);
    }

    protected ImprobDrive(Parcel in)
    {
        super(in);
    }

    @Override
    public void use()
    {
        GameData data = GameData.get();
        data.regenerate();
        //regenerate map and items, player keeps items.
    }
}
