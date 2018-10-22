package com.naes0.madassignment;

import java.util.List;
import java.util.Random;

public class Area
{
    public final int id;
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
    private String itemNames;
    private static int nextId = 0;

    public Area(int id, List<Item> itemList, int northWest, int northEast, int southWest, int southEast)
    {
        this.id = id;
        town = randTown(); //25% chance of being town
        description = "";
        starred = false;
        explored = false;
        this.itemList = itemList;
        itemNames = setStringItems();
        this.terrainNorthWest = northWest;
        this.terrainNorthEast = northEast;
        this.terrainSouthWest = southWest;
        this.terrainSouthEast = southEast;
        this.structure = createStructure();
        this.unExplored = createUnexplored();
        this.unStarred = createUnstarred();
        nextId = id + 1;
    }

    public Area(int id, boolean town, String description, boolean starred, boolean explored,
                List<Item> items, int northWest, int northEast, int southWest, int southEast)
    {
        this.id = id;
        this.town = town;
        this.description = description;
        this.starred = starred;
        this.explored = explored;
        this.itemList = items;
        itemNames = setStringItems();
        this.terrainNorthWest = northWest;
        this.terrainNorthEast = northEast;
        this.terrainSouthWest = southWest;
        this.terrainSouthEast = southEast;
        this.structure = createStructure();
        this.unExplored = createUnexplored();
        this.unStarred = createUnstarred();
        nextId = id + 1;
    }

    public Area(List<Item> itemList, int northWest, int northEast, int southWest, int southEast)
    {
        this(nextId, itemList, northWest, northEast, southWest, southEast);
        nextId++;
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

    public int createStructure()
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

    public int createUnexplored()
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

    public int createUnstarred()
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

    public int getId()
    {
        return  id;
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
        unStarred = createUnstarred();
    }

    public void setItemNames(String s)
    {
        this.itemNames = s;
    }

    public void setItemList(List<Item> itemList)
    {
        this.itemList = itemList;
    }

    public boolean getStarred()
    {
        return starred;
    }

    public void setExplored(boolean boo)
    {
        explored = boo;
        unExplored = createUnexplored();
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
        setItemNames(setStringItems());
    }

    public void removeItem(Item item)
    {
        itemList.remove(item);
        setItemNames(setStringItems());
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

    public String getItemNames()
    {
        return  itemNames;
    }

    public String setStringItems()
    {
        String s = "";
        for(Item item : itemList)
        {
            if(item != null)
            {
                s += item.getDesc() + ",";
            }
        }
        return s;
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
