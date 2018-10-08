package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Usable extends Equipment implements Parcelable
{
    public Usable(String desc, int value, int mass)
    {
        super(desc, value, mass);
    }

    protected Usable(Parcel in)
    {
        super(in);
    }

    public abstract void use();
}
