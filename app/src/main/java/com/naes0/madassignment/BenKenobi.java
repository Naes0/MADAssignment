package com.naes0.madassignment;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

public class BenKenobi extends Equipment
{
    public BenKenobi(String desc, int value, double mass)
    {
        super(desc, value, mass);
        setUsable(true);
    }

    @Override
    public void use(Context c)
    {
        GameData data = GameData.get(c);
        Player player = data.getPlayer();
        Area currArea = data.getCurrArea();
        List<Item> itemList = currArea.getitemList();
        for (Item item : itemList)
        {
            try
            {
                player.addItemNoCash(item);
            }
            catch(WinException e)
            {
                Toast toast = Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                data.reset();
                c.startActivity(new Intent(c, MainActivity.class));
            }
        }
        itemList.clear();
    }

    @Override
    public String getStringType()
    {
        return "BenKenobi";
    }

}
