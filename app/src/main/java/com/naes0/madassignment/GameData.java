package com.naes0.madassignment;

import java.util.ArrayList;
import java.util.List;

public class GameData
{
    public static final int WIDTH = 30;
    public static final int HEIGHT = 10;

    private Area grid[][];
    private Player player;
    private List<Item> itemList;

    private static GameData instance = null;

    public static GameData get()
    {
        if (instance == null)
        {
            instance = new GameData(generateGrid());
        }
        return instance;
    }

    public GameData(Area[][] grid)
    {
        this.grid = grid;
        this.player = new Player();
        this.itemList = itemListSet();
    }

    private static Area[][] generateGrid()
    {

    }

    public Area getArea(int x, int y)
    {
        return grid[x][y];
    }
    public void setArea(int x, int y, Area area)
    {
        grid[x][y] = area;
    }

    public List<Item> itemListSet()
    {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Food() )
        return itemList
    }
    public Player getPlayer()
    {
        return player;
    }
}
