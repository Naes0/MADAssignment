package com.naes0.madassignment;

public class GameData
{
    private Area grid[][];
    private Player player;
    private static GameData instance;

    public static GameData get()
    {
        if (instance == null)
        {
            instance = new GameData();
        }
        return instance;
    }

    public GameData()
    {
        grid = new Area[3][3];
        grid[0][0] = new Area(false);
        grid[0][0].setExplored(true);
        grid[0][1] = new Area(true);
        grid[0][1].addItem(new Food("Apple", 5, 10));
        grid[0][1].addItem(new Equipment("Necklace", 25, 5));
        grid[0][2] = new Area(false);
        grid[1][0] = new Area(false);
        grid[1][0].addItem(new Food("Apple", 5, 10));
        grid[1][1] = new Area(true);
        grid[1][2] = new Area(false);
        grid[2][0] = new Area(false);
        grid[2][0].addItem(new Equipment("Boots", 6, 2));
        grid[2][1] = new Area(true);
        grid[2][2] = new Area(true);
        grid[2][2].addItem(new Food("Mango", 10, 25));
        grid[2][2].addItem(new Equipment("Boots", 6, 2));
    }

    public Area getArea(int x, int y)
    {
        return grid[x][y];
    }
    public void setArea(int x, int y, Area area)
    {
        grid[x][y] = area;
    }
}
