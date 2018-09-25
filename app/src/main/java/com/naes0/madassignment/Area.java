package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Area implements Parcelable
{
    private boolean town;
    private List<Item> itemlist;
    private String description;
    private boolean starred;
    private boolean explored;

    public Area(boolean town)
    {
        this.town = town;
        itemlist = new ArrayList<Item>();
        description = "";
        starred = false;
        explored = false;
    }

    protected Area(Parcel in)
    {
        town = in.readByte() != 0;
        itemlist = in.readArrayList(Item.class.getClassLoader());
    }

    public static final Creator<Area> CREATOR = new Creator<Area>()
    {
        @Override
        public Area createFromParcel(Parcel in)
        {
            return new Area(in);
        }

        @Override
        public Area[] newArray(int size)
        {
            return new Area[size];
        }
    };

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setStarred(boolean boo)
    {
        starred = boo;
    }

    public void setExplored(boolean boo)
    {
        explored = boo;
    }

    public boolean isTown()
    {
        return town;
    }

    public void setTown(boolean town)
    {
        this.town = town;
    }

    public void addItem(Item item)
    {
        itemlist.add(item);
    }

    public List<Item> getItemList()
    {
        return itemlist;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeByte((byte) (town ? 1 : 0));
        parcel.writeList(itemlist);
    }
}
