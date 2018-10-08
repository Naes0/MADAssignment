package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class BenKenobi extends Equipment implements Parcelable
{
    public BenKenobi(String desc, int value, int mass)
    {
        super(desc, value, mass);
    }

    protected BenKenobi(Parcel in)
    {
        super(in);
    }

    @Override
    public void use()
    {
        //get all items in town and wilderness free.
    }
}
