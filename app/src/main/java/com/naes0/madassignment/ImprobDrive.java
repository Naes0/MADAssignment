package com.naes0.madassignment;


public class ImprobDrive extends Equipment
{
    public ImprobDrive(String desc, int value, int mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }


    @Override
    public void use()
    {
        GameData data = GameData.get();
        data.regenerate();
        //regenerate map and items, player keeps items.
    }
}
