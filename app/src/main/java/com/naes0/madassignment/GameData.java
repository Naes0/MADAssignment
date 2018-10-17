package com.naes0.madassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.naes0.madassignment.DatabaseSchema.*;

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
    private static List<Item> winningItemList;
    private SQLiteDatabase db;

    private static Random rand = new Random();
    private static GameData instance = null;

    public static GameData get(Context c)
    {
        if (instance == null)
        {
            instance = new GameData(c);
        }
        return instance;
    }

    public GameData(Context c)
    {
        this.player = new Player();
        this.itemList = itemListSet();
        this.winningItemList = winningItemListSet();
        this.grid = generateGrid(this.player);
        this.db = new DatabaseDbHelper(c.getApplicationContext()).getWritableDatabase();
    }

    public void regenerate()
    {
        grid = generateGrid(player);
    }

    public void reset()
    {
        regenerate();
        player = new Player();
    }

    public static Area[][] generateGrid(Player player)
    {
        Area[][] grid = new Area[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                grid[i][j] = new Area(generateItemList(), R.drawable.ic_grass1, R.drawable.ic_grass3,R.drawable.ic_grass2, R.drawable.ic_grass4);
            }
        }
        grid[0][0].setExplored(true);
        setRandomWinningItems(grid, player);
        for(int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                Log.d("TOWN", "grid[" + i + "][" + j + "] =" + grid[i][j].printItemList());
            }
        }
        return grid;
    }

    public Area getCurrArea()
    {
        return grid[player.getRow()][player.getCol()];
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
        itemList.add(new Food("Broccoli",5, -10));
        //items
        itemList.add(new Equipment("Boots of Swiftness", 20, 5 ));
        itemList.add(new Equipment("BF Sword", 15, 25 ));
        //usable items
        itemList.add(new PortableSmellOScope("Portable Smell-O-Scope", 25, 5 ));
        itemList.add(new ImprobDrive("Improbability Drive", 10, Math.floor(-Math.PI*100)/100));
        itemList.add(new BenKenobi("BenKenobi", 50, 20 ));

        return itemList;
    }

    public List<Item> winningItemListSet()
    {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Equipment("Jade Monkey", 10, 7 ));
        itemList.add(new Equipment("Roadmap", 10, 3 ));
        itemList.add(new Equipment("Ice Scraper", 10, 12 ));
        return itemList;
    }

    public static List<Item> generateItemList()
    {
        List<Item> areaItemList = new ArrayList<Item>();
        int sizeOfList = rand.nextInt(itemList.size() + 1);
        for(int i = 0; i < sizeOfList; i++)
        {
            areaItemList.add(getRandomItem());
        }
        return areaItemList;
    }

    //ensures that only one of each of the winning items is always on the map.
    public static void setRandomWinningItems(Area[][] grid, Player player)
    {
        for(int i = 0; i < 3; i++)
        {
            int randomRow = rand.nextInt(HEIGHT);
            int randomCol = rand.nextInt(WIDTH);
            Boolean boo = !player.contains(winningItemList.get(i));
            if(boo)
            {
                grid[randomRow][randomCol].addItem(winningItemList.get(i));
            }
        }
    }

    //weighted item chances
    public static Item getRandomItem()
    {
        Item item = null;
        double randomDouble = rand.nextDouble() * 0.55;
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
        if( randomDouble > 0.50 && randomDouble <= 0.55)
        {
            item = itemList.get(3);//broccoli
        }
        if( randomDouble > 0.30 && randomDouble <= 0.35)
        {
            item = itemList.get(4);//boots
        }
        if( randomDouble > 0.35 && randomDouble <= 0.40)
        {
            item = itemList.get(5);//bf sword
        }
        if( randomDouble > 0.40 && randomDouble <= 0.45)
        {
            item = itemList.get(6);//p-smelloscope
        }
        if( randomDouble > 0.45 && randomDouble <= 0.475)
        {
            item = itemList.get(7);//improbability drive
        }
        if( randomDouble > 0.475 && randomDouble <= 0.50)
        {
            item = itemList.get(8);//benkenobi
        }
        return item;
    }

    public void addPlayer(Player player)
    {
        ContentValues cv = new ContentValues();
        cv.put(PlayerTable.Cols.ID, player.getId());
        cv.put(PlayerTable.Cols.ROW, player.getRow());
        cv.put(PlayerTable.Cols.COL, player.getCol());
        cv.put(PlayerTable.Cols.CASH, player.getCash());
        cv.put(PlayerTable.Cols.HEALTH, player.getHealth());
        cv.put(PlayerTable.Cols.EQUIPMASS, player.getEquipMass());
        db.insert(AreaTable.NAME, null, cv);
    }

    public void addArea(Area area)
    {
        ContentValues cv = new ContentValues();
        cv.put(AreaTable.Cols.ID, area.getId());
        cv.put(AreaTable.Cols.TOWN, area.isTown());
        cv.put(AreaTable.Cols.DESC, area.getDescription());
        cv.put(AreaTable.Cols.STARRED, area.getStarred());
        cv.put(AreaTable.Cols.EXPLORED, area.isExplored());
        cv.put(AreaTable.Cols.UNEXP, area.getUnexplored());
        cv.put(AreaTable.Cols.UNSTAR, area.getUnstarred());
        db.insert(AreaTable.NAME, null, cv);
    }

    public void addItem(Item item)
    {
        ContentValues cv = new ContentValues();
        cv.put(ItemTable.Cols.ID, item.getID());
        cv.put(ItemTable.Cols.DESC, item.getDesc());
        cv.put(ItemTable.Cols.VALUE, item.getValue());
        cv.put(ItemTable.Cols.TYPE, item.getStringType());
        cv.put(ItemTable.Cols.USE, item.getStringType());
        db.insert(ItemTable.NAME, null, cv);
    }


    public Player getPlayer()
    {
        return player;
    }
}
