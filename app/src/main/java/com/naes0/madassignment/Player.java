package com.naes0.madassignment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Player implements Parcelable
{
    private int row;
    private int col;
    private int cash;
    private double health;
    private double equipMass;
    private List<Equipment> equipmentlist;
    private static final double MAX_HEALTH = 100.0;

    public Player()
    {
        this.row = 0;
        this.col = 0;
        this.cash = 0;
        this.health = MAX_HEALTH;
        this.equipMass = 0.0;
        this.equipmentlist = new ArrayList<Equipment>();
    }

    protected Player(Parcel in)
    {
        row = in.readInt();
        col = in.readInt();
        cash = in.readInt();
        health = in.readDouble();
        equipMass = in.readDouble();
        equipmentlist = in.readArrayList(Equipment.class.getClassLoader());
    }

    public static final Creator<Player> CREATOR = new Creator<Player>()
    {
        @Override
        public Player createFromParcel(Parcel in)
        {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size)
        {
            return new Player[size];
        }
    };

    public int getRow()
    {
        return row;
    }

    public void addRow(int row)
    {
        this.row += row;
    }

    public int getCol()
    {
        return col;
    }

    public void addCol(int col)
    {
        this.col += col;
    }

    public int getCash()
    {
        return cash;
    }

    public void addCash(int cash)
    {
        this.cash += cash;
    }

    public double getHealth()
    {
        return health;
    }

    public void addHealth(double health)
    {
        if (this.health + health >= 100.0)
        {
            this.health = 100.0;
        }
        else
        {
            this.health += health;
        }
    }

    public void setHealth(double health)
    {
        this.health = health;
    }

    public double getEquipMass()
    {
        return equipMass;
    }

    public void addEquipMass(double equipMass)
    {
        this.equipMass += equipMass;
    }

    public String getPos()
    {
        return ("[" + row + "]" + "[" + col + "]");
    }

    public List<Equipment> getEquipmentlist()
    {
        return equipmentlist;
    }

    public void addEquipment(Equipment equip)
    {
        equipmentlist.add(equip);
        addEquipMass(equip.getMassOrHealth());
    }

    public void removeEquipment(Equipment equip)
    {
        equipmentlist.remove(equip);
        addEquipMass(-equip.getMassOrHealth());
    }

    public void decreaseHealth() throws DecreaseHealthException
    {
        setHealth(Math.max(0.0, health - 5.0 - (equipMass / 2.0)));
        if (health <= 0.0)
        {
            throw new DecreaseHealthException("Health is 0");
        }
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(row);
        parcel.writeInt(col);
        parcel.writeInt(cash);
        parcel.writeDouble(health);
        parcel.writeDouble(equipMass);
        parcel.writeList(equipmentlist);
    }
}
