package com.naes0.madassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
            instance = new GameData(c); //create instance of gamedata
            if(!instance.dbContainsPlayer()) //if the database contains a player do not add player to the db
            {
                instance.playerToDB();
            }
            if(!instance.dbContainsGrid()) //if the database contains the grid do not add the grid to the db.
            {
                instance.gridToDB();
            }
        }
        instance.load();// load memory from grid


        /*String s = instance.getTableAsString("DB", AreaTable.NAME);
        Log.d("DB", s);
        String s2 = instance.getTableAsString("DB2", PlayerTable.NAME);
        Log.d("DB2", s2);*/
        return instance;
    }

    //database contains an instance of player
    public boolean dbContainsPlayer()
    {
        boolean boo = false;
        DatabaseCursor cursor = new DatabaseCursor(db.query(PlayerTable.NAME, null, null, null, null ,null,null), itemList);
        if (cursor.getCount() > 0)
        {
            boo = true;
        }
        return boo;
    }

    //database contiatns grid entries already
    public boolean dbContainsGrid()
    {
        boolean boo = false;
        DatabaseCursor cursor = new DatabaseCursor(db.query(AreaTable.NAME, null, null, null, null ,null,null), itemList);
        if (cursor.getCount() > 0)
        {
            boo = true;
        }
        return boo;
    }

    //used to see the output of the tables in the database given a table name.
    public String getTableAsString(String TAG, String tableName)
    {
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() )
        {
            String[] columnNames = allRows.getColumnNames();
            for(int i = 0; i < 1; i++)
            {
                for (String name: columnNames)
                {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";
                allRows.moveToNext();
            } //while (allRows.moveToNext());
        }
        return tableString;
    }

    public GameData(Context c)
    {
        this.db = new DatabaseDbHelper(c.getApplicationContext()).getWritableDatabase();
        this.player = new Player();
        this.itemList = itemListSet();
        this.winningItemList = winningItemListSet();
        this.grid = generateGrid(this.player);
    }

    // clears area table generates grid again and stores it in the db.
    // used for improbability drive.
    public void regenerate()
    {
        clearDatabase(AreaTable.NAME);
        grid = generateGrid(player);
        gridToDB();
    }

    // hard resets everything from database and instance.
    // used for the reset button.
    public void reset()
    {
        clearDatabase(PlayerTable.NAME);
        clearDatabase(AreaTable.NAME);
        player = new Player();
        playerToDB();
        grid = generateGrid(player);
        gridToDB();
    }

    public void clearDatabase(String TABLE_NAME)
    {
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    //genereate grid for the size of HEIGHT and WIDTH
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
        grid[player.getRow()][player.getCol()].setExplored(true);
        setRandomWinningItems(grid, player);
        /*for(int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                instance.addArea(grid[i][j]);
                //Log.d("TOWN", "grid[" + i + "][" + j + "] =" + grid[i][j].printItemList());
            }
        }*/
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

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    // item list stores items
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
        //winning items
        itemList.add(new Equipment("Jade Monkey", 10, 7 ));
        itemList.add(new Equipment("Roadmap", 10, 3 ));
        itemList.add(new Equipment("Ice Scraper", 10, 12 ));
        return itemList;
    }

    // list of all the winning items
    public List<Item> winningItemListSet()
    {
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(new Equipment("Jade Monkey", 10, 7 ));
        itemList.add(new Equipment("Roadmap", 10, 3 ));
        itemList.add(new Equipment("Ice Scraper", 10, 12 ));
        return itemList;
    }

    //generates a random item list
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
        // multiply by 0.55 to make it the random number be between 0.0 and 0.55 which is needed
        // to pick an item through on of the if's.
        double randomDouble = rand.nextDouble() * 0.55;
        if(randomDouble <= 0.15) //30%
        {
            item = itemList.get(0);//apple
        }
        if( randomDouble > 0.15 && randomDouble <= 0.25) //10%
        {
            item = itemList.get(1);//Mango
        }
        if( randomDouble > 0.25 && randomDouble <= 0.30) //5%
        {
            item = itemList.get(2);//Durian
        }
        if( randomDouble > 0.50 && randomDouble <= 0.55) //5%
        {
            item = itemList.get(3);//broccoli
        }
        if( randomDouble > 0.30 && randomDouble <= 0.35) //5%
        {
            item = itemList.get(4);//boots
        }
        if( randomDouble > 0.35 && randomDouble <= 0.40) //5%
        {
            item = itemList.get(5);//bf sword
        }
        if( randomDouble > 0.40 && randomDouble <= 0.45) //5%
        {
            item = itemList.get(6);//p-smelloscope
        }
        if( randomDouble > 0.45 && randomDouble <= 0.475) //2.5%
        {
            item = itemList.get(7);//improbability drive
        }
        if( randomDouble > 0.475 && randomDouble <= 0.50) //
        {
            item = itemList.get(8);//benkenobi
        }
        return item;
    }

    public void gridToDB()
    {
        for(int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                instance.addArea(grid[i][j]);
            }
        }
    }

    public void playerToDB()
    {
        instance.addPlayer(player);
    }

    public void load()
    {
        loadPlayer();
        loadArea();
    }

    //gets the first entry from player table and loads it to the instance.
    public void loadPlayer()
    {
        DatabaseCursor cursor = new DatabaseCursor(db.query(PlayerTable.NAME, null, null, null, null ,null,null), itemList);
        try
        {
            cursor.moveToFirst();
            instance.setPlayer(cursor.getPlayer());
        }
        finally
        {
            cursor.close();
        }
    }

    // gets the areas and loads it to the instances grid.
    public void loadArea()
    {
        DatabaseCursor cursor = new DatabaseCursor(db.query(AreaTable.NAME, null, null, null, null ,null,null), itemList);
        try
        {
            cursor.moveToFirst();
                for(int i = 0; i < HEIGHT; i++)
                {
                    for (int j = 0; j < WIDTH; j++)
                    {
                        instance.setArea(i, j, cursor.getArea());
                        cursor.moveToNext();
                    }
                }
        }
        finally
        {
            cursor.close();
        }
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
        cv.put(PlayerTable.Cols.EQUIPMENT, player.getEquipment());
        db.insert(PlayerTable.NAME, null, cv);
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
        cv.put(AreaTable.Cols.ITEMS, area.getItemNames());
        db.insert(AreaTable.NAME, null, cv);
    }

    public void updatePlayer(Player player)
    {
        ContentValues cv = new ContentValues();
        cv.put(PlayerTable.Cols.ID, player.getId());
        cv.put(PlayerTable.Cols.ROW, player.getRow());
        cv.put(PlayerTable.Cols.COL, player.getCol());
        cv.put(PlayerTable.Cols.CASH, player.getCash());
        cv.put(PlayerTable.Cols.HEALTH, player.getHealth());
        cv.put(PlayerTable.Cols.EQUIPMASS, player.getEquipMass());
        cv.put(PlayerTable.Cols.EQUIPMENT, player.getEquipment());
        String[] whereValue = {};
        db.update(PlayerTable.NAME, cv, PlayerTable.Cols.ID + " = " + player.getId(), whereValue);
    }

    public void updateArea(Area area)
    {
        ContentValues cv = new ContentValues();
        cv.put(AreaTable.Cols.ID, area.getId());
        cv.put(AreaTable.Cols.TOWN, area.isTown());
        cv.put(AreaTable.Cols.DESC, area.getDescription());
        cv.put(AreaTable.Cols.STARRED, area.getStarred());
        cv.put(AreaTable.Cols.EXPLORED, area.isExplored());
        cv.put(AreaTable.Cols.UNEXP, area.getUnexplored());
        cv.put(AreaTable.Cols.UNSTAR, area.getUnstarred());
        cv.put(AreaTable.Cols.ITEMS, area.getItemNames());
        String[] whereValue = {};
        db.update(AreaTable.NAME, cv, AreaTable.Cols.ID + " = " + area.getId(), whereValue);
    }

    public Player getPlayer()
    {
        return player;
    }
}
