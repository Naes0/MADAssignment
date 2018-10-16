package com.naes0.madassignment;

import java.util.List;
import java.util.Random;

public class Area
{
    private boolean town;
    private String description;
    private boolean starred;
    private boolean explored;
    private List<Item> itemList;

    private final int terrainNorthWest;
    private final int terrainSouthWest;
    private final int terrainNorthEast;
    private final int terrainSouthEast;
    private final int structure;
    private int unExplored;
    private int unStarred;

    public Area(List<Item> itemList, int northWest, int northEast,
                int southWest, int southEast)
    {
        town = randTown(); //25% chance of being town
        description = "";
        starred = false;
        explored = false;
        this.itemList = itemList;

        this.terrainNorthWest = northWest;
        this.terrainNorthEast = northEast;
        this.terrainSouthWest = southWest;
        this.terrainSouthEast = southEast;
        this.structure = setStructure();
        this.unExplored = setUnexplored();
        this.unStarred = setUnstarred();
    }

    public boolean randTown()
    {
        boolean isTown = false;
        Random rand = new Random();
        if (rand.nextDouble() <= 0.25)
        {
            isTown = true;
        }
        return isTown;
    }

    public int setStructure()
    {
        int x;
        if (isTown())
        {
            x = R.drawable.ic_building7;
        }
        else
        {
            x = R.drawable.ic_tree4;
        }
        return x;
    }

    public int setUnexplored()
    {
        int x;
        if (isExplored())
        {
            x = 0;
        }
        else
        {
            x = R.color.black;
        }
        return x;
    }

    public int setUnstarred()
    {
        int x;
        if (isStarred())
        {
            x = R.drawable.star;
        }
        else
        {
            x = 0;
        }
        return x;
    }

    public void setitemList(List<Item> list)
    {
        this.itemList = list;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setStarred(boolean boo)
    {
        starred = boo;
        unStarred = setUnstarred();
    }

    public boolean getStarred()
    {
        return starred;
    }

    public void setExplored(boolean boo)
    {
        explored = boo;
        unExplored = setUnexplored();
    }

    public boolean isStarred()
    {
        return starred;
    }

    public boolean isExplored()
    {
        return explored;
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
        itemList.add(item);
    }

    public void removeItem(Item item)
    {
        itemList.remove(item);
    }

    public List<Item> getitemList()
    {
        return itemList;
    }

    public int getNorthWest()
    {
        return terrainNorthWest;
    }

    public int getUnexplored()
    {
        return unExplored;
    }

    public int getUnstarred()
    {
        return unStarred;
    }

    public int getSouthWest()
    {
        return terrainSouthWest;
    }

    public int getNorthEast()
    {
        return terrainNorthEast;
    }

    public int getSouthEast()
    {
        return terrainSouthEast;
    }

    public int getStructure()
    {
        return  structure;
    }

    public String printTown()
    {
        if (isTown())
        {
            return "town";
        }
        else
        {
            return "wilderness";
        }
    }
    
    public String printItemList()
    {
        String itemListstring = "";
        for(Item item : itemList)
        {
            itemListstring = itemListstring + "\n        " + item.getDesc();
        }
        return itemListstring;
    }
}
