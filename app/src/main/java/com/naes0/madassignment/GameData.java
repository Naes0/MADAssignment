package com.naes0.madassignment;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Random;

public class GameData implements Serializable
{
    public static final int WIDTH = 20;
    public static final int HEIGHT = 10;

    private Area grid[][];
    private Player player;
    private static List<Item> itemList;

    private static Random rand = new Random();
    private static GameData instance = null;

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
        this.player = new Player();
        this.itemList = itemListSet();
        this.grid = generateGrid();
    }

    public void regenerate()
    {
        grid = generateGrid();
    }

    public static Area[][] generateGrid()
    {
        Area[][] grid = new Area[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                grid[i][j] = new Area(generateItemList());
                Log.d("TOWN", "grid[" + i + "][" + j + "] =" + grid[i][j].printItemList());
            }
        }
        grid[0][0].setExplored(true);
        return grid;
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
        //food
        itemList.add(new Food("Apple", 5, 5 ));
        itemList.add(new Food("Mango", 10, 15 ));
        itemList.add(new Food("Durian", 20, 35 ));
        //items
        itemList.add(new Equipment("Boots of Swiftness", 20, 5 ));
        itemList.add(new Equipment("BF Sword", 15, 25 ));
        //usable items
        itemList.add(new PortableSmellOScope("Portable Smell-O-Scope", 25, 5 ));
        itemList.add(new ImprobDrive("Improbability Drive", 10, (int) -Math.PI));
        itemList.add(new BenKenobi("BenKenobi", 50, 20 ));
        //winning items
        itemList.add(new Equipment("Jade Monkey", 50, 7 ));
        itemList.add(new Equipment("Roadmap", 50, 3 ));
        itemList.add(new Equipment("Ice Scraper", 50, 12 ));

        return itemList;
    }

    public static List<Item> generateItemList()
    {
        List<Item> areaItemList = new ArrayList<Item>();
        int sizeOfList = rand.nextInt(itemList.size()-3 + 1);
        for(int i = 0; i < sizeOfList; i++)
        {
            areaItemList.add(getRandomItem());
        }
        return areaItemList;
    }

    //weighted item chances
    public static Item getRandomItem()
    {
        Item item = null;
        double randomDouble = rand.nextDouble() * 0.5;
        if(randomDouble <= 0.15)
        {
            item = itemList.get(0);//apple
        }
        if( randomDouble > 0.15 && randomDouble <= 0.25)
        {
            item = itemList.get(1);//Mango
        }
        if( randomDouble > 0.25 && randomDouble <= 0.30)
        {
            item = itemList.get(2);//Durian
        }
        if( randomDouble > 0.30 && randomDouble <= 0.35)
        {
            item = itemList.get(3);//boots
        }
        if( randomDouble > 0.35 && randomDouble <= 0.40)
        {
            item = itemList.get(4);//bf sword
        }
        if( randomDouble > 0.40 && randomDouble <= 0.45)
        {
            item = itemList.get(5);//p smelloscope
        }
        if( randomDouble > 0.45 && randomDouble <= 0.475)
        {
            item = itemList.get(6);//improbability drive
        }
        if( randomDouble > 0.475 && randomDouble <= 0.50)
        {
            item = itemList.get(7);//benkenobi
        }
        return item;
    }

    public Player getPlayer()
    {
        return player;
    }
    public Area[][] getGrid()
    {
        return grid;
    }
}
