package com.naes0.madassignment;

import android.content.Context;

import java.util.List;

public class BenKenobi extends Equipment
{
    public BenKenobi(String desc, int value, int mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }

    @Override
    public void use(Context c)
    {
        GameData data = GameData.get();
        Player player = data.getPlayer();
        Area currArea = data.getCurrArea();
        List<Item> itemList = currArea.getitemList();
        for (Item item : itemList)
        {
            player.addItemNoCash(item);
        }
        itemList.clear();
    }

}
