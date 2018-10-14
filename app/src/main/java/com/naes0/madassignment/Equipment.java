package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment extends Item implements Parcelable
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

    protected Equipment(Parcel in)
    {
        usable = in.
        mass = in.readDouble();
        super.setDesc(in.readString());
        super.setValue(in.readInt());
    }

    public static final Creator<Equipment> CREATOR = new Creator<Equipment>()
    {
        @Override
        public Equipment createFromParcel(Parcel in)
        {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size)
        {
            return new Equipment[size];
        }
    };

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

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeDouble(mass);
        super.writeToParcel(parcel, i);
    }
}
