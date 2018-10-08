package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class PortableSmellOScope extends Equipment implements Parcelable
{
    public PortableSmellOScope(String desc, int value, int mass)
    {
        super(desc, value, mass);
    }

    protected PortableSmellOScope(Parcel in)
    {
        super(in);
    }

    @Override
    public void use()
    {
        //start new activity of item of up to 2 squares away;
    }
}
