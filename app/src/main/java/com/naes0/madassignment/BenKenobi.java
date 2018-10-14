package com.naes0.madassignment;

public class BenKenobi extends Equipment
{
    public BenKenobi(String desc, int value, int mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }

    @Override
    public void use()
    {
        //get all items in town and wilderness free.
    }

}
