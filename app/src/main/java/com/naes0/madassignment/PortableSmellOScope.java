package com.naes0.madassignment;


public class PortableSmellOScope extends Equipment
{
    public PortableSmellOScope(String desc, int value, int mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }
    @Override
    public void use()
    {
        //start new activity of item of up to 2 squares away;
    }
}
