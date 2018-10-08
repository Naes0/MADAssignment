package com.naes0.madassignment;


import android.os.Parcel;
import android.os.Parcelable;

public class Food extends Item implements Parcelable
{
    private double health;

    public Food(String desc, int value, int health)
    {
        this.health = health;
        super.setDesc(desc);
        super.setValue(value);
    }

    protected Food(Parcel in)
    {
        health = in.readDouble();
        super.setDesc(in.readString());
        super.setValue(in.readInt());
    }

    public static final Creator<Food> CREATOR = new Creator<Food>()
    {
        @Override
        public Food createFromParcel(Parcel in)
        {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size)
        {
            return new Food[size];
        }
    };

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
        parcel.writeDouble(health);
        super.writeToParcel(parcel, i);
    }
}
