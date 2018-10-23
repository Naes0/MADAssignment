package com.naes0.madassignment;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    public final int id;
    private int row;
    private int col;
    private int cash;
    private double health;
    private double equipMass;
    private List<Equipment> equipmentlist;
    private String equipment;
    private static final double MAX_HEALTH = 100.0;
    private static int nextId;

    public Player(int id)
    {
        this.id = id;
        this.row = 0;
        this.col = 0;
        this.cash = 50;
        this.health = MAX_HEALTH;
        this.equipMass = 0.0;
        this.equipmentlist = new ArrayList<Equipment>();
        this.equipment = createEquipment();
        nextId = id + 1;
    }

    public Player()
    {
        this(nextId);
        nextId++;
    }

    //returns string of item names separated by commas to store items for areas in the database
    public String createEquipment()
    {
        String s = "";
        for(Equipment e : equipmentlist)
        {
            if(e != null)
            {
                s += e.getDesc() + ",";
            }
        }
        return s;
    }

    public void setEquipment(String equipment)
    {
        this.equipment = equipment;
    }

    public void setEquipmentList(List<Equipment> equipmentList)
    {
        this.equipmentlist = equipmentList;
    }


    public void setRow(int i)
    {
        row = i;
    }

    public void setCol(int j)
    {
        col = j;
    }

    public void setCash(int money)
    {
        cash = money;
    }

    public void setEquipMass(double equipMass)
    {
        this.equipMass = equipMass;
    }

    public String getEquipment()
    {
        return equipment;
    }

    public int getId()
    {
        return id;
    }

    public int getRow()
    {
        return row;
    }

    public void addRow(int row)
    {
        this.row += row;
    }

    public int getCol()
    {
        return col;
    }

    public void addCol(int col)
    {
        this.col += col;
    }

    public int getCash()
    {
        return cash;
    }

    public void addCash(int cash)
    {
        this.cash += cash;
    }

    public double getHealth()
    {
        return health;
    }

    public void addHealth(double health)
    {
        if (this.health + health >= 100.0)
        {
            this.health = 100.0;
        }
        else
        {
            this.health += health;
        }
    }

    public void setHealth(double health)
    {
        this.health = health;
    }

    public double getEquipMass()
    {
        return equipMass;
    }

    public void addEquipMass(double equipMass)
    {
        this.equipMass += equipMass;
    }

    public String getPos()
    {
        return ("[" + row + "]" + "[" + col + "]");
    }

    public List<Equipment> getEquipmentlist()
    {
        return equipmentlist;
    }

    // adds equipment, updates mass, updates the String version of he equipment list.
    //
    public void addEquipment(Equipment equip) throws WinException
    {
        equipmentlist.add(equip);
        addEquipMass(equip.getMassOrHealth());
        setEquipment(createEquipment());
        //win check if the player contains these items throw a win exception.
        boolean sword = false;
        boolean shield = false;
        boolean necklace = false;
        for(Equipment eq : equipmentlist)
        {
            if(!sword)
            {
                sword = eq.getDesc().equals("Jade Monkey");
            }
            if(!shield)
            {
                shield = eq.getDesc().equals("Roadmap");
            }
            if(!necklace)
            {
                necklace = eq.getDesc().equals("Ice Scraper");
            }
        }
        if(sword && shield && necklace)
        {
            throw new WinException("You got the secret items!\n               You Win!");
        }
    }

    // if the player has enough money to purchase the item it adds it to the player, decreases cash
    // returns true if the purchase is successful and false if it isn't.
    public boolean addItem(Item buyItem) throws WinException
    {
        boolean boo = false;
        if (cash >= buyItem.getValue())
        {
            boo = true;
            addCash(-buyItem.getValue());
            if (buyItem instanceof Equipment)
            {
                addEquipment((Equipment) buyItem);
            }
            else
            {
                addHealth(buyItem.getMassOrHealth());
            }
        }
        return boo;
    }

    // the items get added to the player regardless
    public void addItemNoCash(Item buyItem) throws WinException
    {
        if (buyItem instanceof Equipment)
        {
            addEquipment((Equipment) buyItem);
        }
        else
        {
            addHealth(buyItem.getMassOrHealth());
        }
    }

    // removes equipment from player
    public void removeEquipment(Equipment equip)
    {
        equipmentlist.remove(equip);
        addEquipMass(-equip.getMassOrHealth());
        setEquipment(createEquipment());
    }

    // decrease the health, if its 0 or < 0 then throw dead exception, if it causes health to go
    // above 100 then set it too 100.0. (occurs if you have 4-5 improbability drives due to -mass)
    public void decreaseHealth() throws DeadException
    {
        setHealth(Math.max(0.0, health - 5.0 - (equipMass / 2.0)));
        if (health <= 0.0)
        {
            throw new DeadException("You lost all your health!\n            You Lose!");
        }
        else if ( health > 100.0)
        {
            setHealth(100.0);
        }
    }

    public boolean contains(Item item)
    {
        boolean boo = false;
        for (Equipment e : equipmentlist)
        {
            if(e != null && e.getDesc().equals(item.getDesc()))
            {
                boo = true;
            }
        }
        return boo;
    }
}
