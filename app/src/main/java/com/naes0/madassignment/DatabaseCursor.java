package com.naes0.madassignment;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.naes0.madassignment.DatabaseSchema.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCursor extends CursorWrapper
{
    private List<Item> itemList;

    public DatabaseCursor(Cursor cursor, List<Item> itemList)
    {
        super(cursor);
        this.itemList = itemList;
    }

    //player stores a String list of item names and references them for the database
    //grabs all the fields from the player
    public Player getPlayer()
    {
        int id = getInt(getColumnIndex(PlayerTable.Cols.ID));
        int row = getInt(getColumnIndex(PlayerTable.Cols.ROW));
        int col = getInt(getColumnIndex(PlayerTable.Cols.COL));
        int cash = getInt(getColumnIndex(PlayerTable.Cols.CASH));
        double health = getDouble(getColumnIndex(PlayerTable.Cols.HEALTH));
        double equipMass = getDouble(getColumnIndex(PlayerTable.Cols.EQUIPMASS));
        String equipment = getString(getColumnIndex(PlayerTable.Cols.EQUIPMENT));
        String[] parts = equipment.split(",");      //split the string on "," to get the item names in a array.
        List<Equipment> equipmentList = new ArrayList<Equipment>();  //creating list to put in player
        for(int i = 0; i < parts.length; i++)
        {
            if(!parts[i].equals(",") && !parts[i].equals(""))// if parts[] contains empty strings or "," skip them.
            {
                Equipment e = getEquipment(parts[i]); //get equipment for each item name from the parts array
                if( e != null)
                {
                    equipmentList.add(e);
                }
            }
        }
        Player player = new Player(id);
        player.setRow(row);
        player.setCol(col);
        player.setCash(cash);
        player.setHealth(health);
        player.setEquipMass(equipMass);
        player.setEquipmentList(equipmentList);
        String s = player.createEquipment();
        player.setEquipment(s);
        return player;
    }

    public Equipment getEquipment(String name)
    {
        Equipment equip = null;
        for(Item item : itemList)
        {
            if(item.getDesc().equals(name))
            {
                equip = (Equipment) item;
            }
        }
        return equip;
    }

    public Item getItem(String name)
    {
        Item i = null;
        for(Item item : itemList)
        {
            if(item.getDesc().equals(name))
            {
                i = item;
            }
        }
        return i;
    }

    // creates area based on fields obtained from the database
    // gets the items in a similar way to player where its stored as a string
    // and then use to get the correct item from a list and add it to the area.
    public Area getArea()
    {
        int id = getInt(getColumnIndex(AreaTable.Cols.ID));
        boolean town = getInt(getColumnIndex(AreaTable.Cols.TOWN)) > 0;
        String desc = getString(getColumnIndex(AreaTable.Cols.DESC));
        boolean starred = getInt(getColumnIndex(AreaTable.Cols.STARRED)) > 0;
        boolean explored = getInt(getColumnIndex(AreaTable.Cols.EXPLORED)) > 0;
        String itemNames = getString(getColumnIndex(AreaTable.Cols.ITEMS));
        String[] parts = itemNames.split(",");
        List<Item> items = new ArrayList<Item>();
        for(int i = 0; i < parts.length; i++)
        {
            if(!parts[i].equals(",") && !parts[i].equals(""))
            {
                Item item = getItem(parts[i]);

                    items.add(item);

            }
        }
        Area area = new Area(id, town, desc, starred, explored, items,
                R.drawable.ic_grass1, R.drawable.ic_grass3,R.drawable.ic_grass2, R.drawable.ic_grass4);
        return area;
    }
}
