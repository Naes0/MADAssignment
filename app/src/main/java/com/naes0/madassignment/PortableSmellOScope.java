package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class PortableSmellOScope extends Usable implements Parcelable
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

    }
}
